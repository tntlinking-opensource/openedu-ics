package com.ruoyi.teach.paike.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.LongEntity;
import lombok.Data;

/**
 * 任课信息对象 tpk_lesson_plan
 *
 * @author ruoyi
 * @date 2021-10-26
 */
@Data
@TableName("tpk_lesson_plan")
public class LessonPlan extends LongEntity {

    /**
     * 学校ID
     */
    @Excel(name = "学校ID")
    private Long schoolId;

    /**
     * 排课任务ID
     */
    @Excel(name = "排课任务ID", isKey = true)
    private Long lessonScheduleId;

    /**
     * 年级
     */
    @Excel(name = "年级", isKey = true)
    private String grade;

    /**
     * 班级
     */
    @Excel(name = "班级", isKey = true)
    private String adminclass;

    /**
     * 课程
     */
    @Excel(name = "课程", isKey = true)
    private String course;

    /**
     * 课时
     */
    @Excel(name = "课时")
    private String hours;
    private Integer hour;

    /**
     * 任课教师
     */
    @Excel(name = "任课教师")
    private String teacher;
    private String timesPres;
    private String times;

}
