package com.ruoyi.teach.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.TimeEntity;
import lombok.Data;

/**
 * 学校对象 tc_school
 *
 * @author beangle
 * @date 2021-06-24
 */
@Data
@TableName("tc_school")
public class School extends TimeEntity {
    /**
     * ID
     */
    private Long id;
    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;
    /**
     * 地址
     */
    @Excel(name = "地址")
    private String address;
    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String contact;
    /**
     * 联系方式
     */
    @Excel(name = "联系方式")
    private String contactPhone;
    /**
     * 类型
     */
    @Excel(name = "类型")
    private String type;
    /**
     * 性质
     */
    @Excel(name = "性质")
    private String nature;
    /**
     * 状态
     */
    @Excel(name = "状态", readConverterExp = "0=启用,1=禁用")
    private Integer enabled;
    @Excel(name = "是否可见", readConverterExp = "Y=是,N=否")
    private String visiable;
    private Long userId;

}
