package com.ruoyi.teach.school.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.teach.school.domain.Grade;
import com.ruoyi.teach.school.service.GradeService;
import com.ruoyi.teach.school.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * 年级Controller
 *
 * @author yzsoft
 * @date 2023-06-26
 */
@RestController
@RequestMapping("/teach/school/grade")
public class GradeController extends SchoolBaseController {
    @Autowired
    private GradeService gradeService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 获取年级详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.school:grade')")
    @GetMapping(value = {"/getIndexDatas"})
    public AjaxResult getIndexDatas() {
        AjaxResult result = AjaxResult.success();
        return result;
    }

    /**
     * 查询年级列表
     */
    @PreAuthorize("@ss.hasPermi('teach.school:grade')")
    @GetMapping("/list")
    public TableDataInfo list(Grade grade) {
        grade.setSchoolId(getSchoolId());
        startPage();
        List<Grade> list = gradeService.getList(grade);
        return getDataTable(list);
    }

    /**
     * 获取年级详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.school:grade')")
    @GetMapping(value = {"/getEditDatas"})
    public AjaxResult getEditDatas() {
        AjaxResult result = getIndexDatas();
        return result;
    }

    /**
     * 获取年级详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.school:grade')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") Long id) {
        AjaxResult result = AjaxResult.success();
        Grade grade = gradeService.getById(id);
        grade.setTeacher(teacherService.getById(grade.getTeacherId()));
        result.put(AjaxResult.DATA_TAG, grade);
        return result;
    }

    /**
     * 修改年级
     */
    @PreAuthorize("@ss.hasPermi('teach.school:grade')")
    @Log(title = "年级", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult saveOrUpdate(@RequestBody Grade grade) {
        grade.setSchoolId(getSchoolId());
        gradeService.saveOrUpdate(grade);
        return AjaxResult.success();
    }

    /**
     * 删除年级
     */
    @PreAuthorize("@ss.hasPermi('teach.school:grade')")
    @Log(title = "年级", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult remove(@RequestBody Long[] ids) {
        gradeService.removeByIds(getSchoolId(), Arrays.asList(ids));
        return AjaxResult.success();
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<Grade> util = new ExcelUtil<>(Grade.class);
        return util.importTemplateExcel("年级导入模板");
    }

    @Log(title = "年级管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('teach.school:grade')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Grade> util = new ExcelUtil<>(Grade.class);
        List<Grade> grades = util.importExcel(file.getInputStream());
        Long schoolId = getSchoolId();
        for (Grade grade : grades) {
            grade.setSchoolId(schoolId);
        }
        String message = gradeService.importEntity(grades);
        return AjaxResult.success(message);
    }

    /**
     * 导出年级列表
     */
    @PreAuthorize("@ss.hasPermi('teach.school:grade')")
    @Log(title = "年级", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Grade grade) {
        grade.setSchoolId(getSchoolId());
        List<Grade> list = gradeService.getList(grade);
        ExcelUtil<Grade> util = new ExcelUtil<Grade>(Grade.class);
        return util.exportExcel(list, "年级数据");
    }
}
