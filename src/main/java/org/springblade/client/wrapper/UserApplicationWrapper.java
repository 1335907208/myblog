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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springblade.client.entity.UserApplication;
import org.springblade.client.vo.UserApplicationVO;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.project.entity.ProjectUnits;
import org.springblade.project.service.IProjectUnitsService;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-19
 */
public class UserApplicationWrapper extends BaseEntityWrapper<UserApplication, UserApplicationVO>  {

    public static UserApplicationWrapper build() {
        return new UserApplicationWrapper();
    }

	@Override
	public UserApplicationVO entityVO(UserApplication userApplication) {
		UserApplicationVO userApplicationVO = BeanUtil.copy(userApplication, UserApplicationVO.class);

		if(Func.isNotEmpty(userApplicationVO)){
			ProjectUnits projectUnits = SpringUtil.getBean(IProjectUnitsService.class)
				.getOne(new LambdaQueryWrapper<ProjectUnits>()
					.eq(ProjectUnits::getId, userApplicationVO.getProjectId()));
			userApplicationVO.setProjectUnits(projectUnits);
		}

		return userApplicationVO;
	}

}
