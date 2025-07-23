package com.ruoyi.teach.paike.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.teach.paike.PaikeUtil;
import com.ruoyi.teach.paike.domain.LessonPlan;
import com.ruoyi.teach.paike.domain.LessonScheduleRule;
import com.ruoyi.teach.paike.entity.ScheduleData;
import com.ruoyi.teach.paike.service.LessonPlanScheduleService;
import com.ruoyi.teach.paike.service.LessonPlanService;
import com.ruoyi.teach.paike.service.LessonScheduleTimeService;
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
@RequestMapping("/teach/paike/adjust")
public class LessonScheduleAdjustController extends LessonScheduleBaseController {

    @Autowired
    private LessonScheduleTimeService timeService;
    @Autowired
    private LessonPlanService lessonPlanService;
    @Autowired
    private LessonPlanScheduleService scheduleService;

    @GetMapping
    @PreAuthorize("@ss.hasPermi('teach.paike:adjust')")
    public AjaxResult getIndexDatas() {
        AjaxResult ajax = AjaxResult.success();
        Long lessonScheduleId = getLessonScheduleId();
        ScheduleData data = scheduleService.getScheduleData(lessonScheduleId);
        ajax.put("timeConfig", data.getTime());
        ajax.put("lessonPlans", data.getPlans());
        ajax.put("rules", data.getRules());
        return ajax;
    }

    /**
     * 课表调整
     */
    @PostMapping
    @PreAuthorize("@ss.hasPermi('teach.paike:adjust')")
    @Log(title = "课表调整", businessType = BusinessType.UPDATE)
    public AjaxResult saveOrUpdate(@RequestBody List<LessonPlan> plans) {
        lessonPlanService.updateBatchById(plans);
        return AjaxResult.success();
    }

}
