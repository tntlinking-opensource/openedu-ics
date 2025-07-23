package com.ruoyi.teach.school.domain;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

@Data
public class TeachCalendarEntity extends SchoolEntity {
    /**
     * 学年学期ID
     */
    @Excel(name = "学年学期ID")
    private Long teachCalendarId;
    /**
     * 学年
     */
    @Excel(name = "学年")
    private String year;
    /**
     * 学期
     */
    @Excel(name = "学期")
    private String term;

}
