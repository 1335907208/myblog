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
package org.springblade.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.modules.agency.entity.AgencyProject;
import org.springblade.modules.agency.service.IAgencyProjectService;
import org.springblade.modules.system.entity.Role;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.service.IRoleService;
import org.springblade.modules.system.service.IUserService;
import org.springblade.project.entity.ProjectInfo;
import org.springblade.project.service.IProjectInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.system.entity.UserProject;
import org.springblade.modules.system.vo.UserProjectVO;
import org.springblade.modules.system.wrapper.UserProjectWrapper;
import org.springblade.modules.system.service.IUserProjectService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目权限（用户项目关联表） 控制器
 *
 * @author Blade
 * @since 2021-01-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("blade-system/user/project")
@Api(value = "项目权限（用户项目关联表）", tags = "项目权限（用户项目关联表）接口")
public class UserProjectController extends BladeController {

	private IUserProjectService userProjectService;
	private IProjectInfoService projectInfoService;
	private IRoleService roleService;
	private IUserService userService;
	private IAgencyProjectService agencyProjectService;

	@PostMapping("/single")
	@ApiOperation(value = "单用户权限设置", notes = "全删全建用户和项目的关系")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId",value = "用户ID",dataType = "long",paramType = "body",required = true),
			@ApiImplicitParam(name = "projectIds",value = "项目ID",dataType = "string",paramType = "body",required = true)
	})
	public R single(@RequestParam Long userId,@RequestParam String projectIds){
		List<UserProject> userProjects = userProjectService.list(
				new LambdaQueryWrapper<UserProject>().eq(UserProject::getUserId, userId)
		);
		if(Func.isNotEmpty(userProjects)){
			userProjectService.removeByIds(userProjects.stream().map(UserProject::getId).collect(Collectors.toList()));
		}
		List<UserProject> userProjectlist = new ArrayList<>();
		if(Func.isNotEmpty(projectIds)){
			Func.toLongList(projectIds).forEach(projectId -> {
				UserProject userProject = BeanUtil.newInstance(UserProject.class);
				userProject.setProjectId(projectId);
				userProject.setUserId(userId);
				userProjectlist.add(userProject);
			});
		}
		return R.status(userProjectService.saveBatch(userProjectlist));
	}

	@PostMapping("/batch")
	@ApiOperation(value = "批量添加用户权限", notes = "全删全建用户和项目的关系")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userIds",value = "用户IDS",dataType = "string",paramType = "body",required = true),
			@ApiImplicitParam(name = "projectId",value = "项目ID",dataType = "long",paramType = "body",required = true)
	})
	public R batch(@RequestParam String userIds,@RequestParam Long projectId){
		List<UserProject> userProjectlist = new ArrayList<>();
		if(Func.isNotEmpty(userIds)){
			List<UserProject> userProjects = userProjectService.list(
					new LambdaQueryWrapper<UserProject>().eq(UserProject::getProjectId,projectId).in(UserProject::getUserId, Func.toLongList(userIds))
			);
			if(Func.isNotEmpty(userProjects)){
				userProjectService.removeByIds(userProjects.stream().map(UserProject::getId).collect(Collectors.toList()));
			}
			Func.toLongList(userIds).forEach( userid -> {
				UserProject userProject = BeanUtil.newInstance(UserProject.class);
				userProject.setUserId(userid);
				userProject.setProjectId(projectId);
				userProjectlist.add(userProject);
			});
		}
		return R.status(userProjectService.saveBatch(userProjectlist));
	}

	@GetMapping("/projectList")
	@ApiOperation(value = "获取用户被授权的项目列表", notes = "获取用户被授权的项目列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", value = "当前页", paramType = "query", dataType = "int", required = true, defaultValue = "1", example = "1"),
			@ApiImplicitParam(name = "size", value = "每页大小", paramType = "query", dataType = "int", required = true, defaultValue = "10", example = "10"),
			@ApiImplicitParam(name = "userId",value = "用户ID",dataType = "long",paramType = "query",required = true)
	})
	public R projectList(@RequestParam Long userId,Query query){
		IPage<ProjectInfo> page = new Page<>();
		List<UserProject> userProjects = userProjectService.list(
				new LambdaQueryWrapper<UserProject>().eq(UserProject::getUserId, userId)
		);
		if(Func.isNotEmpty(userProjects)){
			LambdaQueryWrapper<ProjectInfo> wrapper = new LambdaQueryWrapper<>();
			wrapper.in(ProjectInfo::getId,userProjects.stream().map(UserProject::getProjectId).collect(Collectors.toList()));
			page = projectInfoService.page(Condition.getPage(query), wrapper);
		}
		return R.data(page);
	}

	@GetMapping("/loginProjectList")
	@ApiOperation(value = "获取当前登录用户被授权的项目列表", notes = "获取当前登录用户被授权的项目列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", value = "当前页", paramType = "query", dataType = "int", required = true, defaultValue = "1", example = "1"),
			@ApiImplicitParam(name = "size", value = "每页大小", paramType = "query", dataType = "int", required = true, defaultValue = "10", example = "10")
	})
	public R loginProjectList(Query query){
		String ali = "agency_manager";
		User user = userService.getById(SecureUtil.getUserId());
		Role byId = roleService.getById(user.getRoleId());
		String roleAlias = byId.getRoleAlias();
		List<Long> longList = new ArrayList<>();
		if(Func.equals(ali,roleAlias)){
			List<AgencyProject> list = agencyProjectService.list(
					new LambdaQueryWrapper<AgencyProject>()
							.eq(AgencyProject::getAgencyId, user.getAgencyId())
			);
			longList = list.stream().map(AgencyProject::getProjectId).collect(Collectors.toList());
		}else{
			List<UserProject> userProjects = userProjectService.list(
					new LambdaQueryWrapper<UserProject>().eq(UserProject::getUserId, SecureUtil.getUserId())
			);
			longList = userProjects.stream().map(UserProject::getProjectId).collect(Collectors.toList());
		}
		return R.data(projectInfoService.page(Condition.getPage(query),new LambdaQueryWrapper<ProjectInfo>().in(ProjectInfo::getId,longList)));
	}

//	/**
//	 * 详情
//	 */
//	@GetMapping("/detail")
//	@ApiOperation(value = "详情", notes = "传入userProject")
//	public R<UserProjectVO> detail(UserProject userProject) {
//		UserProject detail = userProjectService.getOne(Condition.getQueryWrapper(userProject));
//		return R.data(UserProjectWrapper.build().entityVO(detail));
//	}
//
//	/**
//	 * 分页 项目权限（用户项目关联表）
//	 */
//	@GetMapping("/list")
//	@ApiOperation(value = "分页", notes = "传入userProject")
//	public R<IPage<UserProjectVO>> list(UserProject userProject, Query query) {
//		IPage<UserProject> pages = userProjectService.page(Condition.getPage(query), Condition.getQueryWrapper(userProject));
//		return R.data(UserProjectWrapper.build().pageVO(pages));
//	}
//
//
//	/**
//	 * 自定义分页 项目权限（用户项目关联表）
//	 */
//	@GetMapping("/page")
//	@ApiOperation(value = "自定义分页", notes = "传入userProject")
//	public R<IPage<UserProjectVO>> page(UserProjectVO userProject, Query query) {
//		IPage<UserProjectVO> pages = userProjectService.selectUserProjectPage(Condition.getPage(query), userProject);
//		return R.data(pages);
//	}
//
//	/**
//	 * 新增 项目权限（用户项目关联表）
//	 */
//	@PostMapping("/save")
//	@ApiOperation(value = "新增", notes = "传入userProject")
//	public R save(@Valid @RequestBody UserProject userProject) {
//		return R.status(userProjectService.save(userProject));
//	}
//
//	/**
//	 * 修改 项目权限（用户项目关联表）
//	 */
//	@PostMapping("/update")
//	@ApiOperation(value = "修改", notes = "传入userProject")
//	public R update(@Valid @RequestBody UserProject userProject) {
//		return R.status(userProjectService.updateById(userProject));
//	}
//
//	/**
//	 * 新增或修改 项目权限（用户项目关联表）
//	 */
//	@PostMapping("/submit")
//	@ApiOperation(value = "新增或修改", notes = "传入userProject")
//	public R submit(@Valid @RequestBody UserProject userProject) {
//		return R.status(userProjectService.saveOrUpdate(userProject));
//	}
//
//
//	/**
//	 * 删除 项目权限（用户项目关联表）
//	 */
//	@PostMapping("/remove")
//	@ApiOperation(value = "逻辑删除", notes = "传入ids")
//	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
//		return R.status(userProjectService.deleteLogic(Func.toLongList(ids)));
//	}

	
}
