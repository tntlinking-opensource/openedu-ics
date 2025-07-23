package com.ruoyi.teach.school.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.teach.school.domain.TeachCalendar;
import com.ruoyi.teach.school.domain.Teacher;
import com.ruoyi.teach.school.service.TeachCalendarService;
import com.ruoyi.teach.system.domain.School;
import com.ruoyi.teach.system.service.TeachService;
import org.springframework.beans.factory.annotation.Autowired;

public class SchoolBaseController extends BaseController {

    @Autowired
    protected TeachService teachService;
    @Autowired
    protected TeachCalendarService teachCalendarService;

    protected School getSchool() {
        return teachService.getSchool();
    }

    protected Teacher getTeacher() {
        return teachService.getTeacher();
    }

    protected Long getSchoolId(){
        return teachService.getSchoolId();
    }

    protected TeachCalendar getTeachCalendarCurrent(Long schoolId){
        return teachCalendarService.getTeachCalendarCurrent(schoolId);
    }
}
