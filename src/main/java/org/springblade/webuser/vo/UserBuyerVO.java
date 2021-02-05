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
package org.springblade.webuser.vo;

import org.springblade.webuser.entity.UserBuyer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * user_buyer视图实体类
 *
 * @author Blade
 * @since 2020-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserBuyerVO对象", description = "user_buyer")
public class UserBuyerVO extends UserBuyer {
	private static final long serialVersionUID = 1L;
	//一个用户在一个项目，只会有一个抽签号
	private String EOINo;
	//抽签号后四位
}
