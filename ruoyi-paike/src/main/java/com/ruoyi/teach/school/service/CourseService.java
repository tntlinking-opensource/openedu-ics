package com.ruoyi.teach.school.service;

import com.ruoyi.common.core.service.EntityService;
import com.ruoyi.teach.school.domain.Course;

import java.util.List;

/**
 * 课程Service接口
 *
 * @author beangle
 * @date 2021-07-06
 */
public interface CourseService extends EntityService<Course> {
	/**
	 * 查询课程
	 *
	 * @param id 课程ID
	 * @return 课程
	 */
	public Course selectCourseById(Long id);

	/**
	 * 查询课程列表
	 *
	 * @param course 课程
	 * @return 课程集合
	 */
	public List<Course> selectCourseList(Course course);

	/**
	 * 新增课程
	 *
	 * @param schoolId
	 * @param course   课程
	 * @return 结果
	 */
	public int insertCourse(Long schoolId, Course course);

	/**
	 * 修改课程
	 *
	 * @param course 课程
	 * @return 结果
	 */
	public int updateCourse(Course course);

	/**
	 * 批量删除课程
	 *
	 * @param ids 需要删除的课程ID
	 * @return 结果
	 */
	public int deleteCourseByIds(Long schoolId, Long[] ids);

	Course getCourse(String name);

	List<Course> getAll(Long schoolId);

	void initSchool(Long schoolId);

	Course getByName(Long schoolId, String name);
}
