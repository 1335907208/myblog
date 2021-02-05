/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.modules.system.service;

import org.springblade.modules.system.entity.UserProject;
import org.springblade.modules.system.vo.UserProjectVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 项目权限（用户项目关联表） 服务类
 *
 * @author Blade
 * @since 2021-01-28
 */
public interface IUserProjectService extends BaseService<UserProject> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param userProject
	 * @return
	 */
	IPage<UserProjectVO> selectUserProjectPage(IPage<UserProjectVO> page, UserProjectVO userProject);

}
