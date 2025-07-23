package com.ruoyi.teach.paike.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.teach.paike.service.LessonPlanScheduleService;
import com.ruoyi.teach.paike.service.LessonPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课表发布Controller
 *
 * @author ruoyi
 * @date 2021-11-22
 */
@RestController
@RequestMapping("/teach/paike/sync")
public class LessonScheduleSyncController extends LessonScheduleBaseController {

    @Autowired
    private LessonPlanService lessonPlanService;

    @PostMapping
    @PreAuthorize("@ss.hasPermi('teach.paike:sync')")
    public AjaxResult sync() {
        lessonPlanService.sync(getLessonScheduleId());
        return AjaxResult.success();
    }
}
