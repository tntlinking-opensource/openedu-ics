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

}
