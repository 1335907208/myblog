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
package org.springblade.modules.agency.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.agency.entity.AgencyProject;
import org.springblade.modules.agency.vo.AgencyProjectVO;

/**
 * Agency项目权限（agency项目关联表）包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2021-01-27
 */
public class AgencyProjectWrapper extends BaseEntityWrapper<AgencyProject, AgencyProjectVO>  {

	public static AgencyProjectWrapper build() {
		return new AgencyProjectWrapper();
 	}

	@Override
	public AgencyProjectVO entityVO(AgencyProject agencyProject) {
		AgencyProjectVO agencyProjectVO = BeanUtil.copy(agencyProject, AgencyProjectVO.class);

		return agencyProjectVO;
	}

}
