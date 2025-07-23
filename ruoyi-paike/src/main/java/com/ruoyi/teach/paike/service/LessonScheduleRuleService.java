package com.ruoyi.teach.paike.service;

import com.ruoyi.common.core.service.EntityService;
import com.ruoyi.teach.paike.domain.LessonScheduleRule;

import java.util.List;

/**
 * 排课规则Service接口
 *
 * @author yzsoft
 * @date 2021-11-01
 */
public interface LessonScheduleRuleService extends EntityService<LessonScheduleRule> {

    /**
     * 查询排课规则列表
     *
     * @param lessonScheduleRule 排课规则
     * @return 排课规则集合
     */
    List<LessonScheduleRule> selectLessonScheduleRuleList(LessonScheduleRule lessonScheduleRule);

    List<LessonScheduleRule> getByLessonScheduleId(Long lessonScheduleId);

    void saveBatch(Long lessonScheduleId, String name, List<LessonScheduleRule> rules);

    void copy(Long sourceId, Long targetId);
}
