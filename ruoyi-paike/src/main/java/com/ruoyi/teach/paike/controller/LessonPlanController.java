package com.ruoyi.teach.paike.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.teach.paike.domain.LessonPlan;
import com.ruoyi.teach.paike.domain.LessonSchedule;
import com.ruoyi.teach.paike.service.LessonPlanImportService;
import com.ruoyi.teach.paike.service.LessonPlanService;
import com.ruoyi.teach.paike.service.LessonScheduleService;
import com.ruoyi.teach.paike.vo.LessonPlanForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 任课信息Controller
 *
 * @author ruoyi
 * @date 2021-10-26
 */
@RestController
@RequestMapping("/teach/paike/lessonPlan")
public class LessonPlanController extends LessonScheduleBaseController {

    @Autowired
    private LessonPlanService lessonPlanService;
    @Autowired
    private LessonPlanImportService lessonPlanImportService;
    @Autowired
    private LessonScheduleService lessonScheduleService;

    /**
     * 获取任课信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonPlan')")
    @GetMapping(value = {"/getIndexDatas"})
    public AjaxResult getIndexDatas() {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("grades", lessonPlanService.getGrades(getLessonScheduleId()));
        ajax.put("courses", lessonPlanService.getCourses(getLessonScheduleId()));
        return ajax;
    }

    /**
     * 查询任课信息列表
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonPlan')")
    @GetMapping("/list")
    public TableDataInfo list(LessonPlan lessonPlan) {
//        startPage();
        lessonPlan.setLessonScheduleId(getLessonScheduleId());
        List<LessonPlan> list = lessonPlanService.selectLessonPlanList(lessonPlan);
        return getDataTable(list);
    }

    /**
     * 查询任课信息列表
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonPlan')")
    @GetMapping("/listPage")
    public TableDataInfo listPage(LessonPlan lessonPlan) {
        startPage();
        lessonPlan.setLessonScheduleId(getLessonScheduleId());
        List<LessonPlan> list = lessonPlanService.selectLessonPlanList(lessonPlan);
        return getDataTable(list);
    }

    /**
     * 获取任课信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonPlan')")
    @GetMapping(value = {"/getEditDatas"})
    public AjaxResult getEditDatas() {
        AjaxResult ajax = getIndexDatas();
        ajax.put("teachers", lessonPlanService.getTeachers(getLessonScheduleId()));
        return ajax;
    }

    /**
     * 获取任课信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonPlan')")
    @PostMapping(value = {"/getPlanDatas"})
    public AjaxResult getPlanDatas(@RequestBody LessonPlan lessonPlan) {
        Long lessonScheduleId = getLessonScheduleId();
        AjaxResult ajax = AjaxResult.success();
        ajax.put("courses", lessonPlanService.getCourses(lessonScheduleId, lessonPlan.getGrade()));
        ajax.put("teachers", lessonPlanService.getTeachers(lessonScheduleId, lessonPlan.getGrade()));
        return ajax;
    }

    /**
     * 获取任课信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonPlan')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") Long id) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put(AjaxResult.DATA_TAG, lessonPlanService.selectLessonPlanById(id));
        return ajax;
    }

    /**
     * 修改任课信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonPlan')")
    @Log(title = "任课信息", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult saveOrUpdate(@RequestBody LessonPlan lessonPlan) {
        lessonPlan.setSchoolId(getSchoolId());
        lessonPlanService.saveOrUpdate(lessonPlan);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('teach.paike:lessonPlan')")
    @Log(title = "任课信息", businessType = BusinessType.UPDATE)
    @PostMapping("/saveBatch")
    public AjaxResult saveBatch(@RequestBody LessonPlanForm form) {
        form.setLessonScheduleId(getLessonScheduleId());
        form.setSchoolId(getSchoolId());
        lessonPlanService.saveBatch(form);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('teach.paike:lessonPlan')")
    @Log(title = "任课信息", businessType = BusinessType.UPDATE)
    @PostMapping("/saveTeacher")
    public AjaxResult saveTeacher(@RequestBody LessonPlanForm form) {
        form.setLessonScheduleId(getLessonScheduleId());
        form.setSchoolId(getSchoolId());
        lessonPlanService.saveTeacher(form);
        return AjaxResult.success();
    }

    /**
     * 删除任课信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonPlan')")
    @Log(title = "任课信息", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult remove(@RequestBody Long[] ids) {
        return toAjax(lessonPlanService.deleteLessonPlanByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('teach.paike:lessonPlan')")
    @Log(title = "任课信息", businessType = BusinessType.DELETE)
    @PostMapping("/deleteByAdminclass")
    public AjaxResult deleteByAdminclass(@RequestBody LessonPlan form) {
        form.setLessonScheduleId(getLessonScheduleId());
        lessonPlanService.deleteByAdminclass(form);
        return AjaxResult.success();
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<LessonPlan> util = new ExcelUtil<>(LessonPlan.class);
        return util.importTemplateExcel("任课信息导入模板");
    }

    @Log(title = "任课信息管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonPlan')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        List<LessonPlan> lessonPlans = lessonPlanImportService.importExcel(file);
        LessonSchedule schedule = lessonScheduleService.getBySchoolId(getSchoolId());
        lessonPlans.forEach(p -> {
            p.setSchoolId(schedule.getSchoolId());
            p.setLessonScheduleId(schedule.getId());
        });
        String message = lessonPlanService.importEntity(lessonPlans);
        return AjaxResult.success(message);
    }

    /**
     * 导出任课信息列表
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonPlan')")
    @Log(title = "任课信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(LessonPlan lessonPlan) {
        lessonPlan.setGrade(null);
        lessonPlan.setLessonScheduleId(getLessonScheduleId());
        List<LessonPlan> list = lessonPlanService.selectLessonPlanList(lessonPlan);
//        ExcelUtil<LessonPlan> util = new ExcelUtil<LessonPlan>(LessonPlan.class);
//        return util.exportExcel(list, "任课信息数据");
        return lessonPlanImportService.exportExcel(list);
    }
}
