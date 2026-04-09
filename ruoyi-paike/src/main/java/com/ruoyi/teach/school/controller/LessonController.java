package com.ruoyi.teach.school.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.teach.school.domain.Lesson;
import com.ruoyi.teach.school.domain.TeachCalendar;
import com.ruoyi.teach.school.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * 课表管理Controller
 *
 * @author ruoyi
 * @date 2021-07-14
 */
@RestController
@RequestMapping("/teach/school/lesson")
public class LessonController extends SchoolBaseController {
    @Autowired
    private LessonService lessonService;
    @Autowired
    private AdminclassService adminclassService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private LessonTimeService lessonTimeService;
    @Autowired
    private LessonYunXiaoService lessonYunXiaoService;

    /**
     * 查询课表管理列表
     */
    @PreAuthorize("@ss.hasPermi('teach.school:lesson')")
    @GetMapping("/list")
    public TableDataInfo list(Lesson lesson) {
        startPage();
        List<Lesson> list = lessonService.selectLessonList(lesson);
        return getDataTable(list);
    }


    /**
     * 查询课表管理列表
     */
    @PreAuthorize("@ss.hasPermi('teach.school:lesson')")
    @GetMapping("/datas")
    public AjaxResult datas() {
        AjaxResult ajax = AjaxResult.success();
        Long schoolId = getSchoolId();
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendarCurrent(schoolId);
        ajax.put("teachCalendars", teachCalendarService.getBySchoolId(schoolId));
        ajax.put("teachCalendarId", teachCalendar.getId());
        ajax.put("grades", adminclassService.getGrades(schoolId, teachCalendar.getYear()));
        ajax.put("adminclasses", adminclassService.getAll(schoolId, teachCalendar.getYear()));
        ajax.put("courses", courseService.getAll(schoolId));
        ajax.put("teachers", teacherService.getAll(schoolId));
        ajax.put("times", lessonTimeService.getLessonTime(schoolId));
        return ajax;
    }

    /**
     * 导出课表管理列表
     */
    @PreAuthorize("@ss.hasPermi('teach.school:lesson')")
    @Log(title = "课表管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Lesson lesson) {
        List<Lesson> list = lessonService.selectLessonList(lesson);
        ExcelUtil<Lesson> util = new ExcelUtil<Lesson>(Lesson.class);
        return util.exportExcel(list, "课表管理数据");
    }

    /**
     * 获取课表管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.school:lesson')")
    @GetMapping(value = {"/", "/{id}"})
    public AjaxResult getInfo(@PathVariable(value = "id", required = false) Long id) {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(id)) {
            ajax.put(AjaxResult.DATA_TAG, lessonService.selectLessonById(id));
        } else {
            ajax.put(AjaxResult.DATA_TAG, new Lesson());
        }
        return ajax;
    }

    /**
     * 新增课表管理
     */
    @PreAuthorize("@ss.hasPermi('teach.school:lesson')")
    @Log(title = "课表管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Lesson lesson) {
        return toAjax(lessonService.insertLesson(lesson));
    }

    /**
     * 修改课表管理
     */
    @PreAuthorize("@ss.hasPermi('teach.school:lesson')")
    @Log(title = "课表管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Lesson lesson) {
        return toAjax(lessonService.updateLesson(lesson));
    }

    /**
     * 删除课表管理
     */
    @PreAuthorize("@ss.hasPermi('teach.school:lesson')")
    @Log(title = "课表管理", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult remove(@RequestBody Long[] ids) {
        return toAjax(lessonService.deleteLessonByIds(ids));
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<Lesson> util = new ExcelUtil<>(Lesson.class);
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendarCurrent();
        Lesson lesson = new Lesson();
        lesson.setYear(teachCalendar.getYear());
        lesson.setTerm(teachCalendar.getTerm());
        lesson.setGrade("一年级");
        lesson.setAdminclass("一年级（1）班");
        lesson.setWeekday("星期一");
        lesson.setWeekType("全部/单周/双周");
        lesson.setTimeStart("第一节");
        lesson.setTimeEnd("第一节");
        lesson.setCourse("语文");
        lesson.setTeacherCode("T001");
        lesson.setTeacher("张三");
        lesson.setClassroom("一教101");
        lesson.setHour(3);
        lesson.setTimes("1-1、2-1、3-2");
        return util.importTemplateExcel(Arrays.asList(lesson), "课表导入模板");
    }

    @Log(title = "课表管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('teach.school:lesson')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Lesson> util = new ExcelUtil<>(Lesson.class);
        List<Lesson> lessons = util.importExcel(file.getInputStream());
        String message = lessonService.importEntity(lessons);
        return AjaxResult.success(message);
    }

    @Log(title = "课表管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('teach.school:lesson')")
    @PostMapping("/importDataYunXiao")
    public AjaxResult importDataYunXiao(MultipartFile file) throws Exception {
//        return importData(file);
        List<Lesson> lessons = lessonYunXiaoService.importExcel(file);
        String message = lessonService.importEntity(lessons);
        return AjaxResult.success(message);
    }
}
