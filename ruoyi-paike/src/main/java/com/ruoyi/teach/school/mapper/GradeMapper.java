package com.ruoyi.teach.school.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.teach.school.domain.Grade;

import java.util.List;

/**
 * 年级Mapper接口
 *
 * @author yzsoft
 * @date 2023-06-26
 */
public interface GradeMapper extends BaseMapper<Grade> {

    /**
     * 查询年级列表
     *
     * @param grade 年级
     * @return 年级集合
     */
    List<Grade> selectGradeList(Grade grade);
    }
