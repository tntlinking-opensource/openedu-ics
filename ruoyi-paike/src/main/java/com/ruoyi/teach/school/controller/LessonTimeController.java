package com.ruoyi.teach.school.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.teach.school.domain.LessonTime;
import com.ruoyi.teach.school.service.LessonTimeService;
import com.ruoyi.teach.system.domain.School;
import com.ruoyi.teach.system.service.TeachUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 上课节次Controller
 *
 * @author beangle
 * @date 2021-07-06
 */
@RestController
@RequestMapping("/teach/lessonTime")
public class LessonTimeController extends SchoolBaseController {

    @Autowired
    private LessonTimeService lessonTimeService;

    /**
     * 获取教师角色详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach:lessonTime')")
    @GetMapping(value = {"/getIndexDatas"})
    public AjaxResult getIndexDatas() {
        AjaxResult ajax = AjaxResult.success();
//        lessonTimeService.initSchool(getSchoolId());
        return ajax;
    }

    /**
     * 查询上课节次列表
     */
    @PreAuthorize("@ss.hasPermi('teach:lessonTime')")
    @GetMapping("/list")
    public TableDataInfo list(LessonTime lessonTime) {
//        startPage();
//        List<LessonTime> list = lessonTimeService.selectLessonTimeList(lessonTime);
        Long schoolId = getSchoolId();
        List<LessonTime> list = lessonTimeService.getBySchoolId(schoolId);
        return getDataTable(list);
    }

    /**
     * 导出上课节次列表
     */
    @PreAuthorize("@ss.hasPermi('teach:lessonTime')")
    @Log(title = "上课节次", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(LessonTime lessonTime) {
        List<LessonTime> list = lessonTimeService.selectLessonTimeList(lessonTime);
        ExcelUtil<LessonTime> util = new ExcelUtil<LessonTime>(LessonTime.class);
        return util.exportExcel(list, "上课节次数据");
    }

    /**
     * 获取上课节次详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach:lessonTime')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(lessonTimeService.selectLessonTimeById(id));
    }

    /**
     * 新增上课节次
     */
    @PreAuthorize("@ss.hasPermi('teach:lessonTime')")
    @Log(title = "上课节次", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LessonTime lessonTime) {
        School school = TeachUtils.getSchool();
        lessonTime.setSchoolId(school.getId());
        return toAjax(lessonTimeService.insertLessonTime(lessonTime));
    }

    /**
     * 修改上课节次
     */
    @PreAuthorize("@ss.hasPermi('teach:lessonTime')")
    @Log(title = "上课节次", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LessonTime lessonTime) {
        return toAjax(lessonTimeService.updateLessonTime(lessonTime));
    }

    /**
     * 删除上课节次
     */
    @PreAuthorize("@ss.hasPermi('teach:lessonTime')")
    @Log(title = "上课节次", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult remove(@RequestBody Long[] ids) {
        return toAjax(lessonTimeService.deleteLessonTimeByIds(ids));
    }

}
