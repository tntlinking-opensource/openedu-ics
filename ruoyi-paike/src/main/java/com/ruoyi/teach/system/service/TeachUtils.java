package com.ruoyi.teach.system.service;

import com.ruoyi.teach.system.domain.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeachUtils {

    private static TeachService teachService;

    @Autowired
    public void setTeachService(TeachService teachService) {
        TeachUtils.teachService = teachService;
    }

    public static School getSchool() {
        return teachService.getSchool();
    }
}
