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
package org.springblade.modules.agency.service.impl;

import org.springblade.modules.agency.entity.AgencyProject;
import org.springblade.modules.agency.vo.AgencyProjectVO;
import org.springblade.modules.agency.mapper.AgencyProjectMapper;
import org.springblade.modules.agency.service.IAgencyProjectService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * Agency项目权限（agency项目关联表） 服务实现类
 *
 * @author Blade
 * @since 2021-01-27
 */
@Service
public class AgencyProjectServiceImpl extends BaseServiceImpl<AgencyProjectMapper, AgencyProject> implements IAgencyProjectService {

	@Override
	public IPage<AgencyProjectVO> selectAgencyProjectPage(IPage<AgencyProjectVO> page, AgencyProjectVO agencyProject) {
		return page.setRecords(baseMapper.selectAgencyProjectPage(page, agencyProject));
	}

}
