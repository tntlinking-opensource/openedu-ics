package com.ruoyi.teach.paike.intellect.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.teach.paike.controller.LessonScheduleRuleBaseController;
import com.ruoyi.teach.paike.domain.LessonScheduleRule;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班级排课时间Controller
 *
 * @author ruoyi
 * @date 2021-10-31
 */
@RestController
@RequestMapping("/teach/paike/adminclassTimes")
public class AdminclassTimesController extends LessonScheduleRuleBaseController {

    @Override
    protected String getName() {
        return "班级不排课时间";
    }

    /**
     * 获取班级排课时间详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:adminclassTimes')")
    @GetMapping(value = {"/getIndexDatas"})
    public AjaxResult getIndexDatas() {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("timeConfig", lessonScheduleTimeService.getByLessonScheduleId(getLessonScheduleId()));
        ajax.put("adminclasses", lessonPlanService.getGradeAndAdminclass(getLessonScheduleId()));
        return ajax;
    }

    /**
     * 查询班级排课时间列表
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:adminclassTimes')")
    @GetMapping("/list")
    public TableDataInfo list(LessonScheduleRule rule) {
        return super.list(rule);
    }

    /**
     * 修改班级排课时间
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:adminclassTimes')")
    @Log(title = "班级排课时间", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult saveOrUpdate(@RequestBody List<LessonScheduleRule> rules) {
        return super.saveOrUpdate(rules);
    }

}
