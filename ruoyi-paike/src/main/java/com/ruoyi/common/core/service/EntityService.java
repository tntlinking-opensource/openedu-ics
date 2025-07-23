package com.ruoyi.common.core.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface EntityService<T> extends IService<T> {

    String importEntity(List<T> sources);

}
