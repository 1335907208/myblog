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
package org.springblade.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springblade.project.entity.ProjectUnits;
import org.springblade.project.vo.ProjectUnitsVO;
import org.springblade.project.mapper.ProjectUnitsMapper;
import org.springblade.project.service.IProjectUnitsService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 项目全景照片表 服务实现类
 *
 * @author Blade
 * @since 2020-07-28
 */
@Service
public class ProjectUnitsServiceImpl extends BaseServiceImpl<ProjectUnitsMapper, ProjectUnits> implements IProjectUnitsService {

	@Override
	public IPage<ProjectUnitsVO> selectProjectUnitsPage(IPage<ProjectUnitsVO> page, ProjectUnitsVO projectUnits) {
		return page.setRecords(baseMapper.selectProjectUnitsPage(page, projectUnits));
	}


}
