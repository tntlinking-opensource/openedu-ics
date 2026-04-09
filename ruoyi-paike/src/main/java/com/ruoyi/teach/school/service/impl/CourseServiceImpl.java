package com.ruoyi.teach.school.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.vo.ImportEntityData;
import com.ruoyi.teach.school.domain.Course;
import com.ruoyi.teach.school.mapper.CourseMapper;
import com.ruoyi.teach.school.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程Service业务层处理
 *
 * @author beangle
 * @date 2021-07-06
 */
@Service
@EnableAsync
public class CourseServiceImpl extends SchoolEntityServiceImpl<CourseMapper, Course> implements CourseService {

	@Autowired
	private CourseMapper courseMapper;

	/**
	 * 查询课程
	 *
	 * @param id 课程ID
	 * @return 课程
	 */
	@Override
	public Course selectCourseById(Long id) {
		return courseMapper.selectCourseById(id);
	}

	/**
	 * 查询课程列表
	 *
	 * @param course 课程
	 * @return 课程
	 */
	@Override
	public List<Course> selectCourseList(Course course) {
		selectSchoolObjectList(course);
		return courseMapper.selectCourseList(course);
	}

	/**
	 * 新增课程
	 *
	 * @param schoolId
	 * @param course   课程
	 * @return 结果
	 */
	@Override
	@CacheEvict(value = {"Course"}, key = "'School_'+ #p0.schoolId", allEntries = true)
	public int insertCourse(Long schoolId, Course course) {
		course.setSchoolId(schoolId);
		return courseMapper.insertCourse(course);
	}

	/**
	 * 修改课程
	 *
	 * @param course 课程
	 * @return 结果
	 */
	@Override
	@CacheEvict(value = {"Course"}, key = "'School_'+ #p0.schoolId", allEntries = true)
	public int updateCourse(Course course) {
		return courseMapper.updateCourse(course);
	}

	/**
	 * 批量删除课程
	 *
	 * @param ids 需要删除的课程ID
	 * @return 结果
	 */
	@Override
	@CacheEvict(value = {"Course"}, key = "'School_'+ #p0", allEntries = true)
	public int deleteCourseByIds(Long schoolId, Long[] ids) {
		return courseMapper.deleteCourseByIds(ids);
	}

	@Override
	@Cacheable(value = "Course", key = "'School_'+ #p0 +':getAll'")
	public List<Course> getAll(Long schoolId) {
		QueryWrapper query = new QueryWrapper();
		return list(query, schoolId);
	}

	@Override
	public Course getCourse(String name) {
		Course course = new Course();
		course.setSchoolId(getSchool().getId());
		course.setName(name);
		return getOne(new QueryWrapper<>(course));
	}

	@CacheEvict(value = {"Course"}, key = "'School_'+ #p0", allEntries = true)
	public void initSchool(Long schoolId) {
		Course form = new Course();
		form.setSchoolId(schoolId);
		List<Course> list = courseMapper.selectCourseList(form);
		if (!list.isEmpty()) {
			return;
		}
		String[] names = "语文、数学、英语、科学、思想品德、社会、音乐、体育、美术、微机".split("、");
		List<Course> courses = new ArrayList<>();
		for (int i = 0; i < names.length; i++) {
			Course course = new Course();
			course.setSchoolId(schoolId);
			course.setSort(i + 1);
			course.setCode(String.format("%02d", i + 1));
			course.setName(names[i]);
			course.setStatus("0");
			courses.add(course);
		}
		saveBatch(courses);
	}

	@Override
	public Course getByName(Long schoolId, String name) {
		QueryWrapper query = new QueryWrapper();
		query.eq("school_id", schoolId);
		query.eq("name", name);
		List<Course> list = list(query);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	protected void importEntity(ImportEntityData data, Course source) {
		Assert.isTrue(StringUtils.isNotBlank(source.getCode()), "代码不能为空");
		Assert.isTrue(StringUtils.isNotBlank(source.getName()), "名称不能为空");

		Course form = getImportForm(source);
		List<Course> courses = list(new QueryWrapper<>(form));

		Assert.isTrue(courses.size() <= 1, "课程名称重复，无法导入");
		if (!courses.isEmpty()) {
			source.setId(courses.get(0).getId());
		}
//        saveOrUpdate(source);
		super.importEntity(data, source);
	}
}
