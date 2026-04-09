package com.ruoyi.teach.paike.service.impl;

import com.ruoyi.common.core.service.impl.EntityServiceImpl;
import com.ruoyi.teach.paike.domain.LessonScheduleRule;
import com.ruoyi.teach.paike.mapper.LessonScheduleRuleMapper;
import com.ruoyi.teach.paike.service.LessonScheduleRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 排课规则Service业务层处理
 *
 * @author yzsoft
 * @date 2021-11-01
 */
@Service
public class LessonScheduleRuleServiceImpl extends EntityServiceImpl<LessonScheduleRuleMapper, LessonScheduleRule> implements LessonScheduleRuleService {

    @Autowired
    private LessonScheduleRuleMapper lessonScheduleRuleMapper;

    /**
     * 查询排课规则列表
     *
     * @param lessonScheduleRule 排课规则
     * @return 排课规则
     */
    @Override
    public List<LessonScheduleRule> selectLessonScheduleRuleList(LessonScheduleRule lessonScheduleRule) {
        return lessonScheduleRuleMapper.selectLessonScheduleRuleList(lessonScheduleRule);
    }

    @Override
    public List<LessonScheduleRule> getByLessonScheduleId(Long lessonScheduleId) {
        return lessonScheduleRuleMapper.selectAll(lessonScheduleId);
    }

    @Override
    @Transactional
    public void saveBatch(Long lessonScheduleId, String name, List<LessonScheduleRule> rules) {
        lessonScheduleRuleMapper.deleteByLessonScheduleIdAndName(lessonScheduleId, name);
        rules.forEach(c -> {
            c.setLessonScheduleId(lessonScheduleId);
            c.setName(name);
            if (c.getContent().length() > 255) {
                c.setContent(c.getContent().substring(0, 252) + "...");
            }
        });
        saveBatch(rules);
    }

    @Override
    public void copy(Long sourceId, Long targetId) {
        lessonScheduleRuleMapper.copy(sourceId, targetId);
    }

}
