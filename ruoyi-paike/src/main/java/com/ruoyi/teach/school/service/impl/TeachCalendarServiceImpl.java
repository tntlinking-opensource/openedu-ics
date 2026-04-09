package com.ruoyi.teach.school.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.ruoyi.teach.school.domain.TeachCalendar;
import com.ruoyi.teach.school.mapper.TeachCalendarMapper;
import com.ruoyi.teach.school.service.TeachCalendarService;
import com.ruoyi.teach.school.vo.Month;
import com.ruoyi.teach.school.vo.Week;
import com.ruoyi.teach.system.domain.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 学年学期Service业务层处理
 *
 * @author beangle
 * @date 2021-07-05
 */
@Service
@EnableAsync
public class TeachCalendarServiceImpl extends SchoolEntityServiceImpl<TeachCalendarMapper, TeachCalendar> implements TeachCalendarService {
    @Autowired
    private TeachCalendarMapper teachCalendarMapper;

    /**
     * 查询学年学期
     *
     * @param id 学年学期ID
     * @return 学年学期
     */
    @Override
    public TeachCalendar selectTeachCalendarById(Long id) {
//        return teachCalendarMapper.selectTeachCalendarById(id);
        return getById(id);
    }

    /**
     * 查询学年学期列表
     *
     * @param teachCalendar 学年学期
     * @return 学年学期
     */
    @Override
    public List<TeachCalendar> selectTeachCalendarList(TeachCalendar teachCalendar) {
        selectSchoolObjectList(teachCalendar);
        List<TeachCalendar> list = teachCalendarMapper.selectTeachCalendarList(teachCalendar);
        return list;
    }

    /**
     * 新增学年学期
     *
     * @param teachCalendar 学年学期
     * @return 结果
     */
    @Override
    @Caching(evict = {
            @CacheEvict(value = {"TeachCalendar"}, key = "'School_' + #p0.schoolId + ':getBySchoolId'"),
            @CacheEvict(value = {"TeachCalendar"}, key = "'School_' + #p0.schoolId + ':getTeachCalendarCurrent'")
    })
    public int insertTeachCalendar(TeachCalendar teachCalendar) {
        insertSchoolObject(teachCalendar);
        initTeachCalendar(teachCalendar);
        return teachCalendarMapper.insertTeachCalendar(teachCalendar);
    }

    private void initTeachCalendar(TeachCalendar teachCalendar) {
        if ("Y".equals(teachCalendar.getCurrent())) {
            UpdateWrapper query = new UpdateWrapper();
            query.set("`current`", "N");
            query.eq("school_id", teachCalendar.getSchoolId());
            update(query);
        }
        teachCalendar.setWeekNum(getWeekNum(teachCalendar));
    }

    /**
     * 修改学年学期
     *
     * @param teachCalendar 学年学期
     * @return 结果
     */
    @Override
    @Caching(evict = {
            @CacheEvict(value = {"TeachCalendar"}, key = "'School_' + #p0.schoolId + ':getBySchoolId'"),
            @CacheEvict(value = {"TeachCalendar"}, key = "'School_' + #p0.schoolId + ':getTeachCalendarCurrent'")
    })
    public int updateTeachCalendar(TeachCalendar teachCalendar) {
        initTeachCalendar(teachCalendar);
        return teachCalendarMapper.updateTeachCalendar(teachCalendar);
    }

    /**
     * 批量删除学年学期
     *
     * @param ids 需要删除的学年学期ID
     * @return 结果
     */
    @Override
    @Caching(evict = {
            @CacheEvict(value = {"TeachCalendar"}, key = "'School_' + #p0 + ':getBySchoolId'"),
            @CacheEvict(value = {"TeachCalendar"}, key = "'School_' + #p0 + ':getTeachCalendarCurrent'")
    })
    public int deleteTeachCalendarByIds(Long schoolId, Long[] ids) {
        return teachCalendarMapper.deleteTeachCalendarByIds(ids);
    }

    @Override
    public TeachCalendar getTeachCalendarCurrent() {
        return getTeachCalendarCurrent(null);
    }

    @Override
    public TeachCalendar getTeachCalendar(Long schoolId, String year, String term) {
        TeachCalendar teachCalendar = new TeachCalendar();
        teachCalendar.setSchoolId(getSchool().getId());
        teachCalendar.setYear(year);
        teachCalendar.setTerm(term);
        teachCalendar.setDelFlag("0");
        return getOne(new QueryWrapper<>(teachCalendar));
    }

    @Override
    @Cacheable(value = "TeachCalendar", key = "'School_'+ #p0 +':getBySchoolId'")
    public List<TeachCalendar> getBySchoolId(Long schoolId) {
        return getAll(schoolId);
    }

    @Override
    @Cacheable(value = "TeachCalendar", key = "'School_'+ #p0 +':getBySchoolId'")
    public List<TeachCalendar> getAll(Long schoolId) {
        PageHelper.startPage(1, 10);
        QueryWrapper query = new QueryWrapper();
        query.eq("school_id", schoolId);
        query.eq("del_flag", "0");
        query.orderByDesc("year");
        query.orderByDesc("term");
        return list(query);
    }

    @Override
    @Cacheable(value = "TeachCalendar", key = "'School_'+ #p0 +':getTeachCalendarCurrent'")
    public TeachCalendar getTeachCalendarCurrent(Long schoolId) {
        QueryWrapper query = new QueryWrapper();
        query.eq("current", "Y");
        query.eq("del_flag", "0");
        List<TeachCalendar> teachCalendars = list(query, schoolId);
        if (teachCalendars.isEmpty()) {
            initSchool(getSchool());
            return getTeachCalendarCurrent();
        }
        return teachCalendars.get(0);
    }

    @Override
    public TeachCalendar getTeachCalendarPrev(Long schoolId) {
        TeachCalendar calendar = getTeachCalendarCurrent(schoolId);
        PageHelper.startPage(1, 1);
        return teachCalendarMapper.getByStartDate(schoolId, calendar.getStartDate());
    }

    @Override
    public TeachCalendar getById(Long schoolId, Long teachCalendarId) {
        return getById(teachCalendarId);
    }

    @Override
    public TeachCalendar getById(Serializable id) {
        TeachCalendar teachCalendar = super.getById(id);
        if (teachCalendar.getWeekNum() == null) {
            teachCalendar.setWeekNum(getWeekNum(teachCalendar));
        }
        return teachCalendar;
    }

    @Override
    public List<Week> getWeeks() {
        TeachCalendar teachCalendar = getTeachCalendarCurrent();
        return getWeeks(teachCalendar);
    }

    @Override
    public List<Week> getWeeks(TeachCalendar teachCalendar) {
        Calendar now = Calendar.getInstance();
        now.setTime(teachCalendar.getStartDate());
        now.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        List<Week> weeks = new ArrayList<>();
        Integer index = 1;
        while (now.getTime().before(teachCalendar.getEndDate())) {
            Week week = new Week();
            week.setWeek(index++);
            week.setStartDate(now.getTime());
            now.add(Calendar.DAY_OF_YEAR, 6);
            week.setEndDate(now.getTime());
            now.add(Calendar.DAY_OF_YEAR, 1);
            weeks.add(week);
        }
        return weeks;
    }

    private Integer getWeekNum(TeachCalendar teachCalendar) {
        Calendar now = Calendar.getInstance();
        now.setTime(teachCalendar.getStartDate());
        now.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Integer num = 0;
        while (now.getTime().before(teachCalendar.getEndDate())) {
            num++;
            now.add(Calendar.DAY_OF_YEAR, 7);
        }
        return num;
    }

    @Override
    public int getWeek(Long schoolId, Date date) {
        return getWeek(getTeachCalendarCurrent(schoolId), date);
    }

    @Override
    public int getWeek(TeachCalendar teachCalendar, Date date) {
        Assert.notNull(teachCalendar, "teachCalendar is null");
        Assert.notNull(date, "date is null");
        if (teachCalendar.getStartDate().after(date)) {
            date = teachCalendar.getStartDate();
        }
        if (teachCalendar.getEndDate().before(date)) {
            date = teachCalendar.getEndDate();
        }
        Calendar time = Calendar.getInstance();
        time.setTime(teachCalendar.getStartDate());
        time.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        time.set(Calendar.HOUR_OF_DAY, 0);
        int week = 0;
        do {
            week++;
            time.add(Calendar.DAY_OF_YEAR, 7);
        } while (time.getTime().getTime() <= date.getTime());
        return week;
    }

    @Override
    public TeachCalendar getByDate(Long schoolId, Date startDate, Date endDate) {
        QueryWrapper query = new QueryWrapper();
        query.eq("school_id", schoolId);
        query.ge("end_date", startDate);
        query.le("start_date", endDate);
        query.eq("del_flag", '0');
        List<TeachCalendar> list = list(query);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<String> getYears(Long schoolId) {
        return teachCalendarMapper.getYears(schoolId);
    }

    @Override
    public Week getWeekDate(Long calendarId, Integer week) {
        List<Week> weeks = getWeeks(getById(calendarId));
        for (Week w : weeks) {
            if (w.getWeek() == week) {
                return w;
            }
        }
        return null;
    }

    @Override
    public Week getWeekDate(TeachCalendar calendar) {
        List<Week> weeks = getWeeks(calendar);
        int week = getWeek(calendar, new Date());
        for (Week w : weeks) {
            if (w.getWeek() == week) {
                return w;
            }
        }
        return null;
    }

    @Override
    public int getMonth(TeachCalendar calendar, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    @Override
    public List<Month> getMonths(TeachCalendar calendar) {
        Calendar now = Calendar.getInstance();
        now.setTime(calendar.getStartDate());
        now.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        List<Month> months = new ArrayList<>();
        while (now.getTime().before(calendar.getEndDate())) {
            Month month = new Month();
            month.setMonth(now.get(Calendar.MONTH) + 1);
            month.setStartDate(now.getTime());
            now.add(Calendar.MONTH, 1);
            now.set(Calendar.DAY_OF_MONTH, 1);
            now.add(Calendar.DAY_OF_YEAR, -1);
            month.setEndDate(now.getTime());
            months.add(month);
            now.add(Calendar.DAY_OF_YEAR, 1);
        }
        return months;
    }

    public void initSchool(School school) {
        TeachCalendar form = new TeachCalendar();
        form.setSchoolId(school.getId());
        List<TeachCalendar> list = teachCalendarMapper.selectTeachCalendarList(form);
        if (!list.isEmpty()) {
            return;
        }
        TeachCalendar teachCalendar = new TeachCalendar();
        teachCalendar.setSchoolId(school.getId());
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        if (now.get(Calendar.MONTH) < 8) {
            teachCalendar.setYear(String.format("%d-%d", year - 1, year));
            teachCalendar.setTerm("2");
            now.set(Calendar.MONTH, 2);
            now.set(Calendar.DAY_OF_MONTH, 1);
            teachCalendar.setStartDate(now.getTime());
            now.set(Calendar.MONTH, 5);
            now.set(Calendar.DAY_OF_MONTH, 30);
        } else {
            teachCalendar.setYear(String.format("%d-%d", year, year + 1));
            teachCalendar.setTerm("1");
            now.set(Calendar.MONTH, 8);
            now.set(Calendar.DAY_OF_MONTH, 1);
            teachCalendar.setStartDate(now.getTime());
            now.add(Calendar.YEAR, 1);
            now.set(Calendar.MONTH, 0);
            now.set(Calendar.DAY_OF_MONTH, 20);
        }
        teachCalendar.setEndDate(now.getTime());
        teachCalendar.setCurrent("Y");
        saveOrUpdate(teachCalendar);
    }
}
