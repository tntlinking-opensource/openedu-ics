package com.ruoyi.teach.paike.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.teach.paike.domain.ClassroomSpecial;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 特殊教室Mapper接口
 *
 * @author ruoyi
 * @date 2021-10-27
 */
public interface ClassroomSpecialMapper extends BaseMapper<ClassroomSpecial> {
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

    /**
     * 新增特殊教室
     *
     * @param classroomSpecial 特殊教室
     * @return 结果
     */
    int insertClassroomSpecial(ClassroomSpecial classroomSpecial);

    /**
     * 修改特殊教室
     *
     * @param classroomSpecial 特殊教室
     * @return 结果
     */
    int updateClassroomSpecial(ClassroomSpecial classroomSpecial);

    /**
     * 批量删除特殊教室
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteClassroomSpecialByIds(Long[] ids);

    @Select("select * from tpk_classroom_special where lesson_schedule_id = #{0}")
    List<ClassroomSpecial> getByScheduleId(Long scheduleId);

    void copy(Long sourceId, Long targetId);
}
