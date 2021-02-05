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

import org.springblade.modules.agency.entity.Agency;
import org.springblade.modules.agency.vo.AgencyVO;
import org.springblade.modules.agency.mapper.AgencyMapper;
import org.springblade.modules.agency.service.IAgencyService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * Agency 服务实现类
 *
 * @author Blade
 * @since 2021-01-27
 */
@Service
public class AgencyServiceImpl extends BaseServiceImpl<AgencyMapper, Agency> implements IAgencyService {

	@Override
	public IPage<AgencyVO> selectAgencyPage(IPage<AgencyVO> page, AgencyVO agency) {
		return page.setRecords(baseMapper.selectAgencyPage(page, agency));
	}

}
