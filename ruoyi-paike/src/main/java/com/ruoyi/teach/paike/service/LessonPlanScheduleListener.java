package com.ruoyi.teach.paike.service;

import com.ruoyi.teach.paike.entity.ScheduleData;
import com.ruoyi.teach.paike.entity.SchedulePlan;
import com.ruoyi.teach.paike.entity.ScheduleTask;

import java.util.List;

public interface LessonPlanScheduleListener {

    void initSchedulePlan(ScheduleData data, List<SchedulePlan> plans);

    long getTime(ScheduleData data, Object obj, long time);

    void afterScheduling(ScheduleData data, List<ScheduleTask> lessonTasks, ScheduleTask task);

}
