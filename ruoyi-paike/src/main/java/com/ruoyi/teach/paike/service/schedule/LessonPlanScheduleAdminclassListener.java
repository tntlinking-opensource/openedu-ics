package com.ruoyi.teach.paike.service.schedule;

import com.ruoyi.teach.paike.domain.LessonPlan;
import com.ruoyi.teach.paike.entity.*;
import com.ruoyi.teach.paike.service.LessonScheduleConstant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonPlanScheduleAdminclassListener extends LessonPlanScheduleListenerParent {

    @Override
    protected String getRuleName() {
        return LessonScheduleConstant.RULE_ADMINCLASS;
    }

    @Override
    public void initSchedulePlan(ScheduleData data, List<SchedulePlan> plans) {
        getScheduleRules(data).forEach(rule -> {
            plans.forEach(plan -> {
                if (plan.getAdminclasses().contains(rule.getCode())) {
                    plan.setTimes(plan.getTimes() & rule.getTimes());
                }
            });
        });
    }

    @Override
    public long getTime(ScheduleData data, Object obj, long time) {
        if (obj instanceof Adminclass) {
            Adminclass course = (Adminclass) obj;
            for (ScheduleRule rule : data.getRules()) {
                if (LessonScheduleConstant.RULE_ADMINCLASS.equals(rule.getName())) {
                    if (course.getName().equals(rule.getCode())) {
                        time = time & rule.getTimes();
                    }
                }
            }
        } else if (obj instanceof SchedulePlan) {
            SchedulePlan plan = (SchedulePlan) obj;
            for (String adminclassName : plan.getAdminclasses()) {
                Adminclass adminclass = data.getAdminclass(adminclassName);
                time = time & adminclass.getTime();
            }
        } else if (obj instanceof ScheduleTask) {
            ScheduleTask task = (ScheduleTask) obj;
            for (LessonPlan plan : task.getPlan().getPlans()) {
                Adminclass adminclass = data.getAdminclasses().get(plan.getAdminclass());
                time = time & adminclass.getTime();
            }
        }
        return time;
    }

    @Override
    public void afterScheduling(ScheduleData data, List<ScheduleTask> lessonTasks, ScheduleTask task) {
        task.getAdminclasses().forEach(adminclassName -> {
            Adminclass adminclass = data.getAdminclass(adminclassName);
//            System.out.println(adminclassName + ":" + PaikeUtil.toBinaryString(adminclass.getTime(), data.getWeekdays(), data.getTimeOfDay()));
            adminclass.setTime(adminclass.getTime() & (~task.getTime()));
//            System.out.println(adminclassName + ":" + PaikeUtil.toBinaryString(time, data.getWeekdays(), data.getTimeOfDay()));
//            System.out.println(adminclassName + ":" + PaikeUtil.toBinaryString(adminclass.getTime(), data.getWeekdays(), data.getTimeOfDay()));
        });
    }
}
