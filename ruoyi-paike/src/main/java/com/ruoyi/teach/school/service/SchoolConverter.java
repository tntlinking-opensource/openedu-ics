package com.ruoyi.teach.school.service;

import com.ruoyi.teach.school.domain.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolConverter {

	Lesson toLesson(Lesson lesson);

}
