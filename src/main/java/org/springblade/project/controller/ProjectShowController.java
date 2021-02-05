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
import org.springblade.project.entity.ProjectInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.project.entity.ProjectShow;
import org.springblade.project.vo.ProjectShowVO;
import org.springblade.project.wrapper.ProjectShowWrapper;
import org.springblade.project.service.IProjectShowService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;

/**
 * 项目轮播图片表 控制器
 *
 * @author Blade
 * @since 2020-07-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("project/projectshow")
@Api(value = "项目轮播图片表", tags = "项目轮播图片表接口")
public class ProjectShowController extends BladeController {

	private IProjectShowService projectShowService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入projectShow")
	public R<ProjectShowVO> detail(ProjectShow projectShow) {
		ProjectShow detail = projectShowService.getOne(Condition.getQueryWrapper(projectShow));
		return R.data(ProjectShowWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 项目轮播图片表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入projectShow")
	public R<IPage<ProjectShowVO>> list(ProjectShow projectShow, Query query) {
		IPage<ProjectShow> pages = projectShowService.page(Condition.getPage(query), Condition.getQueryWrapper(projectShow));
		return R.data(ProjectShowWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 项目轮播图片表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入projectShow")
	public R<IPage<ProjectShowVO>> page(ProjectShowVO projectShow, Query query) {
		IPage<ProjectShowVO> pages = projectShowService.selectProjectShowPage(Condition.getPage(query), projectShow);
		return R.data(pages);
	}

	/**
	 * 新增 项目轮播图片表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入projectShow")
	public R save(@Valid @RequestBody ProjectShow projectShow) {
		return R.status(projectShowService.save(projectShow));
	}

	/**
	 * 修改 项目轮播图片表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入projectShow")
	public R update(@Valid @RequestBody ProjectShow projectShow) {
		return R.status(projectShowService.updateById(projectShow));
	}

	/**
	 * 新增或修改 项目轮播图片表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入projectShow")
	public R submit(@Valid @RequestBody ProjectShow projectShow) {
		return R.status(projectShowService.saveOrUpdate(projectShow));
	}


	/**
	 * 删除 项目轮播图片表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(projectShowService.deleteLogic(Func.toLongList(ids)));
	}

}
