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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.client.eneity.ProjectUnittype;
import org.springblade.client.eneity.Unit;
import org.springblade.client.service.IProjectUnittypeService;
import org.springblade.client.vo.UnitVO;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.SpringUtil;

/**
 * 项目全景照片表包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-18
 */
public class UnitWrapper extends BaseEntityWrapper<Unit, UnitVO>  {
	private static IProjectUnittypeService projectUnittypeService;
	static {
		projectUnittypeService= SpringUtil.getBean(IProjectUnittypeService.class);
	}
    public static UnitWrapper build() {
        return new UnitWrapper();
    }

	@Override
	public UnitVO entityVO(Unit unit) {
		UnitVO unitVO = BeanUtil.copy(unit, UnitVO.class);
		//户型图url
		ProjectUnittype unittype =projectUnittypeService.getOne(Wrappers.<ProjectUnittype>query().lambda().eq(ProjectUnittype::getId,unitVO.getTypeId()));
		unitVO.setTypeUrl(unittype.getTypeUrl());
		unitVO.setProjectUnittype(unittype);
		return unitVO;
	}

}
