package com.ruoyi.teach.school.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.vo.ImportEntityData;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.teach.school.domain.*;
import com.ruoyi.teach.school.mapper.LessonMapper;
import com.ruoyi.teach.school.service.*;
import com.ruoyi.teach.school.util.LessonUtil;
import com.ruoyi.teach.school.vo.LessonDateTime;
import com.ruoyi.teach.school.vo.LessonImportData;
import com.ruoyi.teach.school.vo.Week;
import com.ruoyi.teach.system.domain.School;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 课表管理Service业务层处理
 *
 * @author ruoyi
 * @date 2021-07-14
 */
@Service
public class LessonServiceImpl extends SchoolEntityServiceImpl<LessonMapper, Lesson> implements LessonService {
    @Autowired
    private LessonMapper lessonMapper;
    @Autowired
    private AdminclassService adminclassService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private LessonTimeService lessonTimeService;
    @Autowired
    private SchoolConverter schoolConverter;

    /**
     * 查询课表管理
     *
     * @param id 课表管理ID
     * @return 课表管理
     */
    @Override
    public Lesson selectLessonById(Long id) {
        Lesson lesson = lessonMapper.selectLessonById(id);
        if (lesson != null) {
            if (lesson.getWeekType() == null) {
                lesson.setWeekType(Lesson.WEEK_TYPE_ALL);
            }
        }
        return lesson;
    }

    /**
     * 查询课表管理列表
     *
     * @param lesson 课表管理
     * @return 课表管理
     */
    @Override
    public List<Lesson> selectLessonList(Lesson lesson) {
        selectSchoolObjectList(lesson);
        List<Lesson> lessons = lessonMapper.selectLessonList(lesson);
        lessons.forEach(v -> {
            if (StringUtils.isBlank(v.getWeekType())) {
                v.setWeekType(Lesson.WEEK_TYPE_ALL);
            }
        });
        return lessons;
    }

    /**
     * 新增课表管理
     *
     * @param lesson 课表管理
     * @return 结果
     */
    @Override
    public int insertLesson(Lesson lesson) {
        insertSchoolObject(lesson);
        initLesson(lesson);
        return lessonMapper.insertLesson(lesson);
    }

    private void initLesson(Lesson lesson) {
        TeachCalendar teachCalendar = teachCalendarService.getById(lesson.getTeachCalendarId());
        lesson.setYear(teachCalendar.getYear());
        lesson.setTerm(teachCalendar.getTerm());
        Adminclass adminclass = adminclassService.getById(lesson.getAdminclassId());
        lesson.setAdminclass(adminclass.getName());
        lesson.setGrade(adminclass.getGrade());
        Course course = courseService.getById(lesson.getCourseId());
        lesson.setCourse(course.getName());
        if (lesson.getTeacherId() != null) {
            Teacher teacher = teacherService.getById(lesson.getTeacherId());
            lesson.setTeacher(teacher.getName());
            lesson.setTeachers(teacher.getName());
        }
        if (lesson.getTeacher2Id() != null) {
            Teacher teacher = teacherService.getById(lesson.getTeacher2Id());
            lesson.setTeachers(lesson.getTeachers() + "," + teacher.getName());
        }
        if (StringUtils.isNotBlank(lesson.getTimes())) {
            String pattern = "(\\d-\\d)(、\\d-\\d)*";
            Assert.isTrue(Pattern.matches(pattern, lesson.getTimes()), "时间安排格式有误");
            lesson.setHour(lesson.getTimes().split("、").length);
        }
        if (StringUtils.isBlank(lesson.getWeekType())) {
            lesson.setWeekType(Lesson.WEEK_TYPE_ALL);
        }
//        Assert.isTrue(lessonMapper.checkTime(lesson) == 0, "时间冲突");
    }

    /**
     * 修改课表管理
     *
     * @param lesson 课表管理
     * @return 结果
     */
    @Override
    public int updateLesson(Lesson lesson) {
        initLesson(lesson);
        return lessonMapper.updateLesson(lesson);
    }

    /**
     * 批量删除课表管理
     *
     * @param ids 需要删除的课表管理ID
     * @return 结果
     */
    @Override
    public int deleteLessonByIds(Long[] ids) {
        return lessonMapper.deleteLessonByIds(ids);
    }

    /**
     * 删除课表管理信息
     *
     * @param id 课表管理ID
     * @return 结果
     */
    @Override
    public int deleteLessonById(Long id) {
        return lessonMapper.deleteLessonById(id);
    }

    private List<Lesson> getAll() {
        return list(new QueryWrapper<>());
    }

    @Override
    protected ImportEntityData initImportEntityData(List<Lesson> sources) {
        School school = getSchool();
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendarCurrent(school.getId());
        LessonImportData data = new LessonImportData();
        data.setAdminclasses(adminclassService.getAll(school.getId(), teachCalendar.getYear()));
        data.setCourses(courseService.getAll(school.getId()));
        data.setTeachers(teacherService.getAll(school.getId()));
        data.setLessons(getLessonsByTeachCalendarId(teachCalendar.getId()));
        data.setPoolSize(200);
        return data;
    }

    @Override
    protected void importEntity(ImportEntityData entityData, Lesson source) {
        LessonImportData data = (LessonImportData) entityData;
        System.out.println(String.format("%d/%d", data.getIndex(), data.getTotal()));
        if (source.getSchoolId() == null) {
            School school = getSchool();
            source.setSchoolId(school.getId());
        }
        Assert.isTrue(StringUtils.isNotBlank(source.getYear()), "学年不能为空");
        Assert.isTrue(StringUtils.isNotBlank(source.getTerm()), "学期不能为空");
        Assert.isTrue(StringUtils.isNotBlank(source.getAdminclass()), "班级不能为空");
//        Assert.isTrue(StringUtils.isNotBlank(source.getWeekday()), "星期不能为空");
//        Assert.isTrue(StringUtils.isNotBlank(source.getTimeStart()), "开始小节不能为空");
//        Assert.isTrue(StringUtils.isNotBlank(source.getTimeEnd()), "结束小节不能为空");
        Assert.isTrue(StringUtils.isNotBlank(source.getCourse()), "课程不能为空");
//        Assert.isTrue(StringUtils.isNotBlank(source.getTeacher()), "教师不能为空");
//                Assert.isTrue(StringUtils.isNotBlank(source.getTeacherCode()), "工号不能为空");

        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendar(source.getSchoolId(), source.getYear(), source.getTerm());
        Assert.notNull(teachCalendar, "学年或学期有误");
        source.setTeachCalendarId(teachCalendar.getId());

        Adminclass adminclass = getAdminclass(data, source, source.getYear(), source.getAdminclass());
//        Assert.notNull(adminclass, String.format("班级名称有误：%s", source.getAdminclassId()));
        source.setAdminclassId(adminclass.getId());
        source.setGrade(adminclass.getGrade());

        Course course = getCourse(data, source);
//        Assert.notNull(course, String.format("课程名称有误：%s", source.getCourse()));
        source.setCourseId(course.getId());

        Teacher teacher = getTeacher(data.getTeachers(), source.getTeacherCode(), source.getTeacher());
//        Assert.notEmpty(teachers, String.format("教师姓名有误：%s", source.getTeacher()));
//        Assert.isTrue(teachers.size() == 1, String.format("教师姓名重复，请填写工号：%s", source.getTeacher()));
        if (teacher != null) {
            source.setTeacherId(teacher.getId());
            if (source.getTeachers() == null) {
                source.setTeachers(teacher.getName());
            } else {
                source.setTeachers(source.getTeachers().replaceAll(source.getTeacher(), teacher.getName()));
                source.setTeacher(teacher.getName());
            }
        }
        if (StringUtils.indexOf(source.getTeachers(), ",") > 0) {
            String teacherName = source.getTeachers().split(",")[1];
            teacher = getTeacher(data.getTeachers(), null, teacherName);
            if (teacher != null) {
                source.setTeacher2Id(teacher.getId());
                source.setTeachers(source.getTeachers().replaceAll(teacherName, teacher.getName()));
            }
        }

        if (source.getHour() == null) {
            if (StringUtils.isNotBlank(source.getTimes())) {
                source.setHour(source.getTimes().split("、").length);
            } else {
                source.setHour(1);
            }
        }

        if (StringUtils.isBlank(source.getWeekType())) {
            source.setWeekType(Lesson.WEEK_TYPE_ALL);
        }
//        if (source.getGrade() == null && source.getAdminclass() != null && source.getAdminclass().length() > 3) {
//            source.setGrade(source.getAdminclass().substring(0, 3));
//        }

        super.importEntity(data, source);
    }

    @Override
    protected void importCheckExists(ImportEntityData data, Lesson source) {
        List<Lesson> lessons = getImportFormLessonList((LessonImportData) data, source);
        if (!lessons.isEmpty()) {
            Lesson lesson = lessons.get(0);
            source.setId(lesson.getId());
        }
        Assert.isTrue(lessons.size() <= 1,
                String.format("课表数据重复，无法导入：%s", source.getInfo()));
    }

    private Adminclass getAdminclass(LessonImportData data, Lesson lesson, String year, String adminclassName) {
        Adminclass adminclass = data.getAdminclasses().stream().filter(d ->
                        d.getYear().equals(year) && d.getName().equals(adminclassName))
                .findFirst().orElse(null);
        if (adminclass == null) {
            adminclass = new Adminclass();
            adminclass.setSchoolId(lesson.getSchoolId());
            adminclass.setYear(lesson.getYear());
            adminclass.setGrade(lesson.getGrade());
            adminclass.setName(lesson.getAdminclass());
            adminclassService.insertAdminclass(lesson.getSchoolId(), adminclass);
            data.getAdminclasses().add(adminclass);
        }
        return adminclass;
    }

    private Course getCourse(LessonImportData data, Lesson lesson) {
        Course course = data.getCourses().stream()
                .filter(c -> c.getName().equals(lesson.getCourse()))
                .findFirst().orElse(null);
        if (course == null) {
            course = new Course();
            course.setSort(99);
            course.setSchoolId(lesson.getSchoolId());
            course.setCode("AUTO");
            course.setName(lesson.getCourse());
            course.setStatus("0");
            courseService.insertCourse(lesson.getSchoolId(), course);
            data.getCourses().add(course);
        }
        return course;
    }

    private Teacher getTeacher(List<Teacher> teachers, String teacherCode, String teacher) {
        return teacherService.getTeacher(teachers, teacherCode, teacher);
    }

    private List<Lesson> getImportFormLessonList(LessonImportData data, Lesson source) {
        return data.getLessons().stream().filter(l ->
                        l.getYear().equals(source.getYear())
                                && l.getTerm().equals(source.getTerm())
                                && l.getAdminclass().equals(source.getAdminclass())
                                && l.getCourse().equals(source.getCourse())
                                && l.getWeekType().equals(source.getWeekType())
//                        && l.getWeekday().equals(source.getWeekday())
//                        && (l.getTimeStart().equals(source.getTimeStart()) || l.getTimeStart().equals(source.getTimeEnd())
//                        || l.getTimeEnd().equals(source.getTimeStart()) || l.getTimeEnd().equals(source.getTimeEnd()))
        ).collect(Collectors.toList());
    }

    @Override
    public List<Lesson> getLessonsByTeacher(Teacher teacher) {
        return getByTeacher(teacher);
    }

    @Override
    public List<Lesson> getByTeacher(Teacher teacher) {
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendarCurrent(teacher.getSchoolId());
        Lesson lesson = new Lesson();
        lesson.setTeachCalendarId(teachCalendar.getId());
        QueryWrapper<Lesson> query = new QueryWrapper<>(lesson);
        query.eq("teacher_id", teacher.getId()).or().eq("teacher2_id", teacher.getId());
//        lesson.setTeacher(teacher.getName());
        List<Lesson> lessons = list(query);
        lessons.forEach(v -> v.setCourseName(v.getCourse()));
//        return getLessonsByTimes(lessons, teachCalendar.getSchoolId());
        initDateTimes(teacher.getSchoolId(), lessons);
        return lessons;
    }

    @Override
    public List<Lesson> getLessonsByAdminclassId(Long adminclassId) {
        Assert.notNull(adminclassId, "班级ID为空");
        Adminclass adminclass = adminclassService.getById(adminclassId);
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendarCurrent(adminclass.getSchoolId());
        Lesson form = new Lesson();
        form.setTeachCalendarId(teachCalendar.getId());
        form.setAdminclassId(adminclassId);
        List<Lesson> lessons = list(new QueryWrapper<>(form));
        for (Lesson lesson : lessons) {
            if (lesson.getTeacher() == null) {
                lesson.setTeacher("");
            }
        }
//        return getLessonsByTimes(lessons, teachCalendar.getSchoolId());
        initDateTimes(adminclass.getSchoolId(), lessons);
        return lessons;
    }

    @Override
    public List<Lesson> getLessonsBySchoolId(Long schoolId, String year) {
        List<Lesson> lessons = lessonMapper.getLessonsBySchoolId(schoolId, year);
        initDateTimes(schoolId, lessons);
        return lessons;
    }

    @Override
    public List<Lesson> getLessonsByTerm(Long schoolId, TeachCalendar teachCalendar) {
        List<Lesson> lessons = lessonMapper.getLessonsByTerm(schoolId, teachCalendar.getYear(), teachCalendar.getTerm());
        initDateTimes(schoolId, lessons);
        return lessons;
    }

    private List<Lesson> getLessonsByTimes(List<Lesson> lessons, Long schoolId) {
        List<Lesson> list = new ArrayList<>();
        List<LessonTime> timeList = lessonTimeService.getLessonTime(schoolId);
        lessons.forEach(lesson -> {
            if (StringUtils.isBlank(lesson.getTimes())) {
                list.add(lesson);
            } else {
                String[] times = lesson.getTimes().replaceAll("，", "、").split("、");
                for (String time : times) {
                    String[] ss = time.split("-");
                    Lesson temp = schoolConverter.toLesson(lesson);
                    temp.setTimes("");
                    temp.setWeekType(lesson.getWeekType());
                    temp.setWeekday(LessonUtil.getWeekday(ss[0]));
                    temp.setTimeStart(timeList.get(Integer.parseInt(ss[1]) - 1).getName());
                    temp.setTimeEnd(temp.getTimeStart());
                    temp.setTimes(temp.getTimeStart());
                    list.add(temp);
                }
            }
        });
        return list;
    }

    public List<Lesson> getByTeacherId(Long teacherId, Date startDate, Date endDate) {
//        Teacher teacher = teacherService.getById(teacherId);
//        List<Lesson> lessons = getByTeacher(teacher);
//        if (startDate != null && endDate != null
//                && endDate.getTime() - startDate.getTime() < 7 * 24 * 60 * 60 * 1000) {
//            List<String> allWeek1s = Arrays.asList("", "日", "一", "二", "三", "四", "五", "六");
//            List<String> allWeek2s = Arrays.asList("", "7", "1", "2", "3", "4", "5", "6");
//            List<String> week1s = new ArrayList<>();
//            List<String> week2s = new ArrayList<>();
//            Calendar date = Calendar.getInstance();
//            date.setTime(startDate);
//            do {
//                String week = allWeek1s.get(date.get(Calendar.DAY_OF_WEEK));
//                week1s.add(week);
//                week = allWeek2s.get(date.get(Calendar.DAY_OF_WEEK));
//                week2s.add(week);
//                date.add(Calendar.DAY_OF_YEAR, 1);
//            } while (date.getTimeInMillis() <= endDate.getTime());
//            lessons = lessons.stream().filter(l -> {
//                if (StringUtils.isBlank(l.getTimes())) {
//                    return week1s.stream().filter(w -> l.getWeekday().indexOf(w) >= 0).count() > 0;
//                } else {
//                    return true;
//                }
//            }).collect(Collectors.toList());
//
//            lessons.forEach(lesson -> {
//                if (StringUtils.isNotBlank(lesson.getTimes())) {
//                    List<String> times = new ArrayList<>();
//                    String[] ss = lesson.getTimes().split("、");
//                    for (String s : ss) {
//                        if (week2s.contains(s.split("-")[0])) {
//                            times.add(s);
//                        }
//                    }
//                    lesson.setTimes(times.isEmpty() ? null : times.stream().collect(Collectors.joining("、")));
//                }
//            });
////            lessons = lessons.stream().filter(lesson -> StringUtils.isNotBlank(lesson.getTimes()))
////                    .collect(Collectors.toList());
//        }
//        return lessons;
        Teacher teacher = teacherService.getById(teacherId);
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendarCurrent(teacher.getSchoolId());
        List<LessonTime> times = lessonTimeService.getLessonTime(teacher.getSchoolId());
        List<Lesson> lessons = getByTeacher(teacher);
        List<Lesson> results = new ArrayList<>();
        Calendar date = Calendar.getInstance();
        date.setTime(startDate);
        do {
            int week = teachCalendarService.getWeek(teachCalendar, date.getTime());
            int weekday = DateUtils.getWeekday(date.getTime());
            times.forEach(time -> {
                String key = String.format("%d-%d-%d", week, weekday, time.getSort());
                lessons.forEach(lesson -> {
                    if (lesson.getWeekTimes().contains(key) && !results.contains(lesson)) {
                        results.add(lesson);
                    }
                });
            });
            date.add(Calendar.DAY_OF_YEAR, 1);
        } while (date.getTimeInMillis() <= endDate.getTime());
        return results;
    }

    @Override
    public List<Lesson> getLessonsByTeachCalendarId(Long teachCalendarId) {
        QueryWrapper query = new QueryWrapper();
        query.eq("teach_calendar_id", teachCalendarId);
        List<Lesson> list = list(query);
        if (!list.isEmpty() && StringUtils.isBlank(list.get(0).getWeekTimes())) {
            initDateTimes(teachCalendarService.getById(teachCalendarId), list);
        }
        return list;
    }

    private void initDateTimes(Long schoolId, List<Lesson> lessons) {
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendarCurrent(schoolId);
        initDateTimes(teachCalendar, lessons);
    }

    public void initDateTimes(TeachCalendar teachCalendar, List<Lesson> lessons) {
        List<Week> weeks = teachCalendarService.getWeeks(teachCalendar);
        lessons.forEach(lesson -> {
            initWeekTimes(weeks, lesson);
        });
    }

    @Override
    public List<String> getSubjectByTeacher(Long calendarId, Long teacherId) {
        return lessonMapper.getSubjectByTeacher(calendarId, teacherId);
    }

    @Override
    public List<Adminclass> getAdminclassByTeacher(Long calendarId, Long teacherId) {
        return lessonMapper.getAdminclassByTeacher(calendarId, teacherId);
    }

    private void initWeekTimes(Lesson lesson) {
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendarCurrent(lesson.getSchoolId());
        List<Week> weeks = teachCalendarService.getWeeks(teachCalendar);
        initWeekTimes(weeks, lesson);
    }

    private void initWeekTimes(List<Week> weeks, Lesson lesson) {
        List<String> keys = new ArrayList<>();
        if (StringUtils.isNotBlank(lesson.getTimes())) {
            String[] times = lesson.getTimes().split("、");
            List<String> timesExclude = StringUtils.isBlank(lesson.getTimesExclude()) ? new ArrayList<>(0)
                    : Arrays.asList(lesson.getTimesExclude().split("、"));
            if (StringUtils.isBlank(lesson.getWeeks())) {
                for (Week week : weeks) {
                    if ("单周".equals(lesson.getWeekType()) && week.getWeek() % 2 == 0) {
                        continue;
                    }
                    if ("双周".equals(lesson.getWeekType()) && week.getWeek() % 2 == 1) {
                        continue;
                    }
                    for (String time : times) {
                        String key = String.format("%d-%s", week.getWeek(), time);
                        if (timesExclude.contains(key)) {
                            continue;
                        }
                        keys.add(key);
                    }
                }
            } else {
                for (String week : lesson.getWeeks().split(",")) {
                    for (String time : times) {
                        String key = String.format("%s-%s", week, time);
                        if (timesExclude.contains(key)) {
                            continue;
                        }
                        keys.add(key);
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(lesson.getTimesInclude())) {
            String[] timesInclude = StringUtils.split(lesson.getTimesInclude(), "、");
            for (String key : timesInclude) {
                if (!keys.contains(key)) {
                    keys.add(key);
                }
            }
        }
        List<LessonDateTime> dateTimes = keys.stream().map(key -> getLessonDateTime(key))
                .filter(v -> v != null).collect(Collectors.toList());
        Calendar calendar = Calendar.getInstance();
        for (LessonDateTime dateTime : dateTimes) {
            Optional<Week> first = weeks.stream().filter(w -> w.getWeek() == dateTime.getWeek()).findFirst();
            if (!first.isPresent()) {
                continue;
            }
            Week week = first.get();
            calendar.setTime(week.getStartDate());
            calendar.add(Calendar.DAY_OF_WEEK, dateTime.getWeekday());
            dateTime.setDate(calendar.getTime());
        }
        Collections.sort(dateTimes, Comparator.comparing(LessonDateTime::getWeek)
                .thenComparing(LessonDateTime::getWeekday).thenComparing(LessonDateTime::getTime));
        lesson.setDateTimes(dateTimes);

        StringBuilder sb = new StringBuilder();
        for (LessonDateTime dt : dateTimes) {
            if (sb.length() > 0) {
                sb.append("、");
            }
            sb.append(String.format("%d-%d-%d", dt.getWeek(), dt.getWeekday(), dt.getTime()));
        }
        lesson.setWeekTimes(sb.toString());
    }

    @Override
    public void addTimeInclude(Long teachCalendarId, Long adminclassId, Long courseId, Long teacherId, List<String> weekTimes) {
        Lesson lesson = getLesson(teachCalendarId, adminclassId, courseId, teacherId);
        for (String weekTime : weekTimes) {
            addTimeInclude(lesson, weekTime);
        }
        lessonMapper.updateLesson(lesson);
    }

    @Override
    public void addTimeInclude(Lesson lesson, String weekTime) {
        String timesInclude = lesson.getTimesInclude();
        String timesExclude = lesson.getTimesExclude();
        if (timesInclude == null) {
            timesInclude = "";
        }
        if (timesExclude == null) {
            timesExclude = "";
        }
        List<String> timesIncludes = new ArrayList<>(Arrays.asList(timesInclude.split("、")));
        List<String> timesExcludes = new ArrayList<>(Arrays.asList(timesExclude.split("、")));
        timesExcludes.remove(weekTime);
        String time = weekTime.substring(weekTime.indexOf("-") + 1);
        if ((lesson.getTimes() == null || !Arrays.asList(lesson.getTimes().split("、")).contains(time))
                && !timesIncludes.contains(weekTime)) {
//            if (timesInclude.length() > 0) {
//                timesInclude += "、";
//            }
//            timesInclude += weekTime;
            timesIncludes.add(weekTime);
        }
        lesson.setTimesInclude(String.join("、", timesIncludes));
        lesson.setTimesExclude(String.join("、", timesExcludes));
    }

    @Override
    public void removeTimeExclude(Long teachCalendarId, Long adminclassId, Long courseId, Long teacherId, List<String> weekTimes) {
        Lesson lesson = getLesson(teachCalendarId, adminclassId, courseId, teacherId);
        String timesExclude = lesson.getTimesExclude();
        if (StringUtils.isBlank(timesExclude)) {
            return;
        }
        for (String weekTime : weekTimes) {
            timesExclude = timesExclude.replaceAll(weekTime, "");
            timesExclude = timesExclude.replaceAll("、、", "、");
        }
        lesson.setTimesExclude(timesExclude);
        lessonMapper.updateLesson(lesson);
    }

    @Override
    public void addTimeExclude(Long teachCalendarId, Long adminclassId, Long courseId, Long teacherId, List<String> weekTimes) {
        Lesson lesson = getLesson(teachCalendarId, adminclassId, courseId, teacherId);
        String timesInclude = lesson.getTimesInclude();
        String timesExclude = lesson.getTimesExclude();
        if (timesInclude == null) {
            timesInclude = "";
        }
        if (timesExclude == null) {
            timesExclude = "";
        }
        List<String> timesIncludes = new ArrayList<>(Arrays.asList(timesInclude.split("、")));
        List<String> timesExcludes = new ArrayList<>(Arrays.asList(timesExclude.split("、")));
        for (String weekTime : weekTimes) {
//            if (timesIncludes.contains( weekTime)) {
//                timesInclude = timesInclude.replace(weekTime, "");
//                timesInclude = timesInclude.replace("、、", "、");
//            }
            timesIncludes.remove(weekTime);
            if (!timesExcludes.contains(weekTime)) {
//                if (timesExclude.length() > 0) {
//                    timesExclude += "、";
//                }
//                timesExclude += weekTime;
                timesExcludes.add(weekTime);
            }
        }
//        lesson.setTimesInclude(timesInclude);
//        lesson.setTimesExclude(timesExclude);
        lesson.setTimesInclude(String.join("、", timesIncludes));
        lesson.setTimesExclude(String.join("、", timesExcludes));
        lessonMapper.updateLesson(lesson);
    }

    @Override
    public void removeTimeInclude(Long teachCalendarId, Long adminclassId, Long courseId, Long teacherId, List<String> weekTimes) {
        Lesson lesson = getLesson(teachCalendarId, adminclassId, courseId, teacherId);
        String timesInclude = lesson.getTimesInclude();
        if (StringUtils.isBlank(timesInclude)) {
            return;
        }
        for (String weekTime : weekTimes) {
            timesInclude = timesInclude.replaceAll(weekTime, "");
            timesInclude = timesInclude.replaceAll("、、", "、");
        }
        lesson.setTimesInclude(timesInclude);
        lessonMapper.updateLesson(lesson);
    }

    @Override
    public Lesson getLessonByTeacher(Long teachCalendarId, Long adminclassId, Long courseId, Long teacherId) {
        List<Lesson> list = getLessons(teachCalendarId, adminclassId, courseId, teacherId);
        Assert.isTrue(!list.isEmpty(), "课程信息有误");
        Lesson lesson = list.get(0);
        initWeekTimes(lesson);
        return lesson;
    }

    @Override
    public Lesson getLessonByTeacherAndCourse(Long teachCalendarId, Long adminclassId, Long courseId, Long teacherId) {
        List<Lesson> list = getLessons(teachCalendarId, adminclassId, courseId, teacherId);
        if (list.isEmpty()) {
            Lesson lesson = new Lesson();
            TeachCalendar calendar = teachCalendarService.getById(teachCalendarId);
            Adminclass adminclass = adminclassService.getById(adminclassId);
            Course course = courseService.getById(courseId);
            Teacher teacher = teacherService.getById(teacherId);
            lesson.setSchoolId(calendar.getSchoolId());
            lesson.setYear(calendar.getYear());
            lesson.setTerm(calendar.getTerm());
            lesson.setTeachCalendarId(teachCalendarId);
            lesson.setAdminclassId(adminclassId);
            lesson.setAdminclass(adminclass.getName());
            lesson.setCourseId(courseId);
            lesson.setCourse(course.getName());
            lesson.setTeacherId(teacherId);
            lesson.setTeacher(teacher.getName());
            lesson.setGrade(adminclass.getGrade());
            lesson.setHour(1);
            lesson.setWeekType("全部");
            return lesson;
        }
        return list.get(0);
    }

    private List<Lesson> getLessons(Long teachCalendarId, Long adminclassId, Long courseId, Long teacherId) {
        QueryWrapper query = new QueryWrapper();
        query.eq("teach_calendar_id", teachCalendarId);
        query.eq("adminclass_id", adminclassId);
        query.eq("course_id", courseId);
        query.eq("teacher_id", teacherId);
        return list(query);
    }

    private Lesson getLesson(Long teachCalendarId, Long adminclassId, Long courseId, Long teacherId) {
        QueryWrapper query = new QueryWrapper();
        query.eq("teach_calendar_id", teachCalendarId);
        query.eq("adminclass_id", adminclassId);
        query.eq("course_id", courseId);
        query.eq("teacher_id", teacherId);
        List<Lesson> list = list(query);
        Assert.isTrue(!list.isEmpty(), "课程信息错误");
        return list.get(0);
    }

    private LessonDateTime getLessonDateTime(String key) {
        String[] ss = key.split("-");
        if (ss.length != 3) {
            return null;
        }
        LessonDateTime time = new LessonDateTime();
        time.setWeek(Integer.parseInt(ss[0]));
        time.setWeekday(Integer.parseInt(ss[1]));
        time.setTime(Integer.parseInt(ss[2]));
        return time;
    }

    @Override
    public List<Course> getTeacherCourses(Long teacherId) {
        Teacher teacher = teacherService.getById(teacherId);
        return getTeacherCourses(teacher);
    }

    @Override
    public List<Course> getTeacherCourses(Teacher teacher) {
        return lessonMapper.getTeacherCourses(teacher);
    }

    @Override
    public List<String> getWeekTimes(Long schoolId, Date lessonDate, String times) {
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendarCurrent(schoolId);
        List<LessonTime> lessonTimes = lessonTimeService.getLessonTime(schoolId);
        return getWeekTimes(teachCalendar, lessonTimes, lessonDate, times);
    }

    @Override
    public List<String> getWeekTimes(TeachCalendar teachCalendar, List<LessonTime> lessonTimes, Date lessonDate, String times) {
        Integer week = teachCalendarService.getWeek(teachCalendar, lessonDate);
        Integer weekday = DateUtils.getWeekday(lessonDate);
        String[] timess = times.split("、");
        List<String> weekTimes = new ArrayList<>();
        for (String time : timess) {
            Optional<LessonTime> first = lessonTimes.stream().filter(t -> t.getName().equals(time)).findFirst();
            if (first.isPresent()) {
                String weekTime = String.format("%d-%d-%d", week, weekday, first.get().getSort());
                weekTimes.add(weekTime);
            }
        }
        return weekTimes;
    }

    @Override
    public List<Course> getCourses(Long teachCalendarId) {
        return lessonMapper.getCourses(teachCalendarId);
    }
}
