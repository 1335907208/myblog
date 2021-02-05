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
package org.springblade.client.controller;

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
import org.springblade.client.eneity.ProjectUnittype;
import org.springblade.client.vo.ProjectUnittypeVO;
import org.springblade.client.wrapper.ProjectUnittypeWrapper;
import org.springblade.client.service.IProjectUnittypeService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 项目全景照片表 控制器
 *
 * @author Blade
 * @since 2020-09-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("client/projectunittype")
@Api(value = "项目全景照片表", tags = "项目全景照片表接口")
public class ProjectUnittypeController extends BladeController {

	private IProjectUnittypeService projectUnittypeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入projectUnittype")
	public R<ProjectUnittypeVO> detail(ProjectUnittype projectUnittype) {
		ProjectUnittype detail = projectUnittypeService.getOne(Condition.getQueryWrapper(projectUnittype));
		return R.data(ProjectUnittypeWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 项目全景照片表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入projectUnittype")
	public R<IPage<ProjectUnittypeVO>> list(ProjectUnittype projectUnittype, Query query) {
		IPage<ProjectUnittype> pages = projectUnittypeService.page(Condition.getPage(query), Condition.getQueryWrapper(projectUnittype));
		return R.data(ProjectUnittypeWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 项目全景照片表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入projectUnittype")
	public R<IPage<ProjectUnittypeVO>> page(ProjectUnittypeVO projectUnittype, Query query) {
		IPage<ProjectUnittypeVO> pages = projectUnittypeService.selectProjectUnittypePage(Condition.getPage(query), projectUnittype);
		return R.data(pages);
	}

	/**
	 * 新增 项目全景照片表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入projectUnittype")
	public R save(@Valid @RequestBody ProjectUnittype projectUnittype) {
		return R.status(projectUnittypeService.save(projectUnittype));
	}

	/**
	 * 修改 项目全景照片表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入projectUnittype")
	public R update(@Valid @RequestBody ProjectUnittype projectUnittype) {
		return R.status(projectUnittypeService.updateById(projectUnittype));
	}

	/**
	 * 新增或修改 项目全景照片表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入projectUnittype")
	public R submit(@Valid @RequestBody ProjectUnittype projectUnittype) {
		return R.status(projectUnittypeService.saveOrUpdate(projectUnittype));
	}


	/**
	 * 删除 项目全景照片表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(projectUnittypeService.deleteLogic(Func.toLongList(ids)));
	}


}
