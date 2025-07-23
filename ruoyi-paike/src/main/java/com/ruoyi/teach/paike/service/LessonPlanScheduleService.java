package com.ruoyi.teach.paike.service;

import com.ruoyi.teach.paike.domain.LessonPlan;
import com.ruoyi.teach.paike.domain.LessonSchedule;
import com.ruoyi.teach.paike.entity.LessonRow;
import com.ruoyi.teach.paike.entity.ScheduleData;

import java.util.List;

public interface LessonPlanScheduleService {

    ScheduleData getScheduleDataBySchoolId(Long schoolId);

    ScheduleData getScheduleData(Long lessonScheduleId);

    void autoSchedule(LessonSchedule lessonSchedule, List<LessonPlan> plans);
}
