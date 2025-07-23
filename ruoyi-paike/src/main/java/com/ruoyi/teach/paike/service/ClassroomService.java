package com.ruoyi.teach.paike.service;

import com.ruoyi.common.core.service.EntityService;
import com.ruoyi.teach.paike.domain.Classroom;

import java.util.List;

/**
 * 班级教室Service接口
 *
 * @author ruoyi
 * @date 2021-10-27
 */
public interface ClassroomService extends EntityService<Classroom> {
    /**
     * 查询班级教室
     *
     * @param id 班级教室ID
     * @return 班级教室
     */
    Classroom selectClassroomById(Long id);

    /**
     * 查询班级教室列表
     *
     * @param classroom 班级教室
     * @return 班级教室集合
     */
    List<Classroom> selectClassroomList(Classroom classroom);

    List<Classroom> getByScheduleId(Long scheduleId);

    /**
     * 批量删除班级教室
     *
     * @param ids 需要删除的班级教室ID
     * @return 结果
     */
    int deleteClassroomByIds(Long[] ids);

    void copy(Long sourceId, Long targetId);
}
