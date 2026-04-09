package com.ruoyi.teach.paike.entity;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.teach.paike.PaikeUtil;
import com.ruoyi.teach.paike.domain.LessonScheduleTime;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ScheduleData {

    private Long schoolId;
    private Long scheduleId;
    private LessonScheduleTime time;
    private List<ScheduleRule> rules;
    private List<ScheduleClassroom> rooms;
    private List<SchedulePlan> plans;
    private Map<String, Adminclass> adminclasses;
    private List<String> adminclassNames;
    private Map<String, Teacher> teachers;
    private List<String> teacherNames;
    private Map<String, Course> courses;
    private Long timeOfWeek;
    private List<ScheduleTask> tasks;
    private List<String> classrooms;

    public ScheduleData() {
    }

    public ScheduleData(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setRules(List<ScheduleRule> rules) {
        this.rules = rules;
        for (ScheduleRule rule : rules) {
            rule.setData(JSON.parseObject(rule.getJson()));
            String times = PaikeUtil.formatTime(rule.getData().getString("times"));
            rule.setPlanTimes(times);
        }
    }

    public Integer getWeekdays() {
        return time.getWeekdays();
    }

    public Long getTimeOfWeek() {
        if (timeOfWeek == null) {
            timeOfWeek = 0L;
            for (int i = 0; i < getTimeLength(); i++) {
                timeOfWeek = (timeOfWeek << 1) + 1;
            }
        }
        return timeOfWeek;
    }

    public int getTimeLength() {
        return time.getWeekdays() * getTimeOfDay();
    }

    public Integer getTimeOfDay() {
        return time.getMorning() + time.getAfternoon() + time.getNight();
    }

    public Teacher getTeacher(String teacherName) {
        return teachers.get(teacherName);
    }

    public Adminclass getAdminclass(String adminclassName) {
        return adminclasses.get(adminclassName);
    }

    public Course getCourse(String course) {
        return courses.get(course);
    }
}
