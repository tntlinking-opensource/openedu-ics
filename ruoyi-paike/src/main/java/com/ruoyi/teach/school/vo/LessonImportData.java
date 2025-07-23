package com.ruoyi.teach.school.vo;

import com.ruoyi.common.core.vo.ImportEntityData;
import com.ruoyi.teach.school.domain.Adminclass;
import com.ruoyi.teach.school.domain.Course;
import com.ruoyi.teach.school.domain.Lesson;
import com.ruoyi.teach.school.domain.Teacher;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LessonImportData extends ImportEntityData {
    private List<Adminclass> adminclasses = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Lesson> lessons = new ArrayList<>();
}
