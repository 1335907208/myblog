package org.springblade.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.springblade.client.eneity.Unit;
import org.springblade.client.service.IUnitService;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.lottery.entity.Lottery;
import org.springblade.lottery.service.ILotteryService;
import org.springblade.lottery.wrapper.LotteryWrapper;
import org.springblade.purchaseIntent.entity.PurchaseIntent;
import org.springblade.purchaseIntent.entity.PurchaseOrder;
import org.springblade.purchaseIntent.service.IPurchaseIntentService;
import org.springblade.purchaseIntent.service.IPurchaseOrderService;
import org.springblade.purchaseIntent.vo.PurchaseOrderVO;
import org.springblade.purchaseIntent.wrapper.PurchaseIntentWrapper;
import org.springblade.purchaseIntent.wrapper.PurchaseOrderWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("client")
public class lotteryController {

	IUnitService iUnitService;
	ILotteryService iLotteryService;
	IPurchaseIntentService iPurchaseIntentService;
	IPurchaseOrderService iPurchaseOrderService;

	/**
	 * 开盘详情
	 *
	 */
	@RequestMapping("/opening/detail")
	public R detail(HttpServletRequest request,@RequestParam String projectId) {
		HashMap<String, Object> map = new HashMap<>();
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
		//3.根据用户session查出用户的意向信息（house_application表，包括unit表关联数据）注意，用户可能是buyer也可能是agent且用户有可能没有意向
		//用户的意向信息（house_application表，包括unit表关联数据）
		HttpSession session = request.getSession();
		Long userid = Func.toLong(session.getAttribute("userid"));
		PurchaseIntent application = iPurchaseIntentService.getOne(new LambdaQueryWrapper<PurchaseIntent>()
			.eq(PurchaseIntent::getCreateUser, userid)
			.eq(PurchaseIntent::getProjectId,projectId), false);
		if(Func.isNotEmpty(application)){
			map.put("application", PurchaseIntentWrapper.build().entityVO(application));
		}
		//4.通过项目ID，查出抽奖表已抽中人员信息（status > 0）（house_application_lottery表
		//抽奖表已抽中人员信息
		List<Lottery> lotteryList = iLotteryService.list(new LambdaQueryWrapper<Lottery>()
			.eq(Lottery::getProjectId,projectId)
			.gt(Lottery::getStatus, 0));
		if(Func.isNotEmpty(lotteryList)){
			map.put("lotteryVOList",LotteryWrapper.build().listVO(lotteryList));
		}
		return R.data(map,"成功！");
	}

	/**
	 * 中奖列表
	 *
	 */
//	@RequestMapping("/opening/lottery")
//	public R lotteryList(String projectId) {
//		//1.通过项目ID，查出抽奖表已抽中人员信息（status > 0）（house_application_lottery表）
//
//		return R.data(0,"成功！");
//	}

	/**
	 * 购房列表
	 *
	 */
	@RequestMapping("/opening/buy")
	public R buyList(@RequestParam String projectId) {
		//1.通过项目ID，本次开盘已成功购房信息（house_order表 status=9）
		List<PurchaseOrder> list = iPurchaseOrderService.list(new LambdaQueryWrapper<PurchaseOrder>()
			.eq(PurchaseOrder::getProjectId, projectId).eq(PurchaseOrder::getStatus, 9));
		return R.data(PurchaseOrderWrapper.build().listVO(list),"成功！");
	}

	/**
	 * 自己当前抽奖状态
	 *
	 */
	@RequestMapping("/opening/myStatus")
	public R myStatus(HttpServletRequest request) {
		//  1.根据session中的用户ID 查询当前用户的抽奖信息（house_application_lottery表）
		HttpSession session = request.getSession();
		Long userid = Func.toLong(session.getAttribute("userid"));
		Lottery lottery = iLotteryService.getOne(new LambdaQueryWrapper<Lottery>()
			.eq(Lottery::getUserId, userid), false);
		return R.data(LotteryWrapper.build().entityVO(lottery),"成功！");
	}

	/**
	 * 更新准备就绪状态
	 *
	 */
	@RequestMapping("/opening/ready")
	public R ready(HttpServletRequest request) {
		//  1.判断此用户是否为等待选房状态（status = 2）用户，如果不是返回异常
		HttpSession session = request.getSession();
		Long userid = Func.toLong(session.getAttribute("userid"));
		Lottery lottery = iLotteryService.getOne(new LambdaQueryWrapper<Lottery>()
			.eq(Lottery::getUserId, userid)
			.eq(Lottery::getStatus,2), true);
		//  2.更新此用户的状态status为3（house_application_lottery表）
		lottery.setStatus(3);
		if(iLotteryService.updateById(lottery)){
			return R.data(LotteryWrapper.build().entityVO(lottery),"成功！");
		}else{
			return R.fail("失败!");
		}
	}


	/**
	 * 申请二次选房
	 *
	 */
	@RequestMapping("/opening/applySecondSelection")
	public R applySecondSelection(HttpServletRequest request) {
		//  1.判断此用户是否为选房失败用户（status = 7 且 选房次数sequence为1），如果不是返回异常
		HttpSession session = request.getSession();
		Long userid = Func.toLong(session.getAttribute("userid"));
		Lottery lottery = iLotteryService.getOne(new LambdaQueryWrapper<Lottery>()
			.eq(Lottery::getUserId, userid)
			.eq(Lottery::getSequence,1)
			.eq(Lottery::getStatus,7), true);
		//  2.更新此用户的状态status为6（house_application_lottery表）
		lottery.setStatus(6);
		//  次数设置为2
		lottery.setSequence(2);
		lottery.setSecondSelectionApplyTime(LocalDateTime.now());
		if(iLotteryService.updateById(lottery)){
			return R.data(LotteryWrapper.build().entityVO(lottery),"成功！");
		}else{
			return R.fail("失败!");
		}
	}

	/**
	 * 购房
	 *
	 */
	@RequestMapping("/opening/buyLottery")
	public R buy(HttpServletRequest request, PurchaseOrderVO purchaseOrderVO) {
		//  1.判断此unit是否可购买，如果不可购买返回失败
		Unit unit = iUnitService.getOne(new LambdaQueryWrapper<Unit>()
			.eq(Unit::getId, purchaseOrderVO.getUnitId())
			.eq(Unit::getSaleStatus, 0),false);
		if(Func.isEmpty(unit)){
			return R.fail("不可购买!");
		}else{
			unit.setSaleStatus(1);
			if(!iUnitService.updateById(unit)){
				return R.fail("Unit购买失败!");
			}
		}
		//  2.通过session判断lottery表此用户状态是否为5选房中，如果不是返回失败
		Long userid = Func.toLong(request.getSession().getAttribute("userid"));
		Lottery lottery = iLotteryService.getOne(new LambdaQueryWrapper<Lottery>()
			.eq(Lottery::getUserId, userid)
			.eq(Lottery::getStatus, 5), false);
		if(Func.isEmpty(lottery)){
			return R.fail("用户状态不可购买!");
		}
		// TODO (待甲方提供接口) 向甲方购房系统提交购房数据，返回成功则继续，否则返回失败
		//  3.在order 表新增一条数据，status=9
		purchaseOrderVO.setCreateUser(lottery.getUserId());
		purchaseOrderVO.setCreateUserType(lottery.getUserType());
		purchaseOrderVO.setApplicationId(lottery.getApplicationId());
		purchaseOrderVO.setCreateTime(DateUtil.now());
		purchaseOrderVO.setProjectId(lottery.getProjectId());
		purchaseOrderVO.setEoiNo(lottery.getEoiNo());
		purchaseOrderVO.setStatus(9);
		if(!iPurchaseOrderService.save(purchaseOrderVO)){
			return R.fail("新增购买失败！");
		}
		//  4.修改lottery表对应数据状态为9 购买成功
		lottery.setStatus(9);
		if(iLotteryService.updateById(lottery)){
			return R.success("购买成功!");
		}
		return R.fail("购买失败！");
	}

}
