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
package org.springblade.client.service.impl;

import org.springblade.client.entity.UserJackpot;
import org.springblade.client.vo.UserJackpotVO;
import org.springblade.client.mapper.UserJackpotMapper;
import org.springblade.client.service.IUserJackpotService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2020-09-27
 */
@Service
public class UserJackpotServiceImpl extends BaseServiceImpl<UserJackpotMapper, UserJackpot> implements IUserJackpotService {

	@Override
	public IPage<UserJackpotVO> selectUserJackpotPage(IPage<UserJackpotVO> page, UserJackpotVO userJackpot) {
		return page.setRecords(baseMapper.selectUserJackpotPage(page, userJackpot));
	}

}
