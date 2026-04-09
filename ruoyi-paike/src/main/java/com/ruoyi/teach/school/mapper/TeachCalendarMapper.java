package com.ruoyi.teach.school.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.teach.school.domain.TeachCalendar;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 学年学期Mapper接口
 *
 * @author beangle
 * @date 2021-07-05
 */
public interface TeachCalendarMapper extends BaseMapper<TeachCalendar> {
	/**
	 * 查询学年学期
	 *
	 * @param id 学年学期ID
	 * @return 学年学期
	 */
	public TeachCalendar selectTeachCalendarById(Long id);

	/**
	 * 查询学年学期列表
	 *
	 * @param teachCalendar 学年学期
	 * @return 学年学期集合
	 */
	public List<TeachCalendar> selectTeachCalendarList(TeachCalendar teachCalendar);

	/**
	 * 新增学年学期
	 *
	 * @param teachCalendar 学年学期
	 * @return 结果
	 */
	public int insertTeachCalendar(TeachCalendar teachCalendar);

	/**
	 * 修改学年学期
	 *
	 * @param teachCalendar 学年学期
	 * @return 结果
	 */
	public int updateTeachCalendar(TeachCalendar teachCalendar);

	/**
	 * 删除学年学期
	 *
	 * @param id 学年学期ID
	 * @return 结果
	 */
	public int deleteTeachCalendarById(Long id);

	/**
	 * 批量删除学年学期
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTeachCalendarByIds(Long[] ids);

	List<String> getYears(Long schoolId);

	TeachCalendar getByStartDate(@Param("schoolId") Long schoolId, @Param("startDate") Date startDate);
}
