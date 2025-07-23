package com.ruoyi.teach.paike.entity;

import lombok.Data;

@Data
public class TeacherConfig {
    private String name;
    private Long time = 0L;

    public TeacherConfig() {
    }

    public TeacherConfig(String name, long time) {
        this.name = name;
        this.time = time;
    }
}
