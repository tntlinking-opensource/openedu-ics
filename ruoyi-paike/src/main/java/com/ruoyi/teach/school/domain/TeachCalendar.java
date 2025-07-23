package com.ruoyi.teach.school.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 学年学期对象 tc_teach_calendar
 *
 * @author beangle
 * @date 2021-07-05
 */
@Data
@TableName("ts_teach_calendar")
public class TeachCalendar extends SchoolEntity {

    private Long id;

    @Excel(name = "学年")
    private String year;

    @Excel(name = "学期")
    private String term;
    @TableField(exist = false)
    private String name;


    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    @Excel(name = "是否当前学期")
    @TableField("`current`")
    private String current;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    private Integer weekNum;

    public String getName() {
        return String.format("%s-%s", year, term);
    }

}
