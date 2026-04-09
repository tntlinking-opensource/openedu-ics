package com.ruoyi.teach.system.service.impl;

import com.ruoyi.teach.school.domain.Teacher;
import com.ruoyi.teach.system.domain.School;
import com.ruoyi.teach.system.service.TeachService;
import org.springframework.stereotype.Service;

@Service
public class TeachServiceImpl implements TeachService {

    @Override
    public School getSchool() {
        School school = new School();
        school.setId(getSchoolId());
        return school;
    }

    @Override
    public Long getSchoolId() {
        return 0L;
    }

    @Override
    public Teacher getTeacher() {
        return null;
    }
}
