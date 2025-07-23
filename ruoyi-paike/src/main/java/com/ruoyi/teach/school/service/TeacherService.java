package com.ruoyi.teach.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.teach.school.domain.Teacher;

import java.util.List;
import java.util.Map;

/**
 * 教师Service接口
 *
 * @author beangle
 * @date 2021-07-08
 */
public interface TeacherService extends IService<Teacher> {
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

	void updateTeacherCacheEvict(Long userId);

	/**
	 * 批量删除教师
	 *
	 * @param ids 需要删除的教师ID
	 * @return 结果
	 */
	public int deleteTeacherByIds(Long[] ids);

	/**
	 * 删除教师信息
	 *
	 * @param id 教师ID
	 * @return 结果
	 */
	public int deleteTeacherById(Long id);

	Teacher getByCode(Long schoolId, String teacherCode);

	Teacher getByName(Long schoolId, String name);

	List<Teacher> getBySchoolId(Long schoolId);

	List<Teacher> getTeachers();

	String importTeacher(List<Teacher> teachers);

	List<Teacher> selectTeacherByIds(Long[] teacherIds);

	Teacher getByUserId(Long userId);

	Teacher getByUserIdEvict(Long userId);

	Teacher getByUser(SysUser user);

	List<Teacher> getAll(Long schoolId);

	List<Teacher> getTeachers(String code, String name);

//	Teacher getTeacher();

	Teacher getTeacherByPhone(Long schoolId, String phone);

	List<Teacher> getByKey(Long schoolId, String key);

	void changeSchool(Long userId, Long schoolId);

	Map<String, Teacher> getTeacherMap(Long schoolId);

	Teacher getTeacher(List<Teacher> teachers, String code, String name);

    Teacher getByName(List<Teacher> teachers, String name);

	void updateOpenid(Long id, String openid);

    Integer getTeacherCount(Long userId);
}
