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
import org.springblade.project.entity.ProjectCctv;
import org.springblade.project.vo.ProjectCctvVO;
import org.springblade.project.wrapper.ProjectCctvWrapper;
import org.springblade.project.service.IProjectCctvService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 项目CCTV表 控制器
 *
 * @author Blade
 * @since 2020-07-02
 */
@RestController
@AllArgsConstructor
@RequestMapping("project/projectcctv")
@Api(value = "项目CCTV表", tags = "项目CCTV表接口")
public class ProjectCctvController extends BladeController {

	private IProjectCctvService projectCctvService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入projectCctv")
	public R<ProjectCctvVO> detail(ProjectCctv projectCctv) {
		ProjectCctv detail = projectCctvService.getOne(Condition.getQueryWrapper(projectCctv));
		return R.data(ProjectCctvWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 项目CCTV表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入projectCctv")
	public R<IPage<ProjectCctvVO>> list(ProjectCctv projectCctv, Query query) {
		IPage<ProjectCctv> pages = projectCctvService.page(Condition.getPage(query), Condition.getQueryWrapper(projectCctv));
		return R.data(ProjectCctvWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 项目CCTV表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入projectCctv")
	public R<IPage<ProjectCctvVO>> page(ProjectCctvVO projectCctv, Query query) {
		IPage<ProjectCctvVO> pages = projectCctvService.selectProjectCctvPage(Condition.getPage(query), projectCctv);
		return R.data(pages);
	}

	/**
	 * 新增 项目CCTV表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入projectCctv")
	public R save(@Valid @RequestBody ProjectCctv projectCctv) {
		return R.status(projectCctvService.save(projectCctv));
	}

	/**
	 * 修改 项目CCTV表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入projectCctv")
	public R update(@Valid @RequestBody ProjectCctv projectCctv) {
		return R.status(projectCctvService.updateById(projectCctv));
	}

	/**
	 * 新增或修改 项目CCTV表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入projectCctv")
	public R submit(@Valid @RequestBody ProjectCctv projectCctv) {
		return R.status(projectCctvService.saveOrUpdate(projectCctv));
	}

	
	/**
	 * 删除 项目CCTV表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(projectCctvService.deleteLogic(Func.toLongList(ids)));
	}

	
}
