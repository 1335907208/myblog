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
import org.springblade.client.entity.UserJackpot;
import org.springblade.client.vo.UserJackpotVO;
import org.springblade.client.wrapper.UserJackpotWrapper;
import org.springblade.client.service.IUserJackpotService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-09-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("client/userjackpot")
@Api(value = "", tags = "接口")
public class UserJackpotController extends BladeController {

	private IUserJackpotService userJackpotService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入userJackpot")
	public R<UserJackpotVO> detail(UserJackpot userJackpot) {
		UserJackpot detail = userJackpotService.getOne(Condition.getQueryWrapper(userJackpot));
		return R.data(UserJackpotWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入userJackpot")
	public R<IPage<UserJackpotVO>> list(UserJackpot userJackpot, Query query) {
		IPage<UserJackpot> pages = userJackpotService.page(Condition.getPage(query), Condition.getQueryWrapper(userJackpot));
		return R.data(UserJackpotWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入userJackpot")
	public R<IPage<UserJackpotVO>> page(UserJackpotVO userJackpot, Query query) {
		IPage<UserJackpotVO> pages = userJackpotService.selectUserJackpotPage(Condition.getPage(query), userJackpot);
		return R.data(pages);
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入userJackpot")
	public R save(@Valid @RequestBody UserJackpot userJackpot) {
		return R.status(userJackpotService.save(userJackpot));
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入userJackpot")
	public R update(@Valid @RequestBody UserJackpot userJackpot) {
		return R.status(userJackpotService.updateById(userJackpot));
	}

	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入userJackpot")
	public R submit(@Valid @RequestBody UserJackpot userJackpot) {
		return R.status(userJackpotService.saveOrUpdate(userJackpot));
	}

	
	/**
	 * 删除 
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(userJackpotService.deleteLogic(Func.toLongList(ids)));
	}

	
}
