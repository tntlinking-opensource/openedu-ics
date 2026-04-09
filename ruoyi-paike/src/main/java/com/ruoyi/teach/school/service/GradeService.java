package com.ruoyi.teach.school.service;

import com.ruoyi.common.core.service.EntityService;
import com.ruoyi.teach.school.domain.Grade;

import java.util.List;

/**
 * 年级Service接口
 *
 * @author yzsoft
 * @date 2023-06-26
 */
public interface GradeService extends EntityService<Grade> {

    /**
     * 查询年级列表
     *
     * @param grade 年级
     * @return 年级集合
     */
    List<Grade> getList(Grade grade);

    List<Grade> getBySchool(Long schoolId);

    void removeByIds(Long schoolId, List<Long> list);
}
