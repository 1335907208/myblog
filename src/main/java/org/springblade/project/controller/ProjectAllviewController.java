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
import org.springblade.project.entity.ProjectAllview;
import org.springblade.project.vo.ProjectAllviewVO;
import org.springblade.project.wrapper.ProjectAllviewWrapper;
import org.springblade.project.service.IProjectAllviewService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 项目全景照片表 控制器
 *
 * @author Blade
 * @since 2020-07-02
 */
@RestController
@AllArgsConstructor
@RequestMapping("project/projectallview")
@Api(value = "项目全景照片表", tags = "项目全景照片表接口")
public class ProjectAllviewController extends BladeController {

	private IProjectAllviewService projectAllviewService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入projectAllview")
	public R<ProjectAllviewVO> detail(ProjectAllview projectAllview) {
		ProjectAllview detail = projectAllviewService.getOne(Condition.getQueryWrapper(projectAllview));
		ProjectAllview projectAllview1 = new ProjectAllview();
		return R.data(ProjectAllviewWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 项目全景照片表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入projectAllview")
	public R<IPage<ProjectAllviewVO>> list(ProjectAllview projectAllview, Query query) {
		IPage<ProjectAllview> pages = projectAllviewService.page(Condition.getPage(query), Condition.getQueryWrapper(projectAllview));
		return R.data(ProjectAllviewWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 项目全景照片表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入projectAllview")
	public R<IPage<ProjectAllviewVO>> page(ProjectAllviewVO projectAllview, Query query) {
		IPage<ProjectAllviewVO> pages = projectAllviewService.selectProjectAllviewPage(Condition.getPage(query), projectAllview);
		return R.data(pages);
	}

	/**
	 * 新增 项目全景照片表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入projectAllview")
	public R save(@Valid @RequestBody ProjectAllview projectAllview) {
		return R.status(projectAllviewService.save(projectAllview));
	}

	/**
	 * 修改 项目全景照片表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入projectAllview")
	public R update(@Valid @RequestBody ProjectAllview projectAllview) {
		return R.status(projectAllviewService.updateById(projectAllview));
	}

	/**
	 * 新增或修改 项目全景照片表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入projectAllview")
	public R submit(@Valid @RequestBody ProjectAllview projectAllview) {
		return R.status(projectAllviewService.saveOrUpdate(projectAllview));
	}


	/**
	 * 删除 项目全景照片表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(projectAllviewService.deleteLogic(Func.toLongList(ids)));
	}


}
