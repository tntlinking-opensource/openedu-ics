package com.ruoyi.teach.school.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.teach.school.domain.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教师Mapper接口
 *
 * @author beangle
 * @date 2021-07-08
 */
public interface TeacherMapper extends BaseMapper<Teacher> {
    /**
     * 查询教师
     *
     * @param id 教师ID
     * @return 教师
     */
    public Teacher selectTeacherById(Long id);

    /**
     * 查询教师列表
     *
     * @param teacher 教师
     * @return 教师集合
     */
    public List<Teacher> selectTeacherList(Teacher teacher);

    /**
     * 新增教师
     *
     * @param teacher 教师
     * @return 结果
     */
    public int insertTeacher(Teacher teacher);

    /**
     * 修改教师
     *
     * @param teacher 教师
     * @return 结果
     */
    public int updateTeacher(Teacher teacher);

    /**
     * 删除教师
     *
     * @param id 教师ID
     * @return 结果
     */
    public int deleteTeacherById(Long id);

    /**
     * 批量删除教师
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTeacherByIds(Long[] ids);

    int deleteUserByIds(List<Long> ids);

    Teacher getByUserId(Long userId);

    List<Teacher> getAllByUserId(Long userId);

    List<Teacher> getActivateByUserId(Long userId);

    List<Teacher> getByUser(SysUser user);

    void updateOpenid(@Param("id") Long id, @Param("openid") String openid);

    Integer getTeacherCount(@Param("userId") Long userId);
}
