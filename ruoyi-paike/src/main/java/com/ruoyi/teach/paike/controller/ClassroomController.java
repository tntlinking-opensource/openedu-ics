package com.ruoyi.teach.paike.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.teach.paike.domain.Classroom;
import com.ruoyi.teach.paike.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 班级教室Controller
 *
 * @author ruoyi
 * @date 2021-10-27
 */
@RestController
@RequestMapping("/teach/paike/classroom")
public class ClassroomController extends LessonScheduleBaseController {

    @Autowired
    private ClassroomService classroomService;

    /**
     * 获取班级教室详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:classroom')")
    @GetMapping(value = {"/getIndexDatas"})
    public AjaxResult getIndexDatas() {
        AjaxResult ajax = AjaxResult.success();
        return ajax;
    }

    /**
     * 查询班级教室列表
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:classroom')")
    @GetMapping("/list")
    public TableDataInfo list(Classroom classroom) {
        startPage();
        classroom.setLessonScheduleId(getLessonScheduleId());
        List<Classroom> list = classroomService.selectClassroomList(classroom);
        return getDataTable(list);
    }

    /**
     * 获取班级教室详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:classroom')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") Long id) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put(AjaxResult.DATA_TAG, classroomService.selectClassroomById(id));
        return ajax;
    }

    /**
     * 获取班级教室详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:classroom')")
    @GetMapping(value = {"/getEditDatas"})
    public AjaxResult getEditDatas() {
        AjaxResult ajax = getIndexDatas();
        return ajax;
    }

    /**
     * 修改班级教室
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:classroom')")
    @Log(title = "班级教室", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult saveOrUpdate(@RequestBody Classroom classroom) {
        if (classroom.getId() == null) {
            classroom.setSchoolId(getSchoolId());
        }
        classroomService.saveOrUpdate(classroom);
        return AjaxResult.success();
    }

    /**
     * 删除班级教室
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:classroom')")
    @Log(title = "班级教室", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult remove(@RequestBody Long[] ids) {
        return toAjax(classroomService.deleteClassroomByIds(ids));
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<Classroom> util = new ExcelUtil<>(Classroom.class);
        return util.importTemplateExcel("班级教室导入模板");
    }

    @Log(title = "班级教室管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('teach.paike:classroom')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Classroom> util = new ExcelUtil<>(Classroom.class);
        List<Classroom> classrooms = util.importExcel(file.getInputStream());
        Long schoolId = getSchoolId();
        Long lessonScheduleId = getLessonScheduleId();
        classrooms.forEach(c -> {
            c.setSchoolId(schoolId);
            c.setLessonScheduleId(lessonScheduleId);
        });
        String message = classroomService.importEntity(classrooms);
        return AjaxResult.success(message);
    }

    /**
     * 导出班级教室列表
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:classroom')")
    @Log(title = "班级教室", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Classroom classroom) {
        List<Classroom> list = classroomService.selectClassroomList(classroom);
        ExcelUtil<Classroom> util = new ExcelUtil<Classroom>(Classroom.class);
        return util.exportExcel(list, "班级教室数据");
    }
}
