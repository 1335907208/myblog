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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import org.springblade.project.entity.ProjectInfo;
import org.springblade.project.vo.ProjectInfoVO;
import org.springblade.project.wrapper.ProjectInfoWrapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.project.entity.ProjectUnits;
import org.springblade.project.vo.ProjectUnitsVO;
import org.springblade.project.wrapper.ProjectUnitsWrapper;
import org.springblade.project.service.IProjectUnitsService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目全景照片表 控制器
 *
 * @author Blade
 * @since 2020-07-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("project/projectunits")
@Api(value = "项目全景照片表", tags = "项目全景照片表接口")
public class ProjectUnitsController extends BladeController {

	private IProjectUnitsService projectUnitsService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入projectUnits")
	public R<ProjectUnitsVO> detail(ProjectUnits projectUnits) {
		ProjectUnits detail = projectUnitsService.getOne(Condition.getQueryWrapper(projectUnits));
		return R.data(ProjectUnitsWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 项目全景照片表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入projectUnits")
	public R<IPage<ProjectUnitsVO>> list(ProjectUnits projectUnits, Query query) {
		IPage<ProjectUnits> pages = projectUnitsService.page(Condition.getPage(query), Condition.getQueryWrapper(projectUnits));
		return R.data(ProjectUnitsWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 项目全景照片表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入projectUnits")
	public R<IPage<ProjectUnitsVO>> page(ProjectUnitsVO projectUnits, Query query) {
		IPage<ProjectUnitsVO> pages = projectUnitsService.selectProjectUnitsPage(Condition.getPage(query), projectUnits);
		return R.data(pages);
	}

	/**
	 * 新增 项目全景照片表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入projectUnits")
	public R save(@Valid @RequestBody ProjectUnits projectUnits) {
		return R.status(projectUnitsService.save(projectUnits));
	}

	/**
	 * 修改 项目全景照片表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入projectUnits")
	public R update(@Valid @RequestBody ProjectUnits projectUnits) {
		return R.status(projectUnitsService.updateById(projectUnits));
	}

	/**
	 * 新增或修改 项目全景照片表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入projectUnits")
	public R submit(@Valid @RequestBody ProjectUnits projectUnits) {
		return R.status(projectUnitsService.saveOrUpdate(projectUnits));
	}


	/**
	 * 删除 项目全景照片表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(projectUnitsService.deleteLogic(Func.toLongList(ids)));
	}


	/**
	 * 根据bedrooms查询block
	 */
	@GetMapping("/floor-list")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "根据block查询floor栋集合", notes = "")
	public  R<List<ProjectUnitsVO>> floorList(@RequestParam Map param) {
		QueryWrapper<ProjectUnits> projectUnitsQueryWrapper = new QueryWrapper<>();

		String projectId = "";
		if (param.get("projectId") != null){
			projectId = param.get("projectId").toString();
		}

		if (!"".equals(projectId) && projectId != null){
			projectUnitsQueryWrapper.eq("project_id", projectId);
		}

		String block = "";
		if (param.get("block") != null){
			block = param.get("block").toString();
		}

		if (!"".equals(block) && block != null){
			projectUnitsQueryWrapper.eq("block", block);
		}

		projectUnitsQueryWrapper.groupBy("floor");
		projectUnitsQueryWrapper.select("floor");

		List<ProjectUnits> projectUnitsList = projectUnitsService.list(projectUnitsQueryWrapper);

		return R.data(ProjectUnitsWrapper.build().listVO(projectUnitsList));
	}

	/**
	 * 根据block查询units栋集合
	 */
	@GetMapping("/units-list")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "根据block查询units栋集合", notes = "")
	public R<List<ProjectUnitsVO>> unitsList(@RequestParam HashMap<String,Object> param) {
		QueryWrapper<ProjectUnits> projectUnitsQueryWrapper = new QueryWrapper<>();

		String projectId = "";
		if(param.get("projectId") != null){
			projectId = param.get("projectId").toString();
		}
		String floor = "";
		if(param.get("floor") != null){
			floor = param.get("floor").toString();
		}
		String block = "";
		if (param.get("block") != null){
			block = param.get("block").toString();
		}

		if (!"".equals(projectId)){
			projectUnitsQueryWrapper.eq("project_id", projectId);
		}
		if (!"".equals(block)){
			projectUnitsQueryWrapper.eq("floor", floor);
		}
		if (!"".equals(block)){
			projectUnitsQueryWrapper.eq("block", block);
		}

		List<ProjectUnits> projectUnitsList = projectUnitsService.list(projectUnitsQueryWrapper);

		return R.data(ProjectUnitsWrapper.build().listVO(projectUnitsList));
	}

	/**
	 * 根据id查unit详情
	 */
	@GetMapping("/units-detail")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "根据id查unit详情", notes = "")
	public R<List<ProjectUnitsVO>> unitsDetail(@Valid @RequestParam String id) {
		QueryWrapper<ProjectUnits> projectUnitsQueryWrapper = new QueryWrapper<>();

		if (!"".equals(id) && id != null){
			projectUnitsQueryWrapper.eq("id", id);
		}

		List<ProjectUnits> projectUnitsList = projectUnitsService.list(projectUnitsQueryWrapper);

		return R.data(ProjectUnitsWrapper.build().listVO(projectUnitsList));
	}

	/**
	 *  根据projectId返回bedrooms了表
	 */
	@GetMapping("/block-list")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = " 根据projectId返回block集合", notes = "")
	public R<List<ProjectUnitsVO>> blockList(@Valid @RequestParam String projectId) {
		QueryWrapper<ProjectUnits> projectUnitsQueryWrapper = new QueryWrapper<>();

		if (!"".equals(projectId) && projectId != null){
			projectUnitsQueryWrapper.eq("project_id", projectId);
		}
		projectUnitsQueryWrapper.groupBy("block");
		projectUnitsQueryWrapper.select("block");
		List<ProjectUnits> projectUnitsList = projectUnitsService.list(projectUnitsQueryWrapper);

		return R.data(ProjectUnitsWrapper.build().listVO(projectUnitsList));
	}

}
