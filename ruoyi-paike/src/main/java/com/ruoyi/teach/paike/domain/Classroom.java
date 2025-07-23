package com.ruoyi.teach.paike.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.LongEntity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 班级教室对象 tpk_classroom
 *
 * @author ruoyi
 * @date 2021-10-27
 */
@Data
@TableName("tpk_classroom")
public class Classroom extends LongEntity {

    /**
     * 学校ID
     */
    private Long schoolId;

    /**
     * 排课任务ID
     */
    private Long lessonScheduleId;

    /**
     * 年级
     */
    @Excel(name = "年级")
    private String grade;

    /**
     * 班级
     */
    @Excel(name = "班级")
    private String adminclass;

    /**
     * 教学楼
     */
    @Excel(name = "教学楼")
    private String building;

    /**
     * 教室号
     */
    @Excel(name = "教室号")
    private String room;

    @TableField(exist = false)
    private String name;

    public String getName() {
        if (StringUtils.isBlank(room)) {
            return building;
        }
        return building + room;
    }
}
