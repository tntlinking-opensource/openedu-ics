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
 * 合班Controller
 */
@RestController
@RequestMapping("/teach/paike/mixedClass")
public class MixedClassController extends LessonScheduleRuleBaseController {

    @Override
    protected String getName() {
        return "合班";
    }

    /**
     * 获取合班详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:mixedClass')")
    @GetMapping(value = {"/getIndexDatas"})
    public AjaxResult getIndexDatas() {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("lessonPlans", lessonPlanService.getByLessonScheduleId(getLessonScheduleId()));
        return ajax;
    }

    /**
     * 查询合班列表
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:mixedClass')")
    @GetMapping("/list")
    public TableDataInfo list(LessonScheduleRule rule) {
        return super.list(rule);
    }

    /**
     * 修改合班
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:mixedClass')")
    @Log(title = "合班", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult saveOrUpdate(@RequestBody List<LessonScheduleRule> rules) {
        return super.saveOrUpdate(rules);
    }

}
