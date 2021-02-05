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
package org.springblade.webuser.controller;

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
import org.springblade.webuser.entity.UserBuyer;
import org.springblade.webuser.vo.UserBuyerVO;
import org.springblade.webuser.wrapper.UserBuyerWrapper;
import org.springblade.webuser.service.IUserBuyerService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * user_buyer 控制器
 *
 * @author Blade
 * @since 2020-06-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("webuser/userbuyer")
@Api(value = "user_buyer", tags = "user_buyer接口")
public class UserBuyerController extends BladeController {

	private IUserBuyerService userBuyerService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入userBuyer")
	public R<UserBuyerVO> detail(UserBuyer userBuyer) {
		UserBuyer detail = userBuyerService.getOne(Condition.getQueryWrapper(userBuyer));
		return R.data(UserBuyerWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 user_buyer
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入userBuyer")
	public R<IPage<UserBuyerVO>> list(UserBuyer userBuyer, Query query) {
		IPage<UserBuyer> pages = userBuyerService.page(Condition.getPage(query), Condition.getQueryWrapper(userBuyer));
		return R.data(UserBuyerWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 user_buyer
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入userBuyer")
	public R<IPage<UserBuyerVO>> page(UserBuyerVO userBuyer, Query query) {
		IPage<UserBuyerVO> pages = userBuyerService.selectUserBuyerPage(Condition.getPage(query), userBuyer);
		return R.data(pages);
	}

	/**
	 * 新增 user_buyer
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入userBuyer")
	public R save(@Valid @RequestBody UserBuyer userBuyer) {
		return R.status(userBuyerService.save(userBuyer));
	}

	/**
	 * 修改 user_buyer
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入userBuyer")
	public R update(@Valid @RequestBody UserBuyer userBuyer) {
		return R.status(userBuyerService.updateById(userBuyer));
	}

	/**
	 * 新增或修改 user_buyer
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入userBuyer")
	public R submit(@Valid @RequestBody UserBuyer userBuyer) {
		return R.status(userBuyerService.saveOrUpdate(userBuyer));
	}


	/**
	 * 删除 user_buyer
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(userBuyerService.deleteLogic(Func.toLongList(ids)));
	}


}
