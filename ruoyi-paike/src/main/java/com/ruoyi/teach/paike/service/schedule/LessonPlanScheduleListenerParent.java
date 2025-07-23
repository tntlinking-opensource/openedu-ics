package com.ruoyi.teach.paike.service.schedule;

import com.ruoyi.teach.paike.entity.ScheduleData;
import com.ruoyi.teach.paike.entity.SchedulePlan;
import com.ruoyi.teach.paike.entity.ScheduleRule;
import com.ruoyi.teach.paike.entity.ScheduleTask;
import com.ruoyi.teach.paike.service.LessonPlanScheduleListener;

import java.util.List;
import java.util.stream.Collectors;

public abstract class LessonPlanScheduleListenerParent implements LessonPlanScheduleListener {

    protected abstract String getRuleName();

    @Override
    public void initSchedulePlan(ScheduleData data, List<SchedulePlan> plans) {

    }

    @Override
    public long getTime(ScheduleData data, Object obj, long time) {
        return time;
    }

    protected List<ScheduleRule> getScheduleRules(ScheduleData data) {
        return data.getRules().stream().filter(rule -> getRuleName().equals(rule.getName()))
                .collect(Collectors.toList());
    }

    protected ScheduleRule getScheduleRule(ScheduleData data) {
        List<ScheduleRule> rules = getScheduleRules(data);
        return rules.isEmpty() ? null : rules.get(0);
    }

    @Override
    public void afterScheduling(ScheduleData data, List<ScheduleTask> lessonTasks, ScheduleTask task) {

    }
}
