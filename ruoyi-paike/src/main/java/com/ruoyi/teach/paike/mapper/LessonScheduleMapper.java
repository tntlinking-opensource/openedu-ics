package com.ruoyi.teach.paike.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.teach.paike.domain.LessonSchedule;

import java.util.List;

/**
 * 排课任务Mapper接口
 *
 * @author ruoyi
 * @date 2021-10-25
 */
public interface LessonScheduleMapper extends BaseMapper<LessonSchedule> {
    /**
     * 查询排课任务
     *
     * @param id 排课任务ID
     * @return 排课任务
     */
    LessonSchedule selectLessonScheduleById(Long id);

    LessonSchedule selectBySchoolId(Long schoolId);

    /**
     * 查询排课任务列表
     *
     * @param lessonSchedule 排课任务
     * @return 排课任务集合
     */
    List<LessonSchedule> selectLessonScheduleList(LessonSchedule lessonSchedule);

    /**
     * 新增排课任务
     *
     * @param lessonSchedule 排课任务
     * @return 结果
     */
    int insertLessonSchedule(LessonSchedule lessonSchedule);

    /**
     * 修改排课任务
     *
     * @param lessonSchedule 排课任务
     * @return 结果
     */
    int updateLessonSchedule(LessonSchedule lessonSchedule);

    /**
     * 批量删除排课任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteLessonScheduleByIds(Long[] ids);

    void updateIsCurrent(LessonSchedule lessonSchedule);

    void deleteLessonScheduleTime(Long id);
}
