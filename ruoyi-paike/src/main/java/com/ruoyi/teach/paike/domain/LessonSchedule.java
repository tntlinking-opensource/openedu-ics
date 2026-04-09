package com.ruoyi.teach.paike.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.LongEntity;
import com.ruoyi.teach.school.domain.TeachCalendar;
import lombok.Data;

import java.util.Date;

/**
 * 排课任务对象 t_lesson_schedule
 *
 * @author ruoyi
 * @date 2021-10-25
 */
@Data
@TableName("tpk_lesson_schedule")
public class LessonSchedule extends LongEntity {

    /**
     * 学校ID
     */
    @Excel(name = "学校ID")
    private Long schoolId;

    /**
     * 学年学期
     */
    @Excel(name = "学年学期")
    private Long teachCalenderId;

    @TableField(exist = false)
    private TeachCalendar teachCalender;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String isConflict;
    private String isAutoSchedule;

    /**
     * 是否当前排课
     */
    @Excel(name = "是否当前排课")
    private String isCurrent;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;

}
