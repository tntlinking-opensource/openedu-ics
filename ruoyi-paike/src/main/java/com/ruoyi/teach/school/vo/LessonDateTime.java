package com.ruoyi.teach.school.vo;

import lombok.Data;

import java.util.Date;

@Data
public class LessonDateTime {
    private Date date;
    private Integer week;
    private Integer weekday;
    private Integer time;
}
