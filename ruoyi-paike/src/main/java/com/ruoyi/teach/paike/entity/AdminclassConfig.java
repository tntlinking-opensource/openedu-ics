package com.ruoyi.teach.paike.entity;

import lombok.Data;

@Data
public class AdminclassConfig {
    private String name;
    private Long time = 0L;

    public AdminclassConfig() {
    }

    public AdminclassConfig(String name, long time) {
        this.name = name;
        this.time = time;
    }
}
