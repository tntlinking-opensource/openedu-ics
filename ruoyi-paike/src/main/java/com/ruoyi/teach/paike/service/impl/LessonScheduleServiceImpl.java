package com.ruoyi.teach.paike.service.impl;

import com.ruoyi.common.core.service.impl.EntityServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.teach.paike.domain.LessonPlan;
import com.ruoyi.teach.paike.domain.LessonSchedule;
import com.ruoyi.teach.paike.mapper.LessonScheduleMapper;
import com.ruoyi.teach.paike.service.*;
import com.ruoyi.teach.paike.vo.LessonScheduleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 排课任务Service业务层处理
 *
 * @author ruoyi
 * @date 2021-10-25
 */
@Service
public class LessonScheduleServiceImpl extends EntityServiceImpl<LessonScheduleMapper, LessonSchedule> implements LessonScheduleService {

    @Autowired
    private LessonScheduleMapper lessonScheduleMapper;
    @Lazy
    @Autowired
    private LessonPlanService lessonPlanService;
    @Lazy
    @Autowired
    private LessonPlanScheduleService lessonPlanScheduleService;
    @Autowired
    private PaikeConverter paikeConverter;
    @Lazy
    @Autowired
    private ClassroomService classroomService;
    @Lazy
    @Autowired
    private ClassroomSpecialService classroomSpecialService;
    @Lazy
    @Autowired
    private LessonScheduleRuleService lessonScheduleRuleService;
    @Lazy
    @Autowired
    private LessonScheduleTimeService lessonScheduleTimeService;

    /**
     * 查询排课任务
     *
     * @param id 排课任务ID
     * @return 排课任务
     */
    @Override
    public LessonSchedule selectLessonScheduleById(Long id) {
        return lessonScheduleMapper.selectLessonScheduleById(id);
    }

    @Override
    @Cacheable(value = "lessonSchedule:", key = "'selectBySchoolId_' + #p0")
    public LessonSchedule getBySchoolId(Long schoolId) {
        LessonSchedule lessonSchedule = lessonScheduleMapper.selectBySchoolId(schoolId);
        Assert.notNull(lessonSchedule, "请设置当前排课任务");
        return lessonSchedule;
    }

    /**
     * 查询排课任务列表
     *
     * @param lessonSchedule 排课任务
     * @return 排课任务
     */
    @Override
    public List<LessonSchedule> selectLessonScheduleList(LessonSchedule lessonSchedule) {
        return lessonScheduleMapper.selectLessonScheduleList(lessonSchedule);
    }

    /**
     * 新增排课任务
     *
     * @param form 排课任务
     * @return 结果
     */
    @Override
    @Transactional
    @CacheEvict(value = "lessonSchedule:", key = "'selectBySchoolId_' + #p0.schoolId")
    public void insertLessonSchedule(LessonScheduleForm form) {
        LessonSchedule lessonSchedule = paikeConverter.toLessonSchedule(form);
        lessonSchedule.setCreateTime(DateUtils.getNowDate());
        lessonSchedule.setUpdateTime(DateUtils.getNowDate());
        save(lessonSchedule);
        updateIsCurrent(lessonSchedule);
        if (form.getCopyId() != null) {
            copyLessonSchedule(form.getCopyId(), lessonSchedule.getId());
        }
    }

    private void copyLessonSchedule(Long sourceId, Long targetId) {
        classroomService.copy(sourceId, targetId);
        classroomSpecialService.copy(sourceId, targetId);
        lessonPlanService.copy(sourceId, targetId);
        lessonScheduleRuleService.copy(sourceId, targetId);
        lessonScheduleTimeService.copy(sourceId, targetId);
    }

    private void updateIsCurrent(LessonSchedule lessonSchedule) {
        if ("Y".equals(lessonSchedule.getIsCurrent())) {
            lessonScheduleMapper.updateIsCurrent(lessonSchedule);
        }
    }

    /**
     * 修改排课任务
     *
     * @param lessonSchedule 排课任务
     * @return 结果
     */
    @Override
    @CacheEvict(value = "lessonSchedule:", key = "'selectBySchoolId_' + #p0.schoolId")
    public void updateLessonSchedule(LessonSchedule lessonSchedule) {
        lessonSchedule.setUpdateTime(DateUtils.getNowDate());
        updateById(lessonSchedule);
        updateIsCurrent(lessonSchedule);
    }

    /**
     * 批量删除排课任务
     *
     * @param schoolId
     * @param ids      需要删除的排课任务ID
     * @return 结果
     */
    @Override
    @Transactional
    @CacheEvict(value = "lessonSchedule:", key = "'selectBySchoolId_' + #p0")
    public int deleteLessonScheduleByIds(Long schoolId, Long[] ids) {
        for (Long id : ids) {
            lessonScheduleMapper.deleteLessonScheduleTime(id);
            lessonScheduleMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    @Transactional
    public void start(Long lessonScheduleId) {
        LessonSchedule lessonSchedule = getById(lessonScheduleId);
        lessonPlanService.cleanTimes(lessonScheduleId);
        List<LessonPlan> plans = lessonPlanService.getByLessonScheduleId(lessonScheduleId);
        lessonPlanScheduleService.autoSchedule(lessonSchedule, plans);
        lessonPlanService.updateBatchById(plans);
        lessonSchedule.setIsAutoSchedule("Y");
        updateById(lessonSchedule);
    }

}
