package com.ruoyi.teach.paike.entity;

import lombok.Data;

/**
 * 课程安排
 */
@Data
public class CourseConfig {
    private String name;
    private long time;

    public CourseConfig() {
    }

    public CourseConfig(String name, long time) {
        this.name = name;
        this.time = time;
    }
}
