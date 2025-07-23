package com.ruoyi.teach.school.service;

import com.ruoyi.teach.school.domain.Lesson;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LessonYunXiaoService {
    List<Lesson> importExcel(MultipartFile file) throws Exception;
}
