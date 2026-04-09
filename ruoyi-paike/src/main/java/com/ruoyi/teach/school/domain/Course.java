package com.ruoyi.teach.school.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * 课程对象 ts_course
 *
 * @author beangle
 * @date 2021-07-06
 */
@Data
@TableName("ts_course")
public class Course extends SchoolEntity {
    @Excel(name = "排序")
    private Integer sort;
    @Excel(name = "代码")
    private String code;
    @Excel(name = "名称", isKey = true)
    private String name;
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用", type = Excel.Type.NONE)
    private String status;
    @Excel(name = "工作量系数")
    private Float criterion;
}
