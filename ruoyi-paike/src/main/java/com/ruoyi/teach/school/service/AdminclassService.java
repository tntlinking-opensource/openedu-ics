package com.ruoyi.teach.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.teach.school.domain.Adminclass;
import com.ruoyi.teach.school.domain.Grade;
import com.ruoyi.teach.school.domain.Teacher;

import java.util.List;
import java.util.Map;

/**
 * 班级Service接口
 *
 * @author beangle
 * @date 2021-07-07
 */
public interface AdminclassService extends IService<Adminclass> {
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
    int insertAdminclass(Long schoolId, Adminclass adminclass);

    /**
     * 修改班级
     *
     * @param id
     * @param adminclass 班级
     * @return 结果
     */
    public int updateAdminclass(Long id, Adminclass adminclass);

    /**
     * 批量删除班级
     *
     * @param schoolId
     * @param year
     * @return 结果
     */
    List<Adminclass> getClassBySchoolId(Long schoolId, String year);

    public int deleteAdminclassByIds(Long id, Long[] ids);

    String importAdminclass(List<Adminclass> adminclasses);

    Adminclass getAdminclass(Long schoolId, String year, String name);

    @Deprecated
    List<Adminclass> getAll();

    List<Adminclass> getAll(Long schoolId);

    List<Adminclass> getAll(Long schoolId, String year);

    List<Adminclass> getBySchool(Long schoolId);
    List<Adminclass> getBySchoolId(Long schoolId);

    List<Adminclass> getBySchoolId(Long schoolId, String year);

    List<Adminclass> getByIds(List<Long> ids);

    Map<String, Adminclass> getAdminclassMap(Long schoolId);

    List<Adminclass> getByTeacherId(Long schoolId, Long teacherId);

    List<Adminclass> getByTeacherId(Long schoolId, String year, Long teacherId);


    List<Adminclass> getByTeacher(Teacher teacher);

    List<Grade> getGrades(Long schoolId);

    List<Grade> getGrades(Long schoolId, String year);

    Map<Long, Integer> getStudentNumMap(Long schoolId);

    Integer getStudentNum(Long adminclassId);

    List<Adminclass> getByGrade(Long schoolId, String year, String grade);

    Adminclass getByName(List<Adminclass> adminclasses, String name);

    void updateStudentNum(Long schoolId);

    void updateStudentNum(Long schoolId, String year);
}
