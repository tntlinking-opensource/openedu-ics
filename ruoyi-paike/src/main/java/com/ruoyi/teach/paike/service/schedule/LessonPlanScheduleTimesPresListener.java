package com.ruoyi.teach.paike.service.schedule;

import com.ruoyi.teach.paike.PaikeUtil;
import com.ruoyi.teach.paike.domain.LessonPlan;
import com.ruoyi.teach.paike.entity.Adminclass;
import com.ruoyi.teach.paike.entity.ScheduleData;
import com.ruoyi.teach.paike.entity.SchedulePlan;
import com.ruoyi.teach.paike.entity.Teacher;
import com.ruoyi.teach.paike.service.LessonScheduleConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 预排课
 */
@Component
public class LessonPlanScheduleTimesPresListener extends LessonPlanScheduleListenerParent {

    @Override
    protected String getRuleName() {
        return LessonScheduleConstant.RULE_SCHOOL;
    }

    @Override
    public void initSchedulePlan(ScheduleData data, List<SchedulePlan> plans) {
    }

    @Override
    public long getTime(ScheduleData data, Object obj, long time) {
        for (SchedulePlan plan : data.getPlans()) {
            for (LessonPlan p : plan.getPlans()) {
                if (StringUtils.isBlank(p.getTimesPres())) {
                    continue;
                }
                long parseTime = PaikeUtil.parseTime(p.getTimesPres(), data.getWeekdays(), data.getTimeOfDay());
                if (obj instanceof Adminclass) {
                    Adminclass adminclass = (Adminclass) obj;
                    if (adminclass.getName().equals(p.getAdminclass())) {
                        time = time & parseTime;
                    }
                } else if (obj instanceof Teacher) {
                    Teacher teacher = (Teacher) obj;
                    if (teacher.getName().equals(p.getTeacher())) {
                        time = time & parseTime;
                    }
                }
            }
        }
        return time;
    }

}
