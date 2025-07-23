package com.ruoyi.teach.school.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * 教师对象 ts_teacher
 *
 * @author beangle
 * @date 2021-07-08
 */
@Data
@TableName("ts_teacher")
public class Teacher extends SchoolEntity {

    public static final String ACTIVATE_YES = "yes";
    public static final String ACTIVATE_NO = "no";

    private Long id;
    private Long userId;
    @Excel(name = "工号")
    private String code;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "别名")
    private String name2;
    @Excel(name = "手机号")
    private String phone;
    @TableField(exist = false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String password;
    @Excel(name = "邮箱")
    private String email;
    @Excel(name = "卡号")
    private String card;
    /**
     * 公众号openid
     */
    @Excel(name = "openid")
    private String openid;
    @Excel(name = "是否激活", readConverterExp = "yes=是,no=否")
    private String activate;
    @Excel(name = "状态", readConverterExp = "0=启用,1=禁用")
    private String status;

    @TableField(exist = false)
    private String label;
    @TableLogic
    private String delFlag;
    // 学院id
    private Long collegeId;
    // 院系id
    private Long facultyId;

    public Teacher() {
    }

    public Teacher(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return String.format("%s（%s）", name, code);
    }

}
