package com.ruoyi.teach.school.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * 年级对象 ts_grade
 *
 * @author yzsoft
 * @date 2023-06-26
 */
@Data
@TableName("ts_grade")
public class Grade extends SchoolEntity {
    // 编号
    @Excel(name = "编号")
    private String code;
    // 名称
    @Excel(name = "名称")
    private String name;
    // 年级组长
    @Excel(name = "年级组长")
    private Long teacherId;
    @TableField(exist = false)
    private Teacher teacher;
    // $column.columnComment
    private Integer deleted;

    public Grade() {
    }

    public Grade(String name) {
        this.name = name;
    }

}
