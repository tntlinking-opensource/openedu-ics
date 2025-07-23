package com.ruoyi.teach.paike.intellect.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.teach.paike.controller.LessonScheduleRuleBaseController;
import com.ruoyi.teach.paike.domain.LessonScheduleRule;
import com.ruoyi.teach.paike.service.LessonScheduleTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 全校排课时间Controller
 *
 * @author ruoyi
 * @date 2021-10-28
 */
@RestController
@RequestMapping("/teach/paike/schoolTimes")
public class SchoolTimesController extends LessonScheduleRuleBaseController {

    @Autowired
    private LessonScheduleTimeService lessonScheduleTimeService;

    @Override
    protected String getName() {
        return "全校不排课时间";
    }

    /**
     * 获取全校排课时间详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:schoolTimes')")
    @GetMapping(value = {"/getIndexDatas"})
    public AjaxResult getIndexDatas() {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("timeConfig", lessonScheduleTimeService.getByLessonScheduleId(getLessonScheduleId()));
        return ajax;
    }

    /**
     * 查询全校排课时间列表
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:schoolTimes')")
    @GetMapping("/list")
    public TableDataInfo list(LessonScheduleRule rule) {
        return super.list(rule);
    }

    /**
     * 修改全校排课时间
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:schoolTimes')")
    @Log(title = "全校排课时间", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult saveOrUpdate(@RequestBody List<LessonScheduleRule> rules) {
        return super.saveOrUpdate(rules);
    }
}
