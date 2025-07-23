package com.ruoyi.teach.paike.service.schedule;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.teach.paike.PaikeUtil;
import com.ruoyi.teach.paike.domain.*;
import com.ruoyi.teach.paike.entity.*;
import com.ruoyi.teach.paike.service.*;
import com.ruoyi.teach.util.ChineseNumberComparator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.ruoyi.teach.paike.util.LessonPlanScheduleUtil.transformTimes;

@Slf4j
@Service
public class LessonPlanScheduleServiceImpl implements LessonPlanScheduleService {

    @Autowired
    private LessonScheduleService lessonScheduleService;
    @Autowired
    private LessonScheduleTimeService timeService;
    @Autowired
    private LessonPlanService lessonPlanService;
    @Autowired
    private LessonScheduleRuleService ruleService;
    @Autowired
    private List<LessonPlanScheduleListener> listeners;
    @Autowired
    private LessonScheduleConverter converter;
    @Autowired
    private ClassroomSpecialService classroomSpecialService;

    private Random random = new Random();

    @Override
    public ScheduleData getScheduleDataBySchoolId(Long schoolId) {
        LessonSchedule lessonSchedule = lessonScheduleService.getBySchoolId(schoolId);
        ScheduleData data = getScheduleData(lessonSchedule.getId());
        data.setSchoolId(schoolId);
        return data;
    }

    @Override
    public ScheduleData getScheduleData(Long lessonScheduleId) {
        return getScheduleData(lessonScheduleId, null);
    }

    public ScheduleData getScheduleData(Long lessonScheduleId, List<LessonPlan> lessonPlans) {
        ScheduleData data = new ScheduleData(lessonScheduleId);
        data.setTime(timeService.getByLessonScheduleId(lessonScheduleId));
        data.setRules(getScheduleRules(data));
        data.setRooms(getClassroomSpecials(data));
        if (lessonPlans == null) {
            lessonPlans = lessonPlanService.getByLessonScheduleId(lessonScheduleId);
        }
        List<SchedulePlan> plans = getSchedulePlan(data, lessonPlans);
        data.setPlans(plans);
        data.getPlans().forEach(p -> {
            p.setId(p.getPlans().get(0).getId());
            p.setConfigTimes(PaikeUtil.toBinaryString(p.getTimes(), data.getTimeLength()));
            p.getPlans().forEach(plan -> p.setPlanTimes(plan.getTimes()));
        });
        return data;
    }

    @Override
    public void autoSchedule(LessonSchedule lessonSchedule, List<LessonPlan> lessonPlans) {
        // 没有教师的任课信息不参与排课
//        lessonPlans = lessonPlans.stream().filter(p -> StringUtils.isNotBlank(p.getTeacher())).collect(Collectors.toList());
        ScheduleData data = getScheduleData(lessonSchedule.getId(), lessonPlans);
        data.setAdminclasses(getAdminclasses(data));
        data.setTeachers(getTeachers(data));
        data.setCourses(getCourses(data));
        initSchedulePlanTimes(data);
        data.setTasks(getLessonTasks(data));
        setLessonTasksTime(data);
        scheduling(data);
        setLessonPlanTimes(data, lessonPlans);
//        printScheduleData(data);
//        lessonPlans.forEach(plan -> System.out.println(plan.getAdminclass() + plan.getCourse() + ":" + plan.getHour() + ":" + plan.getTimes()));
    }

    public List<ScheduleRule> getScheduleRules(ScheduleData data) {
        List<LessonScheduleRule> scheduleRules = ruleService.getByLessonScheduleId(data.getScheduleId());
        List<ScheduleRule> rules = converter.toScheduleRuleList(scheduleRules);
        rules.forEach(rule -> {
            rule.setData(JSON.parseObject(rule.getJson()));
            String times = rule.get("times");
            if (StringUtils.isNotBlank(times)) {
                rule.setTimes(transformTimes(data, times));
            }
        });
        return rules;
    }

    private List<ScheduleClassroom> getClassroomSpecials(ScheduleData data) {
        List<ClassroomSpecial> rooms = classroomSpecialService.getByScheduleId(data.getScheduleId());
        List<ScheduleClassroom> rules = converter.toScheduleClassroomList(rooms);
        rules.forEach(rule -> {
            if (StringUtils.isNotBlank(rule.getCourse())) {
                rule.setCourses(Arrays.asList(rule.getCourse().split("、")));
            }
            if (StringUtils.isNotBlank(rule.getGrade())) {
                rule.setGrades(Arrays.asList(rule.getGrade().split("、")));
            }
            if (StringUtils.isNotBlank(rule.getAdminclass())) {
                rule.setAdminclasses(Arrays.asList(rule.getAdminclass().split("、")));
            }
        });
        return rules;
    }

    private List<SchedulePlan> getSchedulePlan(ScheduleData data, List<LessonPlan> lessonPlans) {
        List<SchedulePlan> plans = new ArrayList<>();
        for (LessonPlan lessonPlan : lessonPlans) {
            SchedulePlan plan = new SchedulePlan();
            plan.addLessonPlan(lessonPlan);
            plans.add(plan);
        }
        for (LessonPlanScheduleListener listener : listeners) {
            listener.initSchedulePlan(data, plans);
        }
        return plans;
    }

    private Map<String, Adminclass> getAdminclasses(ScheduleData data) {
        Map<String, Adminclass> map = new HashMap<>();
        for (SchedulePlan plan : data.getPlans()) {
            plan.getPlans().forEach(p -> {
                String adminclassName = p.getAdminclass();
                if (map.containsKey(adminclassName)) {
                    return;
                }
                Adminclass adminclass = new Adminclass();
                adminclass.setName(adminclassName);
                long time = getObjectTime(data, adminclass);
                adminclass.setTime(time);
                map.put(adminclassName, adminclass);
            });
        }
        return map;
    }

    private long getObjectTime(ScheduleData data, Object obj) {
        long time = data.getTimeOfWeek();
        for (LessonPlanScheduleListener listener : listeners) {
            time = listener.getTime(data, obj, time);
        }
        return time;
    }


    private Map<String, Teacher> getTeachers(ScheduleData data) {
        Map<String, Teacher> map = new HashMap<>();
        for (SchedulePlan plan : data.getPlans()) {
            plan.getPlans().forEach(p -> {
                String teacherName = p.getTeacher();
                if(StringUtils.isBlank(teacherName)){
                    return;
                }
                if (map.containsKey(teacherName)) {
                    return;
                }
                Teacher teacher = new Teacher();
                teacher.setName(teacherName);
                long time = getObjectTime(data, teacher);
                teacher.setTime(time);
                map.put(teacherName, teacher);
            });
        }
        return map;
    }

    private Map<String, Course> getCourses(ScheduleData data) {
        Map<String, Course> map = new HashMap<>();
        for (SchedulePlan plan : data.getPlans()) {
            plan.getPlans().forEach(p -> {
                String courseName = p.getGrade() + p.getCourse();
                if (map.containsKey(courseName)) {
                    return;
                }
                Course course = new Course();
                course.setName(courseName);
                long time = getObjectTime(data, course);
                course.setTime(time);
                map.put(courseName, course);
            });
        }
        return map;
    }

    private void initSchedulePlanTimes(ScheduleData data) {
        data.getPlans().forEach(plan -> {
            plan.setTimes(getObjectTime(data, plan));
        });
    }

    private List<ScheduleTask> getLessonTasks(ScheduleData data) {
        List<ScheduleTask> list = new ArrayList<>();
        Random random = new Random();
        for (SchedulePlan plan : data.getPlans()) {
//            if("一年级托管".equals(plan.getCourse())){
//                System.out.println(plan);
//            }
            List<Integer> weekdays = getSchedulePlanWeekdays(data, plan);
            List<Integer> hours = getSchedulePlanHours(weekdays.size(), plan);
            while (!weekdays.isEmpty() && !hours.isEmpty()) {
                int days = 1;
                if (weekdays.size() > hours.size()) {
                    if (hours.size() == 1) {
                        days = weekdays.size();
                    } else {
                        double avg = 1.0 * weekdays.size() / hours.size();
                        days = (int) avg;
                        if (avg > days && random.nextDouble() >= 0.5) {
                            days++;
                        }
                    }
                }
                int weekdayStart = weekdays.remove(0);
                int weekdayEnd = weekdayStart;
                while (days > 1) {
                    weekdayEnd = weekdays.remove(0);
                    days--;
                }
                int hour = hours.remove(0);
                ScheduleTask task = new ScheduleTask();
                task.setPlan(plan);
                task.setWeekdayStart(weekdayStart);
                task.setWeekdayEnd(weekdayEnd);
                task.setHour(hour);
                list.add(task);
            }
        }
        return list;
    }

    private List<Integer> getSchedulePlanWeekdays(ScheduleData data, SchedulePlan plan) {
        String times = Long.toBinaryString(plan.getTimes());
        List<Integer> weekdays = new ArrayList<>();
        for (int i = 0; i < data.getWeekdays(); i++) {
            int index = (data.getWeekdays() - i - 1) * data.getTimeOfDay();
            if (times.length() <= index) {
                continue;
            }
            int end = times.length() - index;
            int start = Math.max(0, end - data.getTimeOfDay());
//            String timeOfDay = times.substring(start, end);
//            int num = PaikeUtil.getOneCount(timeOfDay);
            int num = PaikeUtil.getOneCount(times, start, end);
            if (num > 0) {
                weekdays.add(i);
            }
        }
        return weekdays;
    }

    private List<Integer> getSchedulePlanHours(int weekdays, SchedulePlan plan) {
        String planHours = plan.getHours();
        String[] ss = planHours.split("\\+");
        int oneHours = Integer.parseInt(ss[0]);
        int twoHours = ss.length == 2 ? Integer.parseInt(ss[1]) : 0;
        // 预排课
        if (StringUtils.isNotBlank(plan.getTimesPres())) {
            int hours = plan.getTimesPres().split("、").length;
            if (hours < oneHours) {
                oneHours -= hours;
            } else {
                if (hours < twoHours * 2) {
                    twoHours -= hours / 2;
                    if (hours % 2 == 1) {
                        oneHours -= 1;
                    }
                } else {
                    twoHours = 0;
                    hours -= twoHours * 2;
                    oneHours -= hours;
                }
            }
        }
        if (oneHours + twoHours > weekdays) {
            int temp = oneHours + twoHours - weekdays;
            oneHours -= temp * 2;
            twoHours += temp;
        }
        List<Integer> hours = new ArrayList<>();
        for (int i = 0; i < oneHours; i++) {
            hours.add(1);
        }
        for (int i = 0; i < twoHours; i++) {
            hours.add(2);
        }
        Collections.shuffle(hours);
        return hours;
    }

    private void setLessonTasksTime(ScheduleData data) {
        for (ScheduleTask task : data.getTasks()) {
            long timeAvailable = data.getTimeOfWeek();
            for (LessonPlanScheduleListener listener : listeners) {
                timeAvailable = listener.getTime(data, task, timeAvailable);
            }
            task.setTimeAvailable(timeAvailable);
            long timeSuggest = timeAvailable & getTimeSuggest(data, task);
            task.setTimeSuggest(timeSuggest);
        }
    }

    private long getTimeSuggest(ScheduleData data, ScheduleTask task) {
        long timeSuggest = 0;
        for (int i = task.getWeekdayStart(); i <= task.getWeekdayEnd(); i++) {
            for (int j = 0; j < data.getTimeOfDay(); j++) {
                timeSuggest = (timeSuggest << 1) + 1;
            }
        }
        for (int i = 0; i < data.getWeekdays() - task.getWeekdayEnd() - 1; i++) {
            timeSuggest = (timeSuggest << data.getTimeOfDay());
        }
        return timeSuggest;
    }

    /**
     * 排课
     *
     * @param data
     */
    private void scheduling(ScheduleData data) {
        if (data.getTasks().isEmpty()) {
            return;
        }
        List<ScheduleTask> lessonTasks = new ArrayList<>(data.getTasks());
        do {
            sortScheduleTask(data, lessonTasks);
            ScheduleTask task = lessonTasks.remove(0);
            long time = getScheduleTaskTime(data, task);
            task.setTime(time);
//            if (task.getAdminclasses().contains("一年级1班")) {
//                System.out.print(task.getCourse());
//                System.out.print("-");
//                System.out.print(task.getWeekdayEnd());
//                System.out.print("-");
//                System.out.print(task.getWeekdayStart());
//                System.out.print("-");
//                System.out.print(task.getWeight());
//                System.out.println();
//                System.out.println(PaikeUtil.toBinaryString(data.getAdminclass(task.getAdminclasses().get(0)).getTime()));
//                System.out.println(PaikeUtil.toBinaryString(task.getTimeAvailable()));
//                System.out.println(PaikeUtil.toBinaryString(task.getTimeSuggest()));
//                System.out.println(PaikeUtil.formatTime(task.getTime(), data.getWeekdays(), data.getTimeOfDay()));
//                printScheduleData(data, "一年级1班");
//                System.out.println();
//            }
//            addTeacherTime(data, task, time);
//            addAdmincalssTime(data, task, time);
            afterScheduling(data, lessonTasks, task);
            addScheduleTaskTime(lessonTasks, task, time);
        } while (!lessonTasks.isEmpty());
    }

    private void afterScheduling(ScheduleData data, List<ScheduleTask> lessonTasks, ScheduleTask task) {
        for (LessonPlanScheduleListener listener : listeners) {
            listener.afterScheduling(data, lessonTasks, task);
        }
    }

    private void sortScheduleTask(ScheduleData data, List<ScheduleTask> lessonTasks) {
        lessonTasks.forEach(t -> {
//            int weight = t.getHour() * 1000;
            int weight = 0;
            weight += (data.getTimeLength() - PaikeUtil.getOneCount(t.getTimeSuggest())) * 100;
            weight += (data.getTimeLength() - PaikeUtil.getOneCount(t.getTimeAvailable()));
            weight += t.getHour();
            t.setWeight(weight);
//            System.out.println(t.getCourse());
//            if("一年级托管".equals(t.getCourse())){
//                System.out.println(t.getWeight());
//            }
        });
        lessonTasks.sort(Comparator.comparingInt(ScheduleTask::getWeight).reversed());
    }

    private long getAdminclassTime(ScheduleData data, ScheduleTask task) {
        long time = ~0L;
        for (String adminclassName : task.getAdminclasses()) {
            Adminclass adminclass = data.getAdminclasses().get(adminclassName);
            time = time & adminclass.getTime();
        }
//        System.out.println("getAdminclassTime: " + PaikeUtil.toBinaryString(time, data.getWeekdays(), data.getTimeOfDay()));
        return time;
    }

    private long getScheduleTaskTime(ScheduleData data, ScheduleTask task) {
        long timeAvailable = task.getTimeSuggest();
        long timeAdminclass = getAdminclassTime(data, task);
        if (timeAvailable == 0) {
            // 没有可用时间，使用班级时间
            timeAvailable = timeAdminclass & task.getTimeAvailable();
        }
        long slot = getScheduleTaskTime(data, task, timeAvailable);
        if (slot == 0) {
            timeAvailable = timeAdminclass;
            slot = getScheduleTaskTime(data, task, timeAvailable);
        }
        return slot;
    }

    private long getScheduleTaskTime(ScheduleData data, ScheduleTask task, Long timeAvailable) {
        LessonScheduleTime time = data.getTime();
        long slot = ~((~0L) << task.getHour());
        List<Long> slots = new ArrayList<>();
        for (int i = 0; i < data.getTimeLength(); i++) {
            try {
                if (task.getHour() > 1
                        && ((time.getNight() > 0 && (i % data.getTimeOfDay() == data.getTime().getNight() - 1))
                        || (i % data.getTimeOfDay() == data.getTime().getAfternoon() - 1))) {
                    continue;
                }
                if ((timeAvailable & slot) == slot) {
                    slots.add(slot);
                }
            } catch (Exception e) {

            } finally {
                slot = slot << 1;
            }
        }
        if (slots.isEmpty()) {
            return 0;
        }
        return slots.get(random.nextInt(slots.size()));
    }


    @Deprecated
    private void addTeacherTime(ScheduleData data, ScheduleTask task, long time) {
        task.getTeachers().forEach(teacherName -> {
            Teacher teacher = data.getTeacher(teacherName);
            teacher.setTime(teacher.getTime() & (~time));
        });
    }

    @Deprecated
    private void addAdmincalssTime(ScheduleData data, ScheduleTask task, long time) {
        task.getAdminclasses().forEach(adminclassName -> {
            Adminclass adminclass = data.getAdminclass(adminclassName);
//            System.out.println(adminclassName + ":" + PaikeUtil.toBinaryString(adminclass.getTime(), data.getWeekdays(), data.getTimeOfDay()));
            adminclass.setTime(adminclass.getTime() & (~time));
//            System.out.println(adminclassName + ":" + PaikeUtil.toBinaryString(time, data.getWeekdays(), data.getTimeOfDay()));
//            System.out.println(adminclassName + ":" + PaikeUtil.toBinaryString(adminclass.getTime(), data.getWeekdays(), data.getTimeOfDay()));
        });
    }

    private void addScheduleTaskTime(List<ScheduleTask> lessonTasks, ScheduleTask task, long time) {
//        int[] num = new int[]{0};
        lessonTasks.forEach(t -> {
            if (!Collections.disjoint(t.getAdminclasses(), task.getAdminclasses())
                    || !Collections.disjoint(t.getTeachers(), task.getTeachers())) {
//                t.setTime(t.getTime() & (~time));
                t.setTimeSuggest(t.getTimeSuggest() & (~time));
                t.setTimeAvailable(t.getTimeAvailable() & (~time));
//                num[0]++;
            }
        });
//        System.out.println("lessonTasks.size: " + lessonTasks.size());
//        System.out.println("addScheduleTaskTime: " + num[0]);
    }

    private void setLessonPlanTimes(ScheduleData data, List<LessonPlan> lessonPlans) {
        lessonPlans.forEach(plan -> {
            StringBuilder sb = new StringBuilder();
            //预排课
            if (StringUtils.isNotBlank(plan.getTimesPres())) {
                sb.append(plan.getTimesPres());
            }
            data.getTasks().forEach(task -> {
                if (task.getAdminclasses().contains(plan.getAdminclass())
                        && task.getCourseName().equals(plan.getCourse())) {
                    if (sb.length() > 0) {
                        sb.append("、");
                    }
                    sb.append(PaikeUtil.formatTime(task.getTime(), data.getWeekdays(), data.getTimeOfDay()));
                }
            });
            plan.setTimes(sb.toString());
        });
    }

    private void printScheduleData(ScheduleData data) {
        printScheduleData(data, null);
    }

    private void printScheduleData(ScheduleData data, String adminclassName) {
        ChineseNumberComparator comparator = new ChineseNumberComparator();
        List<String> adminclasses = new ArrayList<>(data.getAdminclasses().keySet());
        Collections.sort(adminclasses, comparator);
        for (String adminclass : adminclasses) {
            if (adminclassName != null && !adminclassName.equals(adminclass)) {
                continue;
            }
            System.out.println(adminclass);
            System.out.println("===========================================================================");
            System.out.print("\t");
            for (int i = 0; i < data.getWeekdays(); i++) {
                System.out.print("星期" + (i + 1));
                System.out.print("\t\t\t");
            }
            System.out.println();
            for (int i = 0; i < data.getTimeOfDay(); i++) {
                if (i == 4) {
                    System.out.println("  ------------------------------------------------------------------------");
                }
                System.out.print(i + 1);
                System.out.print("\t");
                for (int j = 0; j < data.getWeekdays(); j++) {
                    ScheduleTask task = getScheduleTask(data, adminclass, j, i);
                    if (task != null) {
                        System.out.print(task.getCourseName().substring(0, 2));
                        System.out.print("-");
                        System.out.print(task.getTeacher().substring(0, 2));
                    } else {
                        System.out.print("\t\t");
                    }
                    System.out.print("\t\t");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private ScheduleTask getScheduleTask(ScheduleData data, String adminclass, int weekday, int timeOfDay) {
        long time = 1;
        time = time << (data.getTimeOfDay() - timeOfDay - 1);
        time = time << (data.getWeekdays() - weekday - 1) * data.getTimeOfDay();
        long ftime = time;
        return data.getTasks().stream().filter(t -> t.getAdminclasses().contains(adminclass)
                && (t.getTime() & ftime) == ftime).findAny().orElse(null);
    }
}
