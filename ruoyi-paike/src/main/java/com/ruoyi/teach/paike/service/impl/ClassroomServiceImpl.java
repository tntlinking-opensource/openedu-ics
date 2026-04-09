package com.ruoyi.teach.paike.service.impl;

import com.ruoyi.common.core.service.impl.EntityServiceImpl;
import com.ruoyi.teach.paike.domain.Classroom;
import com.ruoyi.teach.paike.mapper.ClassroomMapper;
import com.ruoyi.teach.paike.service.ClassroomService;
import com.ruoyi.teach.paike.service.LessonScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 班级教室Service业务层处理
 *
 * @author ruoyi
 * @date 2021-10-27
 */
@Service
public class ClassroomServiceImpl extends EntityServiceImpl<ClassroomMapper, Classroom> implements ClassroomService {

    @Autowired
    private ClassroomMapper classroomMapper;
    @Autowired
    private LessonScheduleService lessonScheduleService;

    /**
     * 查询班级教室
     *
     * @param id 班级教室ID
     * @return 班级教室
     */
    @Override
    public Classroom selectClassroomById(Long id) {
        return classroomMapper.selectClassroomById(id);
    }

    /**
     * 查询班级教室列表
     *
     * @param classroom 班级教室
     * @return 班级教室
     */
    @Override
    public List<Classroom> selectClassroomList(Classroom classroom) {
        return classroomMapper.selectClassroomList(classroom);
    }

    @Override
    public List<Classroom> getByScheduleId(Long scheduleId) {
        return classroomMapper.getByScheduleId(scheduleId);
    }

    /**
     * 新增班级教室
     *
     * @param classroom 班级教室
     * @return 结果
     */
    @Override
    public boolean saveOrUpdate(Classroom classroom) {
        Assert.notNull(classroom.getSchoolId(), "schoolId is null");
        if (classroom.getId() == null) {
            classroom.setLessonScheduleId(lessonScheduleService.getBySchoolId(classroom.getSchoolId()).getId());
        } else {
        }
        return super.saveOrUpdate(classroom);
    }

    /**
     * 批量删除班级教室
     *
     * @param ids 需要删除的班级教室ID
     * @return 结果
     */
    @Override
    public int deleteClassroomByIds(Long[] ids) {
        return classroomMapper.deleteClassroomByIds(ids);
    }

    @Override
    public void copy(Long sourceId, Long targetId) {
        classroomMapper.copy(sourceId, targetId);
    }

}
