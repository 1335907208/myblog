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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.client.eneity.Unit;
import org.springblade.client.entity.UserOrder;
import org.springblade.client.service.IUnitService;
import org.springblade.common.utils.CommonUtil;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.purchaseIntent.entity.PurchaseIntentUnit;
import org.springblade.purchaseIntent.entity.PurchaseOrder;
import org.springblade.purchaseIntent.service.IPurchaseIntentUnitService;
import org.springblade.purchaseIntent.service.IPurchaseOrderService;
import org.springblade.purchaseIntent.vo.PurchaseOrderVO;
import org.springblade.purchaseIntent.wrapper.PurchaseOrderWrapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-09-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("client/purchaseOrder")
@Api(value = "购房意向接口", tags = "购房意向接口")
public class PurchaseOrderController extends BladeController {

	private IPurchaseOrderService purchaseOrderService;
	private IPurchaseIntentUnitService purchaseIntentUnitService;
	private IUnitService unitService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入purchaseOrder")
	public R<PurchaseOrderVO> detail(PurchaseOrder purchaseOrder) {
		PurchaseOrder detail = purchaseOrderService.getOne(Condition.getQueryWrapper(purchaseOrder));
		return R.data(PurchaseOrderWrapper.build().entityVO(detail));
	}

	/**
	 * 分页
	 */
//	@GetMapping("/list")
//	@ApiOperation(value = "分页", notes = "传入purchaseOrder")
//	public R<IPage<PurchaseOrderVO>> list(PurchaseOrder purchaseOrder, Query query) {
//		IPage<PurchaseOrder> pages = purchaseOrderService.page(Condition.getPage(query), Condition.getQueryWrapper(purchaseOrder));
//		return R.data(PurchaseOrderWrapper.build().pageVO(pages));
//	}


	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperation(value = "自定义分页", notes = "传入purchaseOrder")
	public R<IPage<PurchaseOrderVO>> page(PurchaseOrderVO purchaseOrder, Query query) {
		purchaseOrder.setProjectId(CommonUtil.build().getProjectId());
		IPage<PurchaseOrderVO> pages = purchaseOrderService.selectPurchaseOrderPage(Condition.getPage(query), purchaseOrder);
		return R.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入purchaseOrder")
	public R save(@Valid @RequestBody PurchaseOrder purchaseOrder) {
		return R.status(purchaseOrderService.save(purchaseOrder));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入purchaseOrder")
	public R update(@Valid @RequestBody PurchaseOrder purchaseOrder) {
		return R.status(purchaseOrderService.updateById(purchaseOrder));
	}

	/**
	 * 新增或修改
	 */
//	@PostMapping("/submit")
//	@ApiOperation(value = "新增或修改", notes = "传入purchaseOrder")
//	public R submit(@Valid @RequestBody PurchaseOrder purchaseOrder) {
//		return R.status(purchaseOrderService.saveOrUpdate(purchaseOrder));
//	}

	@PostMapping("/confirm")
	@ApiOperation(value = "确认", notes = "传入userOrder")
	public R confirm(@Valid @RequestBody UserOrder userOrder){
		PurchaseOrder order = purchaseOrderService.getById(userOrder.getId());
		if(Func.isNotEmpty(order)){
			//TODO 第三方 unit单元状态信息确认
			order.setStatus(9);//状态 0=意向 1=预定 9=购买成功
			if(purchaseOrderService.updateById(order)){
				List<PurchaseIntentUnit> list = purchaseIntentUnitService.list(
						new LambdaQueryWrapper<PurchaseIntentUnit>()
								.eq(PurchaseIntentUnit::getApplicationId, order.getApplicationId())
				);
				List<Long> collect = list.stream().map(PurchaseIntentUnit::getUnitId).collect(Collectors.toList());
				List<Unit> units = unitService.listByIds(collect);
				units.forEach(unit -> unit.setSaleStatus(1));//是否售出（0：未售出，可卖，1：已售出，不可卖）
				return R.status(unitService.updateBatchById(units));
			}
		}
		return R.status(false);
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(purchaseOrderService.deleteLogic(Func.toLongList(ids)));
	}


}
