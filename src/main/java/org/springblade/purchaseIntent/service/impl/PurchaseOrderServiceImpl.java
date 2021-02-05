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

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.purchaseIntent.entity.PurchaseOrder;
import org.springblade.purchaseIntent.mapper.PurchaseOrderMapper;
import org.springblade.purchaseIntent.service.IPurchaseOrderService;
import org.springblade.purchaseIntent.vo.PurchaseOrderVO;
import org.springblade.purchaseIntent.wrapper.PurchaseOrderWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2020-10-09
 */
@Service
public class PurchaseOrderServiceImpl extends BaseServiceImpl<PurchaseOrderMapper,PurchaseOrder> implements IPurchaseOrderService {

	@Override
	public IPage<PurchaseOrderVO> selectPurchaseOrderPage(IPage<PurchaseOrderVO> page, PurchaseOrderVO purchaseOrder) {
		List<PurchaseOrder> purchaseOrders = baseMapper.selectPurchaseOrderPage(page, purchaseOrder);
		return page.setRecords(PurchaseOrderWrapper.build().listVO(purchaseOrders));
	}

}




