/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.purchaseIntent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.purchaseIntent.entity.PurchaseIntent;
import org.springblade.purchaseIntent.entity.PurchaseIntentUnit;
import org.springblade.purchaseIntent.entity.PurchaseIntentUser;
import org.springblade.purchaseIntent.entity.PurchaseOrder;
import org.springblade.purchaseIntent.mapper.PurchaseIntentMapper;
import org.springblade.purchaseIntent.service.IPurchaseIntentService;
import org.springblade.purchaseIntent.service.IPurchaseIntentUnitService;
import org.springblade.purchaseIntent.service.IPurchaseIntentUserService;
import org.springblade.purchaseIntent.service.IPurchaseOrderService;
import org.springblade.purchaseIntent.vo.PurchaseIntentUnitVO;
import org.springblade.purchaseIntent.vo.PurchaseIntentVO;
import org.springblade.purchaseIntent.wrapper.PurchaseIntentWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2020-09-29
 */
@Service
public class PurchaseIntentServiceImpl extends BaseServiceImpl<PurchaseIntentMapper,PurchaseIntent> implements IPurchaseIntentService {

	@Autowired
	IPurchaseIntentUserService iPurchaseIntentUserService;
	@Autowired
	IPurchaseIntentUnitService iPurchaseIntentUnitService;
	@Autowired
	IPurchaseOrderService iPurchaseOrderService;

	@Override
	public IPage<PurchaseIntentVO> selectPurchaseIntentPage(IPage<PurchaseIntentVO> page, PurchaseIntentVO purchaseIntent) {
		List<PurchaseIntent> purchaseIntentList = baseMapper.selectPurchaseIntentPage(page, purchaseIntent);
		return page.setRecords(PurchaseIntentWrapper.build().listVO(purchaseIntentList));
	}

	@Override
	public List<PurchaseIntentVO> list(PurchaseIntentVO purchaseIntent) {
		LambdaQueryWrapper<PurchaseIntent> wrapper = new LambdaQueryWrapper<>();
		if(Func.isNotEmpty(purchaseIntent.getCreateUser())){
			wrapper.eq(PurchaseIntent::getCreateUser,purchaseIntent.getCreateUser());
		}
		List<PurchaseIntent> purchaseIntents = baseMapper.selectList(wrapper);
		List<PurchaseIntentVO> purchaseIntentVOS = PurchaseIntentWrapper.build().listVO(purchaseIntents);
		return purchaseIntentVOS;
	}

	@Override
	public boolean save(PurchaseIntentVO purchaseIntent) {
		Date now = DateUtil.now();
		purchaseIntent.setCreateTime(now);
		purchaseIntent.setUpdateTime(now);
		List<PurchaseIntentUnitVO> unitVOList = purchaseIntent.getPurchaseIntentUnitVOList();
		if(Func.isNotEmpty(unitVOList)){
			purchaseIntent.setBulkPurchase(unitVOList.size() > 1?1:0);//批量采购 0=否 1=是 默认否
		}
        List<PurchaseIntentUser> purchaseIntentUserList = purchaseIntent.getPurchaseIntentUserList();
        if(Func.isNotEmpty(purchaseIntentUserList)){
            checkDuplicate(purchaseIntent);
        }
        if(SqlHelper.retBool(baseMapper.insert(purchaseIntent))){
            Long purchaseIntentId = purchaseIntent.getId();
            Long projectId = purchaseIntent.getProjectId();
            //批量存储买方
			if(Func.isNotEmpty(purchaseIntentUserList)){
				for (PurchaseIntentUser buyer : purchaseIntentUserList) {
					buyer.setApplicationId(purchaseIntentId);
				}
			}
			//批量存储单元
			List<PurchaseIntentUnit> purchaseIntentUnitList = unitList(purchaseIntentId, projectId, unitVOList);
			return iPurchaseIntentUserService.saveBatch(purchaseIntentUserList)
				&& iPurchaseIntentUnitService.saveBatch(purchaseIntentUnitList);
		}
		return false;
	}

	@Override
	public boolean updateById(PurchaseIntentVO purchaseIntent) {
		purchaseIntent.setUpdateTime(DateUtil.now());
		List<PurchaseIntentUnitVO> unitVOList = purchaseIntent.getPurchaseIntentUnitVOList();
		if(Func.isNotEmpty(unitVOList)){
			purchaseIntent.setBulkPurchase(unitVOList.size() > 1?1:0);//批量采购 0=否 1=是 默认否
		}
        List<PurchaseIntentUser> purchaseIntentUserList = purchaseIntent.getPurchaseIntentUserList();
		if(Func.isNotEmpty(purchaseIntentUserList)){
            checkDuplicate(purchaseIntent);
        }
        if(SqlHelper.retBool(baseMapper.updateById(purchaseIntent))){
            Long purchaseIntentId = purchaseIntent.getId();
            Long projectId = purchaseIntent.getProjectId();
            iPurchaseIntentUserService.remove(new LambdaQueryWrapper<PurchaseIntentUser>()
				.eq(PurchaseIntentUser::getApplicationId, purchaseIntentId));
            iPurchaseIntentUnitService.remove(new LambdaQueryWrapper<PurchaseIntentUnit>()
				.eq(PurchaseIntentUnit::getApplicationId, purchaseIntentId));
            //批量存储买方
			if(Func.isNotEmpty(purchaseIntentUserList)){
				for (PurchaseIntentUser buyer : purchaseIntentUserList) {
					buyer.setApplicationId(purchaseIntentId);
				}
			}
			//批量存储单元
			List<PurchaseIntentUnit> purchaseIntentUnitList = unitList(purchaseIntentId, projectId, unitVOList);
			return iPurchaseIntentUserService.saveBatch(purchaseIntentUserList)
					&& iPurchaseIntentUnitService.saveBatch(purchaseIntentUnitList);
		}
		return false;
	}

    /**
     * 判断重复
     */
    private void checkDuplicate(PurchaseIntentVO purchaseIntent) {
        //判断重复意向
        List<PurchaseIntentUser> UserList = purchaseIntent.getPurchaseIntentUserList();
        if(Func.isNotEmpty(UserList)){
			Set<String> noiSet = UserList.stream().map(PurchaseIntentUser::getPassportNo).collect(Collectors.toSet());
			Set<String> nameSet = UserList.stream().map(PurchaseIntentUser::getName).collect(Collectors.toSet());
			Set<String> contactSet = UserList.stream().map(PurchaseIntentUser::getContact).collect(Collectors.toSet());
			List<PurchaseIntentUser> list = iPurchaseIntentUserService.list(
                    new LambdaQueryWrapper<PurchaseIntentUser>().or()
							.in(PurchaseIntentUser::getPassportNo, noiSet)
							.in(PurchaseIntentUser::getName,nameSet)
							.in(PurchaseIntentUser::getContact,contactSet)
            );
            if(Func.isNotEmpty(list)){
//                List<Long> intentIdList = list.stream().map(u -> u.getApplicationId()).collect(Collectors.toList());
//                List<PurchaseIntent> intentList = baseMapper.selectBatchIds(intentIdList);
//                intentList.forEach((PurchaseIntent intent) -> {
//                    intent.setDuplicate(1);
//                    baseMapper.updateById(intent);
//                });
                purchaseIntent.setDuplicate(1);
            }
        }
    }

	private List<PurchaseIntentUnit> unitList(Long purchaseIntentId, Long projectId, List<PurchaseIntentUnitVO> purchaseIntentUnitVOList) {
		List<PurchaseIntentUnit> purchaseIntentUnitList = new ArrayList<>();
		if(Func.isNotEmpty(purchaseIntentUnitVOList)){
            for (PurchaseIntentUnitVO unitVO : purchaseIntentUnitVOList) {
                unitVO.setApplicationId(purchaseIntentId);
                unitVO.setProjectId(projectId);
                purchaseIntentUnitList.add(BeanUtil.copy(unitVO,PurchaseIntentUnit.class));
            }
        }
        return purchaseIntentUnitList;
	}

	@Override
	public Integer mccConfirm(PurchaseIntent one,boolean b) {
		if(b){
			PurchaseIntent intent = baseMapper.selectOne(
				new LambdaQueryWrapper<PurchaseIntent>()
					.eq(PurchaseIntent::getProjectId, one.getProjectId())
					.orderByDesc(PurchaseIntent::getEoiNo)
					.last("limit 1")
			);
//			Integer integer = baseMapper.selectMaxEoi(one);
			String s = Func.toStr(Func.isEmpty(intent) ? 10000 : 10001 + Func.toInt(intent.getEoiNo()) );
			one.setEoiNo(s.substring(1, s.length()));
			one.setStatus(9);
		}else{
			one.setStatus(7);
		}
		return baseMapper.updateById(one);
	}

	@Override
	public boolean orderSave(@Valid PurchaseIntentVO purchaseIntent) {
		if(this.save(purchaseIntent)){
			PurchaseOrder purchaseOrder = new PurchaseOrder();
			purchaseOrder.setApplicationId(purchaseIntent.getId());
			purchaseOrder.setCreateUserType(purchaseIntent.getCreateUserType());
			purchaseOrder.setCreateUser(purchaseIntent.getCreateUser());
			purchaseOrder.setProjectId(purchaseIntent.getProjectId());
			purchaseOrder.setUnitId(purchaseIntent.getPurchaseIntentUnitVOList().get(0).getUnitId());
			return iPurchaseOrderService.save(purchaseOrder);
		}
		return false;
	}

	@Override
	public List<PurchaseIntentVO> statisticVO(IPage<PurchaseIntentVO> page,@Valid PurchaseIntentVO intentVO) {
		List<PurchaseIntentVO> list = baseMapper.statistic(page,intentVO);
		page.setRecords(list);
		return list;
	}
}
