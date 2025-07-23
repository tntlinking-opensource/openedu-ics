package com.ruoyi.teach.school.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.teach.school.domain.Teacher;
import com.ruoyi.teach.school.mapper.TeacherMapper;
import com.ruoyi.teach.school.service.TeacherService;
import com.ruoyi.teach.system.domain.School;
import com.ruoyi.teach.system.service.TeachUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 教师Service业务层处理
 *
 * @author beangle
 * @date 2021-07-08
 */
@Service
public class TeacherServiceImpl extends SchoolEntityServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private TeachUserService teachUserService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 查询教师
     *
     * @param id 教师ID
     * @return 教师
     */
    @Override
    public Teacher selectTeacherById(Long id) {
        return teacherMapper.selectTeacherById(id);
    }

    /**
     * 查询教师列表
     *
     * @param teacher 教师
     * @return 教师
     */
    @Override
    public List<Teacher> selectTeacherList(Teacher teacher) {
//        selectSchoolObjectList(teacher);
        if (teacher.getDelFlag() == null) {
            teacher.setDelFlag("0");
        }
        return teacherMapper.selectTeacherList(teacher);
    }

    /**
     * 新增教师
     *
     * @param teacher 教师
     * @return 结果
     */
    @Override
//    @CacheEvict(value = {"teacher"}, allEntries = true)
    public int insertTeacher(Teacher teacher) {
        insertSchoolObject(teacher);
        checkCode(teacher);
        if (teacher.getUserId() == null && StringUtils.isNotBlank(teacher.getPhone())) {
            SysUser user = createUser(teacher);
            teacher.setUserId(user.getUserId());
        }
        if (StringUtils.isBlank(teacher.getStatus())) {
            teacher.setStatus("0");
        }
        return teacherMapper.insertTeacher(teacher);
    }

    private void checkCode(Teacher teacher) {
        QueryWrapper query = new QueryWrapper();
        query.eq("code", teacher.getCode());
        query.ne(teacher.getId() != null, "id", teacher.getId());
        Teacher tea = getOne(query, teacher.getSchoolId());
        Assert.isTrue(tea == null, "工号重复");
    }

    private SysUser createUser(Teacher teacher) {
//        return teachUserService.createUser(teacher.getPhone(), teacher.getName(), teacher.getPassword());
        SysUser user = new SysUser();
        user.setUserName(teacher.getPhone());
        user.setNickName(teacher.getName());
        user.setPassword(teacher.getPassword());
//        user.setType(SysUser.TYPE_TEACHER);
//        user.setSchoolId(teacher.getSchoolId());
        return teachUserService.createUser(user);
    }

    /**
     * 修改教师
     *
     * @param teacher 教师
     * @return 结果
     */
    @Override
//    @CacheEvict(value = {"teacher"}, allEntries = true)
    @CacheEvict(value = "teacher", key = "'getByUserId_' + #p0.userId")
    public int updateTeacher(Teacher teacher) {
        checkCode(teacher);
        if ("1".equals(teacher.getStatus())) {
            teacher.setActivate(Teacher.ACTIVATE_NO);
            if (teacher.getUserId() != null) {
                SysUser user = new SysUser();
                user.setUserId(teacher.getUserId());
//                userService.updateSchoolId(user);
            }
        }
        if (StringUtils.isNotBlank(teacher.getPhone())) {
            if (teacher.getUserId() == null) {
                SysUser user = createUser(teacher);
                teacher.setUserId(user.getUserId());
            } else {
                teachUserService.updateUser(teacher.getUserId(), teacher.getPhone(), teacher.getName());
                if (StringUtils.isBlank(teacher.getStatus())) {
                    teacher.setStatus("0");
                }
            }
        }
        return teacherMapper.updateTeacher(teacher);
    }

    @Override
    @CacheEvict(value = "teacher", key = "'getByUserId_' + #p0")
    public void updateTeacherCacheEvict(Long userId) {

    }

    /**
     * 批量删除教师
     *
     * @param ids 需要删除的教师ID
     * @return 结果
     */
    @Override
    @CacheEvict(value = {"teacher"}, allEntries = true)
    public int deleteTeacherByIds(Long[] ids) {
        List<Teacher> teachers = selectTeacherByIds(ids);
        List<Long> userIds = teachers.stream().map(t -> t.getUserId()).collect(Collectors.toList());
//		userService.deleteUserByIds(userIds.toArray(new Long[]{}));
        teacherMapper.deleteTeacherByIds(ids);
        teacherMapper.deleteUserByIds(userIds);
        return 1;
    }

    @Override
    public List<Teacher> selectTeacherByIds(Long[] ids) {
        QueryWrapper query = new QueryWrapper();
        query.in("id", ids);
        return list(query);
    }

    @Override
    @Cacheable(value = "teacher", key = "'getByUserId_' + #p0")
    public Teacher getByUserId(Long userId) {
        return getByUser(userId, null);
    }

    @Override
    @CacheEvict(value = "teacher", key = "'getByUserId_' + #p0")
    public Teacher getByUserIdEvict(Long userId) {
        return getByUserId(userId);
    }

    @Override
    @Cacheable(value = "teacher", key = "'getByUserId_' + #p0.userId")
    public Teacher getByUser(SysUser user) {
        return getByUser(user.getUserId(), user);
    }

    public Teacher getByUser(Long userId, SysUser user) {
        if (user == null) {
            user = SecurityUtils.getLoginUser().getUser();
        }
        List<Teacher> teachers = teacherMapper.getByUser(user);
//        if (teachers.isEmpty()) {
//            teachers = teacherMapper.getAllByUserId(userId);
//            if (teachers.isEmpty()) {
//                return null;
//            }
//            Teacher teacher = teachers.get(0);
//            teacher.setActivate(Teacher.ACTIVATE_YES);
//            updateTeacher(teacher);
//            return teacher;
//        } else if (teachers.size() > 1) {
//            for (int i = 1; i < teachers.size(); i++) {
//                Teacher teacher = teachers.get(i);
//                teacher.setActivate(Teacher.ACTIVATE_NO);
//                updateTeacher(teacher);
//            }
//        }
        if (userId == 1 && teachers.isEmpty()) {
            if (user == null) {
                user = SecurityUtils.getLoginUser().getUser();
            }
            Teacher teacher = new Teacher();
            teacher.setId(1L);
            teacher.setUserId(userId);
//            teacher.setSchoolId(user.getSchoolId());
            if (teacher.getSchoolId() == null) {
                teacher.setSchoolId(1L);
            }
            return teacher;
        }
        return teachers.isEmpty() ? null : teachers.get(0);
    }

    @Override
    @CacheEvict(value = "teacher", key = "'getByUserId_' + #p0")
    public void changeSchool(Long userId, Long schoolId) {
        if (userId == 1) {
//            redisCache.setCacheObject("yzsoft_school_id", schoolId);
            return;
        }
//        List<Teacher> teachers = getTeachersByUserId(userId);
//        teachers.forEach(t -> {
//            t.setActivate(schoolId.equals(t.getSchoolId()) ? "yes" : "no");
//        });
//        updateBatchById(teachers);
        SysUser user = new SysUser();
        user.setUserId(userId);
//        user.setSchoolId(schoolId);
//        userService.updateSchoolId(user);
    }

    @Override
    public Map<String, Teacher> getTeacherMap(Long schoolId) {
        List<Teacher> list = getBySchoolId(schoolId);
        Map<String, Teacher> map = new HashMap<>();
        for (Teacher teacher : list) {
            String key = teacher.getName();
            if (StringUtils.isNotBlank(teacher.getName2())) {
                key = String.format("%s（%s）", teacher.getName(), teacher.getName2());
            }
            map.put(key, teacher);
        }
        return map;
    }

    @Override
    public Teacher getTeacher(List<Teacher> teachers, String code, String name) {
        name = name.replaceAll("\\s", "");
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (Teacher t : teachers) {
            if (t.getCode().equals(code)) {
                return t;
            }
            if (t.getName2() != null && (" " + t.getName2() + " ").contains(" " + name + " ")) {
                return t;
            }
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    @Override
    public Teacher getByName(List<Teacher> teachers, String name) {
        name = name.replaceAll("\\s", "");
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (Teacher t : teachers) {
            if (t.getName2() != null && (" " + t.getName2() + " ").contains(" " + name + " ")) {
                return t;
            }
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    @Override
    public void updateOpenid(Long id, String openid) {
        Assert.isTrue(StringUtils.isNotBlank(openid), "openid不能为空");
        teacherMapper.updateOpenid(id, openid);
    }

    @Override
    public Integer getTeacherCount(Long userId) {
        return teacherMapper.getTeacherCount(userId);
    }

    private List<Teacher> getTeachersByUserId(Long userId) {
        QueryWrapper query = new QueryWrapper();
        query.eq("user_id", userId);
        return list(query);
    }

    @Override
    public List<Teacher> getAll(Long schoolId) {
//		QueryWrapper query = new QueryWrapper();
//		query.orderByAsc("name");
//		return list(query, schoolId);
        return getBySchoolId(schoolId);
    }

    @Override
    public List<Teacher> getTeachers(String code, String name) {
        Teacher teacher = new Teacher();
        teacher.setSchoolId(getSchoolId());
        if (!StringUtils.isBlank(code)) {
            teacher.setCode(code);
        }
        teacher.setName(name);
        return list(new QueryWrapper<>(teacher));
    }

//    @Override
//    public Teacher getTeacher() {
//        Teacher teacher = getByUserId(SecurityUtils.getLoginUser().getUser().getUserId());
//        Assert.notNull(teacher, "教师信息不存在");
//        return teacher;
//    }

    @Override
    public Teacher getTeacherByPhone(Long schoolId, String phone) {
        Teacher teacher = new Teacher();
        teacher.setPhone(phone);
        return getOne(new QueryWrapper(teacher), schoolId);
    }

    @Override
    public List<Teacher> getByKey(Long schoolId, String key) {
        Page<Teacher> page = new Page(1, 10);
        QueryWrapper<Teacher> query = new QueryWrapper();
        query.eq("school_id", schoolId);
        query.eq("status", "0");
        query.eq("del_flag", "0");
        if (StringUtils.isNotBlank(key)) {
            query.and(i -> i.like("code", key).or().like("name", key));
        }
        return list(page, query, schoolId);
    }

    /**
     * 删除教师信息
     *
     * @param id 教师ID
     * @return 结果
     */
    @Override
    public int deleteTeacherById(Long id) {
//        Teacher teacher = getById(id);
//        sysUserService.deleteUserById(teacher.getUserId());
        return teacherMapper.deleteTeacherById(id);
    }

    @Override
    public Teacher getByCode(Long schoolId, String teacherCode) {
        QueryWrapper query = new QueryWrapper();
        query.eq("code", teacherCode);
        return getOne(query, schoolId);
    }

    @Override
    public Teacher getByName(Long schoolId, String name) {
        QueryWrapper query = new QueryWrapper();
        query.eq("name", name);
        return getOne(query, schoolId);
    }

    @Override
    public List<Teacher> getBySchoolId(Long schoolId) {
        QueryWrapper query = new QueryWrapper();
        query.eq("school_id", schoolId);
        query.eq("status", "0");
        query.eq("del_flag", "0");
        query.orderByAsc("code");
        return list(query);
    }

    @Override
    public List<Teacher> getTeachers() {
//        QueryWrapper query = new QueryWrapper();
//        query.eq("status", "0");
//        query.orderByAsc("code");
//        return list(query, getSchoolId());
        return getBySchoolId(getSchoolId());
    }

    @Override
    public String importTeacher(List<Teacher> teachers) {
        if (teachers == null || teachers.size() == 0) {
            throw new ServiceException("导入班级数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        School school = getSchool();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (int i = 0; i < teachers.size(); i++) {
            Teacher source = teachers.get(i);
            try {
                source.setSchoolId(school.getId());
                Assert.isTrue(StringUtils.isNotBlank(source.getCode()), "工号不能为空");
                Assert.isTrue(StringUtils.isNotBlank(source.getName()), "姓名不能为空");
                Assert.isTrue(StringUtils.isNotBlank(source.getPhone()), "手机号不能为空");
                source.setName(source.getName().replaceAll("\\s", ""));
//                Teacher teacher = getByCode(school.getId(), source.getPhone());
                Teacher teacher = getTeacherByPhone(school.getId(), source.getPhone());
                if (teacher == null) {
                    teacher = getByCode(school.getId(), source.getCode());
                }
                if (teacher == null) {
                    this.insertTeacher(source);
                } else {
                    source.setId(teacher.getId());
                    source.setUserId(teacher.getUserId());
                    this.updateTeacher(source);
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
}
