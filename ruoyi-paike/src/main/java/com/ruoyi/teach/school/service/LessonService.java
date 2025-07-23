package com.ruoyi.teach.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.teach.school.domain.*;

import java.util.Date;
import java.util.List;

/**
 * 课表管理Service接口
 *
 * @author ruoyi
 * @date 2021-07-14
 */
public interface LessonService extends IService<Lesson> {
    /**
     * 查询课表管理
     *
     * @param id 课表管理ID
     * @return 课表管理
     */
    public Lesson selectLessonById(Long id);

    /**
     * 查询课表管理列表
     *
     * @param lesson 课表管理
     * @return 课表管理集合
     */
    public List<Lesson> selectLessonList(Lesson lesson);

    /**
     * 新增课表管理
     *
     * @param lesson 课表管理
     * @return 结果
     */
    public int insertLesson(Lesson lesson);

    /**
     * 修改课表管理
     *
     * @param lesson 课表管理
     * @return 结果
     */
    public int updateLesson(Lesson lesson);

    /**
     * 批量删除课表管理
     *
     * @param ids 需要删除的课表管理ID
     * @return 结果
     */
    public int deleteLessonByIds(Long[] ids);

    /**
     * 删除课表管理信息
     *
     * @param id 课表管理ID
     * @return 结果
     */
    public int deleteLessonById(Long id);

    String importEntity(List<Lesson> sources);

    List<Lesson> getLessonsByTeacher(Teacher teacher);

    List<Lesson> getByTeacher(Teacher teacher);

    List<Lesson> getLessonsByAdminclassId(Long adminclassId);

    /**
     * 按年查询课程
     * @param schoolId
     * @param year
     * @return
     */
    List<Lesson> getLessonsBySchoolId(Long schoolId, String year);

    /**
     * 按学期查询课程
     * @param schoolId
     * @param teachCalendar
     * @return
     */
    List<Lesson> getLessonsByTerm(Long schoolId, TeachCalendar teachCalendar);

    List<Lesson> getByTeacherId(Long teacherId, Date startDate, Date endDate);

    List<Lesson> getLessonsByTeachCalendarId(Long teachCalendarId);

    void addTimeInclude(Long teachCalendarId, Long adminclassId, Long courseId, Long teacherId, List<String> weekTimes);

    void addTimeInclude(Lesson lesson, String weekTime);

    void removeTimeExclude(Long teachCalendarId, Long adminclassId, Long courseId, Long teacherId, List<String> weekTimes);

    void addTimeExclude(Long teachCalendarId, Long adminclassId, Long courseId, Long teacherId, List<String> weekTimes);

    void removeTimeInclude(Long teachCalendarId, Long adminclassId, Long courseId, Long teacherId, List<String> weekTimes);

    /**
     * 如果不存在，报错
     *
     * @param teachCalendarId
     * @param adminclassId
     * @param courseId
     * @param teacherId
     * @return
     */
    Lesson getLessonByTeacher(Long teachCalendarId, Long adminclassId, Long courseId, Long teacherId);

    /**
     * 如果不存在，新建
     *
     * @param teachCalendarId
     * @param adminclassId
     * @param courseId
     * @param teacherId
     * @return
     */
    Lesson getLessonByTeacherAndCourse(Long teachCalendarId, Long adminclassId, Long courseId, Long teacherId);

    List<Course> getCourses(Long teachCalendarId);

    List<Course> getTeacherCourses(Long teacherId);

    List<Course> getTeacherCourses(Teacher teacher);

    List<String> getWeekTimes(Long schoolId, Date lessonDate, String times);

    List<String> getWeekTimes(TeachCalendar teachCalendar, List<LessonTime> lessonTimes, Date lessonDate, String times);

    void initDateTimes(TeachCalendar teachCalendar, List<Lesson> lessons);

    List<String> getSubjectByTeacher(Long calendarId, Long teacherId);

    List<Adminclass> getAdminclassByTeacher(Long calendarId, Long teacherId);
}
