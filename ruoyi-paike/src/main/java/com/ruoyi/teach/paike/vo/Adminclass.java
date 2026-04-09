package com.ruoyi.teach.paike.vo;

import lombok.Data;

@Data
public class Adminclass {

    private String grade;
    private String name;

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
