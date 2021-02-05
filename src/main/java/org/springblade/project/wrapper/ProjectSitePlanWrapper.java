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
package org.springblade.project.wrapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.modules.system.service.IDictService;
import org.springblade.project.entity.ProjectGallery;
import org.springblade.project.entity.ProjectSitePlan;
import org.springblade.project.entity.ProjectSitePlanHotPot;
import org.springblade.project.service.IProjectGalleryService;
import org.springblade.project.service.IProjectIntroductionService;
import org.springblade.project.service.IProjectSitePlanHotPotService;
import org.springblade.project.vo.ProjectSitePlanVO;

import java.util.List;

/**
 * 项目楼盘地图表包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-07-01
 */
public class ProjectSitePlanWrapper extends BaseEntityWrapper<ProjectSitePlan, ProjectSitePlanVO>  {

	private static IProjectSitePlanHotPotService projectSitePlanHotPotService;
	private static IDictService dictService;

	static {
		projectSitePlanHotPotService = SpringUtil.getBean(IProjectSitePlanHotPotService.class);
		dictService = SpringUtil.getBean(IDictService.class);

	}

    public static ProjectSitePlanWrapper build() {
        return new ProjectSitePlanWrapper();
    }

	@Override
	public ProjectSitePlanVO entityVO(ProjectSitePlan projectSitePlan) {
		ProjectSitePlanVO projectSitePlanVO = BeanUtil.copy(projectSitePlan, ProjectSitePlanVO.class);

		//加载地图热点配置信息
		//projectId 实际为【ProjectSitePlan】的ID
		List<ProjectSitePlanHotPot> list = projectSitePlanHotPotService.list(Wrappers.<ProjectSitePlanHotPot>query().lambda().eq(ProjectSitePlanHotPot::getProjectId,projectSitePlanVO.getId()));

		projectSitePlanVO.setProjectSitePlanHotPotList(list);


		projectSitePlanVO.setSitePlanTypeName( dictService.getValue("planType", Integer.valueOf(projectSitePlan.getPlanType().toString())));

		return projectSitePlanVO;
	}

}
