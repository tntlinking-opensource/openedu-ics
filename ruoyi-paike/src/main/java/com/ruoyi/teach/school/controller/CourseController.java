package com.ruoyi.teach.school.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.teach.school.domain.Course;
import com.ruoyi.teach.school.service.CourseService;
import com.ruoyi.teach.system.domain.School;
import com.ruoyi.teach.system.service.TeachUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 课程Controller
 *
 * @author beangle
 * @date 2021-07-06
 */
@RestController
@RequestMapping("/teach/school/course")
public class CourseController extends SchoolBaseController {

    @Autowired
    private CourseService courseService;

    @PreAuthorize("@ss.hasPermi('teach.school:course')")
    @GetMapping(value = {"/getIndexDatas"})
    public AjaxResult getIndexDatas() {
        courseService.initSchool(getSchoolId());
        AjaxResult ajax = AjaxResult.success();
        return ajax;
    }

    /**
     * 查询课程列表
     */
    @PreAuthorize("@ss.hasPermi('teach.school:course')")
    @GetMapping("/list")
    public TableDataInfo list(Course course) {
        startPage();
        List<Course> list = courseService.selectCourseList(course);
        return getDataTable(list);
    }

    /**
     * 导出课程列表
     */
    @PreAuthorize("@ss.hasPermi('teach.school:course')")
    @Log(title = "课程", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Course course) {
        List<Course> list = courseService.selectCourseList(course);
        ExcelUtil<Course> util = new ExcelUtil<Course>(Course.class);
        return util.exportExcel(list, "课程数据");
    }

    /**
     * 获取课程详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.school:course')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(courseService.selectCourseById(id));
    }

    /**
     * 新增课程
     */
    @PreAuthorize("@ss.hasPermi('teach.school:course')")
    @Log(title = "课程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Course course) {
        School school = TeachUtils.getSchool();
        course.setSchoolId(school.getId());
        return toAjax(courseService.insertCourse(course.getSchoolId(), course));
    }

    /**
     * 修改课程
     */
    @PreAuthorize("@ss.hasPermi('teach.school:course')")
    @Log(title = "课程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Course course) {
        return toAjax(courseService.updateCourse(course));
    }

    /**
     * 删除课程
     */
    @PreAuthorize("@ss.hasPermi('teach.school:course')")
    @Log(title = "课程", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult remove(@RequestBody Long[] ids) {
        School school = TeachUtils.getSchool();
        return toAjax(courseService.deleteCourseByIds(school.getId(), ids));
    }


    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('teach.school:adminclass')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Course> util = new ExcelUtil<>(Course.class);
        List<Course> courses = util.importExcel(file.getInputStream());
        Long schoolId = getSchoolId();
        courses.forEach(c -> c.setSchoolId(schoolId));
        String message = courseService.importEntity(courses);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<Course> util = new ExcelUtil<>(Course.class);
        return util.importTemplateExcel("课程导入模板");
    }
}
