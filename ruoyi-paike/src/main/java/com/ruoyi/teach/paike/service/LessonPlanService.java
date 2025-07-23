package com.ruoyi.teach.paike.service;

import com.ruoyi.common.core.service.EntityService;
import com.ruoyi.teach.paike.domain.LessonPlan;
import com.ruoyi.teach.paike.vo.Grade;
import com.ruoyi.teach.paike.vo.LessonPlanForm;

import java.util.List;

/**
 * 任课信息Service接口
 *
 * @author ruoyi
 * @date 2021-10-26
 */
public interface LessonPlanService extends EntityService<LessonPlan> {
    /**
     * 查询任课信息
     *
     * @param id 任课信息ID
     * @return 任课信息
     */
    LessonPlan selectLessonPlanById(Long id);

    /**
     * 查询任课信息列表
     *
     * @param lessonPlan 任课信息
     * @return 任课信息集合
     */
    List<LessonPlan> selectLessonPlanList(LessonPlan lessonPlan);

    /**
     * 批量删除任课信息
     *
     * @param ids 需要删除的任课信息ID
     * @return 结果
     */
    int deleteLessonPlanByIds(Long[] ids);

    void deleteByLessonScheduleId(Long lessonScheduleId);

    List<String> getGrades(Long lessonScheduleId);

    List<String> getAdminclasses(Long lessonScheduleId);

    List<Grade> getGradeAndCourse(Long lessonScheduleId);

    List<Grade> getGradeAndAdminclass(Long lessonScheduleId);

    List<LessonPlan> getTeacherCourses(Long lessonScheduleId);

    List<LessonPlan> getByLessonScheduleId(Long lessonScheduleId);

    void sync(Long lessonScheduleId);

    void saveBatch(LessonPlanForm form);

    List<String> getCourses(Long lessonScheduleId);

    List<String> getCourses(Long lessonScheduleId, String grade);

    List<String> getTeachers(Long lessonScheduleId);
    List<String> getTeachers(Long lessonScheduleId, String grade);

    void deleteByAdminclass(LessonPlan form);

    void copy(Long sourceId, Long targetId);

    void cleanTimes(Long lessonScheduleId);

    void saveTeacher(LessonPlanForm form);
}
