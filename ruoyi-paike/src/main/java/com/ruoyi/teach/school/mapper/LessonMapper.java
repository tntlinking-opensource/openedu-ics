package com.ruoyi.teach.school.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.teach.school.domain.Adminclass;
import com.ruoyi.teach.school.domain.Course;
import com.ruoyi.teach.school.domain.Lesson;
import com.ruoyi.teach.school.domain.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课表管理Mapper接口
 *
 * @author ruoyi
 * @date 2021-07-14
 */
public interface LessonMapper extends BaseMapper<Lesson> {
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
	 * 删除课表管理
	 *
	 * @param id 课表管理ID
	 * @return 结果
	 */
	public int deleteLessonById(Long id);

	/**
	 * 批量删除课表管理
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteLessonByIds(Long[] ids);

	Long checkTime(Lesson lesson);

	List<Lesson> getLessonsBySchoolId(@Param("schoolId") Long schoolId, @Param("year") String year);

	List<Lesson> getLessonsByTerm(@Param("schoolId") Long schoolId, @Param("year") String year, @Param("term") String term);

	List<Course> getTeacherCourses(Teacher teacher);

	List<Course> getCourses(Long teachCalendarId);

    List<String> getSubjectByTeacher(@Param("calendarId") Long calendarId, @Param("teacherId") Long teacherId);

	List<Adminclass> getAdminclassByTeacher(@Param("calendarId") Long calendarId, @Param("teacherId") Long teacherId);

}
