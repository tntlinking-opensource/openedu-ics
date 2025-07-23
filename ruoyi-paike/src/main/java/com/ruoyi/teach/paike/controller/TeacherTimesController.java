package com.ruoyi.teach.paike.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.teach.paike.domain.LessonScheduleRule;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教师排课时间Controller
 *
 * @author ruoyi
 * @date 2021-10-31
 */
@RestController
@RequestMapping("/teach/paike/teacherTimes")
public class TeacherTimesController extends LessonScheduleRuleBaseController {

    @Override
    protected String getName() {
        return "教师不排课时间";
    }

    /**
     * 获取教师排课时间详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:teacherTimes')")
    @GetMapping(value = {"/getIndexDatas"})
    public AjaxResult getIndexDatas() {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("timeConfig", lessonScheduleTimeService.getByLessonScheduleId(getLessonScheduleId()));
        ajax.put("teachers", lessonPlanService.getTeacherCourses(getLessonScheduleId()));
        return ajax;
    }

    /**
     * 查询教师排课时间列表
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:teacherTimes')")
    @GetMapping("/list")
    public TableDataInfo list(LessonScheduleRule rule) {
        return super.list(rule);
    }

    /**
     * 修改教师排课时间
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:teacherTimes')")
    @Log(title = "教师排课时间", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult saveOrUpdate(@RequestBody List<LessonScheduleRule> rules) {
        return super.saveOrUpdate(rules);
    }

}
