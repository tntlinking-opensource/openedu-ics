package com.ruoyi.teach.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.teach.system.domain.School;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学校Mapper接口
 *
 * @author beangle
 * @date 2021-06-24
 */
public interface SchoolMapper extends BaseMapper<School> {
	/**
	 * 查询学校
	 *
	 * @param id 学校ID
	 * @return 学校
	 */
	School selectSchoolById(Long id);

	/**
	 * 查询学校列表
	 *
	 * @param school 学校
	 * @return 学校集合
	 */
	List<School> selectSchoolList(School school);

	/**
	 * 新增学校
	 *
	 * @param school 学校
	 * @return 结果
	 */
	int insertSchool(School school);

	/**
	 * 修改学校
	 *
	 * @param school 学校
	 * @return 结果
	 */
	int updateSchool(School school);

	/**
	 * 删除学校
	 *
	 * @param id 学校ID
	 * @return 结果
	 */
	int deleteSchoolById(Long id);

	/**
	 * 批量删除学校
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	int deleteSchoolByIds(Long[] ids);

	List<SysUser> getUsersByRoleId(@Param("schoolId") Long schoolId, @Param("roleId") Long roleId);

	List<SysUser> getUsersByRole(@Param("schoolId") Long schoolId, @Param("role") String role);

	List<School> getSchoolsByUserId(Long userId);

	void updateSchoolUser(School school);

	List<School> getEnabled();

	List<School> getVisiable();

}
