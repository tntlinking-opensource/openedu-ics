package com.ruoyi.teach.paike.service;

import com.ruoyi.teach.paike.domain.LessonSchedule;
import com.ruoyi.teach.paike.vo.LessonScheduleForm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaikeConverter {

    LessonSchedule toLessonSchedule(LessonScheduleForm form);

}
