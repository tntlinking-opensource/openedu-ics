package com.ruoyi.teach.paike.service.schedule;

import com.ruoyi.teach.paike.entity.*;
import com.ruoyi.teach.paike.service.LessonScheduleConstant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonPlanScheduleCourseListener extends LessonPlanScheduleListenerParent {

    @Override
    protected String getRuleName() {
        return LessonScheduleConstant.RULE_COURSE;
    }

    @Override
    public void initSchedulePlan(ScheduleData data, List<SchedulePlan> plans) {
        getScheduleRules(data).forEach(rule -> {
            plans.forEach(plan -> {
                if (plan.getCourse().equals(rule.getCode())) {
                    plan.setTimes(plan.getTimes() & rule.getTimes());
                }
            });
        });
    }

    @Override
    public long getTime(ScheduleData data, Object obj, long time) {
        if (obj instanceof Course) {
            Course course = (Course) obj;
            for (ScheduleRule rule : data.getRules()) {
                if (LessonScheduleConstant.RULE_COURSE.equals(rule.getName())) {
                    if (course.getName().equals(rule.getCode())) {
                        time = time & rule.getTimes();
                    }
                }
            }
        } else if (obj instanceof SchedulePlan) {
            SchedulePlan plan = (SchedulePlan) obj;
            for (String courseName : plan.getCourses()) {
                Course course = data.getCourse(courseName);
                time = time & course.getTime();
            }
        } else if (obj instanceof ScheduleTask) {
            ScheduleTask task = (ScheduleTask) obj;
            Course course = data.getCourse(task.getCourse());
            if (course == null) {
                System.out.println(task.getCourse());
            }
            time = time & course.getTime();
        }
        return time;
    }

}
