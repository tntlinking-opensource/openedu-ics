package com.ruoyi.teach.paike.service;

import com.ruoyi.teach.paike.entity.LessonRow;
import com.ruoyi.teach.paike.entity.ScheduleData;
import com.ruoyi.teach.paike.vo.ViewAdminclassForm;

import java.util.List;

public interface LessonTableService {

    void setRoom(ScheduleData data);

    List<LessonRow> getRowsByAdminclass(ScheduleData data, ViewAdminclassForm form);

    String exportAdminclass(ScheduleData data, List<LessonRow> rows, ViewAdminclassForm form);

}
