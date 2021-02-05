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
package org.springblade.webuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.client.eneity.ResultCode;
import org.springblade.client.util.MaillUtill;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.api.IResultCode;
import org.springblade.core.tool.utils.Func;
import org.springblade.webuser.entity.UserBuyer;
import org.springblade.webuser.mapper.UserBuyerMapper;
import org.springblade.webuser.service.IUserBuyerService;
import org.springblade.webuser.vo.UserBuyerVO;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * user_buyer 服务实现类
 *
 * @author Blade
 * @since 2020-06-24
 */
@Service
public class UserBuyerServiceImpl extends BaseServiceImpl<UserBuyerMapper, UserBuyer> implements IUserBuyerService {

	@Override
	public IPage<UserBuyerVO> selectUserBuyerPage(IPage<UserBuyerVO> page, UserBuyerVO userBuyer) {
		return page.setRecords(baseMapper.selectUserBuyerPage(page, userBuyer));
	}

	@Override
	public IResultCode regist(UserBuyer userBuyer) {
		ResultCode rc=new ResultCode();
		//未激活
		final Integer zt = 0;
		//生成 验证码 code
		String code = UUID.randomUUID().toString().replace("-", "");
		userBuyer.setStatus(0);
		userBuyer.setCode(code);
		userBuyer.setEmailAddress(userBuyer.getUsername());
		try
		{
			new MaillUtill().send(userBuyer.getUsername(),code,"active");
			rc.setCode(baseMapper.insert(userBuyer));
		}
		catch (Exception e)
		{
			log.error("error:发送邮件失败");
			throw new RuntimeException("发送邮件失败");
		}
		return rc;
	}

	@Override
	public Integer active(String code) {
		UserBuyer userBuyer = baseMapper.selectOne(new QueryWrapper<UserBuyer>()
			.eq("code",code));
		if(Func.isNotEmpty(userBuyer)){
			if(userBuyer.getStatus() == 0){
				userBuyer.setStatus(1);
				return baseMapper.updateById(userBuyer);
			}else if(userBuyer.getStatus() == 1){
				return -1;
			}
		}
		return 0;
	}


}
