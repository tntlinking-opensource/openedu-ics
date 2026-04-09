package com.ruoyi.teach.paike.service;

import com.ruoyi.common.core.service.EntityService;
import com.ruoyi.teach.paike.domain.LessonScheduleTime;

import java.util.List;

/**
 * 作息安排Service接口
 *
 * @author ruoyi
 * @date 2021-10-25
 */
public interface LessonScheduleTimeService extends EntityService<LessonScheduleTime> {

    LessonScheduleTime getByLessonScheduleId(Long lessonScheduleId);

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
    void insertLessonScheduleTime(LessonScheduleTime lessonScheduleTime);

    /**
     * 修改作息安排
     *
     * @param lessonScheduleTime 作息安排
     * @return 结果
     */
    void updateLessonScheduleTime(LessonScheduleTime lessonScheduleTime);

    void copy(Long sourceId, Long targetId);
}
