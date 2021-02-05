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
package org.springblade.purchaseIntent.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.purchaseIntent.entity.PurchaseIntent;
import org.springblade.purchaseIntent.vo.PurchaseIntentVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 服务类
 *
 * @author Blade
 * @since 2020-10-09
 */
public interface IPurchaseIntentService extends BaseService<PurchaseIntent> {
	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param purchaseIntent
	 * @return
	 */
	IPage<PurchaseIntentVO> selectPurchaseIntentPage(IPage<PurchaseIntentVO> page, PurchaseIntentVO purchaseIntent);

	/**
	 * 自定义列表
	 *
	 * @param purchaseIntent
	 * @return
	 */
	List<PurchaseIntentVO> list(PurchaseIntentVO purchaseIntent);

	/**
	 * 自定义新增
	 * @param purchaseIntent
	 * @return
	 */
	boolean save(PurchaseIntentVO purchaseIntent);

	/**
	 * 自定义修改
	 * @param purchaseIntent
	 * @return
	 */
	boolean updateById(PurchaseIntentVO purchaseIntent);

	Integer mccConfirm(PurchaseIntent one,boolean b);

	boolean orderSave(@Valid PurchaseIntentVO purchaseIntent);

	/**
	 * 中介公司意向统计
	 * @param intentVO
	 * @return
	 */
    List<PurchaseIntentVO> statisticVO(IPage<PurchaseIntentVO> page,@Valid PurchaseIntentVO intentVO);

}
