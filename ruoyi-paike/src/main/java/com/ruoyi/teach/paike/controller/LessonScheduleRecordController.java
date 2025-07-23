package com.ruoyi.teach.paike.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.teach.paike.domain.LessonScheduleRecord;
import com.ruoyi.teach.paike.service.LessonScheduleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 排课存档Controller
 *
 * @author yzsoft
 * @date 2021-11-30
 */
@RestController
@RequestMapping("/paike/record")
public class LessonScheduleRecordController extends LessonScheduleBaseController {

    @Autowired
    private LessonScheduleRecordService lessonScheduleRecordService;

    /**
     * 获取排课存档详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:record')")
    @GetMapping(value = {"/getIndexDatas"})
    public AjaxResult getIndexDatas() {
        AjaxResult ajax = AjaxResult.success();
        return ajax;
    }

    /**
     * 查询排课存档列表
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:record')")
    @GetMapping("/list")
    public TableDataInfo list(LessonScheduleRecord lessonScheduleRecord) {
        startPage();
        lessonScheduleRecord.setLessonScheduleId(getLessonScheduleId());
        List<LessonScheduleRecord> list = lessonScheduleRecordService.getList(lessonScheduleRecord);
        return getDataTable(list);
    }

    /**
     * 获取排课存档详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:record')")
    @GetMapping(value = {"/getEditDatas"})
    public AjaxResult getEditDatas() {
        AjaxResult ajax = getIndexDatas();
        return ajax;
    }

    /**
     * 获取排课存档详细信息
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:record')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") Long id) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put(AjaxResult.DATA_TAG, lessonScheduleRecordService.getById(id));
        return ajax;
    }

    /**
     * 修改排课存档
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:record')")
    @Log(title = "排课存档", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult saveOrUpdate(@RequestBody LessonScheduleRecord lessonScheduleRecord) {
        lessonScheduleRecord.setLessonScheduleId(getLessonScheduleId());
        lessonScheduleRecordService.saveOrUpdate(lessonScheduleRecord);
        return AjaxResult.success();
    }

    /**
     * 删除排课存档
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:record')")
    @Log(title = "排课存档", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult remove(@RequestBody Long[] ids) {
        lessonScheduleRecordService.removeByIds(Arrays.asList(ids));
        return AjaxResult.success();
    }

    /**
     * 删除排课存档
     */
    @PreAuthorize("@ss.hasPermi('teach.paike:record')")
    @Log(title = "载入存档", businessType = BusinessType.UPDATE)
    @GetMapping("/reload/{id}")
    public AjaxResult reload(@PathVariable Long id) {
        lessonScheduleRecordService.reload(id);
        return AjaxResult.success();
    }

}