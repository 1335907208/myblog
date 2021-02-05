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
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.service.IUserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.webuser.entity.UserAgent;
import org.springblade.webuser.vo.UserAgentVO;
import org.springblade.webuser.wrapper.UserAgentWrapper;
import org.springblade.webuser.service.IUserAgentService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * user_agent 控制器
 *
 * @author Blade
 * @since 2020-06-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("webuser/useragent")
@Api(value = "user_agent", tags = "user_agent接口")
public class UserAgentController extends BladeController {

	private IUserAgentService userAgentService;

	private IUserService userService;
	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入userAgent")
	public R<UserAgentVO> detail(UserAgent userAgent) {
		UserAgent detail = userAgentService.getOne(Condition.getQueryWrapper(userAgent));
		return R.data(UserAgentWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 user_agent
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入userAgent")
	public R<IPage<UserAgentVO>> list(UserAgentVO userAgent, Query query) {
		User user = userService.getById(SecureUtil.getUserId());
		if(Func.isNotEmpty(user) && Func.isNotEmpty(user.getAgencyId())){
			userAgent.setCompany(Func.toStr(user.getAgencyId()));
		}
		IPage<UserAgent> pages = userAgentService.page(Condition.getPage(query), Condition.getQueryWrapper(userAgent));
		return R.data(UserAgentWrapper.build().pageVO(pages));
	}


//	/**
//	 * 自定义分页 user_agent
//	 */
//	@GetMapping("/page")
//	@ApiOperationSupport(order = 3)
//	@ApiOperation(value = "分页", notes = "传入userAgent")
//	public R<IPage<UserAgentVO>> page(UserAgentVO userAgent, Query query) {
//		IPage<UserAgentVO> pages = userAgentService.selectUserAgentPage(Condition.getPage(query), userAgent);
//		return R.data(pages);
//	}

	/**
	 * 新增 user_agent
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入userAgent")
	public R save(@Valid @RequestBody UserAgent userAgent) {
		return R.status(userAgentService.save(userAgent));
	}

	/**
	 * 修改 user_agent
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入userAgent")
	public R update(@Valid @RequestBody UserAgent userAgent) {
		return R.status(userAgentService.updateById(userAgent));
	}

	/**
	 * 新增或修改 user_agent
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入userAgent")
	public R submit(@Valid @RequestBody UserAgent userAgent) {
		return R.status(userAgentService.saveOrUpdate(userAgent));
	}

	
	/**
	 * 删除 user_agent
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(userAgentService.deleteLogic(Func.toLongList(ids)));
	}

	
}
