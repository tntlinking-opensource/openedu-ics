package com.ruoyi.teach.school.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * 班级对象 ts_adminclass
 *
 * @author beangle
 * @date 2021-07-07
 */
@Data
@TableName("ts_adminclass")
public class Adminclass extends SchoolEntity {
    private Long id;
    @Excel(name = "学年")
    private String year;
    @Excel(name = "年级")
    private String grade;
    @Excel(name = "编号")
    private String code;
    @Excel(name = "名称")
    private String name;
    private String img;
    private Long teacherId;
    @TableField(exist = false)
    @Excel(name = "班主任工号", type = Excel.Type.IMPORT)
    private String teacherCode;
    @Excel(name = "班主任姓名")
    private String teacherName;
    @Excel(name = "状态", readConverterExp = "0=启用,1=禁用")
    private String status;
    @Excel(name = "学生人数")
    private Integer studentNum;
    // 学院id
    private Long collegeId;
    // 院系id
    private Long facultyId;
}
