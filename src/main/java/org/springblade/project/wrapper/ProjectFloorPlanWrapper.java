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

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.project.entity.ProjectFloorPlan;
import org.springblade.project.vo.ProjectFloorPlanVO;

/**
 * 项目楼层平图表包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-07-01
 */
public class ProjectFloorPlanWrapper extends BaseEntityWrapper<ProjectFloorPlan, ProjectFloorPlanVO>  {

    public static ProjectFloorPlanWrapper build() {
        return new ProjectFloorPlanWrapper();
    }

	@Override
	public ProjectFloorPlanVO entityVO(ProjectFloorPlan projectFloorPlan) {
		ProjectFloorPlanVO projectFloorPlanVO = BeanUtil.copy(projectFloorPlan, ProjectFloorPlanVO.class);

		return projectFloorPlanVO;
	}

}
