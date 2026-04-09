package com.ruoyi.teach.school.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.teach.school.domain.LessonTime;

import java.util.List;

/**
 * 上课节次Mapper接口
 *
 * @author beangle
 * @date 2021-07-06
 */
public interface LessonTimeMapper extends BaseMapper<LessonTime> {
    /**
     * 查询上课节次
     *
     * @param id 上课节次ID
     * @return 上课节次
     */
    public LessonTime selectLessonTimeById(Long id);

    /**
     * 查询上课节次列表
     *
     * @param lessonTime 上课节次
     * @return 上课节次集合
     */
    public List<LessonTime> selectLessonTimeList(LessonTime lessonTime);

    /**
     * 新增上课节次
     *
     * @param lessonTime 上课节次
     * @return 结果
     */
    public int insertLessonTime(LessonTime lessonTime);

    /**
     * 修改上课节次
     *
     * @param lessonTime 上课节次
     * @return 结果
     */
    public int updateLessonTime(LessonTime lessonTime);

    /**
     * 删除上课节次
     *
     * @param id 上课节次ID
     * @return 结果
     */
    public int deleteLessonTimeById(Long id);

    /**
     * 批量删除上课节次
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteLessonTimeByIds(Long[] ids);

    List<LessonTime> getLessonTime(Long schoolId);
}
