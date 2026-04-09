package com.ruoyi.teach.paike.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.teach.paike.domain.LessonScheduleTime;
import com.ruoyi.teach.paike.service.LessonScheduleTimeService;
import com.ruoyi.teach.school.controller.SchoolBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 作息安排Controller
 *
 * @author ruoyi
 * @date 2021-10-25
 */
@RestController
@RequestMapping("/teach/paike/time")
public class LessonScheduleTimeController extends LessonScheduleBaseController {

    @Autowired
    private LessonScheduleTimeService lessonScheduleTimeService;

    /**
     * 获取作息安排详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:time')")
    @GetMapping(value = {"/getIndexDatas"})
    public AjaxResult getIndexDatas() {
        AjaxResult ajax = AjaxResult.success();
        return ajax;
    }

    /**
     * 查询作息安排列表
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:time')")
    @GetMapping("/list")
    public TableDataInfo list(LessonScheduleTime lessonScheduleTime) {
        startPage();
        List<LessonScheduleTime> list = lessonScheduleTimeService.selectLessonScheduleTimeList(lessonScheduleTime);
        return getDataTable(list);
    }

    /**
     * 获取作息安排详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:time')")
    @GetMapping("/getInfo")
    public AjaxResult getInfo() {
        AjaxResult ajax = AjaxResult.success();
        ajax.put(AjaxResult.DATA_TAG, lessonScheduleTimeService.getByLessonScheduleId(getLessonScheduleId()));
        return ajax;
    }

    /**
     * 获取作息安排详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:time')")
    @GetMapping(value = {"/getEditDatas"})
    public AjaxResult getEditDatas() {
        AjaxResult ajax = getIndexDatas();
        return ajax;
    }

    /**
     * 新增作息安排
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:time')")
    @Log(title = "作息安排", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LessonScheduleTime lessonScheduleTime) {
        lessonScheduleTime.setSchoolId(getSchoolId());
        lessonScheduleTime.setLessonScheduleId(getLessonScheduleId());
        lessonScheduleTimeService.insertLessonScheduleTime(lessonScheduleTime);
        return AjaxResult.success();
    }

    /**
     * 修改作息安排
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:time')")
    @Log(title = "作息安排", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LessonScheduleTime lessonScheduleTime) {
        lessonScheduleTimeService.updateLessonScheduleTime(lessonScheduleTime);
        return AjaxResult.success();
    }
}
