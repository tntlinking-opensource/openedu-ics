package com.ruoyi.teach.paike.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.service.impl.EntityServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.teach.paike.domain.LessonPlan;
import com.ruoyi.teach.paike.domain.LessonScheduleRecord;
import com.ruoyi.teach.paike.mapper.LessonScheduleRecordMapper;
import com.ruoyi.teach.paike.service.LessonPlanService;
import com.ruoyi.teach.paike.service.LessonScheduleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * 排课存档Service业务层处理
 *
 * @author yzsoft
 * @date 2021-11-30
 */
@Service
public class LessonScheduleRecordServiceImpl extends EntityServiceImpl<LessonScheduleRecordMapper, LessonScheduleRecord> implements LessonScheduleRecordService {

    @Autowired
    private LessonScheduleRecordMapper lessonScheduleRecordMapper;
    @Autowired
    private LessonPlanService planService;

    /**
     * 查询排课存档列表
     *
     * @param lessonScheduleRecord 排课存档
     * @return 排课存档
     */
    @Override
    public List<LessonScheduleRecord> getList(LessonScheduleRecord lessonScheduleRecord) {
        return lessonScheduleRecordMapper.selectLessonScheduleRecordList(lessonScheduleRecord);
    }
    /**
     * 新增排课存档
     *
     * @param record 排课存档
     * @return 结果
     */
    @Override
    public boolean saveOrUpdate(LessonScheduleRecord record) {
        if (record.getId() == null) {
            String json = getJson(record);
            record.setJson(json);
            Assert.isTrue(record.getJson().length() < 16777216, "数据太大，无法存档");
        }
        record.setCreateTime(new Date());
        return super.saveOrUpdate(record);
    }

    private String getJson(LessonScheduleRecord record) {
        List<LessonPlan> plans = planService.getByLessonScheduleId(record.getLessonScheduleId());
        return JSONObject.toJSONString(plans);
    }

    @Override
    @Transactional
    public void reload(Long id) {
        LessonScheduleRecord record = getById(id);
        List<LessonPlan> plans = JSON.parseArray(record.getJson(), LessonPlan.class);
        plans.forEach(p->p.setId(null));
        planService.deleteByLessonScheduleId(record.getLessonScheduleId());
        planService.saveBatch(plans);
    }


}
