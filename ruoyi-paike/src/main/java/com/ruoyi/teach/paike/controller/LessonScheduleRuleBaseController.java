package com.ruoyi.teach.paike.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.teach.paike.PaikeUtil;
import com.ruoyi.teach.paike.domain.LessonScheduleRule;
import com.ruoyi.teach.paike.domain.LessonScheduleTime;
import com.ruoyi.teach.paike.service.LessonPlanService;
import com.ruoyi.teach.paike.service.LessonScheduleRuleService;
import com.ruoyi.teach.paike.service.LessonScheduleTimeService;
import com.ruoyi.teach.paike.util.LessonPlanScheduleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public abstract class LessonScheduleRuleBaseController extends LessonScheduleBaseController {

    @Autowired
    protected LessonScheduleTimeService lessonScheduleTimeService;
    @Autowired
    protected LessonPlanService lessonPlanService;
    @Autowired
    private LessonScheduleRuleService ruleService;

    protected abstract String getName();

    /**
     * 查询班级排课时间列表
     */
    public TableDataInfo list(LessonScheduleRule rule) {
        rule.setLessonScheduleId(getLessonScheduleId());
        rule.setName(getName());
        List<LessonScheduleRule> list = ruleService.selectLessonScheduleRuleList(rule);
        if (!list.isEmpty()) {
            LessonScheduleTime time = lessonScheduleTimeService.getByLessonScheduleId(getLessonScheduleId());
            list.forEach(r -> {
                JSONObject json = JSONObject.parseObject(r.getJson());
                String times = LessonPlanScheduleUtil.formatTimes(time, json.getString("times"));
                json.put("times", times);
                r.setJson(json.toJSONString());
            });
        }
        return getDataTable(list);
    }

    /**
     * 修改班级排课时间
     */
    public AjaxResult saveOrUpdate(@RequestBody List<LessonScheduleRule> rules) {
        Long schoolId = getSchoolId();
        rules.forEach(c -> {
            c.setSchoolId(schoolId);
        });
        ruleService.saveBatch(getLessonScheduleId(), getName(), rules);
        return AjaxResult.success();
    }

}
