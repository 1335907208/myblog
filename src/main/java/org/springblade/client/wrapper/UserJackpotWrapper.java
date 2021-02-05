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
package org.springblade.client.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.client.entity.UserJackpot;
import org.springblade.client.vo.UserJackpotVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-27
 */
public class UserJackpotWrapper extends BaseEntityWrapper<UserJackpot, UserJackpotVO>  {

    public static UserJackpotWrapper build() {
        return new UserJackpotWrapper();
    }

	@Override
	public UserJackpotVO entityVO(UserJackpot userJackpot) {
		UserJackpotVO userJackpotVO = BeanUtil.copy(userJackpot, UserJackpotVO.class);

		return userJackpotVO;
	}

}
