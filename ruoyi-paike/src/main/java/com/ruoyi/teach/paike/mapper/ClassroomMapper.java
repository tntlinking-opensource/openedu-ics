package com.ruoyi.teach.paike.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.teach.paike.domain.Classroom;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 班级教室Mapper接口
 *
 * @author ruoyi
 * @date 2021-10-27
 */
public interface ClassroomMapper extends BaseMapper<Classroom> {
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

    @Select("select * from tpk_classroom where lesson_schedule_id = #{0}")
    List<Classroom> getByScheduleId(Long scheduleId);

    /**
     * 新增班级教室
     *
     * @param classroom 班级教室
     * @return 结果
     */
    int insertClassroom(Classroom classroom);

    /**
     * 修改班级教室
     *
     * @param classroom 班级教室
     * @return 结果
     */
    int updateClassroom(Classroom classroom);

    /**
     * 批量删除班级教室
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteClassroomByIds(Long[] ids);

    void copy(Long sourceId, Long targetId);
}
