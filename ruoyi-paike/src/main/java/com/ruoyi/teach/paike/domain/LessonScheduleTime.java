package com.ruoyi.teach.paike.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.LongEntity;
import lombok.Data;

/**
 * 作息安排对象 t_lesson_schedule_time
 *
 * @author ruoyi
 * @date 2021-10-25
 */
@Data
@TableName("tpk_lesson_schedule_time")
public class LessonScheduleTime extends LongEntity {

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
     * 每周天数
     */
    @Excel(name = "每周天数")
    private Integer weekdays;

    /**
     * 上午节数
     */
    @Excel(name = "上午节数")
    private Integer morning;

    /**
     * 下午节数
     */
    @Excel(name = "下午节数")
    private Integer afternoon;

    /**
     * 晚上节数
     */
    @Excel(name = "晚上节数")
    private Integer night;

    public Integer getCount() {
        return morning + afternoon + night;
    }

    public Integer getTotal() {
        return weekdays * getCount();
    }

    public String getTimes() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < weekdays; i++) {
            if (i > 0) {
                sb.append(";");
            }
            for (int j = 0; j < getCount(); j++) {
                sb.append("1");
            }
        }
        return sb.toString();
    }
}
