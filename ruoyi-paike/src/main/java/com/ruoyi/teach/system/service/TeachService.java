package com.ruoyi.teach.system.service;

import com.ruoyi.teach.school.domain.Teacher;
import com.ruoyi.teach.system.domain.School;

public interface TeachService {
    School getSchool();

    Long getSchoolId();

    Teacher getTeacher();
}
