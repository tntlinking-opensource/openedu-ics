package com.ruoyi.teach.paike.service.schedule;

import com.alibaba.fastjson2.JSONArray;
import com.ruoyi.teach.paike.entity.ScheduleData;
import com.ruoyi.teach.paike.entity.SchedulePlan;
import com.ruoyi.teach.paike.entity.ScheduleRule;
import com.ruoyi.teach.paike.service.LessonScheduleConstant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LessonPlanScheduleSchoolListener extends LessonPlanScheduleListenerParent {

    @Override
    protected String getRuleName() {
        return LessonScheduleConstant.RULE_SCHOOL;
    }

    @Override
    public void initSchedulePlan(ScheduleData data, List<SchedulePlan> plans) {
        ScheduleRule rule = getScheduleRule(data);
        if(rule == null){
            return;
        }
        plans.forEach(plan-> plan.setTimes(plan.getTimes() & rule.getTimes()));
    }

    @Override
    public long getTime(ScheduleData data, Object obj, long time) {
        for (ScheduleRule rule : data.getRules()) {
            if (LessonScheduleConstant.RULE_SCHOOL.equals(rule.getName())) {
                return time & rule.getTimes();
            }
        }
        return time;
    }

}
