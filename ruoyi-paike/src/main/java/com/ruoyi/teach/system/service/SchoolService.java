package com.ruoyi.teach.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.teach.system.domain.School;

/**
 * 学校Service接口
 *
 * @author beangle
 * @date 2021-06-24
 */
public interface SchoolService extends IService<School> {

	/**
	 * 根据当前用户名查询学校
	 *
	 * @return
	 */
	School getSchool();

}
