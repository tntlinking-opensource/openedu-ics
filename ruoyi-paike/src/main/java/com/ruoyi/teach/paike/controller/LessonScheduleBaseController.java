package com.ruoyi.teach.paike.controller;

import com.ruoyi.teach.paike.service.LessonScheduleService;
import com.ruoyi.teach.school.controller.SchoolBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 排课任务Controller
 */
@RestController
@RequestMapping("/teach/paike/lessonSchedule")
public class LessonScheduleBaseController extends SchoolBaseController {

    @Autowired
    protected LessonScheduleService lessonScheduleService;

    protected Long getLessonScheduleId() {
        return lessonScheduleService.getBySchoolId(getSchoolId()).getId();
    }
}
