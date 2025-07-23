package com.ruoyi.teach.paike.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.LongEntity;
import lombok.Data;

/**
 * 排课规则对象 tpk_lesson_schedule_rule
 *
 * @author yzsoft
 * @date 2021-11-01
 */
@Data
@TableName("tpk_lesson_schedule_rule")
public class LessonScheduleRule extends LongEntity {

    /**
     * 学校ID
     */
    @Excel(name = "学校ID")
    private Long schoolId;

    /**
     * 排课任务ID
     */
    @Excel(name = "排课任务ID")
    private Long lessonScheduleId;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 代码
     */
    @Excel(name = "代码")
    private String code;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 数据
     */
    @Excel(name = "数据")
    private String json;

}
