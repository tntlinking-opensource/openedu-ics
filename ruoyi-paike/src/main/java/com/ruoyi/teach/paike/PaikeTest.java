package com.ruoyi.teach.paike;

import com.ruoyi.teach.paike.entity.*;
import com.ruoyi.teach.util.ChineseNumberComparator;

import java.util.*;

public class PaikeTest {

    private TimeConfig timeConfig;
    private List<CourseConfig> courseConfigs;
    private List<AdminclassConfig> adminclassConfigs;
    private List<TeacherConfig> teacherConfigs;
    private List<LessonInfo> lessonInfos;
    private List<Adminclass> adminclasses;
    private List<Teacher> teachers;
    private List<ScheduleTask> lessonTasks;

    public static void main(String[] args) {
        new PaikeTest().run();
    }

    public void run() {
        // 作息时间
        this.timeConfig = PaikeUtil.getTimeConfig();
        // 课程不排课时间
        this.courseConfigs = PaikeUtil.getCourseConfigs();
        // 班级不排课时间
        this.adminclassConfigs = PaikeUtil.getAdminclassConfigs();
        // 教师不排课时间
        this.teacherConfigs = PaikeUtil.getTeacherConfigs();
        // 任课信息
        this.lessonInfos = PaikeUtil.getLessonInfos();
        this.adminclasses = getAdminclasses();
        this.teachers = getTeachers();
        this.lessonTasks = getLessonTasks();
        setLessonTasksTime();
        arranging();
        printLessonTask();
    }

    private ScheduleTask getLessonTask(Adminclass adminclass, int weekday, int timeOfDay) {
        long time = 1;
        time = time << weekday * timeConfig.getTimeOfDay();
        time = time << timeOfDay;
        long ftime = time;
        return lessonTasks.stream().filter(t -> t.getInfo().getAdminclassName().equals(adminclass.getName())
                && (t.getTime() & ftime) == ftime).findAny().orElse(null);
    }

    private void arranging() {
        List<ScheduleTask> lessonTasks = new ArrayList<>(this.lessonTasks);
        int timeLength = timeConfig.getTimeLength();
        do {
            sortLessonTasks(lessonTasks);
            ScheduleTask task = lessonTasks.remove(0);
            Teacher teacher = getTeacher(task);
            Adminclass adminclass = getAdminclass(adminclasses, task);
            long adminClassTime = adminclass.getTime() & task.getTimeSuggest() & task.getTimeAvailable();
            long needTime = ~((~0) << task.getHour());
            if (adminClassTime == 0) {
                // 没有可用时间，使用班级时间
                adminClassTime = adminclass.getTime() & task.getTimeAvailable();
            }
            for (int i = 0; i < timeLength; i++) {
                if ((adminClassTime & needTime) == needTime) {
                    long taskTime = needTime << i;
                    task.setTime(taskTime);
                    task.setTimeIndex(i);
//                    System.out.println(Long.toBinaryString(adminclass.getTime()));
                    adminClassTime = adminclass.getTime() & (~taskTime);
                    adminclass.setTime(adminClassTime);
//                    System.out.println(Long.toBinaryString(task.getTime()));
//                    System.out.println(Long.toBinaryString(adminclass.getTime()));
                    break;
                }
                adminClassTime = adminClassTime >> 1;
            }
            if (task.getTime() == 0) {
                System.out.println(task);
                System.out.println(Long.toBinaryString(task.getTimeAvailable()));
                System.out.println(Long.toBinaryString(adminclass.getTime()));
            }
//            System.out.println(Long.toBinaryString(teacher.getTime()));
            teacher.setTime(teacher.getTime() & (~task.getTime()));
//            System.out.println(Long.toBinaryString(teacher.getTime()));
            resetTimeAvaliable(lessonTasks, teacher, adminclass);
        } while (!lessonTasks.isEmpty());
    }

    private static void resetTimeAvaliable(List<ScheduleTask> lessonTasks, Teacher teacher, Adminclass adminclass) {
        // 同步班级时间
        lessonTasks.stream().filter(t -> t.getInfo().getAdminclassName().equals(adminclass.getName()))
                .forEach(t -> t.setTimeAvailable(t.getTimeAvailable() & adminclass.getTime()));
        // 同步教师时间
        lessonTasks.stream().filter(t -> t.getInfo().getTeacherName().equals(teacher.getName()))
                .forEach(t -> t.setTimeAvailable(t.getTimeAvailable() & teacher.getTime()));
    }

    private void printLessonTask() {
        for (Adminclass adminclass : adminclasses) {
            System.out.println();
            System.out.println(adminclass.getName());
            System.out.println("===========================================================================");
            System.out.print("\t");
            for (int i = 0; i < timeConfig.getWeekdays(); i++) {
                System.out.print("星期" + (i + 1));
                System.out.print("\t\t\t");
            }
            System.out.println();
            for (int i = 0; i < timeConfig.getTimeOfDay(); i++) {
                if (i == 4) {
                    System.out.println("  ------------------------------------------------------------------------");
                }
                System.out.print(i + 1);
                System.out.print("\t");
                for (int j = 0; j < timeConfig.getWeekdays(); j++) {
                    ScheduleTask task = getLessonTask(adminclass, j, i);
                    if (task != null) {
                        System.out.print(task.getInfo().getCourseName().substring(0, 2));
                        System.out.print("-");
                        System.out.print(task.getInfo().getTeacherName().substring(0, 2));
                    }
                    System.out.print("\t\t");
                }
                System.out.println();
            }
        }
//        for (LessonTask task : lessonTasks){
//            System.out.println(task.getInfo().getCourseName() + ": " + task.getTimeIndex() + ":" + Long.toBinaryString(task.getTime()));
//            System.out.println(task);
//        }
    }

    private static Adminclass getAdminclass(List<Adminclass> adminclasses, ScheduleTask task) {
        return adminclasses.stream()
                .filter(a -> a.getName().equals(task.getInfo().getAdminclassName()))
                .findAny().get();
    }

    private Teacher getTeacher(ScheduleTask task) {
        return teachers.stream()
                .filter(a -> a.getName().equals(task.getInfo().getTeacherName()))
                .findAny().get();
    }

    private List<Adminclass> getAdminclasses() {
        List<Adminclass> list = new ArrayList<>();
        Set<String> names = new HashSet<>();
        for (LessonInfo info : lessonInfos) {
            if (names.contains(info.getAdminclassName())) {
                continue;
            }
            names.add(info.getAdminclassName());
            Adminclass adminclass = new Adminclass();
            adminclass.setName(info.getAdminclassName());
            adminclass.setTime(timeConfig.getTimeOfWeek());
            // 班级不排课时间
            AdminclassConfig config = getAdminclassConfig(adminclass.getName());
            adminclass.setTime(adminclass.getTime() & config.getTime());
            list.add(adminclass);
        }
        ChineseNumberComparator comparator = new ChineseNumberComparator();
        list.sort((o1, o2) -> comparator.compare(o1.getName(), o2.getName()));
        return list;
    }

    private AdminclassConfig getAdminclassConfig(String name) {
        Optional<AdminclassConfig> any = adminclassConfigs.stream().filter(c -> c.getName().equals(name)).findAny();
        if (any.isPresent()) {
            return any.get();
        }
        AdminclassConfig config = new AdminclassConfig(name, ~0);
//        adminclassConfigs.add(config);
        return config;
    }

    private List<Teacher> getTeachers() {
        List<Teacher> list = new ArrayList<>();
        Set<String> names = new HashSet<>();
        for (LessonInfo info : lessonInfos) {
            if (names.contains(info.getTeacherName())) {
                continue;
            }
            names.add(info.getTeacherName());
            Teacher adminclass = new Teacher();
            adminclass.setName(info.getTeacherName());
            adminclass.setTime(timeConfig.getTimeOfWeek());
            list.add(adminclass);
        }
        ChineseNumberComparator comparator = new ChineseNumberComparator();
        list.sort((o1, o2) -> comparator.compare(o1.getName(), o2.getName()));
        return list;
    }


    private List<ScheduleTask> getLessonTasks() {
        List<ScheduleTask> list = new ArrayList<>();
        int weekdays = timeConfig.getWeekdays();
        for (LessonInfo info : lessonInfos) {
            double hours = info.getLessonHour();
            for (int i = 0; i < weekdays; i++) {
                int hour = (int) Math.round(hours / (weekdays - i));
                if (hour == 0) {
                    continue;
                }
                hours -= hour;
                ScheduleTask task = new ScheduleTask();
                task.setInfo(info);
//                task.setWeekday(i);
                task.setHour(hour);
                list.add(task);
            }
        }
        return list;
    }

    private void setLessonTasksTime() {
        long day = 0;
        int hourOfDay = timeConfig.getTimeOfDay();
        for (int i = 0; i < hourOfDay; i++) {
            day = (day << 1) + 1;
        }
        Random random = new Random();
        for (ScheduleTask task : lessonTasks) {
            long taskTime = day;
            // 随机第一节课
            taskTime = taskTime & (~(0L | (random.nextInt(10) % 2)));
//            System.out.println(Long.toBinaryString(taskTime));
            for (int i = 0; i < task.getWeekday(); i++) {
                taskTime = taskTime << hourOfDay;
            }
            task.setTimeAvailable(~0);
            AdminclassConfig adminclassConfig = getAdminclassConfig(task.getInfo().getAdminclassName());
            CourseConfig courseConfig = getCourseConfig(task.getInfo().getCourseName());
            TeacherConfig teacherConfig = getTeacherConfig(task.getInfo().getTeacherName());
            task.setTimeAvailable(task.getTimeAvailable() & adminclassConfig.getTime());
            task.setTimeAvailable(task.getTimeAvailable() & courseConfig.getTime());
            task.setTimeAvailable(task.getTimeAvailable() & teacherConfig.getTime());
            task.setTimeSuggest(taskTime);
        }
    }

    private CourseConfig getCourseConfig(String courseName) {
        Optional<CourseConfig> any = courseConfigs.stream().filter(c -> c.getName().equals(courseName)).findAny();
        if (any.isPresent()) {
            return any.get();
        }
        CourseConfig config = new CourseConfig(courseName, ~0);
        courseConfigs.add(config);
        return config;
    }

    private TeacherConfig getTeacherConfig(String name) {
        Optional<TeacherConfig> any = teacherConfigs.stream().filter(c -> c.getName().equals(name)).findAny();
        if (any.isPresent()) {
            return any.get();
        }
        TeacherConfig config = new TeacherConfig(name, ~0);
        return config;
    }

    private void sortLessonTasks(List<ScheduleTask> lessonTasks) {
        lessonTasks.forEach(t -> {
            int weight = t.getHour() * 10000;
            weight += (timeConfig.getTimeOfDay() - PaikeUtil.getOneCount(t.getTimeSuggest())) * 100;
            weight += (timeConfig.getTimeLength() - PaikeUtil.getOneCount(t.getTimeAvailable()));
            t.setWeight(weight);
        });
        lessonTasks.sort(Comparator.comparingInt(ScheduleTask::getWeight).reversed());
    }
}
