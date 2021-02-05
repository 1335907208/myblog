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

import org.springblade.project.entity.ProjectSitePlanHotPot;
import org.springblade.project.vo.ProjectSitePlanHotPotVO;
import org.springblade.project.mapper.ProjectSitePlanHotPotMapper;
import org.springblade.project.service.IProjectSitePlanHotPotService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 项目楼盘地图热点信息表 服务实现类
 *
 * @author Blade
 * @since 2020-07-01
 */
@Service
public class ProjectSitePlanHotPotServiceImpl extends BaseServiceImpl<ProjectSitePlanHotPotMapper, ProjectSitePlanHotPot> implements IProjectSitePlanHotPotService {

	@Override
	public IPage<ProjectSitePlanHotPotVO> selectProjectSitePlanHotPotPage(IPage<ProjectSitePlanHotPotVO> page, ProjectSitePlanHotPotVO projectSitePlanHotPot) {
		return page.setRecords(baseMapper.selectProjectSitePlanHotPotPage(page, projectSitePlanHotPot));
	}

}
