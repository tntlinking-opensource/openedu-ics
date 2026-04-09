package com.ruoyi.teach.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.teach.school.domain.TeachCalendar;
import com.ruoyi.teach.school.vo.Month;
import com.ruoyi.teach.school.vo.Week;

import java.util.Date;
import java.util.List;

/**
 * 学年学期Service接口
 *
 * @author beangle
 * @date 2021-07-05
 */
public interface TeachCalendarService extends IService<TeachCalendar> {
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
	 * 批量删除学年学期
	 *
	 * @param ids 需要删除的学年学期ID
	 * @return 结果
	 */
	public int deleteTeachCalendarByIds(Long schoolId, Long[] ids);

	List<TeachCalendar> getAll(Long schoolId);

	@Deprecated
	TeachCalendar getTeachCalendarCurrent();

	TeachCalendar getTeachCalendarCurrent(Long schoolId);

	TeachCalendar getTeachCalendarPrev(Long schoolId);

	TeachCalendar getTeachCalendar(Long schoolId, String year, String term);

	List<TeachCalendar> getBySchoolId(Long schoolId);

	TeachCalendar getById(Long schoolId, Long teachCalendarId);

	List<Week> getWeeks();

	List<Week> getWeeks(TeachCalendar teachCalendar);

	int getWeek(Long schoolId, Date date);

	int getWeek(TeachCalendar teachCalendar, Date date);

	TeachCalendar getByDate(Long schoolId, Date startDate, Date endDate);

	List<String> getYears(Long schoolId);

    Week getWeekDate(Long calendarId, Integer week);

	Week getWeekDate(TeachCalendar calendar);

    int getMonth(TeachCalendar calendar, Date date);

	List<Month> getMonths(TeachCalendar calendar);
}
