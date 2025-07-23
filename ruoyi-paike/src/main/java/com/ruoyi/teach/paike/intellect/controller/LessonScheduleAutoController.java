package com.ruoyi.teach.paike.intellect.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.teach.paike.controller.LessonScheduleBaseController;
import com.ruoyi.teach.paike.service.LessonScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自动排课Controller
 *
 * @author yzsoft
 * @date 2021-11-09
 */
@RestController
@RequestMapping("/teach/paike/lessonScheduleAuto")
public class LessonScheduleAutoController extends LessonScheduleBaseController {

    @Autowired
    private LessonScheduleService lessonScheduleService;

    /**
     * 获取自动排课详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonScheduleAuto')")
    @GetMapping(value = {"/getIndexDatas"})
    public AjaxResult getIndexDatas() {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("lessonSchedule", lessonScheduleService.getById(getLessonScheduleId()));
        return ajax;
    }

    /**
     * 自动排课
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonScheduleAuto')")
    @Log(title = "自动排课", businessType = BusinessType.UPDATE)
    @PostMapping("/startSchedule")
    public AjaxResult startSchedule() {
        lessonScheduleService.start(getLessonScheduleId());
        return AjaxResult.success();
    }
}
