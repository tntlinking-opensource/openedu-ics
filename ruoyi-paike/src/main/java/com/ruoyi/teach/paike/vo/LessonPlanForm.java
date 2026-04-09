package com.ruoyi.teach.paike.vo;

import com.ruoyi.teach.paike.domain.LessonPlan;
import lombok.Data;

import java.util.List;

@Data
public class LessonPlanForm {
    private List<Long> ids;
    private Long schoolId;
    private Long lessonScheduleId;
    private String adminclass;
    private String grade;
    private String teacher;
    private List<LessonPlan> plans;
    private List<Long> removePlanIds;
}
