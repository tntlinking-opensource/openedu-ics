package com.ruoyi.teach.paike.entity;

import lombok.Data;

import java.util.List;

@Data
public class ScheduleTask {

    private LessonInfo info;
    private SchedulePlan plan;

    private Integer weekday = 5;
    private Integer weekdayStart;
    private Integer weekdayEnd;
    private Integer hour;
    private long time;
    private int timeIndex;
    private long timeAvailable;
    private long timeSuggest;
    private int weight;

    public String getGrade() {
        return plan.getGrade();
    }

    public String getCourse() {
        return plan.getCourse();
    }

    public String getCourseName() {
        return plan.getCourseName();
    }

    public List<String> getAdminclasses() {
        return plan.getAdminclasses();
    }

    public List<String> getTeachers() {
        return plan.getTeachers();
    }

    public String getTeacher() {
        return plan.getTeachers().get(0);
    }
}
