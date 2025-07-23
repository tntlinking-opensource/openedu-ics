package com.ruoyi.teach.paike.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.teach.paike.domain.LessonPlan;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 任课信息Mapper接口
 *
 * @author ruoyi
 * @date 2021-10-26
 */
public interface LessonPlanMapper extends BaseMapper<LessonPlan> {
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
     * 新增任课信息
     *
     * @param lessonPlan 任课信息
     * @return 结果
     */
    int insertLessonPlan(LessonPlan lessonPlan);

    /**
     * 修改任课信息
     *
     * @param lessonPlan 任课信息
     * @return 结果
     */
    int updateLessonPlan(LessonPlan lessonPlan);

    /**
     * 批量删除任课信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteLessonPlanByIds(Long[] ids);

    @Select("select * from tpk_lesson_plan where lesson_schedule_id = #{lessonScheduleId}")
    List<LessonPlan> selectByLessonScheduleId(Long lessonScheduleId);

    @Select("select distinct grade from tpk_lesson_plan where lesson_schedule_id = #{lessonScheduleId}")
    List<String> getGrades(Long lessonScheduleId);

    @Select("select distinct adminclass from tpk_lesson_plan where lesson_schedule_id = #{lessonScheduleId}")
    List<String> getAdminclasses(Long lessonScheduleId);

    @Select("select grade, course from tpk_lesson_plan" +
            " where lesson_schedule_id = #{lessonScheduleId}" +
            " group by grade, course" +
            " order by grade, course")
    List<LessonPlan> selectGradeAndCourse(Long lessonScheduleId);

    @Select("select grade, adminclass from tpk_lesson_plan" +
            " where lesson_schedule_id = #{lessonScheduleId}" +
            " group by grade, adminclass" +
            " order by grade, adminclass")
    List<LessonPlan> getGradeAndAdminclass(Long lessonScheduleId);

    @Select("select teacher, course from tpk_lesson_plan" +
            " where lesson_schedule_id = #{lessonScheduleId}" +
            "       and teacher is not null" +
            "       and teacher <> ''" +
            " group by teacher, course" +
            " order by teacher, course")
    List<LessonPlan> getTeacherCourses(Long lessonScheduleId);

    @Select("select * from tpk_lesson_plan" +
            " where lesson_schedule_id = #{lessonScheduleId}")
    List<LessonPlan> getAll(Long lessonScheduleId);

    @Delete("delete from tpk_lesson_plan" +
            " where lesson_schedule_id = #{lessonScheduleId}")
    void deleteByLessonScheduleId(Long lessonScheduleId);

    void deleteByAdminclass(@Param("lessonScheduleId") Long lessonScheduleId, @Param("adminclass") String adminclass);

    List<String> getCourses(Long lessonScheduleId, String grade);

    @Select("select distinct teacher from tpk_lesson_plan" +
            " where lesson_schedule_id = #{arg0}" +
            " and teacher is not null" +
            " and teacher <> ''")
    List<String> getTeachers(Long lessonScheduleId);

    @Select("select distinct teacher from tpk_lesson_plan" +
            " where lesson_schedule_id = #{arg0}" +
            " and grade = #{arg1}" +
            " and teacher is not null" +
            " and teacher <> ''")
    List<String> getTeachersByGrade(Long lessonScheduleId, String grade);

    void copy(Long sourceId, Long targetId);

}
