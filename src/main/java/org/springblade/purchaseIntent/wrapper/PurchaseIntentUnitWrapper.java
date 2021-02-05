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
import org.springblade.purchaseIntent.entity.PurchaseIntentUnit;
import org.springblade.purchaseIntent.vo.PurchaseIntentUnitVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-09
 */
public class PurchaseIntentUnitWrapper extends BaseEntityWrapper<PurchaseIntentUnit,PurchaseIntentUnitVO>{

	public static PurchaseIntentUnitWrapper build(){
		return new PurchaseIntentUnitWrapper();
	}

	@Override
	public PurchaseIntentUnitVO entityVO(PurchaseIntentUnit purchaseIntentUnit) {
		PurchaseIntentUnitVO purchaseIntentUnitVO = BeanUtil.copy(purchaseIntentUnit, PurchaseIntentUnitVO.class);
		if(Func.isNotEmpty(purchaseIntentUnitVO)){
			Unit unit = SpringUtil.getBean(UnitServiceImpl.class).getById(purchaseIntentUnit.getUnitId());
			purchaseIntentUnitVO.setUnitVO(UnitWrapper.build().entityVO(unit));
		}
		return purchaseIntentUnitVO;
	}
}
