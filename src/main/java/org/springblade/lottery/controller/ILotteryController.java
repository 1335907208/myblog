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
package org.springblade.lottery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springblade.client.eneity.Unit;
import org.springblade.client.service.IUnitService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.lottery.entity.Lottery;
import org.springblade.lottery.service.ILotteryService;
import org.springblade.lottery.vo.LotteryVO;
import org.springblade.lottery.wrapper.LotteryWrapper;
import org.springblade.project.entity.ProjectInfo;
import org.springblade.project.service.IProjectInfoService;
import org.springblade.purchaseIntent.entity.PurchaseIntent;
import org.springblade.purchaseIntent.entity.PurchaseOrder;
import org.springblade.purchaseIntent.service.IPurchaseIntentService;
import org.springblade.purchaseIntent.service.IPurchaseOrderService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-09-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("client/lottery")
@Api(value = "抽奖接口", tags = "抽奖接口")
public class ILotteryController extends BladeController {

	IUnitService iUnitService;
	ILotteryService iLotteryService;
	IPurchaseIntentService iPurchaseIntentService;
	IProjectInfoService iProjectInfoService;
	IPurchaseOrderService iPurchaseOrderService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "lotteryVO")
	public R<LotteryVO> detail(LotteryVO lotteryVO) {
		Lottery detail = iLotteryService.getOne(Condition.getQueryWrapper(lotteryVO));
		return R.data(LotteryWrapper.build().entityVO(detail));
	}


	/**
	 * 自定义分页
	 */
//	@GetMapping("/page")
//	@ApiOperation(value = "自定义分页", notes = "传入lotteryVO")
//	public R<IPage<LotteryVO>> page(LotteryVO lotteryVO, Query query) {
//		//  增加查询条件 状态
//
//		IPage<LotteryVO> pages = iLotteryService.selectLotteryPage(Condition.getPage(query), lotteryVO);
//		return R.data(pages);
//	}

	/**
	 * 新增
	 */
//	@PostMapping("/save")
//	@ApiOperation(value = "新增", notes = "传入lotteryVO")
//	public R save(@Valid @RequestBody LotteryVO lotteryVO) {
//		return R.status(iLotteryService.save(lotteryVO));
//	}

	/**
	 * 修改
	 */
//	@PostMapping("/update")
//	@ApiOperation(value = "修改", notes = "传入lotteryVO")
//	public R update(@Valid @RequestBody LotteryVO lotteryVO) {
//		return R.status(iLotteryService.updateById(lotteryVO));
//	}

	/**
	 * 删除
	 */
//	@PostMapping("/remove")
//	@ApiOperation(value = "逻辑删除", notes = "传入ids")
//	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
//		return R.status(iLotteryService.removeByIds(Func.toLongList(ids)));
//	}

	/**
	 * 开盘详情
	 *
	 */
	@RequestMapping("/openDetail")
	@ApiOperation(value = "开盘详情", notes = "传入projectId")
	public R openDetail(@ApiParam(value = "项目ID", required = true) @RequestParam String projectId){
		Map<String, Object> map = new HashMap<>();
		//1.通过项目ID，查出可售房源总数、已售房源数量（house_project_unit表）
		//可售房源总数
		Integer sellable = iUnitService.count(new LambdaQueryWrapper<Unit>()
			.eq(Unit::getProjectId, projectId)
			.eq(Unit::getSaleStatus, 0));
		map.put("sellable",sellable);
		//已售房源数量
		Integer totalUnit = iUnitService.count(new LambdaQueryWrapper<Unit>()
			.eq(Unit::getProjectId, projectId));
		map.put("sold",totalUnit-sellable);
		//2.通过项目ID，查出抽签人总数、已抽签人数（status != 0）、已选房人数（包含失败的status >3）（house_application_lottery表）
		//抽签人总数
		Integer totalLottery = iLotteryService.count(new LambdaQueryWrapper<Lottery>()
			.eq(Lottery::getProjectId, projectId));
		map.put("totalLottery",totalLottery);
		//已抽签人数（status != 0）
		Integer drawLottery = iLotteryService.count(new LambdaQueryWrapper<Lottery>()
			.eq(Lottery::getProjectId, projectId)
			.ne(Lottery::getStatus, 0));
		map.put("drawLottery",drawLottery);
		//已选房人数（包含失败的status >3）
		Integer chooseLottery = iLotteryService.count(new LambdaQueryWrapper<Lottery>()
			.eq(Lottery::getProjectId, projectId)
			.gt(Lottery::getStatus, 3));
		map.put("chooseLottery",chooseLottery);

		//4.通过项目ID，查出抽奖表已抽中人员信息（status > 0）（house_application_lottery表
		//抽奖表人员信息
		List<Lottery> lotteryList = iLotteryService.list(new LambdaQueryWrapper<Lottery>()
			.eq(Lottery::getProjectId, projectId).orderByAsc(Lottery::getLotteryTime));
		if(Func.isNotEmpty(lotteryList)){
			map.put("lotteryVOList",LotteryWrapper.build().listVO(lotteryList));
		}
		return R.data(map,"Success!");
	}


	/**
	 * 开盘
	 */
	@PostMapping("/opening")
	@ApiOperation(value = "开盘", notes = "传入projectId")
	public R opening(@ApiParam(value = "项目ID", required = true) @RequestParam String projectId) {
		//  从application表中获取符合条件（status为9 MCC确认）的数据存入lottery表
		List<PurchaseIntent> purchaseIntentList = iPurchaseIntentService.list(new LambdaQueryWrapper<PurchaseIntent>()
			.eq(PurchaseIntent::getProjectId, projectId)
			.eq(PurchaseIntent::getStatus, 9));
		if(Func.isNotEmpty(purchaseIntentList)){
			List<Lottery> lotteryList = new ArrayList<>();
			purchaseIntentList.forEach((application)->{
				Lottery lottery = new Lottery();
				lottery.setProjectId(application.getProjectId());
				lottery.setApplicationId(application.getId());
				lottery.setEoiNo(application.getEoiNo());
				lottery.setUserId(application.getCreateUser());
				lottery.setUserType(application.getCreateUserType());
				lottery.setStatus(0);
				lotteryList.add(lottery);
			});
			// 修改projectInfo状态为已开盘
			ProjectInfo projectInfo = iProjectInfoService.getById(projectId);
			projectInfo.setOpenStatus(1);
			iProjectInfoService.updateById(projectInfo);
			return R.status(iLotteryService.saveBatch(lotteryList));
		}else{
			return R.fail("No intention information!");
		}
	}

	/**
	 * 重新开盘
	 */
	@PostMapping("/reOpening")
	@ApiOperation(value = "重新开盘", notes = "传入projectId")
	public R reOpening(@ApiParam(value = "项目ID", required = true) @RequestParam String projectId) {
		//清除订单
		List<PurchaseOrder> purchaseOrderList = iPurchaseOrderService.list(new LambdaQueryWrapper<PurchaseOrder>()
			.eq(PurchaseOrder::getProjectId, projectId));
		if(Func.isNotEmpty(purchaseOrderList)){
			List<Long> purchaseOrderIdList = purchaseOrderList.stream().map(PurchaseOrder::getId).collect(Collectors.toList());
			iPurchaseOrderService.removeByIds(purchaseOrderIdList);
		}

		//清除抽奖信息
		List<Lottery> lotteryList = iLotteryService.list(new LambdaQueryWrapper<Lottery>()
			.eq(Lottery::getProjectId, projectId));
		if(Func.isNotEmpty(lotteryList)){
			List<Long> lotteryIdList = lotteryList.stream().map(Lottery::getId).collect(Collectors.toList());
			iLotteryService.removeByIds(lotteryIdList);
		}

		//重新开盘
		return this.opening(projectId);
	}

	/**
	 * 抽奖
	 */
	@PostMapping("/lottery")
	@ApiOperation(value = "抽奖", notes = "传入projectId")
	public R lottery(@ApiParam(value = "主键集合", required = true) @RequestParam String projectId) {
		//  从lottery表中status=0的抽取一条数据作为中奖用户，将状态改为1
		List<Lottery> lotteryList = iLotteryService.list(new LambdaQueryWrapper<Lottery>()
			.eq(Lottery::getProjectId, projectId).eq(Lottery::getStatus,0));
		if(Func.isNotEmpty(lotteryList)){
			Lottery lottery = lotteryList.get(RandomUtils.nextInt(0, lotteryList.size() - 1));
			lottery.setStatus(1);
			lottery.setLotteryTime(LocalDateTime.now());
			if(iLotteryService.updateById(lottery)){
				return R.data(LotteryWrapper.build().entityVO(lottery), "Success!");
			}else{
				return R.status(false);
			}
		}else {
			return R.fail("No users participated in the lottery!");
		}

	}

	/**
	 * 设为选房
	 */
	@PostMapping("/setStatusSelection")
	@ApiOperation(value = "设置为状态为选房", notes = "传入lotteryId")
	public R setStatusSelection(@ApiParam(value = "主键集合", required = true) @RequestParam String lotteryId) {
		//  1.判断该项目是否有状态为5 选房中的用户，如果有则报错
		Lottery lottery = iLotteryService.getById(lotteryId);
		List<Lottery> list = iLotteryService.list(new LambdaQueryWrapper<Lottery>()
			.eq(Lottery::getProjectId, lottery.getProjectId()).eq(Lottery::getStatus, 5));
		if(Func.isNotEmpty(list)){
			throw new IllegalStateException("Setting status failed! There are already users selecting rooms!");
		}
		//  2.将当前数据状态设置为5 选房中
		lottery.setStatus(5);
		Integer sequence = lottery.getSequence();
		if(1 == sequence){
			lottery.setFirstSelectionTime(LocalDateTime.now());
		}
		if(2 == sequence){
			lottery.setSecondSelectionTime(LocalDateTime.now());
		}
		return R.status(iLotteryService.updateById(lottery));
	}

	/**
	 * 设为等待选房
	 */
	@PostMapping("/setStatusWaiting")
	@ApiOperation(value = "设置为状态为等待选房", notes = "传入lotteryId")
	public R setStatusWaiting(@ApiParam(value = "主键集合", required = true) @RequestParam String lotteryId) {
		//  1.判断该项目是否有状态为2和3 等待选房的用户，如果有则报错
		Lottery lottery = iLotteryService.getById(lotteryId);
		List<Lottery> list = iLotteryService.list(new LambdaQueryWrapper<Lottery>()
			.eq(Lottery::getProjectId, lottery.getProjectId())
			.in(Lottery::getStatus, 2, 3));
		if(Func.isNotEmpty(list)){
			throw new IllegalStateException("Setting status failed! There are already users waiting for room selection!");
		}
		//  2.将当前数据状态设置为2 等待选房
		lottery.setStatus(2);
		return R.status(iLotteryService.updateById(lottery));
	}

	/**
	 * 设为选房失败
	 */
	@PostMapping("/setStatusFail")
	@ApiOperation(value = "设置为选房失败", notes = "传入lotteryId")
	public R setStatusFail(@ApiParam(value = "主键集合", required = true) @RequestParam String lotteryId) {
		//  1.判断该条数据状态是否为5 选房中，否则报错
		Lottery lottery = iLotteryService.getById(lotteryId);
		if(Func.isNotEmpty(lottery)){
			Integer lotteryStatus = lottery.getStatus();
			if(5 != lottery.getStatus()){
				throw new IllegalStateException("Setting status failed! Not a user in the room selection!");
			}
		}
		//  2.将当前数据状态设置为7 选房失败
		lottery.setStatus(7);
		return R.status(iLotteryService.updateById(lottery));
	}

	/**
	 * 手动输入中奖
	 */
	@PostMapping("/lotteryAdd")
	@ApiOperation(value = "新增中奖人（手动）", notes = "传入lotteryVO")
	public R lotteryAdd(@Valid @RequestBody LotteryVO lotteryVO) {
		//  1.根据eoino和projectId查询判断该条数据status是否为0 如果不为0 则报错不允许重复中签，如果不存在该信息则报错eoino错误
		Lottery lottery = iLotteryService.getOne(new LambdaQueryWrapper<Lottery>()
			.eq(Lottery::getProjectId, lotteryVO.getProjectId())
			.eq(Lottery::getEoiNo, lotteryVO.getEoiNo()));
		if(Func.isEmpty(lottery)){
			throw new NullPointerException("Eoino error, lottery number does not exist!");
		}else if(0<lottery.getStatus()){
			throw new IllegalStateException("Repeated winning is not allowed!");
		}else{
			//  2.将该条数据status改为1，中签时间改为当前时间
			lottery.setStatus(1);
			lottery.setLotteryTime(LocalDateTime.now());
			return R.status(iLotteryService.updateById(lottery));
		}
	}

}
