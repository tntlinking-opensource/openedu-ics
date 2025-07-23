package com.ruoyi.teach.paike.service.schedule;

import com.ruoyi.teach.paike.domain.LessonPlan;
import com.ruoyi.teach.paike.entity.*;
import com.ruoyi.teach.paike.service.LessonScheduleConstant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonPlanScheduleTeacherListener extends LessonPlanScheduleListenerParent {

    @Override
    protected String getRuleName() {
        return LessonScheduleConstant.RULE_TEACHER;
    }

    @Override
    public void initSchedulePlan(ScheduleData data, List<SchedulePlan> plans) {
        getScheduleRules(data).forEach(rule -> {
            plans.forEach(plan -> {
                if (plan.getTeachers().contains(rule.getCode())) {
                    plan.setTimes(plan.getTimes() & rule.getTimes());
                }
            });
        });
    }

    @Override
    public long getTime(ScheduleData data, Object obj, long time) {
        try{
            if (obj instanceof Teacher) {
                Teacher teacher = (Teacher) obj;
                for (ScheduleRule rule : data.getRules()) {
                    if (LessonScheduleConstant.RULE_TEACHER.equals(rule.getName())) {
                        if (teacher.getName().equals(rule.getCode())) {
                            time = time & rule.getTimes();
                        }
                    }
                }
            } else if (obj instanceof SchedulePlan) {
                SchedulePlan plan = (SchedulePlan) obj;
                for (String teacherName : plan.getTeachers()) {
                    Teacher teacher = data.getTeacher(teacherName);
                    time = time & teacher.getTime();
                }
            } else if (obj instanceof ScheduleTask) {
                ScheduleTask task = (ScheduleTask) obj;
                for (LessonPlan plan : task.getPlan().getPlans()) {
                    Teacher teacher = data.getTeachers().get(plan.getTeacher());
                    if(teacher != null){
                        time = time & teacher.getTime();
                    }
                }
            }
        }catch (Exception e){
        }
        return time;
    }

    @Override
    public void afterScheduling(ScheduleData data, List<ScheduleTask> lessonTasks, ScheduleTask task) {
        task.getTeachers().forEach(teacherName -> {
            Teacher teacher = data.getTeacher(teacherName);
            teacher.setTime(teacher.getTime() & (~task.getTime()));
        });
    }
}
