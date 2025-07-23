package com.ruoyi.teach.paike.service;

import com.ruoyi.common.core.service.EntityService;
import com.ruoyi.teach.paike.domain.ClassroomSpecial;

import java.util.List;

/**
 * 特殊教室Service接口
 *
 * @author ruoyi
 * @date 2021-10-27
 */
public interface ClassroomSpecialService extends EntityService<ClassroomSpecial> {
    /**
     * 查询特殊教室
     *
     * @param id 特殊教室ID
     * @return 特殊教室
     */
    ClassroomSpecial selectClassroomSpecialById(Long id);

    /**
     * 查询特殊教室列表
     *
     * @param classroomSpecial 特殊教室
     * @return 特殊教室集合
     */
    List<ClassroomSpecial> selectClassroomSpecialList(ClassroomSpecial classroomSpecial);

    List<ClassroomSpecial> getByScheduleId(Long scheduleId);

    /**
     * 批量删除特殊教室
     *
     * @param ids 需要删除的特殊教室ID
     * @return 结果
     */
    int deleteClassroomSpecialByIds(Long[] ids);

    void copy(Long sourceId, Long targetId);
}
