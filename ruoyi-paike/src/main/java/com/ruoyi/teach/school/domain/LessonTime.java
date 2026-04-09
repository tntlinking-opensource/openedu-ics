package com.ruoyi.teach.school.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * 上课节次对象 ts_lesson_time
 *
 * @author beangle
 * @date 2021-07-06
 */
@Data
@TableName("ts_lesson_time")
public class LessonTime extends SchoolEntity {

    private Long id;

    @Excel(name = "排序")
    private Integer sort;

    @Excel(name = "名称")
    private String name;
    @Excel(name = "节次顺序")
    private Integer timeIndex;
    @Excel(name = "开始时间")
    private String startTime;
    @Excel(name = "结束时间")
    private String endTime;

    @Excel(name = "上午/下午")
    private String category;
    @TableField(exist = false)
    private String times;
    @Excel(name = "上午/下午")
    private String lessonType;

    public String getTimes() {
        return String.format("%s~%s", startTime, endTime);
    }
}
