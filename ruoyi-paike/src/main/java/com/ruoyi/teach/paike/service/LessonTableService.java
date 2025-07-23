package com.ruoyi.teach.paike.service;

import com.ruoyi.teach.paike.entity.LessonRow;
import com.ruoyi.teach.paike.entity.ScheduleData;
import com.ruoyi.teach.paike.vo.ViewAdminclassForm;

import java.util.List;

public interface LessonTableService {

    void setRoom(ScheduleData data);

    List<LessonRow> getRowsByAdminclass(ScheduleData data, ViewAdminclassForm form);

    List<LessonRow> getRowsByTeacher(ScheduleData data);

    String exportAdminclass(ScheduleData data, List<LessonRow> rows, ViewAdminclassForm form);

    String exportTeacher(ScheduleData data, List<LessonRow> rows, ViewAdminclassForm form);

    String exportClassroom(ViewAdminclassForm form);

    String exportAdminclasses(ViewAdminclassForm form);

    String exportTeachers(ViewAdminclassForm form);
}
