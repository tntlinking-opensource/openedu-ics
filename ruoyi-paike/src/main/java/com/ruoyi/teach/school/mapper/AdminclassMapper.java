package com.ruoyi.teach.school.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.teach.school.domain.Adminclass;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 班级Mapper接口
 *
 * @author beangle
 * @date 2021-07-07
 */
public interface AdminclassMapper extends BaseMapper<Adminclass> {
	/**
	 * 查询班级
	 *
	 * @param id 班级ID
	 * @return 班级
	 */
	public Adminclass selectAdminclassById(Long id);

	/**
	 * 查询班级列表
	 *
	 * @param adminclass 班级
	 * @return 班级集合
	 */
	public List<Adminclass> selectAdminclassList(Adminclass adminclass);

	/**
	 * 新增班级
	 *
	 * @param adminclass 班级
	 * @return 结果
	 */
	public int insertAdminclass(Adminclass adminclass);

	/**
	 * 修改班级
	 *
	 * @param adminclass 班级
	 * @return 结果
	 */
	public int updateAdminclass(Adminclass adminclass);

	/**
	 * 删除班级
	 *
	 * @param id 班级ID
	 * @return 结果
	 */
	public int deleteAdminclassById(Long id);

	/**
	 * 批量删除班级
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteAdminclassByIds(Long[] ids);

	List<Adminclass> getClassBySchoolId(@Param("schoolId") Long schoolId, @Param("year") String year);

	List<Adminclass> getStudentNum(@Param("schoolId") Long schoolId);

	Integer getStudentNumByAdminclass(Long adminclassId);

	List<Adminclass> getByGrade(@Param("schoolId") Long schoolId, @Param("year") String year, @Param("grade") String grade);

    void updateStudentNum(@Param("schoolId") Long schoolId, @Param("year") String year);

	Long getClassTeacherRoleId();

	void deleteClassTeacher( @Param("schoolId") Long schoolId, @Param("teacherId") Long teacherId, @Param("roleId") Long roleId);

	void insertTeacherRole(@Param("schoolId") Long schoolId, @Param("teacherId") Long teacherId, @Param("roleId") Long roleId);
}
