package com.ruoyi.teach.paike.service.impl;

import com.ruoyi.common.core.service.impl.EntityServiceImpl;
import com.ruoyi.teach.paike.domain.ClassroomSpecial;
import com.ruoyi.teach.paike.mapper.ClassroomSpecialMapper;
import com.ruoyi.teach.paike.service.ClassroomSpecialService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 特殊教室Service业务层处理
 *
 * @author ruoyi
 * @date 2021-10-27
 */
@Service
public class ClassroomSpecialServiceImpl extends EntityServiceImpl<ClassroomSpecialMapper, ClassroomSpecial> implements ClassroomSpecialService {

    @Autowired
    private ClassroomSpecialMapper classroomSpecialMapper;

    /**
     * 查询特殊教室
     *
     * @param id 特殊教室ID
     * @return 特殊教室
     */
    @Override
    public ClassroomSpecial selectClassroomSpecialById(Long id) {
        return classroomSpecialMapper.selectClassroomSpecialById(id);
    }

    /**
     * 查询特殊教室列表
     *
     * @param classroomSpecial 特殊教室
     * @return 特殊教室
     */
    @Override
    public List<ClassroomSpecial> selectClassroomSpecialList(ClassroomSpecial classroomSpecial) {
        return classroomSpecialMapper.selectClassroomSpecialList(classroomSpecial);
    }

    @Override
    public List<ClassroomSpecial> getByScheduleId(Long scheduleId) {
        return classroomSpecialMapper.getByScheduleId(scheduleId);
    }

    /**
     * 新增特殊教室
     *
     * @param classroomSpecial 特殊教室
     * @return 结果
     */
    @Override
    public boolean saveOrUpdate(ClassroomSpecial classroomSpecial) {
        Assert.notNull(classroomSpecial.getSchoolId(), "SchoolId is null");
        Assert.notNull(classroomSpecial.getLessonScheduleId(), "LessonScheduleId is null");
        Assert.isTrue(StringUtils.isNotBlank(classroomSpecial.getCourse()), "课程不能为空");
        Assert.isTrue(StringUtils.isNotBlank(classroomSpecial.getBuilding()), "教学楼不能为空");
//        Assert.isTrue(StringUtils.isNotBlank(classroomSpecial.getRoom()), "教室不能为空");
        Assert.isTrue(StringUtils.length(classroomSpecial.getGrade()) < 255, "年级太多了");
        Assert.isTrue(StringUtils.length(classroomSpecial.getAdminclass()) < 255, "班级太多了");
        if (classroomSpecial.getId() == null) {
        } else {
        }
        return super.saveOrUpdate(classroomSpecial);
    }

    /**
     * 批量删除特殊教室
     *
     * @param ids 需要删除的特殊教室ID
     * @return 结果
     */
    @Override
    public int deleteClassroomSpecialByIds(Long[] ids) {
        return classroomSpecialMapper.deleteClassroomSpecialByIds(ids);
    }

    @Override
    public void copy(Long sourceId, Long targetId) {
        classroomSpecialMapper.copy(sourceId, targetId);
    }

}
