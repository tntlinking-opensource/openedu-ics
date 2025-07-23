package com.ruoyi.teach.paike.service.impl;

import com.ruoyi.common.core.service.impl.EntityServiceImpl;
import com.ruoyi.teach.paike.domain.LessonSchedule;
import com.ruoyi.teach.paike.domain.LessonScheduleTime;
import com.ruoyi.teach.paike.mapper.LessonScheduleTimeMapper;
import com.ruoyi.teach.paike.service.LessonScheduleService;
import com.ruoyi.teach.paike.service.LessonScheduleTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作息安排Service业务层处理
 *
 * @author ruoyi
 * @date 2021-10-25
 */
@Service
public class LessonScheduleTimeServiceImpl extends EntityServiceImpl<LessonScheduleTimeMapper, LessonScheduleTime> implements LessonScheduleTimeService {

    @Autowired
    private LessonScheduleTimeMapper lessonScheduleTimeMapper;
    @Autowired
    private LessonScheduleService lessonScheduleService;

    @Override
    public LessonScheduleTime getByLessonScheduleId(Long lessonScheduleId) {
        LessonScheduleTime time = lessonScheduleTimeMapper.selectByLessonScheduleId(lessonScheduleId);
        if (time == null) {
            time = new LessonScheduleTime();
            time.setWeekdays(5);
            time.setMorning(4);
            time.setAfternoon(3);
            time.setNight(0);
        }
        return time;
    }

    /**
     * 查询作息安排列表
     *
     * @param lessonScheduleTime 作息安排
     * @return 作息安排
     */
    @Override
    public List<LessonScheduleTime> selectLessonScheduleTimeList(LessonScheduleTime lessonScheduleTime) {
        return lessonScheduleTimeMapper.selectLessonScheduleTimeList(lessonScheduleTime);
    }

    /**
     * 新增作息安排
     *
     * @param lessonScheduleTime 作息安排
     * @return 结果
     */
    @Override
    public void insertLessonScheduleTime(LessonScheduleTime lessonScheduleTime) {
        save(lessonScheduleTime);
    }

    /**
     * 修改作息安排
     *
     * @param lessonScheduleTime 作息安排
     * @return 结果
     */
    @Override
    public void updateLessonScheduleTime(LessonScheduleTime lessonScheduleTime) {
        updateById(lessonScheduleTime);
    }

    @Override
    public void copy(Long sourceId, Long targetId) {
        lessonScheduleTimeMapper.copy(sourceId, targetId);
    }

}
