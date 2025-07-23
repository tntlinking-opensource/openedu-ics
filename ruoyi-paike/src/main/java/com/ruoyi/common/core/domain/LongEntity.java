package com.ruoyi.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * Entity基类
 */
@Data
public class LongEntity implements Serializable {

    @TableId
    private Long id;

    /**
     * 搜索值
     */
    @TableField(exist = false)
    private String searchValue;

    /**
     * 请求参数
     */
    @TableField(exist = false)
    private Map<String, Object> params;
}
