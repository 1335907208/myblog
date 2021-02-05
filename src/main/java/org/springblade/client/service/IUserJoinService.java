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
package org.springblade.client.service;

import org.springblade.client.entity.UserJoin;
import org.springblade.client.vo.UserJoinVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * user_buyer 服务类
 *
 * @author Blade
 * @since 2020-09-30
 */
public interface IUserJoinService extends BaseService<UserJoin> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param userJoin
	 * @return
	 */
	IPage<UserJoinVO> selectUserJoinPage(IPage<UserJoinVO> page, UserJoinVO userJoin);

}
