package com.ruoyi.teach.paike.entity;

import lombok.Data;

/**
 * 任课信息
 */
@Data
public class LessonInfo {
    /**
     * 班级
     */
    private String adminclassName;
    /**
     * 课程
     */
    private String courseName;
    /**
     * 课时
     */
    private Integer lessonHour;
    /**
     * 教师姓名
     */
    private String teacherName;

    public LessonInfo() {
    }

    public LessonInfo(String gradeName, String adminclassName, String courseName, Integer lessonHour, String teacherName) {
//        this.gradeName = gradeName;
        this.adminclassName = gradeName + adminclassName;
        this.courseName = courseName;
        this.lessonHour = lessonHour;
        this.teacherName = teacherName;
    }
}
