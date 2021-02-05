/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.project.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.User;
import org.springblade.project.entity.ProjectGallery;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.project.entity.ProjectFloorPlan;
import org.springblade.project.vo.ProjectFloorPlanVO;
import org.springblade.project.wrapper.ProjectFloorPlanWrapper;
import org.springblade.project.service.IProjectFloorPlanService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;

/**
 * 项目楼层平图表 控制器
 *
 * @author Blade
 * @since 2020-07-01
 */
@RestController
@AllArgsConstructor
@RequestMapping("project/projectfloorplan")
@Api(value = "项目楼层平图表", tags = "项目楼层平图表接口")
public class ProjectFloorPlanController extends BladeController {

	private IProjectFloorPlanService projectFloorPlanService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入projectFloorPlan")
	public R<ProjectFloorPlanVO> detail(ProjectFloorPlan projectFloorPlan) {
		ProjectFloorPlan detail = projectFloorPlanService.getOne(Condition.getQueryWrapper(projectFloorPlan));
		return R.data(ProjectFloorPlanWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 项目楼层平图表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入projectFloorPlan")
	public R<IPage<ProjectFloorPlanVO>> list(ProjectFloorPlan projectFloorPlan, Query query) {
		IPage<ProjectFloorPlan> pages = projectFloorPlanService.page(Condition.getPage(query), Condition.getQueryWrapper(projectFloorPlan));
		return R.data(ProjectFloorPlanWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 项目楼层平图表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入projectFloorPlan")
	public R<IPage<ProjectFloorPlanVO>> page(ProjectFloorPlanVO projectFloorPlan, Query query) {
		IPage<ProjectFloorPlanVO> pages = projectFloorPlanService.selectProjectFloorPlanPage(Condition.getPage(query), projectFloorPlan);
		return R.data(pages);
	}

	/**
	 * 新增 项目楼层平图表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入projectFloorPlan")
	public R save(@Valid @RequestBody ProjectFloorPlan projectFloorPlan) {
		return R.status(projectFloorPlanService.save(projectFloorPlan));
	}

	/**
	 * 修改 项目楼层平图表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入projectFloorPlan")
	public R update(@Valid @RequestBody ProjectFloorPlan projectFloorPlan) {
		return R.status(projectFloorPlanService.updateById(projectFloorPlan));
	}

	/**
	 * 新增或修改 项目楼层平图表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入projectFloorPlan")
	public R submit(@Valid @RequestBody ProjectFloorPlan projectFloorPlan) {
		return R.status(projectFloorPlanService.saveOrUpdate(projectFloorPlan));
	}

	/**
	 * 楼层平面图列表
	 *
	 * @param projectId
	 * @return
	 */
	@GetMapping("/floor-list")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "floorPlan列表", notes = "传入projectId")
	public R<List<ProjectFloorPlan>> floorList(@RequestParam String projectId) {
		List<ProjectFloorPlan> list = projectFloorPlanService.list(Wrappers.<ProjectFloorPlan>query().lambda().eq(ProjectFloorPlan::getProjectId,projectId));
		return R.data(list);
	}

	/**
	 * 删除 项目楼层平图表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(projectFloorPlanService.deleteLogic(Func.toLongList(ids)));
	}


}
