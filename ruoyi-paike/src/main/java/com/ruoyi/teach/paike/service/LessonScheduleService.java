package com.ruoyi.teach.paike.service;

import com.ruoyi.common.core.service.EntityService;
import com.ruoyi.teach.paike.domain.LessonSchedule;
import com.ruoyi.teach.paike.vo.LessonScheduleForm;

import java.util.List;

/**
 * 排课任务Service接口
 *
 * @author ruoyi
 * @date 2021-10-25
 */
public interface LessonScheduleService extends EntityService<LessonSchedule> {
    /**
     * 查询排课任务
     *
     * @param id 排课任务ID
     * @return 排课任务
     */
    LessonSchedule selectLessonScheduleById(Long id);

    LessonSchedule getBySchoolId(Long schoolId);

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
    void insertLessonSchedule(LessonScheduleForm form);

    /**
     * 修改排课任务
     *
     * @param lessonSchedule 排课任务
     * @return 结果
     */
    void updateLessonSchedule(LessonSchedule lessonSchedule);

    /**
     * 批量删除排课任务
     *
     * @param schoolId
     * @param ids      需要删除的排课任务ID
     * @return 结果
     */
    int deleteLessonScheduleByIds(Long schoolId, Long[] ids);

    void start(Long lessonScheduleId);
}
