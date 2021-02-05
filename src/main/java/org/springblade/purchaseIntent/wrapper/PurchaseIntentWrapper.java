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
package org.springblade.purchaseIntent.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springblade.modules.agency.entity.Agency;
import org.springblade.modules.agency.service.impl.AgencyServiceImpl;
import org.springblade.project.entity.ProjectInfo;
import org.springblade.project.service.IProjectInfoService;
import org.springblade.purchaseIntent.entity.PurchaseIntent;
import org.springblade.purchaseIntent.entity.PurchaseIntentUnit;
import org.springblade.purchaseIntent.entity.PurchaseIntentUser;
import org.springblade.purchaseIntent.service.impl.PurchaseIntentUnitServiceImpl;
import org.springblade.purchaseIntent.service.impl.PurchaseIntentUserServiceImpl;
import org.springblade.purchaseIntent.vo.PurchaseIntentVO;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.webuser.entity.UserAgent;
import org.springblade.webuser.service.impl.UserAgentServiceImpl;

import java.util.List;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-09
 */
public class PurchaseIntentWrapper extends BaseEntityWrapper<PurchaseIntent,PurchaseIntentVO>{

	public static PurchaseIntentWrapper build(){
		return new PurchaseIntentWrapper();
	}

	@Override
	public PurchaseIntentVO entityVO(PurchaseIntent purchaseIntent) {
		PurchaseIntentVO purchaseIntentVO = BeanUtil.copy(purchaseIntent, PurchaseIntentVO.class);
		if(Func.isNotEmpty(purchaseIntentVO)){
			//关联项目
			Long projectId = purchaseIntent.getProjectId();
			ProjectInfo projectInfo = SpringUtil.getBean(IProjectInfoService.class).getById(projectId);
			purchaseIntentVO.setProjectInfo(projectInfo);
			//关联查询单元
			List<PurchaseIntentUnit> purchaseIntentUnitList = SpringUtil.getBean(PurchaseIntentUnitServiceImpl.class)
				.list(new LambdaQueryWrapper<PurchaseIntentUnit>()
					.eq(PurchaseIntentUnit::getApplicationId, purchaseIntentVO.getId()));
			purchaseIntentVO.setPurchaseIntentUnitVOList(PurchaseIntentUnitWrapper.build().listVO(purchaseIntentUnitList));
			//关联查询买家
			List<PurchaseIntentUser> purchaseIntentUserList = SpringUtil.getBean(PurchaseIntentUserServiceImpl.class)
				.list(new LambdaQueryWrapper<PurchaseIntentUser>()
					.eq(PurchaseIntentUser::getApplicationId, purchaseIntentVO.getId()));
			purchaseIntentVO.setPurchaseIntentUserList(purchaseIntentUserList);
			//关联查询agentUser
            if(Func.isNotEmpty(purchaseIntentVO.getAgentId())){
				UserAgent agent = SpringUtil.getBean(UserAgentServiceImpl.class).getById(purchaseIntentVO.getAgentId());
                purchaseIntentVO.setAgentUser(agent);
            }
			//关联查询agency
			if(Func.isNotEmpty(purchaseIntentVO.getAgency())){
				Agency agency = SpringUtil.getBean(AgencyServiceImpl.class).getById(purchaseIntentVO.getAgency());
				purchaseIntentVO.setAgencyUser(agency);
			}
		}
		return purchaseIntentVO;
	}
}
