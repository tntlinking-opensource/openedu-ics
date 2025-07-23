package com.ruoyi.teach.paike.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.teach.paike.domain.LessonScheduleTime;

import java.util.List;

/**
 * 作息安排Mapper接口
 *
 * @author ruoyi
 * @date 2021-10-25
 */
public interface LessonScheduleTimeMapper extends BaseMapper<LessonScheduleTime> {
    /**
     * 查询作息安排
     *
     * @param id 作息安排ID
     * @return 作息安排
     */
    LessonScheduleTime selectLessonScheduleTimeById(Long id);

    /**
     * 查询作息安排列表
     *
     * @param lessonScheduleTime 作息安排
     * @return 作息安排集合
     */
    List<LessonScheduleTime> selectLessonScheduleTimeList(LessonScheduleTime lessonScheduleTime);

    /**
     * 新增作息安排
     *
     * @param lessonScheduleTime 作息安排
     * @return 结果
     */
    int insertLessonScheduleTime(LessonScheduleTime lessonScheduleTime);

    /**
     * 修改作息安排
     *
     * @param lessonScheduleTime 作息安排
     * @return 结果
     */
    int updateLessonScheduleTime(LessonScheduleTime lessonScheduleTime);

    /**
     * 批量删除作息安排
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteLessonScheduleTimeByIds(Long[] ids);

    LessonScheduleTime selectByLessonScheduleId(Long lessonScheduleId);

    void copy(Long sourceId, Long targetId);
}
