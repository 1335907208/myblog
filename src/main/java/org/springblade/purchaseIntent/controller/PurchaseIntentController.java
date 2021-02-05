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
import org.springblade.common.utils.CommonUtil;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.purchaseIntent.entity.PurchaseIntent;
import org.springblade.purchaseIntent.service.IPurchaseIntentService;
import org.springblade.purchaseIntent.vo.PurchaseIntentVO;
import org.springblade.purchaseIntent.wrapper.PurchaseIntentWrapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-09-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("client/purchaseIntent")
@Api(value = "购房意向接口", tags = "购房意向接口")
public class PurchaseIntentController extends BladeController {

	private IPurchaseIntentService purchaseIntentService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入purchaseIntent")
	public R<PurchaseIntentVO> detail(PurchaseIntent purchaseIntent) {
		PurchaseIntent detail = purchaseIntentService.getOne(Condition.getQueryWrapper(purchaseIntent));
		return R.data(PurchaseIntentWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
//	@GetMapping("/list")
//	@ApiOperation(value = "分页", notes = "传入purchaseIntent")
//	public R<IPage<PurchaseIntentVO>> list(PurchaseIntent purchaseIntent, Query query) {
//		IPage<PurchaseIntent> pages = purchaseIntentService.page(Condition.getPage(query), Condition.getQueryWrapper(purchaseIntent));
//		return R.data(PurchaseIntentWrapper.build().pageVO(pages));
//	}


	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperation(value = "自定义分页", notes = "传入purchaseIntentVO")
	public R<IPage<PurchaseIntentVO>> page(PurchaseIntentVO purchaseIntent, Query query) {
		purchaseIntent.setAgencyId(CommonUtil.build().getAgencyIdStr());
		purchaseIntent.setProjectId(CommonUtil.build().getProjectId());
		IPage<PurchaseIntentVO> pages = purchaseIntentService.selectPurchaseIntentPage(Condition.getPage(query), purchaseIntent);
		return R.data(pages);
	}

	/**
	 * 新增意向
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增意向", notes = "传入purchaseIntentVO")
	public R save(@Valid @RequestBody PurchaseIntentVO purchaseIntent) {
		return R.status(purchaseIntentService.save(purchaseIntent));
	}

	/**
	 * 新增订单
	 */
	@PostMapping("/orderSave")
	@ApiOperation(value = "新增订单", notes = "传入purchaseIntentVO")
	public R orderSave(@Valid @RequestBody PurchaseIntentVO purchaseIntent) {
		return R.status(purchaseIntentService.orderSave(purchaseIntent));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入purchaseIntentVO")
	public R update(@Valid @RequestBody PurchaseIntentVO userApplication) {
		return R.status(purchaseIntentService.updateById(userApplication));
	}

	@PostMapping("/agencyConfirm")
	@ApiOperation(value = "中介确认并生成EOINO接口", notes = "传入purchaseIntentVO")
	public R agencyConfirm(@Valid @RequestBody PurchaseIntentVO userApplication){
		PurchaseIntent purchaseIntent = purchaseIntentService.getOne(
				new LambdaQueryWrapper<PurchaseIntent>()
						.eq(PurchaseIntent::getOrderNumber, userApplication.getOrderNumber())
		);
		if(Func.isEmpty(purchaseIntent)){
			return R.fail("订单不存在!");
		}
		if(Func.equals(5,purchaseIntent.getStatus())){
			return R.fail("订单已通过MCC确认!");
		}
		if(Func.equals(1,purchaseIntent.getDuplicate())){
			return R.fail("订单为重复状态!");
		}

		PurchaseIntent one = purchaseIntentService.getOne(
				new LambdaQueryWrapper<PurchaseIntent>()
						.eq(PurchaseIntent::getProjectId, purchaseIntent.getProjectId())
						.orderByDesc(PurchaseIntent::getEoiNo)
						.last("limit 1")
		);
		purchaseIntent.setEoiNo(Func.toStr(Func.isEmpty(one) ? 10000 : 10001 + Func.toInt(one.getEoiNo())).substring(1));
		purchaseIntent.setStatus(5);
		return R.status(purchaseIntentService.updateById(purchaseIntent));
	}

	@PostMapping("/changeDuplicate")
	@ApiOperation(value = "修改意向重复状态", notes = "意向ID")
	public R changeDuplicate(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		List<PurchaseIntent> purchaseIntents = purchaseIntentService.listByIds(Func.toLongList(ids));
		if (Func.isNotEmpty(purchaseIntents)) {
			purchaseIntents.forEach(intent -> {
				Integer duplicate = intent.getDuplicate();
				if (Func.isNotEmpty(duplicate)) {
					intent.setDuplicate(duplicate > 0 ? 0 : 1);
				} else {
					intent.setDuplicate(0);
				}
			});
		}
		return R.status(purchaseIntentService.updateBatchById(purchaseIntents));
	}

	/**
	 * 新增或修改
	 */
//	@PostMapping("/submit")
//	@ApiOperation(value = "新增或修改", notes = "传入purchaseIntent")
//	public R submit(@Valid @RequestBody PurchaseIntent userApplication) {
//		return R.status(purchaseIntentService.saveOrUpdate(userApplication));
//	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(purchaseIntentService.deleteLogic(Func.toLongList(ids)));
	}

	@PostMapping("/statisticByStack")
	@ApiOperation(value = "按楼栋统计中介公司意向数量", notes = "传入purchaseIntentVO")
	public R statisticByStack(@Valid @RequestBody PurchaseIntentVO intentVO, Query query) {
		IPage<PurchaseIntentVO> page = Condition.getPage(query);
		List<PurchaseIntentVO> list = purchaseIntentService.statisticVO(page.setSize(-1), intentVO);
		Map<String, Map<String, Long>> statisticStackMap = new HashMap();
		List<Map<String, Object>> stackList = new ArrayList<>();
		if (Func.isNotEmpty(list)) {
			Set<String> agencySet = list.stream().map(intent -> intent.getAgency()).collect(Collectors.toSet());
			Set<String> stackSet = list.stream().map(intent -> intent.getStack()).collect(Collectors.toSet());
			for (String agency : agencySet) {
				HashMap<String, Long> stackmap = new HashMap<>();
				stackSet.forEach(stack -> stackmap.put(stack, 0L));
				statisticStackMap.put(agency, stackmap);
			}
			for (PurchaseIntentVO vo : list) {
				Map<String, Long> map = statisticStackMap.get(vo.getAgency());
				map.replace(vo.getStack(), map.get(vo.getStack()) + 1);
			}
			stackList = statisticList(statisticStackMap, agencySet);
		}
		return R.data(stackList, "statisticByStack");
	}

	private List<Map<String, Object>> statisticList(Map<String, Map<String, Long>> map, Set<String> set) {
		List<Map<String, Object>> list = new ArrayList<>();
		String name = "name";
		String statistic = "statistic";
		set.forEach(agency -> {
            Map<String, Object> stackMap = new HashMap<>();
            stackMap.put(name, agency);
            stackMap.put(statistic, map.get(agency));
            list.add(stackMap);
        });
		return list;
	}


	@PostMapping("/statisticBylevel")
	@ApiOperation(value = "按楼层统计中介公司意向数量", notes = "传入purchaseIntentVO")
	public R statisticBylevel(@Valid @RequestBody PurchaseIntentVO intentVO, Query query) {
		IPage<PurchaseIntentVO> page = Condition.getPage(query);
		List<PurchaseIntentVO> list = purchaseIntentService.statisticVO(page.setSize(-1), intentVO);
		Map<String, Map<String, Long>> statisticlevelMap = new HashMap();
		List<Map<String, Object>> levelList = new ArrayList<>();
		if (Func.isNotEmpty(list)) {
			Set<String> agencys = list.stream().map(intent -> intent.getAgency()).collect(Collectors.toSet());
			Set<String> levelSet = list.stream().map(intent -> intent.getLevel()).collect(Collectors.toSet());
			for (String agency : agencys) {
				HashMap<String, Long> levelmap = new HashMap<>();
				levelSet.forEach(level -> levelmap.put(level, 0L));
				statisticlevelMap.put(agency, levelmap);
			}
			for (PurchaseIntentVO vo : list) {
				Map<String, Long> map = statisticlevelMap.get(vo.getAgency());
				map.replace(vo.getLevel(), map.get(vo.getLevel()) + 1);
			}
			levelList = statisticList(statisticlevelMap, agencys);
		}
		return R.data(levelList, "statisticBylevel");
	}

	@PostMapping("/statisticByUnitType")
	@ApiOperation(value = "按户型统计意向数量", notes = "传入purchaseIntentVO")
	public R statisticByUnitType(@Valid @RequestBody PurchaseIntentVO intentVO, Query query) {
		IPage<PurchaseIntentVO> page = Condition.getPage(query);
		List<PurchaseIntentVO> list = purchaseIntentService.statisticVO(page.setSize(-1), intentVO);
		Map<String, Long> unitTypeMap = new HashMap();
		if (Func.isNotEmpty(list)) {
			Set<String> unitTypeSet = list.stream().map(unit -> unit.getUnitType()).collect(Collectors.toSet());
			unitTypeSet.forEach(u -> unitTypeMap.put(u, 0L));
			list.forEach(vo -> unitTypeMap.replace(vo.getUnitType(), unitTypeMap.get(vo.getUnitType()) + 1));
		}
		return R.data(unitTypeMap, "statisticByUnitType");
	}

	@PostMapping("/statisticBulk")
	@ApiOperation(value = "批量意向和中介意向统计", notes = "传入purchaseIntentVO")
	public R statisticBulk(@Valid @RequestBody PurchaseIntentVO intentVO, Query query) {
		List<Map<String, Object>> bulkList = new ArrayList<>();
		List<PurchaseIntent> intentList = purchaseIntentService.list(
				new LambdaQueryWrapper<PurchaseIntent>()
						.eq(PurchaseIntent::getProjectId,intentVO.getProjectId())
		);
		if(Func.isNotEmpty(intentList)){
			Set<String> agencys = intentList.stream().map(intent -> intent.getAgency()).collect(Collectors.toSet());
			String agent = "agent";
			String bulk = "bulk";
			String name = "name";
			String statistic = "statistic";
			agencys.forEach(agency -> {
				if(Func.isNotEmpty(agency)){
					Map<String, Long> map = new HashMap();
					map.put(bulk,intentList.stream().filter(intent -> intent.getBulkPurchase() == 1 && agency.equals(intent.getAgency())).count());
					map.put(agent,intentList.stream().filter(intent -> agent.equals(intent.getCreateUserType()) && agency.equals(intent.getAgency())).count());
					Map<String, Object> stackMap = new HashMap<>();
					stackMap.put(name,agency);
					stackMap.put(statistic,map);
					bulkList.add(stackMap);
				}
			});
		}
		return R.data(bulkList, "statisticByUnitType");
	}
}
