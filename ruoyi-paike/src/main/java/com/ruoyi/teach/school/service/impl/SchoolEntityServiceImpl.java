package com.ruoyi.teach.school.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.domain.LongEntity;
import com.ruoyi.common.core.service.impl.EntityServiceImpl;
import com.ruoyi.teach.school.domain.*;
import com.ruoyi.teach.school.service.TeachCalendarService;
import com.ruoyi.teach.school.service.TeacherService;
import com.ruoyi.teach.system.domain.School;
import com.ruoyi.teach.system.service.SchoolService;
import com.ruoyi.teach.system.service.TeachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

public class SchoolEntityServiceImpl<M extends BaseMapper<T>, T extends LongEntity> extends EntityServiceImpl<M, T> {

	@Lazy
	@Autowired
	protected TeachService teachService;
	@Autowired
	protected SchoolService schoolService;
	@Lazy
	@Autowired
	protected TeachCalendarService teachCalendarService;
	@Lazy
	@Autowired
	protected TeacherService teacherService;

	protected void selectSchoolObjectList(ISchoolEntity schoolEntity) {
		if (schoolEntity.getSchoolId() == null) {
			schoolEntity.setSchoolId(getSchoolId());
		}
//        initSchool(schoolEntity.getSchoolId());
	}

	public void insertSchoolObject(ISchoolEntity schoolEntity) {
		if (schoolEntity.getSchoolId() == null) {
			schoolEntity.setSchoolId(getSchoolId());
		}
	}

	protected School getSchool() {
		return teachService.getSchool();
	}

	protected Long getSchoolId() {
		return teachService.getSchoolId();
	}

	protected TeachCalendar getTeachCalendarCurrent() {
		return teachCalendarService.getTeachCalendarCurrent();
	}

	protected Teacher getTeacher() {
		return teachService.getTeacher();
	}

	public T getOne(Wrapper<T> queryWrapper, Long schoolId) {
		if (schoolId == null) {
			schoolId = getSchoolId();
		}
		QueryWrapper query = (QueryWrapper) queryWrapper;
		query.eq("school_id", schoolId);
		return (T) super.getOne(query);
	}

	@Override
	public T getOne(Wrapper<T> queryWrapper) {
		return super.getOne(queryWrapper);
	}

	public List<T> list(Wrapper<T> queryWrapper, Long schoolId) {
		if (schoolId == null) {
			schoolId = getSchoolId();
		}
		QueryWrapper query = (QueryWrapper) queryWrapper;
		query.eq("school_id", schoolId);
		return super.list(query);
	}

	public List<T> list(IPage<T> page, Wrapper<T> queryWrapper) {
		return list(page, queryWrapper, null);
	}

	public List<T> list(IPage<T> page, Wrapper<T> queryWrapper, Long schoolId) {
		QueryWrapper query = (QueryWrapper) queryWrapper;
		if (schoolId == null) {
			query.eq("school_id", getSchoolId());
		}
		page = super.page(page, query);
		return page.getRecords();
	}

	protected List<T> list(T entity) {
		SchoolEntity schoolEntity = (SchoolEntity) entity;
		if (schoolEntity.getSchoolId() == null) {
			schoolEntity.setSchoolId(getSchoolId());
		}
		QueryWrapper query = new QueryWrapper(entity);
		return list(query);
	}

	@Override
	public boolean saveOrUpdate(T entity) {
		setSchoolId(entity);
		return super.saveOrUpdate(entity);
	}

	private void setSchoolId(T entity) {
		SchoolEntity schoolEntity = (SchoolEntity) entity;
		if (schoolEntity.getSchoolId() == null) {
			schoolEntity.setSchoolId(getSchoolId());
		}
	}

	protected void initSchool(Long schoolId) {

	}

	protected void initTeacherObject(TeacherObject teacherObject) {
		if (teacherObject.getTeacherId() != null) {
			Teacher teacher = teacherService.getById(teacherObject.getTeacherId());
			teacherObject.setTeacherName(teacher.getName());
		}
	}

	protected void initTeachCalendarEntity(TeachCalendarEntity entity) {
		TeachCalendar teachCalendar;
		if (entity.getTeachCalendarId() == null) {
			teachCalendar = teachCalendarService.getTeachCalendarCurrent(entity.getSchoolId());
		} else {
			teachCalendar = teachCalendarService.getById(entity.getTeachCalendarId());
		}
		entity.setTeachCalendarId(teachCalendar.getId());
		entity.setYear(teachCalendar.getYear());
		entity.setTerm(teachCalendar.getTerm());
	}

	@Override
	protected void importEntity(T source) {
//        super.importEntity(source);
		setSchoolId(source);
	}
}
