package com.ruoyi.teach.paike.service;

import java.util.List;

import com.ruoyi.teach.paike.domain.LessonScheduleRecord;
import com.ruoyi.common.core.service.EntityService;

/**
 * 排课存档Service接口
 *
 * @author yzsoft
 * @date 2021-11-30
 */
public interface LessonScheduleRecordService extends EntityService<LessonScheduleRecord> {

    /**
     * 查询排课存档列表
     *
     * @param lessonScheduleRecord 排课存档
     * @return 排课存档集合
     */
    List<LessonScheduleRecord> getList(LessonScheduleRecord lessonScheduleRecord);

    void reload(Long id);
}
