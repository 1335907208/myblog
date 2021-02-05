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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.agency.entity.Agency;
import org.springblade.modules.agency.service.IAgencyService;
import org.springblade.project.entity.ProjectInfo;
import org.springblade.project.service.IProjectInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.agency.entity.AgencyProject;
import org.springblade.modules.agency.vo.AgencyProjectVO;
import org.springblade.modules.agency.wrapper.AgencyProjectWrapper;
import org.springblade.modules.agency.service.IAgencyProjectService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Agency项目权限（agency项目关联表） 控制器
 *
 * @author Blade
 * @since 2021-01-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("agency/agencyproject")
@Api(value = "Agency项目权限（agency项目关联表）", tags = "Agency项目权限（agency项目关联表）接口")
public class AgencyProjectController extends BladeController {

	private IAgencyProjectService agencyProjectService;
	private IProjectInfoService projectInfoService;
    private IAgencyService agencyService;

	@PostMapping("/single")
	@ApiOperation(value = "权限设置", notes = "全删全建 中介公司 和 项目 的关系")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "agencyId",value = "agencyId",dataType = "long",paramType = "body",required = true),
			@ApiImplicitParam(name = "projectIds",value = "projectIds",dataType = "string",paramType = "body")
	})
	public R single(@RequestParam Long agencyId,@RequestParam String projectIds){
		List<AgencyProject> aencyProjects = agencyProjectService.list(
				new LambdaQueryWrapper<AgencyProject>().eq(AgencyProject::getAgencyId, agencyId)
		);
		if(Func.isNotEmpty(aencyProjects)){
			agencyProjectService.removeByIds(aencyProjects.stream().map(AgencyProject::getId).collect(Collectors.toList()));
		}
		List<AgencyProject> list = new ArrayList<>();
		if(Func.isNotEmpty(projectIds)){
			List<Long> longs = Func.toLongList(projectIds);
			if(Func.isNotEmpty(longs)){
				longs.forEach(projectId -> {
					AgencyProject a = BeanUtil.newInstance(AgencyProject.class);
					a.setAgencyId(agencyId);
					a.setProjectId(projectId);
					list.add(a);
				});
			}
		}
		return R.status(agencyProjectService.saveBatch(list));
	}

	@PostMapping("/batch")
	@ApiOperation(value = "批量添加用户权限", notes = "全删全建用户和项目的关系")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userIds",value = "用户IDS",dataType = "string",paramType = "body",required = true),
			@ApiImplicitParam(name = "projectId",value = "项目ID",dataType = "long",paramType = "body",required = true)
	})
	public R batch(@RequestParam String agencyIds,@RequestParam Long projectId){
		List<AgencyProject> list = new ArrayList<>();
		if(Func.isNotEmpty(agencyIds)){
			List<AgencyProject> aencyProjects = agencyProjectService.list(
					new LambdaQueryWrapper<AgencyProject>()
							.eq(AgencyProject::getProjectId,projectId)
							.in(AgencyProject::getAgencyId, Func.toLongList(agencyIds))
			);
			if(Func.isNotEmpty(aencyProjects)){
				agencyProjectService.removeByIds(aencyProjects.stream().map(AgencyProject::getId).collect(Collectors.toList()));
			}
			Func.toLongList(agencyIds).forEach(agencyId -> {
				AgencyProject a = BeanUtil.newInstance(AgencyProject.class);
				a.setProjectId(projectId);
				a.setAgencyId(agencyId);
				list.add(a);
			});
		}
		return R.status(agencyProjectService.saveBatch(list));
	}

	@GetMapping("/projectList")
	@ApiOperation(value = "获取被授权的项目列表", notes = "获取被授权的项目列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "agencyId",value = "agencyId",dataType = "long",paramType = "query",required = true)
	})
	public R projectList(@RequestParam Long agencyId){
		List<AgencyProject> list = agencyProjectService.list(
				new LambdaQueryWrapper<AgencyProject>().eq(AgencyProject::getAgencyId, agencyId)
		);
		List<ProjectInfo> projectInfos = new ArrayList<>();
		if(Func.isNotEmpty(list)){
			projectInfos = projectInfoService.listByIds(list.stream().map(AgencyProject::getProjectId).collect(Collectors.toList()));
		}
		return R.data(projectInfos);
	}

    @GetMapping("/agencyList")
    @ApiOperation(value = "获取被授权的中介列表", notes = "获取被授权的中介列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId",value = "projectId",dataType = "long",paramType = "query",required = true)
    })
    public R agencyList(@RequestParam Long projectId){
        List<AgencyProject> list = agencyProjectService.list(
                new LambdaQueryWrapper<AgencyProject>().eq(AgencyProject::getProjectId, projectId)
        );
        List<Agency> agencyList = new ArrayList<>();
        if(Func.isNotEmpty(list)){
            agencyList = agencyService.list(new LambdaQueryWrapper<Agency>()
                    .in(Agency::getId,list.stream().map(AgencyProject::getAgencyId).collect(Collectors.toList()))
            );
        }
        return R.data(agencyList);
    }
	
}
