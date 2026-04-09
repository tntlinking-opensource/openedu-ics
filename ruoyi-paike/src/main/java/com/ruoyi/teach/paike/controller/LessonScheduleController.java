package com.ruoyi.teach.paike.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.teach.paike.domain.LessonSchedule;
import com.ruoyi.teach.paike.service.LessonScheduleService;
import com.ruoyi.teach.paike.vo.LessonScheduleForm;
import com.ruoyi.teach.school.controller.SchoolBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 排课任务Controller
 */
@RestController
@RequestMapping("/teach/paike/lessonSchedule")
public class LessonScheduleController extends SchoolBaseController {

    @Autowired
    private LessonScheduleService lessonScheduleService;

    /**
     * 获取排课任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonSchedule')")
    @GetMapping(value = {"/getIndexDatas"})
    public AjaxResult getIndexDatas() {
        AjaxResult ajax = AjaxResult.success();
        return ajax;
    }

    /**
     * 查询排课任务列表
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonSchedule')")
    @GetMapping("/list")
    public TableDataInfo list(LessonSchedule lessonSchedule) {
        startPage();
        lessonSchedule.setSchoolId(getSchoolId());
        List<LessonSchedule> list = lessonScheduleService.selectLessonScheduleList(lessonSchedule);
        return getDataTable(list);
    }

    /**
     * 获取排课任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonSchedule')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") Long id) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put(AjaxResult.DATA_TAG, lessonScheduleService.selectLessonScheduleById(id));
        return ajax;
    }

    /**
     * 获取排课任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonSchedule')")
    @GetMapping(value = {"/getEditDatas"})
    public AjaxResult getEditDatas() {
        AjaxResult ajax = getIndexDatas();
        return ajax;
    }

    /**
     * 新增排课任务
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonSchedule')")
    @Log(title = "排课任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LessonScheduleForm form) {
        form.setSchoolId(getSchoolId());
        lessonScheduleService.insertLessonSchedule(form);
        return AjaxResult.success();
    }

    /**
     * 修改排课任务
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonSchedule')")
    @Log(title = "排课任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LessonSchedule lessonSchedule) {
        lessonScheduleService.updateLessonSchedule(lessonSchedule);
        return AjaxResult.success();
    }

    /**
     * 删除排课任务
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonSchedule')")
    @Log(title = "排课任务", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult remove(@RequestBody Long[] ids) {
        return toAjax(lessonScheduleService.deleteLessonScheduleByIds(getSchoolId(), ids));
    }
}
