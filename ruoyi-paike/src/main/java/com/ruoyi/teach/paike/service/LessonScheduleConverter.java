package com.ruoyi.teach.paike.service;

import com.ruoyi.teach.paike.domain.ClassroomSpecial;
import com.ruoyi.teach.paike.domain.LessonPlan;
import com.ruoyi.teach.paike.domain.LessonScheduleRule;
import com.ruoyi.teach.paike.entity.ScheduleClassroom;
import com.ruoyi.teach.paike.entity.ScheduleRule;
import com.ruoyi.teach.school.domain.Lesson;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonScheduleConverter {

    ScheduleRule toScheduleRule(LessonScheduleRule rule);

    List<ScheduleRule> toScheduleRuleList(List<LessonScheduleRule> rules);

    ScheduleClassroom toScheduleClassroom(ClassroomSpecial rule);

    List<ScheduleClassroom> toScheduleClassroomList(List<ClassroomSpecial> rooms);

    Lesson toLesson(LessonPlan plan);

    List<Lesson> toLessonList(List<LessonPlan> plans);

    Lesson cloneLesson(Lesson lesson);
}
