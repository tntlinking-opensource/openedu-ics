package com.ruoyi.teach.paike.entity;

import com.ruoyi.teach.paike.domain.LessonPlan;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SchedulePlan {
    private Long id;
    private List<LessonPlan> plans = new ArrayList<>();
    private List<String> adminclasses = new ArrayList<>();
    private List<String> teachers = new ArrayList<>();
    private List<String> courses = new ArrayList<>();
    private String hours;
    private int hour;
    private Long times = ~0L;
    private String configTimes;
    private String planTimes;
    private String classroom;

    public void addLessonPlan(LessonPlan plan) {
        plans.add(plan);
        this.hours = plan.getHours();
        this.hour = plan.getHour();
        if (!adminclasses.contains(plan.getAdminclass())) {
            adminclasses.add(plan.getAdminclass());
        }
        if (StringUtils.isNotBlank(plan.getTeacher())) {
            if (!teachers.contains(plan.getTeacher())) {
                teachers.add(plan.getTeacher());
            }
        }
        if (!courses.contains(plan.getCourse())) {
            courses.add(plan.getGrade() + plan.getCourse());
        }
    }

    public void addLessonPlans(List<LessonPlan> plans) {
        for (LessonPlan plan : plans) {
            addLessonPlan(plan);
        }
    }

    public String getCourse() {
        return courses.get(0);
    }

    public String getGrade() {
        return plans.get(0).getGrade();
    }

    public String getCourseName() {
        return plans.get(0).getCourse();
    }

    public String getTeacher() {
        return teachers.isEmpty() ? null : teachers.get(0);
    }

    public String getAdminclass() {
        return adminclasses.get(0);
    }

    public String getAdminclassName() {
        return adminclasses.stream().collect(Collectors.joining("、"));
    }

    public String getTimesPres() {
        return plans.get(0).getTimesPres();
    }
}
