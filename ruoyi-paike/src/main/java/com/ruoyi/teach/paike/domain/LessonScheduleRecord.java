package com.ruoyi.teach.paike.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.LongEntity;
import lombok.Data;

import java.util.Date;

/**
 * 排课存档对象 tpk_lesson_schedule_record
 *
 * @author yzsoft
 * @date 2021-11-30
 */
@Data
@TableName("tpk_lesson_schedule_record")
public class LessonScheduleRecord extends LongEntity {

    /**
     * 排课任务ID
     */
    private Long lessonScheduleId;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 数据
     */
    @Excel(name = "数据")
    private String json;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;

}
