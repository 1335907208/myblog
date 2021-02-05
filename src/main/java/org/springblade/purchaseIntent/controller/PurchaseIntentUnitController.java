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
package org.springblade.purchaseIntent.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.purchaseIntent.entity.PurchaseIntentUnit;
import org.springblade.purchaseIntent.service.IPurchaseIntentUnitService;
import org.springblade.purchaseIntent.vo.PurchaseIntentUnitVO;
import org.springblade.purchaseIntent.wrapper.PurchaseIntentUnitWrapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-09-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("client/purchaseIntentUnit")
@Api(value = "购房意向接口", tags = "购房意向接口")
public class PurchaseIntentUnitController extends BladeController {

	private IPurchaseIntentUnitService purchaseIntentUnitService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入purchaseIntentUnit")
	public R<PurchaseIntentUnitVO> detail(PurchaseIntentUnit purchaseIntentUnit) {
		PurchaseIntentUnit detail = purchaseIntentUnitService.getOne(Condition.getQueryWrapper(purchaseIntentUnit));
		return R.data(PurchaseIntentUnitWrapper.build().entityVO(detail));
	}

	/**
	 * 分页
	 */
//	@GetMapping("/list")
//	@ApiOperation(value = "分页", notes = "传入purchaseIntentUnit")
//	public R<IPage<PurchaseIntentUnitVO>> list(PurchaseIntentUnit purchaseIntentUnit, Query query) {
//		IPage<PurchaseIntentUnit> pages = purchaseIntentUnitService.page(Condition.getPage(query), Condition.getQueryWrapper(purchaseIntentUnit));
//		return R.data(PurchaseIntentUnitWrapper.build().pageVO(pages));
//	}


	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperation(value = "自定义分页", notes = "传入purchaseIntentUnit")
	public R<IPage<PurchaseIntentUnitVO>> page(PurchaseIntentUnitVO purchaseIntentUnit, Query query) {
		IPage<PurchaseIntentUnitVO> pages = purchaseIntentUnitService.selectPurchaseIntentUnitPage(Condition.getPage(query), purchaseIntentUnit);
		return R.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入purchaseIntentUnit")
	public R save(@Valid @RequestBody PurchaseIntentUnit purchaseIntentUnit) {
		return R.status(purchaseIntentUnitService.save(purchaseIntentUnit));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入purchaseIntentUnit")
	public R update(@Valid @RequestBody PurchaseIntentUnit purchaseIntentUnit) {
		return R.status(purchaseIntentUnitService.updateById(purchaseIntentUnit));
	}

	/**
	 * 新增或修改
	 */
//	@PostMapping("/submit")
//	@ApiOperation(value = "新增或修改", notes = "传入purchaseIntentUnit")
//	public R submit(@Valid @RequestBody PurchaseIntentUnit purchaseIntentUnit) {
//		return R.status(purchaseIntentUnitService.saveOrUpdate(purchaseIntentUnit));
//	}


	/**
	 * 删除
	 */
//	@PostMapping("/remove")
//	@ApiOperation(value = "逻辑删除", notes = "传入ids")
//	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
//		return R.status(purchaseIntentUnitService.deleteLogic(Func.toLongList(ids)));
//	}


}
