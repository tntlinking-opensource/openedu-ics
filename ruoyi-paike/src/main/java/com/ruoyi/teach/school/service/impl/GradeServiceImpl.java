package com.ruoyi.teach.school.service.impl;

import com.ruoyi.common.core.service.impl.EntityServiceImpl;
import com.ruoyi.teach.school.domain.Grade;
import com.ruoyi.teach.school.mapper.GradeMapper;
import com.ruoyi.teach.school.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 年级Service业务层处理
 *
 * @author yzsoft
 * @date 2023-06-26
 */
@Service
public class GradeServiceImpl extends EntityServiceImpl<GradeMapper, Grade> implements GradeService {
    @Autowired
    private GradeMapper gradeMapper;

    /**
     * 查询年级列表
     *
     * @param grade 年级
     * @return 年级
     */
    @Override
    public List<Grade> getList(Grade grade) {
        return gradeMapper.selectGradeList(grade);
    }

    @Override
    @Cacheable(value = "Grade", key = "'getBySchool_' + #p0")
    public List<Grade> getBySchool(Long schoolId) {
        Assert.notNull(schoolId, "schoolId is null");
        Grade form = new Grade();
        form.setSchoolId(schoolId);
        form.setDeleted(0);
        List<Grade> list = getList(form);
        if (list == null || list.isEmpty()) {
            list = initSchool(schoolId);
        }
        return list;
    }

    private List<Grade> initSchool(Long schoolId) {
        String[] grades = new String[]{"一年级", "二年级", "三年级", "四年级", "五年级", "六年级"};
        List<Grade> list = new ArrayList<>();
        for (int i = 0; i < grades.length; i++) {
            String grade = grades[i];
            Grade g = new Grade();
            g.setSchoolId(schoolId);
            g.setCode(String.format("%d", i + 1));
            g.setName(grade);
            g.setDeleted(0);
            list.add(g);
        }
        saveBatch(list);
        return list;
    }

    @Override
    public Grade getById(Serializable id) {
        Grade grade = super.getById(id);
        return grade;
    }

    /**
     * 新增年级
     *
     * @param grade 年级
     * @return 结果
     */
    @Override
    @CacheEvict(value = {"Grade"}, key = "'getBySchool_' + #p0.schoolId")
    public boolean saveOrUpdate(Grade grade) {
        if (grade.getId() == null) {
        } else {
        }
        return super.saveOrUpdate(grade);
    }

    @Override
    @CacheEvict(value = {"Grade"}, key = "'getBySchool_' + #p0")
    public void removeByIds(Long schoolId, List<Long> list) {
        this.removeByIds(list);
    }

}
