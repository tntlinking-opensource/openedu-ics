package com.ruoyi.teach.paike.mapper;

import java.util.List;

import com.ruoyi.teach.paike.domain.LessonScheduleRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 排课存档Mapper接口
 *
 * @author yzsoft
 * @date 2021-11-30
 */
public interface LessonScheduleRecordMapper extends BaseMapper<LessonScheduleRecord> {

    /**
     * 查询排课存档列表
     *
     * @param lessonScheduleRecord 排课存档
     * @return 排课存档集合
     */
    List<LessonScheduleRecord> selectLessonScheduleRecordList(LessonScheduleRecord lessonScheduleRecord);
    }
