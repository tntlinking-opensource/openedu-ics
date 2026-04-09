package com.ruoyi.teach.paike.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.teach.paike.domain.ClassroomSpecial;
import com.ruoyi.teach.paike.service.ClassroomSpecialService;
import com.ruoyi.teach.paike.service.LessonPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 特殊教室Controller
 *
 * @author ruoyi
 * @date 2021-10-27
 */
@RestController
@RequestMapping("/teach/paike/classroomSpecial")
public class ClassroomSpecialController extends LessonScheduleBaseController {

    @Autowired
    private ClassroomSpecialService classroomSpecialService;
    @Autowired
    private LessonPlanService lessonPlanService;

    /**
     * 获取特殊教室详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:classroomSpecial')")
    @GetMapping(value = {"/getIndexDatas"})
    public AjaxResult getIndexDatas() {
        AjaxResult ajax = AjaxResult.success();
        Long lessonScheduleId = getLessonScheduleId();
        ajax.put("courses", lessonPlanService.getCourses(lessonScheduleId));
        return ajax;
    }

    /**
     * 查询特殊教室列表
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:classroomSpecial')")
    @GetMapping("/list")
    public TableDataInfo list(ClassroomSpecial classroomSpecial) {
        startPage();
        classroomSpecial.setLessonScheduleId(getLessonScheduleId());
        List<ClassroomSpecial> list = classroomSpecialService.selectClassroomSpecialList(classroomSpecial);
        return getDataTable(list);
    }

    /**
     * 获取特殊教室详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:classroomSpecial')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") Long id) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put(AjaxResult.DATA_TAG, classroomSpecialService.selectClassroomSpecialById(id));
        return ajax;
    }

    /**
     * 获取特殊教室详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:classroomSpecial')")
    @GetMapping(value = {"/getEditDatas"})
    public AjaxResult getEditDatas() {
        AjaxResult ajax = getIndexDatas();
        Long lessonScheduleId = getLessonScheduleId();
        ajax.put("gradeOptions", lessonPlanService.getGrades(lessonScheduleId));
        ajax.put("adminclassOptions", lessonPlanService.getAdminclasses(lessonScheduleId));
        return ajax;
    }

    /**
     * 修改特殊教室
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:classroomSpecial')")
    @Log(title = "特殊教室", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult saveOrUpdate(@RequestBody ClassroomSpecial classroomSpecial) {
        if (classroomSpecial.getId() == null) {
            classroomSpecial.setSchoolId(getSchoolId());
            classroomSpecial.setLessonScheduleId(getLessonScheduleId());
        }
        classroomSpecialService.saveOrUpdate(classroomSpecial);
        return AjaxResult.success();
    }

    /**
     * 删除特殊教室
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:classroomSpecial')")
    @Log(title = "特殊教室", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult remove(@RequestBody Long[] ids) {
        return toAjax(classroomSpecialService.deleteClassroomSpecialByIds(ids));
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<ClassroomSpecial> util = new ExcelUtil<>(ClassroomSpecial.class);
        return util.importTemplateExcel("特殊教室导入模板");
    }

    @Log(title = "特殊教室管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('teach.paike:classroomSpecial')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<ClassroomSpecial> util = new ExcelUtil<>(ClassroomSpecial.class);
        List<ClassroomSpecial> classroomSpecials = util.importExcel(file.getInputStream());
        Long schoolId = getSchoolId();
        Long lessonScheduleId = getLessonScheduleId();
        classroomSpecials.forEach(c -> {
            c.setSchoolId(schoolId);
            c.setLessonScheduleId(lessonScheduleId);
        });
        String message = classroomSpecialService.importEntity(classroomSpecials);
        return AjaxResult.success(message);
    }

    /**
     * 导出特殊教室列表
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:classroomSpecial')")
    @Log(title = "特殊教室", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ClassroomSpecial classroomSpecial) {
        List<ClassroomSpecial> list = classroomSpecialService.selectClassroomSpecialList(classroomSpecial);
        ExcelUtil<ClassroomSpecial> util = new ExcelUtil<ClassroomSpecial>(ClassroomSpecial.class);
        return util.exportExcel(list, "特殊教室数据");
    }
}
