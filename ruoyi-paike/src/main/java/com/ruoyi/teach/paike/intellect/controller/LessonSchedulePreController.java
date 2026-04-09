package com.ruoyi.teach.paike.intellect.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.teach.paike.controller.LessonScheduleBaseController;
import com.ruoyi.teach.paike.domain.LessonPlan;
import com.ruoyi.teach.paike.service.LessonPlanService;
import com.ruoyi.teach.paike.service.LessonScheduleRuleService;
import com.ruoyi.teach.paike.service.LessonScheduleTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 排课预排Controller
 *
 * @author yzsoft
 * @date 2021-11-09
 */
@RestController
@RequestMapping("/teach/paike/lessonSchedulePre")
public class LessonSchedulePreController extends LessonScheduleBaseController {

    @Autowired
    private LessonScheduleTimeService lessonScheduleTimeService;
    @Autowired
    private LessonPlanService lessonPlanService;
    @Autowired
    private LessonScheduleRuleService ruleService;

    /**
     * 获取排课预排详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonSchedulePre')")
    @GetMapping(value = {"/getIndexDatas"})
    public AjaxResult getIndexDatas() {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("lessonPlans", lessonPlanService.getByLessonScheduleId(getLessonScheduleId()));
        ajax.put("rules", ruleService.getByLessonScheduleId(getLessonScheduleId()));
        ajax.put("timeConfig", lessonScheduleTimeService.getByLessonScheduleId(getLessonScheduleId()));
        return ajax;
    }

    /**
     * 修改排课预排
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:lessonSchedulePre')")
    @Log(title = "排课预排", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult saveOrUpdate(@RequestBody List<LessonPlan> plans) {
        lessonPlanService.updateBatchById(plans);
        return AjaxResult.success();
    }

//    @GetMapping("/importTemplate")
//    public AjaxResult importTemplate() {
//        ExcelUtil<LessonSchedulePre> util = new ExcelUtil<>(LessonSchedulePre.class);
//        return util.importTemplateExcel("排课预排导入模板");
//    }
//
//    @Log(title = "排课预排管理", businessType = BusinessType.IMPORT)
//    @PreAuthorize("@ss.hasPermi('teach.paike:lessonSchedulePre')")
//    @PostMapping("/importData")
//    public AjaxResult importData(MultipartFile file) throws Exception {
//        ExcelUtil<LessonSchedulePre> util = new ExcelUtil<>(LessonSchedulePre.class);
//        List<LessonSchedulePre> lessonSchedulePres = util.importExcel(file.getInputStream());
//        String message = lessonSchedulePreService.importEntity(lessonSchedulePres);
//        return AjaxResult.success(message);
//    }
//
//    /**
//     * 导出排课预排列表
//     */
//    @PreAuthorize("@ss.hasPermi('teach.paike:lessonSchedulePre')")
//    @Log(title = "排课预排", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(LessonSchedulePre lessonSchedulePre) {
//        List<LessonSchedulePre> list = lessonSchedulePreService.selectLessonSchedulePreList(lessonSchedulePre);
//        ExcelUtil<LessonSchedulePre> util = new ExcelUtil<LessonSchedulePre>(LessonSchedulePre.class);
//        return util.exportExcel(list, "排课预排数据");
//    }
}
