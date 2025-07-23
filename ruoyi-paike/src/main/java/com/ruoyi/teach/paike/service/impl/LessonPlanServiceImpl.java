package com.ruoyi.teach.paike.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.common.core.service.impl.EntityServiceImpl;
import com.ruoyi.common.core.vo.ImportEntityData;
import com.ruoyi.teach.paike.PaikeUtil;
import com.ruoyi.teach.paike.domain.LessonPlan;
import com.ruoyi.teach.paike.domain.LessonSchedule;
import com.ruoyi.teach.paike.mapper.LessonPlanMapper;
import com.ruoyi.teach.paike.service.LessonPlanService;
import com.ruoyi.teach.paike.service.LessonScheduleConverter;
import com.ruoyi.teach.paike.service.LessonScheduleService;
import com.ruoyi.teach.paike.vo.Adminclass;
import com.ruoyi.teach.paike.vo.Course;
import com.ruoyi.teach.paike.vo.Grade;
import com.ruoyi.teach.paike.vo.LessonPlanForm;
import com.ruoyi.teach.school.domain.Lesson;
import com.ruoyi.teach.school.domain.TeachCalendar;
import com.ruoyi.teach.school.service.LessonService;
import com.ruoyi.teach.school.service.TeachCalendarService;
import com.ruoyi.teach.util.ChineseNumberComparator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.Collator;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 任课信息Service业务层处理
 *
 * @author ruoyi
 * @date 2021-10-26
 */
@Service
public class LessonPlanServiceImpl extends EntityServiceImpl<LessonPlanMapper, LessonPlan> implements LessonPlanService {

    @Autowired
    private LessonPlanMapper lessonPlanMapper;
    @Autowired
    private LessonScheduleService lessonScheduleService;
    @Autowired
    private LessonScheduleConverter converter;
    @Autowired
    private TeachCalendarService teachCalendarService;
    @Autowired
    private LessonService lessonService;

    /**
     * 查询任课信息
     *
     * @param id 任课信息ID
     * @return 任课信息
     */
    @Override
    public LessonPlan selectLessonPlanById(Long id) {
        return lessonPlanMapper.selectLessonPlanById(id);
    }

    /**
     * 查询任课信息列表
     *
     * @param lessonPlan 任课信息
     * @return 任课信息
     */
    @Override
    public List<LessonPlan> selectLessonPlanList(LessonPlan lessonPlan) {
        return lessonPlanMapper.selectLessonPlanList(lessonPlan);
    }

    @Override
    public boolean saveOrUpdate(LessonPlan lessonPlan) {
        if (lessonPlan.getId() == null) {
            LessonSchedule schedule = lessonScheduleService.getBySchoolId(lessonPlan.getSchoolId());
            lessonPlan.setLessonScheduleId(schedule.getId());
        }
        initLessonPlan(lessonPlan);
        return super.saveOrUpdate(lessonPlan);
    }

    private void initLessonPlan(LessonPlan lessonPlan) {
        Assert.isTrue(StringUtils.isNotBlank(lessonPlan.getHours()), "课时不能为空");
        if (StringUtils.isNotBlank(lessonPlan.getHours())) {
            String pattern = "(\\d+)(\\+\\d+)?";
            Assert.isTrue(Pattern.matches(pattern, lessonPlan.getHours()), "课时格式有误：" + lessonPlan.getHours());
        }
        lessonPlan.setHour(PaikeUtil.getHour(lessonPlan.getHours()));
    }

    @Override
    public void saveBatch(LessonPlanForm form) {
        List<LessonPlan> plans = form.getPlans();
        //删除课程
//        lessonPlanMapper.deleteByAdminclass(form.getLessonScheduleId(), form.getAdminclass());
        removeByIds(form.getRemovePlanIds());
        plans = plans.stream().filter(p -> StringUtils.isNotBlank(p.getCourse())
                        && StringUtils.isNotBlank(p.getHours()))
                .collect(Collectors.toList());
        plans.forEach(p -> {
            p.setSchoolId(form.getSchoolId());
            p.setLessonScheduleId(form.getLessonScheduleId());
            p.setAdminclass(form.getAdminclass());
            p.setGrade(form.getGrade());
            initLessonPlan(p);
        });
        saveOrUpdateBatch(plans);
    }

    @Override
    public List<String> getCourses(Long lessonScheduleId) {
        return getCourses(lessonScheduleId, null);
    }

    @Override
    public List<String> getCourses(Long lessonScheduleId, String grade) {
        List<String> courses = lessonPlanMapper.getCourses(lessonScheduleId, grade);
        Collections.sort(courses, Collator.getInstance(java.util.Locale.CHINA));
        return courses;
    }

    @Override
    public List<String> getTeachers(Long lessonScheduleId) {
        List<String> teachers = new ArrayList<>();
        List<String> list = lessonPlanMapper.getTeachers(lessonScheduleId);
        list.forEach(names -> {
            for (String name : names.split("\\+")) {
                if (!teachers.contains(name)) {
                    teachers.add(name);
                }
            }
        });
        Collections.sort(teachers);
        return teachers;
    }

    @Override
    public List<String> getTeachers(Long lessonScheduleId, String grade) {
        List<String> teachers = new ArrayList<>();
        List<String> list = lessonPlanMapper.getTeachersByGrade(lessonScheduleId, grade);
        list.forEach(names -> {
            for (String name : names.split("\\+")) {
                if (!teachers.contains(name)) {
                    teachers.add(name);
                }
            }
        });
        Collections.sort(teachers);
        return teachers;
    }

    @Override
    public void deleteByAdminclass(LessonPlan form) {
        lessonPlanMapper.deleteByAdminclass(form.getLessonScheduleId(), form.getAdminclass());
    }

    @Override
    public void copy(Long sourceId, Long targetId) {
        lessonPlanMapper.copy(sourceId, targetId);
    }

    @Override
    public void cleanTimes(Long lessonScheduleId) {
        UpdateWrapper query = new UpdateWrapper<LessonPlan>();
        query.eq("lesson_schedule_id", lessonScheduleId);
        query.setSql("times = times_pres");
        update(query);
    }

    @Override
    public void saveTeacher(LessonPlanForm form) {
        Assert.isTrue(form.getIds() != null, "ids is null");
        Assert.isTrue(!form.getIds().isEmpty(), "ids is null");
        UpdateWrapper query = new UpdateWrapper();
        query.set("teacher", form.getTeacher());
        query.in("id", form.getIds());
        update(query);
    }

    /**
     * 批量删除任课信息
     *
     * @param ids 需要删除的任课信息ID
     * @return 结果
     */
    @Override
    public int deleteLessonPlanByIds(Long[] ids) {
        return lessonPlanMapper.deleteLessonPlanByIds(ids);
    }

    @Override
    public void deleteByLessonScheduleId(Long lessonScheduleId) {
        lessonPlanMapper.deleteByLessonScheduleId(lessonScheduleId);
    }

    @Override
    public List<String> getGrades(Long lessonScheduleId) {
        List<String> grades = lessonPlanMapper.getGrades(lessonScheduleId);
        ChineseNumberComparator comparator = new ChineseNumberComparator();
        Collections.sort(grades, (a, b) -> comparator.compare(a, b));
        return grades;
    }

    @Override
    public List<String> getAdminclasses(Long lessonScheduleId) {
        List<String> adminclasses = lessonPlanMapper.getAdminclasses(lessonScheduleId);
        ChineseNumberComparator comparator = new ChineseNumberComparator();
        Collections.sort(adminclasses, (a, b) -> comparator.compare(a, b));
        return adminclasses;
    }

    @Override
    public List<Grade> getGradeAndCourse(Long lessonScheduleId) {
        List<LessonPlan> datas = lessonPlanMapper.selectGradeAndCourse(lessonScheduleId);
        List<Grade> grades = new ArrayList<>();
        Map<String, Grade> map = new HashMap<>();
        for (LessonPlan plan : datas) {
            Grade grade = map.get(plan.getGrade());
            if (grade == null) {
                grade = new Grade();
                grade.setName(plan.getGrade());
                map.put(plan.getGrade(), grade);
                grades.add(grade);
            }
            grade.getCourses().add(new Course(plan.getGrade(), plan.getCourse()));
        }
        ChineseNumberComparator comparator = new ChineseNumberComparator();
        Collections.sort(grades, (a, b) -> comparator.compare(a.getName(), b.getName()));
        return grades;
    }

    @Override
    public List<Grade> getGradeAndAdminclass(Long lessonScheduleId) {
        List<LessonPlan> datas = lessonPlanMapper.getGradeAndAdminclass(lessonScheduleId);
        List<Grade> grades = new ArrayList<>();
        Map<String, Grade> map = new HashMap<>();
        for (LessonPlan plan : datas) {
            Grade grade = map.get(plan.getGrade());
            if (grade == null) {
                grade = new Grade();
                grade.setName(plan.getGrade());
                map.put(plan.getGrade(), grade);
                grades.add(grade);
            }
            grade.getAdminclasses().add(new Adminclass(plan.getGrade(), plan.getAdminclass()));
        }
        ChineseNumberComparator comparator = new ChineseNumberComparator();
        Collections.sort(grades, (a, b) -> comparator.compare(a.getName(), b.getName()));
        return grades;
    }

    @Override
    public List<LessonPlan> getTeacherCourses(Long lessonScheduleId) {
        List<LessonPlan> datas = lessonPlanMapper.getTeacherCourses(lessonScheduleId);
        return datas;
    }

    @Override
    public List<LessonPlan> getByLessonScheduleId(Long lessonScheduleId) {
        return lessonPlanMapper.getAll(lessonScheduleId);
    }

    @Override
    protected ImportEntityData initImportEntityData(List<LessonPlan> sources) {
        ImportEntityData data = super.initImportEntityData(sources);
        if (ObjectUtil.isNotEmpty(sources)) {
            LessonPlan plan = sources.get(0);
            List<LessonPlan> plans = lessonPlanMapper.selectByLessonScheduleId(plan.getLessonScheduleId());
            data.setDatas(plans);
        }
        return data;
    }

    @Override
    public void sync(Long lessonScheduleId) {
        LessonSchedule schedule = lessonScheduleService.getById(lessonScheduleId);
//        TeachCalendar calendar = teachCalendarService.getTeachCalendarCurrent(schedule.getSchoolId());
        TeachCalendar calendar = teachCalendarService.getById(schedule.getTeachCalenderId());
        List<LessonPlan> plans = getByLessonScheduleId(lessonScheduleId);
        List<Lesson> lessons = converter.toLessonList(plans);
        List<Lesson> weekLessons = new ArrayList<>();
        // 单双周
        lessons.forEach(lesson -> {
            if (lesson.getCourse().indexOf("+") > 0) {
                String[] courses = lesson.getCourse().split("\\+");
                String[] teachers = lesson.getTeacher().split("\\+");
                if (Math.min(courses.length, teachers.length) == 2) {
                    lesson.setCourse(courses[0]);
                    lesson.setTeacher(teachers[0]);
                    lesson.setWeekType(Lesson.WEEK_TYPE_EVEN);
                    Lesson weekLesson = converter.cloneLesson(lesson);
                    weekLesson.setCourse(courses[1]);
                    weekLesson.setTeacher(teachers[1]);
                    weekLesson.setWeekType(Lesson.WEEK_TYPE_ODD);
                    weekLessons.add(weekLesson);
                }
            }
        });
        lessons.addAll(weekLessons);
        lessons.forEach(lesson -> {
            lesson.setId(null);
            lesson.setSchoolId(schedule.getSchoolId());
            lesson.setYear(calendar.getYear());
            lesson.setTerm(calendar.getTerm());
            lesson.setTeachCalendarId(calendar.getId());
        });
        lessonService.importEntity(lessons);
    }
}
