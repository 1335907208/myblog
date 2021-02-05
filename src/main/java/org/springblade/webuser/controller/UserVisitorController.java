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
import org.springblade.webuser.entity.UserVisitor;
import org.springblade.webuser.vo.UserVisitorVO;
import org.springblade.webuser.wrapper.UserVisitorWrapper;
import org.springblade.webuser.service.IUserVisitorService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * user_visitor 控制器
 *
 * @author Blade
 * @since 2020-06-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("webuser/uservisitor")
@Api(value = "user_visitor", tags = "user_visitor接口")
public class UserVisitorController extends BladeController {

	private IUserVisitorService userVisitorService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入userVisitor")
	public R<UserVisitorVO> detail(UserVisitor userVisitor) {
		UserVisitor detail = userVisitorService.getOne(Condition.getQueryWrapper(userVisitor));
		return R.data(UserVisitorWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 user_visitor
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入userVisitor")
	public R<IPage<UserVisitorVO>> list(UserVisitor userVisitor, Query query) {
		IPage<UserVisitor> pages = userVisitorService.page(Condition.getPage(query), Condition.getQueryWrapper(userVisitor));
		return R.data(UserVisitorWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 user_visitor
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入userVisitor")
	public R<IPage<UserVisitorVO>> page(UserVisitorVO userVisitor, Query query) {
		IPage<UserVisitorVO> pages = userVisitorService.selectUserVisitorPage(Condition.getPage(query), userVisitor);
		return R.data(pages);
	}

	/**
	 * 新增 user_visitor
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入userVisitor")
	public R save(@Valid @RequestBody UserVisitor userVisitor) {
		return R.status(userVisitorService.save(userVisitor));
	}

	/**
	 * 修改 user_visitor
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入userVisitor")
	public R update(@Valid @RequestBody UserVisitor userVisitor) {
		return R.status(userVisitorService.updateById(userVisitor));
	}

	/**
	 * 新增或修改 user_visitor
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入userVisitor")
	public R submit(@Valid @RequestBody UserVisitor userVisitor) {
		return R.status(userVisitorService.saveOrUpdate(userVisitor));
	}

	
	/**
	 * 删除 user_visitor
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(userVisitorService.deleteLogic(Func.toLongList(ids)));
	}

	
}
