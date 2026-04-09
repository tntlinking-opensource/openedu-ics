package com.ruoyi.teach.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.teach.system.domain.School;
import com.ruoyi.teach.system.mapper.SchoolMapper;
import com.ruoyi.teach.system.service.SchoolService;
import org.springframework.stereotype.Service;

/**
 * 学校Service业务层处理
 *
 * @author beangle
 * @date 2021-06-24
 */
@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements SchoolService {

    @Override
    public School getSchool() {
        return null;
    }
}
