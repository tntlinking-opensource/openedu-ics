package com.ruoyi.teach.paike;

import com.ruoyi.teach.paike.entity.*;
import com.ruoyi.teach.util.ChineseNumberComparator;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GeneticAlgorithm {
    /**
     * 种群的规模（0-100）
     * 由使用者按经验设定，默认为班级数
     */
    private Integer popsize = 32;
    /**
     * 种群的变异概率
     */
    private Double mutprob = 0.3;
    /**
     * 精英种群的个数
     */
    private Integer elite = 8;
    /**
     * 进化代数（100-500）
     */
    private Integer maxiter = 100;
    /**
     * 所有的种群 每一个种群中存放需要编排的课程列表
     */
    private List<List<Schedule>> population;

    private Random random = new Random();

    TimeConfig timeConfig;
    List<CourseConfig> courseConfigs;
    List<AdminclassConfig> adminclassConfigs;
    List<TeacherConfig> teacherConfigs;
    List<LessonInfo> lessonInfos;
    List<Schedule> schedules;
    List<String> roomRange;

    public List<Schedule> evolution() {
        initSchedules();
        initPopulation();
        //冲突最小的染色体的冲突得分，若为0则说明已获得最优的解，可返回
        int bestScore;
        //最优的课程编排结果
        List<Schedule> bestSchedule = new ArrayList<>();
        for (int i = 0; i < this.maxiter; i++) {
            //获取冲突结果
            List<ScheduleScore> scheduleScores = scheduleCost();
            System.out.print(i + ":");
            for (ScheduleScore score : scheduleScores) {
                //若发现冲突结果为0 则说明可将其当做最优排课结果返回
                bestScore = score.getConflict();
                if (bestScore == 0) {
                    bestSchedule = score.getSchedules();
                    printSchedule(bestSchedule);
                    return bestSchedule;
                }
//                System.out.print(StringUtils.substringAfter(score.toString(), "@"));
//                System.out.print("-");
                System.out.print(score.getConflict());
                System.out.print(",");
            }
            System.out.println();
            //精英种群集合
            List<List<Schedule>> newPopulation = scheduleScores.stream()
                    .map(v -> v.getSchedules()).collect(Collectors.toList());
            while (newPopulation.size() < this.popsize) {
                List<Schedule> tmp;
                if (random.nextDouble() < this.mutprob) {
//                    //落在变异概率内 变异
                    tmp = mutate(newPopulation);
                } else {
                    //交叉
                    tmp = crossover(newPopulation);
                }
                newPopulation.add(tmp);
            }
//            int min = costMap.values().stream().min(Integer::compareTo).get();
//            int max = costMap.values().stream().max(Integer::compareTo).get();
//            System.out.println(String.format("%d : %d", i, min));
//            System.out.println(String.format("%d : %d", i, max));
            this.population = newPopulation;
        }
        printSchedule(population.get(0));
        return bestSchedule;
    }

    public void initSchedules() {
        schedules = new ArrayList<>();
        for (LessonInfo info : lessonInfos) {
//            System.out.println(info);
            double hours = info.getLessonHour();
            int weekdays = timeConfig.getWeekdays();
            while (hours > 0) {
                int weekday = getRandomInt(weekdays / hours);
                if (weekday < 1) {
                    weekday = 1;
                }
                int hour = 1;
                if (info.getLessonHour() > timeConfig.getWeekdays()) {
                    hour = getRandomInt(hours * weekday / weekdays);
                } else {

                }

//                if (hour == 0) {
//                    System.out.println(hour);
//                }

                long timeAvailable = (~0L) << (weekday * timeConfig.getTimeOfDay());
                timeAvailable = ~timeAvailable;
                timeAvailable = timeAvailable << (timeConfig.getWeekdays() - weekdays) * timeConfig.getTimeOfDay();

                timeAvailable = timeAvailableAndConfig(info, timeAvailable);

                if (timeAvailable == 0) {
                    System.out.println(timeAvailable);
                }
//                System.out.println(Long.toBinaryString(timeAvailable));

                Schedule schedule = new Schedule();
                schedule.setInfo(info);
                schedule.setTimeAvailable(timeAvailable);
                schedule.setHours(hour);
                schedules.add(schedule);
                weekdays -= weekday;
                hours -= hour;
            }
        }
    }

    /**
     * 随机初始化不同的种群
     */
    void initPopulation() {
        this.population = new ArrayList<>();
        for (int i = 0; i < this.popsize; i++) {
            List<Schedule> entity = new ArrayList<>();
            List<Schedule> tmps = new ArrayList<>(schedules);
            Map<String, Long> adminclassTimeMap = new HashMap<>();
            Map<String, Long> teacherTimeMap = new HashMap<>();
            while (!tmps.isEmpty()) {
                sortSchedules(tmps, adminclassTimeMap, teacherTimeMap);
                Schedule s = tmps.remove(0);
                Long atime = adminclassTimeMap.getOrDefault(s.getAdminclass(), ~0L);
                Long ttime = teacherTimeMap.getOrDefault(s.getTeacher(), ~0L);
                Schedule tmp = new Schedule(s);
//                if (getRandomSlot(tmp, atime, ttime) == 0) {
//                    System.out.println(s);
//                }
                tmp.setSlot(getRandomSlot(tmp, atime, ttime));
                entity.add(tmp);
                atime = atime & (~tmp.getSlot());
                ttime = ttime & (~tmp.getSlot());
//                System.out.print(Long.toBinaryString(atime));
//                System.out.println(":" + PaikeUtil.getZeroCount(atime));
//                System.out.println(Long.toBinaryString(atime));
                adminclassTimeMap.put(s.getAdminclass(), atime);
                teacherTimeMap.put(s.getTeacher(), ttime);
            }
            Collections.shuffle(entity);
            Collections.sort(entity, Comparator.comparing(Schedule::getHours).reversed());
            this.population.add(entity);
        }
    }

    private long getRandomSlot(Schedule s, Long atime, Long ttime) {
        long timeAvailable = s.getTimeAvailable() & atime & ttime;
        long slot = getRandomSlot(s, timeAvailable);
        if (slot == 0) {
            timeAvailable = s.getTimeAvailable() & atime;
            slot = getRandomSlot(s, timeAvailable);
        }
        if (slot == 0) {
            slot = getRandomSlot(s);
        }
        return slot;
    }

    private long getRandomSlot(Schedule schedule) {
        return getRandomSlot(schedule, schedule.getTimeAvailable());
    }

    private long getRandomSlot(Schedule schedule, Long timeAvailable) {
        long slot = ~((~0L) << schedule.getHours());
        List<Long> slots = new ArrayList<>();
        for (int i = 0; i < timeConfig.getTimeLength(); i++) {
            try {
                if (schedule.getHours() > 1
                        && ((i % timeConfig.getTimeOfDay()) == 3 || (i % timeConfig.getTimeOfDay()) == 6)) {
                    continue;
                }
//            System.out.println(Long.toBinaryString(timeAvailable));
//            System.out.println(Long.toBinaryString(slot));
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

    private long timeAvailableAndConfig(LessonInfo info, long timeAvailable) {
        AdminclassConfig adminclassConfig = getAdminclassConfig(info.getAdminclassName());
        timeAvailable = timeAvailable & adminclassConfig.getTime();
        CourseConfig courseConfig = getCourseConfig(info.getCourseName());
        timeAvailable = timeAvailable & courseConfig.getTime();
        TeacherConfig teacherConfig = getTeacherConfig(info.getTeacherName());
        timeAvailable = timeAvailable & teacherConfig.getTime();
        return timeAvailable;
    }

    private AdminclassConfig getAdminclassConfig(String name) {
        Optional<AdminclassConfig> any = adminclassConfigs.stream().filter(c -> c.getName().equals(name)).findAny();
        if (any.isPresent()) {
            return any.get();
        }
        AdminclassConfig config = new AdminclassConfig(name, ~0L);
        return config;
    }

    private CourseConfig getCourseConfig(String courseName) {
        Optional<CourseConfig> any = courseConfigs.stream().filter(c -> c.getName().equals(courseName)).findAny();
        if (any.isPresent()) {
            return any.get();
        }
        CourseConfig config = new CourseConfig(courseName, ~0L);
        courseConfigs.add(config);
        return config;
    }

    private TeacherConfig getTeacherConfig(String name) {
        Optional<TeacherConfig> any = teacherConfigs.stream().filter(c -> c.getName().equals(name)).findAny();
        if (any.isPresent()) {
            return any.get();
        }
        TeacherConfig config = new TeacherConfig(name, ~0L);
        return config;
    }

    private int getRandomInt(double v) {
        double d = getRandomBool() ? Math.floor(v) : Math.ceil(v);
        return (int) d;
    }

    private boolean getRandomBool() {
        return random.nextInt(2) == 0;
    }

    private void sortSchedules(List<Schedule> schedules, Map<String, Long> adminclassTimeMap, Map<String, Long> teacherTimeMap) {
        schedules.forEach(s -> {
            int weight = s.getHours() * 100;
            Long atime = adminclassTimeMap.getOrDefault(s.getAdminclass(), timeConfig.getTimeAvailable());
            Long ttime = teacherTimeMap.getOrDefault(s.getTeacher(), timeConfig.getTimeAvailable());
            long time = s.getTimeAvailable() & atime & ttime;
            weight += timeConfig.getTimeLength() - PaikeUtil.getOneCount(time);
            s.setWeight(weight);
        });
        schedules.sort(Comparator.comparingInt(Schedule::getWeight).reversed());
    }

    private String getRandomRoom(List<String> roomRange) {
        return roomRange.get(random.nextInt(roomRange.size()));
    }

    private long getRandomSlot() {
        return 1 << random.nextInt(timeConfig.getTimeLength());
    }

    /**
     * 计算课表种群的冲突。
     * 返回：精英种群--精英种群中排名第一的染色体若冲突值为0则说明是可以作为最优解返回
     * 当被测试课表冲突为0的时候，这个课表就是个符合规定的课表。
     * 冲突检测遵循下面几条规则：
     * 同一个教室在同一个时间只能有一门课。
     * 同一个班级在同一个时间只能有一门课。
     * 同一个教师在同一个时间只能有一门课。
     * 但是对于目前系统中已经将班级、教师、课程拼成了一条教学任务
     * 故只需要满足 同一个教室在同一个时间 只能有一各教学任务
     *
     * @return
     */
    List<ScheduleScore> scheduleCost() {
        List<ScheduleScore> scheduleScores = new ArrayList<>();
        //一个染色体有多长==》有多少课程需要安排
        for (List<Schedule> p : population) {
            //对种群遍历，求种群中染色体的适应度
            int conflict = 0;
            conflict += conflictByKey(p, Schedule::getAdminclass);
            conflict += conflictByKey(p, Schedule::getTeacher);
            scheduleScores.add(new ScheduleScore(p, conflict));
        }
        Collections.sort(scheduleScores, Comparator.comparing(ScheduleScore::getConflict));
        return scheduleScores.subList(0, 8);
    }

    private static int conflictByKey(List<Schedule> schedules, Function<Schedule, String> fn) {
        Map<String, Long> slotMap = new HashMap<>();
        int conflict = 0;
        for (Schedule s : schedules) {
            String key = fn.apply(s);
            Long slot = slotMap.get(key);
            if (slot == null) {
                slot = 0L;
            }
            if ((slot & s.getSlot()) == s.getSlot()) {
                conflict++;
            } else {
                slot = slot | s.getSlot();
            }
            slotMap.put(key, slot);
        }
        return conflict;
    }

    /**
     * 变异 根据精英种群集合 在将其中随机一个染色体变异后 返回变异后的染色体
     *
     * @param eiltePopulation
     * @return
     */
    List<Schedule> mutate(List<List<Schedule>> eiltePopulation) {
        //选择变异哪一个精英种群中的染色体
        int getIndex = random.nextInt(elite);
        List<Schedule> ep = eiltePopulation.get(getIndex);
        List<Schedule> tmp = ep.stream().map(s -> new Schedule(s)).collect(Collectors.toList());
        Map<String, Long> adminclassTimeMap = new HashMap<>();
        Map<String, Long> teacherTimeMap = new HashMap<>();
        for (Schedule s : tmp) {
            Long atime = adminclassTimeMap.getOrDefault(s.getAdminclass(), ~0L);
            Long ttime = teacherTimeMap.getOrDefault(s.getTeacher(), ~0L);
            if (random.nextFloat() < 0.3) {
                s.setSlot(getRandomSlot(s, atime, ttime));
            }
            atime = atime & (~s.getSlot());
            ttime = ttime & (~s.getSlot());
            adminclassTimeMap.put(s.getAdminclass(), atime);
            teacherTimeMap.put(s.getTeacher(), ttime);
        }
        return tmp;
    }

    /**
     * 交叉 根据精英种群集合 在将其中两个染色体交叉后 返回一个新的染色体
     *
     * @param eiltePopulation
     * @return
     */
    List<Schedule> crossover(List<List<Schedule>> eiltePopulation) {
        Random random = new Random();
        //选择变异哪两个精英种群
        int getIndex1 = random.nextInt(eiltePopulation.size());
        int getIndex2 = random.nextInt(eiltePopulation.size());

        List<Schedule> e1 = eiltePopulation.get(getIndex1);
        List<Schedule> e2 = eiltePopulation.get(getIndex2);
        List<Schedule> tmp = e1.stream().map(s -> new Schedule(s)).collect(Collectors.toList());
        for (int i = 0; i < e1.size(); i++) {
            if (random.nextDouble() < 0.3) {
                tmp.get(i).setSlot(e2.get(i).getSlot());
            }
        }
        return e1;
    }


    private void printSchedule(List<Schedule> bestSchedule) {
//        for (Schedule schedule : bestSchedule) {
//            if("高一1班".equals(schedule.getAdminclass())){
//                System.out.println(schedule.getAdminclass() + ":" +
//                        schedule.getInfo().getCourseName() + ":" +
//                        formatSlot(schedule.getSlot()));
//            }
//        }
        for (Adminclass adminclass : getAdminclasses(bestSchedule)) {
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
                    Schedule task = getLessonTask(bestSchedule, adminclass, j, i);
                    if (task != null) {
                        System.out.print(task.getInfo().getCourseName().substring(0, 2));
                        System.out.print("-");
                        System.out.print(task.getInfo().getTeacherName().substring(0, 2));
                    } else {
                        System.out.print("一一-一一");
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

    private String formatSlot(long slot) {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < timeConfig.getTimeLength(); i++) {
            if ((slot & 1) == 1) {
                int weekday = i / timeConfig.getTimeOfDay();
                int hour = i % timeConfig.getTimeOfDay();
                names.add(weekday + "-" + hour);
            }
            slot = slot >> 1;
        }
        return names.stream().collect(Collectors.joining(","));
    }

    private List<Adminclass> getAdminclasses(List<Schedule> bestSchedule) {
        Set<String> names = new HashSet<>();
        for (Schedule info : bestSchedule) {
            names.add(info.getAdminclass());
        }
        List<Adminclass> list = names.stream().map(name -> new Adminclass(name))
                .collect(Collectors.toList());
        ChineseNumberComparator comparator = new ChineseNumberComparator();
        list.sort((o1, o2) -> comparator.compare(o1.getName(), o2.getName()));
        return list;
    }

    private Schedule getLessonTask(List<Schedule> bestSchedule, Adminclass adminclass, int weekday, int timeOfDay) {
        long time = 1;
        time = time << weekday * timeConfig.getTimeOfDay();
        time = time << timeOfDay;
        long ftime = time;
        return bestSchedule.stream().filter(t -> t.getInfo().getAdminclassName().equals(adminclass.getName())
                && (t.getSlot() & ftime) == ftime).findAny().orElse(null);
    }

}
