package com.ruoyi.teach.paike.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.teach.paike.domain.LessonPlan;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LessonPlanImportService {

    List<LessonPlan> importExcel(MultipartFile file) throws Exception;

    AjaxResult exportExcel(List<LessonPlan> list);
}
