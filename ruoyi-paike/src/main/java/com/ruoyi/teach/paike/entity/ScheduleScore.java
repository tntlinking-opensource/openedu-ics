package com.ruoyi.teach.paike.entity;

import java.util.List;

public class ScheduleScore {
    private List<Schedule> schedules;
    private int conflict;

    public ScheduleScore(List<Schedule> schedules, int conflict) {
        this.schedules = schedules;
        this.conflict = conflict;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public int getConflict() {
        return conflict;
    }

    public void setConflict(int conflict) {
        this.conflict = conflict;
    }
}
