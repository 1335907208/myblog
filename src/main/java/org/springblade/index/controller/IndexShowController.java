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
package org.springblade.index.controller;

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
import org.springblade.index.entity.IndexShow;
import org.springblade.index.vo.IndexShowVO;
import org.springblade.index.wrapper.IndexShowWrapper;
import org.springblade.index.service.IIndexShowService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;

/**
 * 首页轮播设置表 控制器
 *
 * @author Blade
 * @since 2020-07-07
 */
@RestController
@AllArgsConstructor
@RequestMapping("index/indexshow")
@Api(value = "首页轮播设置表", tags = "首页轮播设置表接口")
@CrossOrigin("*")
public class IndexShowController extends BladeController {

	private IIndexShowService indexShowService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入indexShow")
	public R<IndexShowVO> detail(IndexShow indexShow) {
		IndexShow detail = indexShowService.getOne(Condition.getQueryWrapper(indexShow));
		return R.data(IndexShowWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 首页轮播设置表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入indexShow")
	public R<IPage<IndexShowVO>> list(IndexShow indexShow, Query query) {
		IPage<IndexShow> pages = indexShowService.page(Condition.getPage(query), Condition.getQueryWrapper(indexShow));
		return R.data(IndexShowWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 首页轮播设置表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入indexShow")
	public R<IPage<IndexShowVO>> page(IndexShowVO indexShow, Query query) {
		IPage<IndexShowVO> pages = indexShowService.selectIndexShowPage(Condition.getPage(query), indexShow);
		return R.data(pages);
	}

	/**
	 * 新增 首页轮播设置表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入indexShow")
	public R save(@Valid @RequestBody IndexShow indexShow) {
		return R.status(indexShowService.save(indexShow));
	}

	/**
	 * 修改 首页轮播设置表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入indexShow")
	public R update(@Valid @RequestBody IndexShow indexShow) {
		return R.status(indexShowService.updateById(indexShow));
	}

	/**
	 * 新增或修改 首页轮播设置表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入indexShow")
	public R submit(@Valid @RequestBody IndexShow indexShow) {
		return R.status(indexShowService.saveOrUpdate(indexShow));
	}


	/**
	 * 删除 首页轮播设置表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(indexShowService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 首页轮播图列表
	 *
	 * @return
	 */
	@GetMapping("/index-list")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "IndexShow列表", notes = "")
	public R<List<IndexShow>> indexShowList() {
		List<IndexShow> list = indexShowService.list(Wrappers.<IndexShow>query().lambda().eq(IndexShow::getIsDeleted, 0));
		return R.data(list);
	}


}
