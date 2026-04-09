package com.ruoyi.teach.school.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.teach.school.domain.Course;

import java.util.List;

/**
 * 课程Mapper接口
 *
 * @author beangle
 * @date 2021-07-06
 */
public interface CourseMapper extends BaseMapper<Course> {
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
     * @param course 课程
     * @return 结果
     */
    public int insertCourse(Course course);

    /**
     * 修改课程
     *
     * @param course 课程
     * @return 结果
     */
    public int updateCourse(Course course);

    /**
     * 删除课程
     *
     * @param id 课程ID
     * @return 结果
     */
    public int deleteCourseById(Long id);

    /**
     * 批量删除课程
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCourseByIds(Long[] ids);
}
