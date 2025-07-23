package com.ruoyi.teach.paike.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.teach.paike.entity.LessonRow;
import com.ruoyi.teach.paike.entity.ScheduleData;
import com.ruoyi.teach.paike.service.LessonPlanScheduleService;
import com.ruoyi.teach.paike.service.LessonTableService;
import com.ruoyi.teach.paike.vo.ViewAdminclassForm;
import com.ruoyi.teach.school.service.LessonTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课表调整Controller
 *
 * @author ruoyi
 * @date 2021-11-22
 */
@RestController
@RequestMapping("/teach/paike/view")
public class LessonScheduleViewController extends LessonScheduleBaseController {

    @Autowired
    private LessonPlanScheduleService scheduleService;
    @Autowired
    private LessonTableService lessonTableService;
    @Autowired
    private LessonTimeService lessonTimeService;

    @GetMapping("/adminclass")
    @PreAuthorize("@ss.hasPermi('teach.paike:view')")
    public AjaxResult adminclass() {
        AjaxResult ajax = AjaxResult.success();
        ScheduleData data = scheduleService.getScheduleDataBySchoolId(getSchoolId());
        List<LessonRow> rows = lessonTableService.getRowsByAdminclass(data, null);
        ajax.put("timeConfig", data.getTime());
        ajax.put("rows", rows);
        ajax.put("plans", data.getPlans());
        return ajax;
    }

    @PostMapping("/adminclass")
    @PreAuthorize("@ss.hasPermi('teach.paike:view')")
    public AjaxResult exportAdminclass(@RequestBody ViewAdminclassForm form) {
        ScheduleData data = scheduleService.getScheduleDataBySchoolId(getSchoolId());
        List<LessonRow> rows = lessonTableService.getRowsByAdminclass(data, form);
        String filename = lessonTableService.exportAdminclass(data, rows, form);
        return AjaxResult.success(filename);
    }

    @GetMapping("/teacher")
    @PreAuthorize("@ss.hasPermi('teach.paike:view')")
    public AjaxResult teacher() {
        AjaxResult ajax = AjaxResult.success();
        ScheduleData data = scheduleService.getScheduleDataBySchoolId(getSchoolId());
        List<LessonRow> rows = lessonTableService.getRowsByTeacher(data);
        ajax.put("timeConfig", data.getTime());
        ajax.put("rows", rows);
        ajax.put("plans", data.getPlans());
        return ajax;
    }

    @PostMapping("/teacher")
    @PreAuthorize("@ss.hasPermi('teach.paike:view')")
    public AjaxResult exportTeacher(@RequestBody ViewAdminclassForm form) {
        ScheduleData data = scheduleService.getScheduleDataBySchoolId(getSchoolId());
        List<LessonRow> rows = lessonTableService.getRowsByTeacher(data);
        String filename = lessonTableService.exportTeacher(data, rows, form);
        return AjaxResult.success(filename);
    }

    @GetMapping("/classroom")
    @PreAuthorize("@ss.hasPermi('teach.paike:view')")
    public AjaxResult classroom() {
        AjaxResult ajax = AjaxResult.success();
        ScheduleData data = scheduleService.getScheduleDataBySchoolId(getSchoolId());
        lessonTableService.setRoom(data);
        ajax.put("classrooms", data.getClassrooms());
        ajax.put("timeConfig", data.getTime());
        ajax.put("plans", data.getPlans());
        ajax.put("times", lessonTimeService.getBySchoolId(data.getSchoolId()));
        return ajax;
    }

    @PostMapping("/classroom")
    @PreAuthorize("@ss.hasPermi('teach.paike:view')")
    public AjaxResult exportClassroom(@RequestBody ViewAdminclassForm form) {
        form.setSchoolId(getSchoolId());
        String filename = lessonTableService.exportClassroom(form);
        return AjaxResult.success(filename);
    }

    @GetMapping("/adminclasses")
    @PreAuthorize("@ss.hasPermi('teach.paike:view')")
    public AjaxResult adminclasses() {
        AjaxResult ajax = AjaxResult.success();
        ScheduleData data = scheduleService.getScheduleDataBySchoolId(getSchoolId());
        lessonTableService.setRoom(data);
        ajax.put("timeConfig", data.getTime());
        ajax.put("plans", data.getPlans());
        ajax.put("times", lessonTimeService.getBySchoolId(data.getSchoolId()));
        return ajax;
    }

    @PostMapping("/adminclasses")
    @PreAuthorize("@ss.hasPermi('teach.paike:view')")
    public AjaxResult exportAdminclasses(@RequestBody ViewAdminclassForm form) {
        form.setSchoolId(getSchoolId());
        String filename = lessonTableService.exportAdminclasses(form);
        return AjaxResult.success(filename);
    }

    @GetMapping("/teachers")
    @PreAuthorize("@ss.hasPermi('teach.paike:view')")
    public AjaxResult teachers() {
        return adminclass();
    }

    @PostMapping("/teachers")
    @PreAuthorize("@ss.hasPermi('teach.paike:view')")
    public AjaxResult exportTeachers(@RequestBody ViewAdminclassForm form) {
        form.setSchoolId(getSchoolId());
        String filename = lessonTableService.exportTeachers(form);
        return AjaxResult.success(filename);
    }

    @GetMapping("/times")
    @PreAuthorize("@ss.hasPermi('teach.paike:view')")
    public AjaxResult times() {
        return adminclass();
    }
}
