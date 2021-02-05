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
import org.springblade.client.entity.UserOpening;
import org.springblade.client.vo.UserOpeningVO;
import org.springblade.client.wrapper.UserOpeningWrapper;
import org.springblade.client.service.IUserOpeningService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-09-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("client/useropening")
@Api(value = "", tags = "接口")
public class UserOpeningController extends BladeController {

	private IUserOpeningService userOpeningService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入userOpening")
	public R<UserOpeningVO> detail(UserOpening userOpening) {
		UserOpening detail = userOpeningService.getOne(Condition.getQueryWrapper(userOpening));
		return R.data(UserOpeningWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入userOpening")
	public R<IPage<UserOpeningVO>> list(UserOpening userOpening, Query query) {
		IPage<UserOpening> pages = userOpeningService.page(Condition.getPage(query), Condition.getQueryWrapper(userOpening));
		return R.data(UserOpeningWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入userOpening")
	public R<IPage<UserOpeningVO>> page(UserOpeningVO userOpening, Query query) {
		IPage<UserOpeningVO> pages = userOpeningService.selectUserOpeningPage(Condition.getPage(query), userOpening);
		return R.data(pages);
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入userOpening")
	public R save(@Valid @RequestBody UserOpening userOpening) {
		return R.status(userOpeningService.save(userOpening));
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入userOpening")
	public R update(@Valid @RequestBody UserOpening userOpening) {
		return R.status(userOpeningService.updateById(userOpening));
	}

	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入userOpening")
	public R submit(@Valid @RequestBody UserOpening userOpening) {
		return R.status(userOpeningService.saveOrUpdate(userOpening));
	}

	
	/**
	 * 删除 
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(userOpeningService.deleteLogic(Func.toLongList(ids)));
	}

	
}
