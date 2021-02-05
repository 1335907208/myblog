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
package org.springblade.modules.agency.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.agency.entity.Agency;
import org.springblade.modules.agency.vo.AgencyVO;
import org.springblade.modules.agency.wrapper.AgencyWrapper;
import org.springblade.modules.agency.service.IAgencyService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * Agency 控制器
 *
 * @author Blade
 * @since 2021-01-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("agency/agency")
@Api(value = "Agency", tags = "Agency接口")
public class AgencyController extends BladeController {

	private IAgencyService agencyService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入agency")
	public R<AgencyVO> detail(Agency agency) {
		Agency detail = agencyService.getOne(Condition.getQueryWrapper(agency));
		return R.data(AgencyWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 Agency
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入agency")
	public R<IPage<AgencyVO>> list(Agency agency, Query query) {
		IPage<Agency> pages = agencyService.page(Condition.getPage(query), Condition.getQueryWrapper(agency));
		return R.data(AgencyWrapper.build().pageVO(pages));
	}


//	/**
//	 * 自定义分页 Agency
//	 */
//	@GetMapping("/page")
//	@ApiOperation(value = "自定义分页", notes = "传入agency")
//	public R<IPage<AgencyVO>> page(AgencyVO agency, Query query) {
//		IPage<AgencyVO> pages = agencyService.selectAgencyPage(Condition.getPage(query), agency);
//		return R.data(pages);
//	}

	/**
	 * 新增 Agency
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入agency")
	public R save(@Valid @RequestBody Agency agency) {
		return R.status(agencyService.save(agency));
	}

	/**
	 * 修改 Agency
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入agency")
	public R update(@Valid @RequestBody Agency agency) {
		return R.status(agencyService.updateById(agency));
	}

	/**
	 * 新增或修改 Agency
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入agency")
	public R submit(@Valid @RequestBody Agency agency) {
		return R.status(agencyService.saveOrUpdate(agency));
	}

	
	/**
	 * 删除 Agency
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(agencyService.deleteLogic(Func.toLongList(ids)));
	}

	
}
