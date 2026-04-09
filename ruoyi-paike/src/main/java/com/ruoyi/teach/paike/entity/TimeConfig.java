package com.ruoyi.teach.paike.entity;

import lombok.Data;

/**
 * 作息安排
 */
@Data
public class TimeConfig {
    /**
     * 每周多少天
     */
    private Integer weekdays;
    /**
     * 上午多少节
     */
    private Integer morning;
    /**
     * 下午多少节
     */
    private Integer afternoon;
    /**
     * 晚上多少节
     */
    private Integer evening;
    private Long timeOfWeek;

    public int getTimeLength() {
        return weekdays * getTimeOfDay();
    }

    public Integer getTimeOfDay() {
        return morning + afternoon + evening;
    }

    public Long getTimeAvailable() {
        return ~((~0L) << getTimeLength());
    }
}
