package com.ruoyi.teach.school.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.teach.school.domain.LessonTime;
import com.ruoyi.teach.school.mapper.LessonTimeMapper;
import com.ruoyi.teach.school.service.LessonTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 上课节次Service业务层处理
 *
 * @author beangle
 * @date 2021-07-06
 */
@Service
@EnableAsync
public class LessonTimeServiceImpl extends SchoolEntityServiceImpl<LessonTimeMapper, LessonTime> implements LessonTimeService {

    @Autowired
    private LessonTimeMapper lessonTimeMapper;

    @Override
    public void initSchool(Long schoolId) {

    }

    /**
     * 查询上课节次
     *
     * @param id 上课节次ID
     * @return 上课节次
     */
    @Override
    public LessonTime selectLessonTimeById(Long id) {
        return lessonTimeMapper.selectLessonTimeById(id);
    }

    /**
     * 查询上课节次列表
     *
     * @param lessonTime 上课节次
     * @return 上课节次
     */
    @Override
    public List<LessonTime> selectLessonTimeList(LessonTime lessonTime) {
        selectSchoolObjectList(lessonTime);
        List<LessonTime> list = lessonTimeMapper.selectLessonTimeList(lessonTime);
        return list;
    }


    /**
     * 新增上课节次
     *
     * @param lessonTime 上课节次
     * @return 结果
     */
    @Override
    @CacheEvict(value = {"LessonTime"}, key = "'School_'+ #p0.schoolId", allEntries = true)
    public int insertLessonTime(LessonTime lessonTime) {
        insertSchoolObject(lessonTime);
        return lessonTimeMapper.insertLessonTime(lessonTime);
    }

    /**
     * 修改上课节次
     *
     * @param lessonTime 上课节次
     * @return 结果
     */
    @Override
    @CacheEvict(value = {"LessonTime"}, key = "'School_'+ #p0.schoolId", allEntries = true)
    public int updateLessonTime(LessonTime lessonTime) {
        return lessonTimeMapper.updateLessonTime(lessonTime);
    }

    /**
     * 批量删除上课节次
     *
     * @param ids 需要删除的上课节次ID
     * @return 结果
     */
    @Override
    @CacheEvict(value = {"LessonTime"}, key = "'School_'+ #p0.schoolId", allEntries = true)
    public int deleteLessonTimeByIds(Long[] ids) {
        return lessonTimeMapper.deleteLessonTimeByIds(ids);
    }

    /**
     * 删除上课节次信息
     *
     * @param id 上课节次ID
     * @return 结果
     */
    @Override
    @CacheEvict(value = {"LessonTime"}, key = "'School_'+ #p0.schoolId", allEntries = true)
    public int deleteLessonTimeById(Long id) {
        return lessonTimeMapper.deleteLessonTimeById(id);
    }

    @Override
    @Cacheable(value = "LessonTime", key = "'School_'+ #p0 +':getBySchoolId'")
    public List<LessonTime> getBySchoolId(Long schoolId) {
        QueryWrapper query = new QueryWrapper();
        query.eq("school_id", schoolId);
        query.orderByAsc("sort");
        List<LessonTime> list = list(query);
        if(list.isEmpty()){
            list = initLessonTime(schoolId);
        }
        int sort = 1;
        for (LessonTime lessonTime : list) {
            if("课程".equals(lessonTime.getLessonType())){
                lessonTime.setSort(sort++);
                lessonTime.setTimeIndex(lessonTime.getSort());
            }
        }
        return list;
    }

    @Override
    public Integer getTimeSort(List<LessonTime> times, String time) {
        for (LessonTime lessonTime : times) {
            if (lessonTime.getName().equals(time)) {
                return lessonTime.getSort();
            }
        }
        return 1;
    }

    @Override
    public List<LessonTime> getLessonTime(Long schoolId) {
        List<LessonTime> lessonTime = lessonTimeMapper.getLessonTime(schoolId);
        if(lessonTime.isEmpty()){
            lessonTime = initLessonTime(schoolId);
        }
//        lessonTime.forEach(l->l.setSort(l.getTimeIndex()));
        for (int i = 0; i < lessonTime.size(); i++) {
            lessonTime.get(i).setSort(i + 1);
        }
        return lessonTime;
    }

    public List<LessonTime> initLessonTime(Long schoolId) {
        /*LessonTime form = new LessonTime();
        form.setSchoolId(schoolId);
        List<LessonTime> list = lessonTimeMapper.selectLessonTimeList(form);
        if (!list.isEmpty()) {
            return list;
        }*/
        String[] names = new String[]{"一", "二", "三", "四", "五", "六", "七", "八"};
        String[] categorys = new String[]{"上午", "下午"};
        List<LessonTime> times = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            LessonTime time = new LessonTime();
            time.setSchoolId(schoolId);
            time.setSort(i + 1);
            time.setTimeIndex(i + 1);
            time.setName("第" + names[i] + "节");
            time.setLessonType("课程");
            time.setCategory(categorys[(int) Math.floor(i / (names.length / 2))]);
            times.add(time);
        }
        saveBatch(times);
        return times;
    }
}
