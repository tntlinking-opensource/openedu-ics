package com.ruoyi.teach.paike.vo;

import lombok.Data;

@Data
public class Course {

    private String grade;
    private String name;

    public Course(String grade, String name) {
        this.grade = grade;
        this.name = name;
    }
}
