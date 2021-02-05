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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.project.entity.ProjectIntroduction;
import org.springblade.project.vo.ProjectIntroductionVO;
import org.springblade.project.wrapper.ProjectIntroductionWrapper;
import org.springblade.project.service.IProjectIntroductionService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 项目相册表 控制器
 *
 * @author Blade
 * @since 2020-07-01
 */
@RestController
@AllArgsConstructor
@RequestMapping("project/projectintroduction")
@Api(value = "项目相册表", tags = "项目相册表接口")
public class ProjectIntroductionController extends BladeController {

	private IProjectIntroductionService projectIntroductionService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入projectIntroduction")
	public R<ProjectIntroductionVO> detail(ProjectIntroduction projectIntroduction) {
		ProjectIntroduction detail = projectIntroductionService.getOne(Condition.getQueryWrapper(projectIntroduction));
		return R.data(ProjectIntroductionWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 项目相册表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入projectIntroduction")
	public R<IPage<ProjectIntroductionVO>> list(ProjectIntroduction projectIntroduction, Query query) {
		IPage<ProjectIntroduction> pages = projectIntroductionService.page(Condition.getPage(query), Condition.getQueryWrapper(projectIntroduction));
		return R.data(ProjectIntroductionWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 项目相册表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入projectIntroduction")
	public R<IPage<ProjectIntroductionVO>> page(ProjectIntroductionVO projectIntroduction, Query query) {
		IPage<ProjectIntroductionVO> pages = projectIntroductionService.selectProjectIntroductionPage(Condition.getPage(query), projectIntroduction);
		return R.data(pages);
	}

	/**
	 * 新增 项目相册表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入projectIntroduction")
	public R save(@Valid @RequestBody ProjectIntroduction projectIntroduction) {
		return R.status(projectIntroductionService.save(projectIntroduction));
	}

	/**
	 * 修改 项目相册表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入projectIntroduction")
	public R update(@Valid @RequestBody ProjectIntroduction projectIntroduction) {
		return R.status(projectIntroductionService.updateById(projectIntroduction));
	}

	/**
	 * 新增或修改 项目相册表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入projectIntroduction")
	public R submit(@Valid @RequestBody ProjectIntroduction projectIntroduction) {
		return R.status(projectIntroductionService.saveOrUpdate(projectIntroduction));
	}

	
	/**
	 * 删除 项目相册表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(projectIntroductionService.deleteLogic(Func.toLongList(ids)));
	}

	
}
