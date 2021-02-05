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

import org.springblade.client.eneity.Unit;
import org.springblade.client.service.impl.UnitServiceImpl;
import org.springblade.client.wrapper.UnitWrapper;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.service.impl.UserServiceImpl;
import org.springblade.project.entity.ProjectInfo;
import org.springblade.project.service.impl.ProjectInfoServiceImpl;
import org.springblade.purchaseIntent.entity.PurchaseIntent;
import org.springblade.purchaseIntent.entity.PurchaseOrder;
import org.springblade.purchaseIntent.service.impl.PurchaseIntentServiceImpl;
import org.springblade.purchaseIntent.vo.PurchaseOrderVO;
import org.springblade.webuser.entity.UserBuyer;
import org.springblade.webuser.service.impl.UserBuyerServiceImpl;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-09
 */
public class PurchaseOrderWrapper extends BaseEntityWrapper<PurchaseOrder,PurchaseOrderVO>{

	public static PurchaseOrderWrapper build(){
		return new PurchaseOrderWrapper();
	}

	@Override
	public PurchaseOrderVO entityVO(PurchaseOrder purchaseOrder) {
		PurchaseOrderVO purchaseOrderVO = BeanUtil.copy(purchaseOrder, PurchaseOrderVO.class);
		if(Func.isNotEmpty(purchaseOrderVO)){
			//添加关联application
			PurchaseIntent purchaseIntent = SpringUtil.getBean(PurchaseIntentServiceImpl.class).getById(purchaseOrderVO.getApplicationId());
			purchaseOrderVO.setPurchaseIntentVO(PurchaseIntentWrapper.build().entityVO(purchaseIntent));
			//添加关联unit
			Unit unit = SpringUtil.getBean(UnitServiceImpl.class).getById(purchaseOrderVO.getUnitId());
			purchaseOrderVO.setUnitVO(UnitWrapper.build().entityVO(unit));
			//添加关联project
			ProjectInfo projectInfo = SpringUtil.getBean(ProjectInfoServiceImpl.class).getById(purchaseOrderVO.getProjectId());
			purchaseOrderVO.setProjectInfo(projectInfo);
			//添加关联用户
			UserBuyer userBuyer = SpringUtil.getBean(UserBuyerServiceImpl.class).getById(purchaseOrderVO.getCreateUser());
			purchaseOrderVO.setUserBuyer(userBuyer);
			//关联意向
//			PurchaseIntentServiceImpl bean = SpringUtil.getBean(PurchaseIntentServiceImpl.class);
			PurchaseIntent application = SpringUtil.getBean(PurchaseIntentServiceImpl.class).getById(purchaseOrderVO.getApplicationId());
			purchaseOrderVO.setApplication(application);
		}
		return purchaseOrderVO;
	}
}
