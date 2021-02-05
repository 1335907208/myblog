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
package org.springblade.lottery.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.lottery.entity.Lottery;
import org.springblade.lottery.vo.LotteryVO;
import org.springblade.purchaseIntent.entity.PurchaseIntent;
import org.springblade.purchaseIntent.service.impl.PurchaseIntentServiceImpl;
import org.springblade.webuser.entity.UserAgent;
import org.springblade.webuser.entity.UserBuyer;
import org.springblade.webuser.service.impl.UserAgentServiceImpl;
import org.springblade.webuser.service.impl.UserBuyerServiceImpl;

import java.time.LocalDateTime;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-09
 */
public class LotteryWrapper extends BaseEntityWrapper<Lottery,LotteryVO>{

	public static LotteryWrapper build(){
		return new LotteryWrapper();
	}

	@Override
	public LotteryVO entityVO(Lottery lottery) {
		LotteryVO lotteryVO = BeanUtil.copy(lottery, LotteryVO.class);
		if(Func.isNotEmpty(lotteryVO)){
			PurchaseIntent purchaseIntent = SpringUtil.getBean(PurchaseIntentServiceImpl.class).getById(lotteryVO.getApplicationId());
			lotteryVO.setApplication(purchaseIntent);
			if("buyer".equals(lotteryVO.getUserType())){
				UserBuyer userBuyer = SpringUtil.getBean(UserBuyerServiceImpl.class).getById(lotteryVO.getUserId());
				lotteryVO.setUserBuyer(userBuyer);
			}
			if("agent".equals(lotteryVO.getUserType())){
				UserAgent userAgent = SpringUtil.getBean(UserAgentServiceImpl.class).getById(lotteryVO.getUserId());
				lotteryVO.setUserAgent(userAgent);
			}
			lotteryVO.setCurrentTime(LocalDateTime.now());
		}
		return lotteryVO;
	}
}
