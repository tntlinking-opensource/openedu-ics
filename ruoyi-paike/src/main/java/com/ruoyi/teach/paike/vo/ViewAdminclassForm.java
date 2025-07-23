package com.ruoyi.teach.paike.vo;

import lombok.Data;

import java.util.List;

@Data
public class ViewAdminclassForm {
    private Long schoolId;
    private String grade;
    private String course;
    private String adminclass;
    private String teacher;
    private boolean showRoom;
    private boolean showTeacher;
    private boolean showTime;
    private List<String> classrooms;
    private List<String> grades;
}
