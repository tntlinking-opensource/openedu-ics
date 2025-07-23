package com.ruoyi.teach.paike.entity;

import lombok.Data;

@Data
public class Schedule {
    private LessonInfo info;
    private int weekday;
    private long slot;

    private int hours;
    private long timeAvailable;
    private long timeSuggest;
    private int weight;

    public Schedule() {
    }

    public Schedule(Schedule schedule) {
        this.info = schedule.info;
        this.timeAvailable = schedule.timeAvailable;
        this.hours = schedule.hours;
    }

    public String getAdminclass() {
        return info.getAdminclassName();
    }

    public String getTeacher() {
        return info.getTeacherName();
    }
}
