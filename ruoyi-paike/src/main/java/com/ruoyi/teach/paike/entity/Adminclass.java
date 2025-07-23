package com.ruoyi.teach.paike.entity;

import lombok.Data;

@Data
public class Adminclass {
    private String grade;
    private String name;
    private Long time = 0L;

    public Adminclass() {
    }

    public Adminclass(String grade, String name) {
        this.grade = grade;
        this.name = name;
    }

    public Adminclass(String name) {
        this.name = name;
    }
}
