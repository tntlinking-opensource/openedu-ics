package com.ruoyi.teach.school.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.RuoYiUtil;
import com.ruoyi.teach.school.domain.Adminclass;
import com.ruoyi.teach.school.domain.Grade;
import com.ruoyi.teach.school.domain.TeachCalendar;
import com.ruoyi.teach.school.domain.Teacher;
import com.ruoyi.teach.school.mapper.AdminclassMapper;
import com.ruoyi.teach.school.service.AdminclassService;
import com.ruoyi.teach.school.service.GradeService;
import com.ruoyi.teach.school.service.TeachCalendarService;
import com.ruoyi.teach.school.service.TeacherService;
import com.ruoyi.teach.system.domain.School;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * 班级Service业务层处理
 *
 * @author beangle
 * @date 2021-07-07
 */
@Service
public class AdminclassServiceImpl extends SchoolEntityServiceImpl<AdminclassMapper, Adminclass> implements AdminclassService {
    @Autowired
    private AdminclassMapper adminclassMapper;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeachCalendarService teachCalendarService;
    @Autowired
    private GradeService gradeService;

    /**
     * 查询班级
     *
     * @param id 班级ID
     * @return 班级
     */
    @Override
    public Adminclass selectAdminclassById(Long id) {
        return adminclassMapper.selectAdminclassById(id);
    }

    /**
     * 查询班级列表
     *
     * @param adminclass 班级
     * @return 班级
     */
    @Override
    public List<Adminclass> selectAdminclassList(Adminclass adminclass) {
        selectSchoolObjectList(adminclass);
        return adminclassMapper.selectAdminclassList(adminclass);
    }

    /**
     * 新增班级
     *
     * @param adminclass 班级
     * @return 结果
     */
    @Override
    @CacheEvict(value = {"Adminclass"}, key = "'School_'+ #p0", allEntries = true)
    public int insertAdminclass(Long schoolId, Adminclass adminclass) {
        adminclass.setSchoolId(schoolId);
        initAdminclass(adminclass);
        classTeacherRoleAdd(adminclass);//保存到班主任角色
        return adminclassMapper.insertAdminclass(adminclass);
    }

    private void classTeacherRoleAdd(Adminclass adminclass) {
        //获取班主任角色id
        Long roleId = adminclassMapper.getClassTeacherRoleId();
        if (roleId != null) {
            //删除之前的班主任角色
            adminclassMapper.deleteClassTeacher(adminclass.getSchoolId(), adminclass.getTeacherId(), roleId);
            //添加新的班主任到班主任角色中
            adminclassMapper.insertTeacherRole(adminclass.getSchoolId(), adminclass.getTeacherId(), roleId);
        }
    }

    /**
     * 修改班级
     *
     * @param schoolId
     * @param adminclass 班级
     * @return 结果
     */
    @Override
    @CacheEvict(value = {"Adminclass"}, key = "'School_'+ #p0", allEntries = true)
    public int updateAdminclass(Long schoolId, Adminclass adminclass) {
        initAdminclass(adminclass);
        classTeacherRole(adminclass);//保存到班主任角色
        return adminclassMapper.updateAdminclass(adminclass);
    }

    private void classTeacherRole(Adminclass adminclass) {
        Adminclass oldAdminClass = getById(adminclass.getId());
        //获取班主任角色id
        Long roleId = adminclassMapper.getClassTeacherRoleId();
        if (roleId != null) {
            //删除之前的班主任角色
            adminclassMapper.deleteClassTeacher(oldAdminClass.getSchoolId(), oldAdminClass.getTeacherId(), roleId);
            //删除新的班主任角色 保证不重复
            adminclassMapper.deleteClassTeacher(adminclass.getSchoolId(), adminclass.getTeacherId(), roleId);
            //添加新的班主任到班主任角色中
            adminclassMapper.insertTeacherRole(adminclass.getSchoolId(), adminclass.getTeacherId(), roleId);
        }
    }

    @Override
    public List<Adminclass> getClassBySchoolId(Long schoolId, String year) {
        return adminclassMapper.getClassBySchoolId(schoolId, year);
    }

    private void initAdminclass(Adminclass adminclass) {
        if (adminclass.getTeacherId() != null) {
            Teacher teacher = teacherService.selectTeacherById(adminclass.getTeacherId());
            adminclass.setTeacherName(teacher.getName());
        }
        Assert.isTrue(!existsAdminclass(adminclass), "班级重复");
    }

    private boolean existsAdminclass(Adminclass adminclass) {
        QueryWrapper query = new QueryWrapper();
        query.ne(adminclass.getId() != null, "id", adminclass.getId());
        query.eq("school_id", adminclass.getSchoolId());
        query.eq("year", adminclass.getYear());
        query.eq("name", adminclass.getName());
        return !list(query).isEmpty();
    }

    /**
     * 批量删除班级
     *
     * @param schoolId
     * @param ids      需要删除的班级ID
     * @return 结果
     */
    @Override
    @CacheEvict(value = {"Adminclass"}, key = "'School_'+ #p0", allEntries = true)
    public int deleteAdminclassByIds(Long schoolId, Long[] ids) {
        return adminclassMapper.deleteAdminclassByIds(ids);
    }

    @Override
    public String importAdminclass(List<Adminclass> adminclasses) {
        if (adminclasses == null || adminclasses.size() == 0) {
            throw new ServiceException("导入班级数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        School school = getSchool();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (int i = 0; i < adminclasses.size(); i++) {
            Adminclass source = adminclasses.get(i);
            try {
                source.setSchoolId(school.getId());
                if (StringUtils.isNotBlank(source.getTeacherCode())) {
                    Teacher teacher = teacherService.getByCode(school.getId(), source.getTeacherCode());
                    Assert.notNull(teacher, String.format("教师工号%s有误", source.getTeacherCode()));
                    source.setTeacherId(teacher.getId());
                    source.setTeacherName(teacher.getName());
                }
                Adminclass adminclass = selectByExample(source);
                if (adminclass == null) {
                    this.insertAdminclass(source.getSchoolId(), source);
                } else {
                    source.setId(adminclass.getId());
                    this.updateAdminclass(school.getId(), source);
                }
                successNum++;
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>第" + (i + 2) + "行导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "导入失败共 " + failureNum + " 条数据格式不正确，错误如下：");
            failureMsg.insert(0, "导入成功共 " + successNum + " 条。<br/>");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
        }
        return successMsg.toString();
    }

    @Override
    public List<Adminclass> getAll() {
        TeachCalendar teachCalendar = getTeachCalendarCurrent();
        QueryWrapper query = new QueryWrapper();
        query.eq("year", teachCalendar.getYear());
        query.orderByAsc("grade");
        query.orderByAsc("name");
        return list(query, teachCalendar.getSchoolId());
    }

    @Override
    @Cacheable(value = "Adminclass", key = "'getAll_' + #p0")
    public List<Adminclass> getAll(Long schoolId) {
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendarCurrent(schoolId);
        return getAll(schoolId, teachCalendar.getYear());
    }

    @Override
    @Cacheable(value = "Adminclass", key = "'School_'+ #p0 +':getAdminclass'+ #p1 +'_'+ #p2")
    public Adminclass getAdminclass(Long schoolId, String year, String name) {
        Adminclass adminclass = new Adminclass();
        adminclass.setSchoolId(schoolId);
        adminclass.setYear(year);
        adminclass.setName(name);
        return getOne(new QueryWrapper<>(adminclass));
    }

    @Override
    public List<Adminclass> getAll(Long schoolId, String year) {
        QueryWrapper<Adminclass> query = new QueryWrapper<>();
        query.eq("year", year);
        query.orderByAsc("code");
        List<Adminclass> adminclasses = list(query, schoolId);
//        sortAdminclass(adminclasses);
        return adminclasses;
    }

    @Override
    public List<Adminclass> getBySchool(Long schoolId) {
        return getBySchoolId(schoolId);
    }

    @Override
    public List<Adminclass> getBySchoolId(Long schoolId) {
        TeachCalendar calendar = teachCalendarService.getTeachCalendarCurrent(schoolId);
        List<Adminclass> adminclasses = getBySchoolId(schoolId, calendar.getYear());
        initImg(adminclasses);
        return adminclasses;
    }

    @Override
    public List<Adminclass> getBySchoolId(Long schoolId, String year) {
        return getAll(schoolId, year);
    }

    @Override
    public List<Adminclass> getByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>(0);
        }
        QueryWrapper query = new QueryWrapper();
        query.in("id", ids);
        return list(query);
    }

    @Override
    public Map<String, Adminclass> getAdminclassMap(Long schoolId) {
        List<Adminclass> list = getAll(schoolId);
        Map<String, Adminclass> map = new HashMap<>();
        for (Adminclass adminclass : list) {
            map.put(adminclass.getName(), adminclass);
        }
        return map;
    }

    @Override
    public List<Adminclass> getByTeacherId(Long schoolId, Long teacherId) {
        TeachCalendar calendar = teachCalendarService.getTeachCalendarCurrent(schoolId);
        List<Adminclass> adminclasses = getByTeacherId(schoolId, calendar.getYear(), teacherId);
        initImg(adminclasses);
        return adminclasses;
    }

    @Override
    public Adminclass getById(Serializable id) {
        Adminclass adminclass = super.getById(id);
        initImg(adminclass);
        return adminclass;
    }

    private void initImg(List<Adminclass> adminclasses) {
        for (Adminclass item : adminclasses) {
            initImg(item);
        }
    }

    private void initImg(Adminclass item) {
        String img = item.getImg();
        if (StringUtils.isBlank(img)) {
            img = "/profile/images/studentEval/icon_clazz.png";
        }
        img = RuoYiUtil.getProfile(img);
        item.setImg(img);
    }

    @Override
    public List<Adminclass> getByTeacherId(Long schoolId, String year, Long teacherId) {
        QueryWrapper<Adminclass> query = new QueryWrapper<>();
        query.eq("year", year);
        query.eq("teacher_id", teacherId);
        List<Adminclass> adminclasses = list(query, schoolId);
        sortAdminclass(adminclasses);
        return adminclasses;
    }

    @Override
    public List<Adminclass> getByTeacher(Teacher teacher) {
        return getByTeacherId(teacher.getSchoolId(), teacher.getId());
    }

    private void sortAdminclass(List<Adminclass> adminclasses) {
//        ChineseNumberComparator comparator = new ChineseNumberComparator();
//        Collections.sort(adminclasses, (a, b) -> comparator.compare(a.getName(), b.getName()));
        Comparator comparator = ComparatorUtils.chainedComparator(new BeanComparator("code"), new BeanComparator("name"));
        Collections.sort(adminclasses, comparator);
    }

    @Override
    public List<Grade> getGrades(Long schoolId) {
        TeachCalendar calendar = teachCalendarService.getTeachCalendarCurrent(schoolId);
        return getGrades(schoolId, calendar.getYear());
    }

    @Override
    public List<Grade> getGrades(Long schoolId, String year) {
//        QueryWrapper query = new QueryWrapper();
//        query.select("distinct grade");
//        query.eq("year", year);
//        List<Adminclass> adminclasses = list(query, schoolId);
//        ChineseNumberComparator comparator = new ChineseNumberComparator();
//        Collections.sort(adminclasses, (a, b) -> comparator.compare(a.getGrade(), b.getGrade()));
//        return adminclasses.stream().map(a -> new Grade(a.getGrade())).collect(Collectors.toList());
        return gradeService.getBySchool(schoolId);
    }

    @Override
    public Map<Long, Integer> getStudentNumMap(Long schoolId) {
        List<Adminclass> list = adminclassMapper.getStudentNum(schoolId);
        Map<Long, Integer> map = new HashMap<>();
        for (Adminclass a : list) {
            map.put(a.getId(), a.getStudentNum());
        }
        return map;
    }

    @Override
    public Integer getStudentNum(Long adminclassId) {
        return adminclassMapper.getStudentNumByAdminclass(adminclassId);
    }

    @Override
    public List<Adminclass> getByGrade(Long schoolId, String year, String grade) {
        return adminclassMapper.getByGrade(schoolId, year, grade);
    }

    private Adminclass selectByExample(Adminclass adminclass) {
        QueryWrapper query = new QueryWrapper();
        query.eq("school_id", adminclass.getSchoolId());
        query.eq("year", adminclass.getYear());
        query.eq("grade", adminclass.getGrade());
        query.eq("name", adminclass.getName());
        return getOne(query);
    }

    @Override
    public Adminclass getByName(List<Adminclass> adminclasses, String name) {
        name = name.replaceAll("（", "(");
        name = name.replaceAll("）", ")");
        if (name.contains("(") && !name.contains("班")) {
            name += "班";
        }
        for (Adminclass adminclass : adminclasses) {
            if (adminclass.getName().equals(name)) {
                return adminclass;
            }
        }
        return null;
    }

    @Override
    public void updateStudentNum(Long schoolId) {
        TeachCalendar calendar = teachCalendarService.getTeachCalendarCurrent(schoolId);
        updateStudentNum(schoolId, calendar.getYear());
    }

    @Override
    public void updateStudentNum(Long schoolId, String year) {
        adminclassMapper.updateStudentNum(schoolId, year);
    }
}
