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
import org.springblade.webuser.entity.UserAgent;
import org.springblade.webuser.mapper.UserAgentMapper;
import org.springblade.webuser.service.IUserAgentService;
import org.springblade.webuser.vo.UserAgentVO;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * user_agent 服务实现类
 *
 * @author Blade
 * @since 2020-06-24
 */
@Service
public class UserAgentServiceImpl extends BaseServiceImpl<UserAgentMapper, UserAgent> implements IUserAgentService {

	@Override
	public IPage<UserAgentVO> selectUserAgentPage(IPage<UserAgentVO> page, UserAgentVO userAgent) {
		return page.setRecords(baseMapper.selectUserAgentPage(page, userAgent));
	}

	@Override
	public IResultCode regist(UserAgent userAgent) {
		ResultCode rc=new ResultCode();
		//未激活
		final Integer zt = 0;
		//生成 验证码 code
		String code = UUID.randomUUID().toString().replace("-", "");
		userAgent.setStatus(0);
		userAgent.setCode(code);
		try
		{
			new MaillUtill().send(userAgent.getUsername(),code,"agentActive");
			rc.setCode(baseMapper.insert(userAgent));
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
		UserAgent userAgent=baseMapper.selectOne(new QueryWrapper<UserAgent>().eq("code",code));
		if(Func.isNotEmpty(userAgent)){
			if(userAgent.getStatus() == 0){
				//正常激活,返回激活数量
				userAgent.setStatus(1);
				return baseMapper.updateById(userAgent);
			}else if(userAgent.getStatus() == 1){
				//已激活返回-1
				return -1;
			}
		}
		//激活失败返回0
		return 0;
	}


}
