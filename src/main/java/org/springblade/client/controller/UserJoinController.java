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
import org.springblade.client.entity.UserJoin;
import org.springblade.client.vo.UserJoinVO;
import org.springblade.client.wrapper.UserJoinWrapper;
import org.springblade.client.service.IUserJoinService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * user_buyer 控制器
 *
 * @author Blade
 * @since 2020-09-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("client/userjoin")
@Api(value = "user_buyer", tags = "user_buyer接口")
public class UserJoinController extends BladeController {

	private IUserJoinService userJoinService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入userJoin")
	public R<UserJoinVO> detail(UserJoin userJoin) {
		UserJoin detail = userJoinService.getOne(Condition.getQueryWrapper(userJoin));
		return R.data(UserJoinWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 user_buyer
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入userJoin")
	public R<IPage<UserJoinVO>> list(UserJoin userJoin, Query query) {
		IPage<UserJoin> pages = userJoinService.page(Condition.getPage(query), Condition.getQueryWrapper(userJoin));
		return R.data(UserJoinWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 user_buyer
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入userJoin")
	public R<IPage<UserJoinVO>> page(UserJoinVO userJoin, Query query) {
		IPage<UserJoinVO> pages = userJoinService.selectUserJoinPage(Condition.getPage(query), userJoin);
		return R.data(pages);
	}

	/**
	 * 新增 user_buyer
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入userJoin")
	public R save(@Valid @RequestBody UserJoin userJoin) {
		return R.status(userJoinService.save(userJoin));
	}

	/**
	 * 修改 user_buyer
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入userJoin")
	public R update(@Valid @RequestBody UserJoin userJoin) {
		return R.status(userJoinService.updateById(userJoin));
	}

	/**
	 * 新增或修改 user_buyer
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入userJoin")
	public R submit(@Valid @RequestBody UserJoin userJoin) {
		return R.status(userJoinService.saveOrUpdate(userJoin));
	}

	
	/**
	 * 删除 user_buyer
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(userJoinService.deleteLogic(Func.toLongList(ids)));
	}

	
}
