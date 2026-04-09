package com.ruoyi.teach.school.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.teach.school.domain.TeachCalendar;
import com.ruoyi.teach.school.service.TeachCalendarService;
import com.ruoyi.teach.system.domain.School;
import com.ruoyi.teach.system.service.TeachUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学年学期Controller
 *
 * @author beangle
 * @date 2021-07-05
 */
@RestController
@RequestMapping("/school/teachCalendar")
public class TeachCalendarController extends BaseController {

    @Autowired
    private TeachCalendarService teachCalendarService;

    /**
     * 查询学年学期列表
     */
    @PreAuthorize("@ss.hasPermi('school:teachCalendar')")
    @GetMapping("/list")
    public TableDataInfo list(TeachCalendar teachCalendar) {
        startPage();
        List<TeachCalendar> list = teachCalendarService.selectTeachCalendarList(teachCalendar);
        return getDataTable(list);
    }

    /**
     * 导出学年学期列表
     */
    @PreAuthorize("@ss.hasPermi('school:teachCalendar')")
    @Log(title = "学年学期", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TeachCalendar teachCalendar) {
        List<TeachCalendar> list = teachCalendarService.selectTeachCalendarList(teachCalendar);
        ExcelUtil<TeachCalendar> util = new ExcelUtil<TeachCalendar>(TeachCalendar.class);
        return util.exportExcel(list, "学年学期数据");
    }

    /**
     * 获取学年学期详细信息
     */
    @PreAuthorize("@ss.hasPermi('school:teachCalendar')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(teachCalendarService.selectTeachCalendarById(id));
    }

    /**
     * 新增学年学期
     */
    @PreAuthorize("@ss.hasPermi('school:teachCalendar')")
    @Log(title = "学年学期", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TeachCalendar teachCalendar) {
        School school = TeachUtils.getSchool();
        teachCalendar.setSchoolId(school.getId());
        return toAjax(teachCalendarService.insertTeachCalendar(teachCalendar));
    }

    /**
     * 修改学年学期
     */
    @PreAuthorize("@ss.hasPermi('school:teachCalendar')")
    @Log(title = "学年学期", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TeachCalendar teachCalendar) {
        return toAjax(teachCalendarService.updateTeachCalendar(teachCalendar));
    }

    /**
     * 删除学年学期
     */
    @PreAuthorize("@ss.hasPermi('school:teachCalendar')")
    @Log(title = "学年学期", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult remove(@RequestBody Long[] ids) {
        School school = TeachUtils.getSchool();
        return toAjax(teachCalendarService.deleteTeachCalendarByIds(school.getId(), ids));
    }
}
