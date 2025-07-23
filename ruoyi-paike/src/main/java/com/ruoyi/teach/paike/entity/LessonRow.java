package com.ruoyi.teach.paike.entity;

import lombok.Data;

@Data
public class LessonRow {
    private Integer timeIndex;
    private String time;
    private String times;
    private String grade;
    private String adminclass;
    private String teacher;
    private LessonCell cell1;
    private LessonCell cell2;
    private LessonCell cell3;
    private LessonCell cell4;
    private LessonCell cell5;
    private LessonCell cell6;
    private LessonCell cell7;
}
