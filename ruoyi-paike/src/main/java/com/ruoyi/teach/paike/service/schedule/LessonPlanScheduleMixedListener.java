package com.ruoyi.teach.paike.service.schedule;

import com.alibaba.fastjson2.JSONArray;
import com.ruoyi.teach.paike.entity.ScheduleData;
import com.ruoyi.teach.paike.entity.SchedulePlan;
import com.ruoyi.teach.paike.service.LessonScheduleConstant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LessonPlanScheduleMixedListener extends LessonPlanScheduleListenerParent {

    @Override
    protected String getRuleName() {
        return LessonScheduleConstant.RULE_ADMINCLASS_MIXED;
    }

    @Override
    public void initSchedulePlan(ScheduleData data, List<SchedulePlan> plans) {
        getScheduleRules(data).forEach(r -> {
            String course = r.get("course");
            JSONArray adminclassNames = r.getArray("adminclassNames");
            List<SchedulePlan> ps = plans.stream().filter(plan ->
                    plan.getCourse().indexOf(course) >= 0 && adminclassNames.containsAll(plan.getAdminclasses())
            ).collect(Collectors.toList());
            if (ps.size() > 1) {
                SchedulePlan plan = ps.remove(0);
                for (SchedulePlan p : ps) {
                    plan.addLessonPlans(p.getPlans());
                }
                plans.removeAll(ps);
            }
        });
    }

}
