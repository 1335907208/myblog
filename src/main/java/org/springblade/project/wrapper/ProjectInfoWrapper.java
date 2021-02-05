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
import org.springblade.modules.system.service.IUserService;
import org.springblade.project.entity.*;
import org.springblade.project.service.*;
import org.springblade.project.vo.ProjectInfoVO;

import java.util.List;

/**
 * 项目信息表包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-06-27
 */
public class ProjectInfoWrapper extends BaseEntityWrapper<ProjectInfo, ProjectInfoVO>  {
	private static IProjectGalleryService projectGalleryService;
	private static IProjectIntroductionService projectIntroductionService;
	private static IProjectSitePlanService projectSitePlanService;
	private static IProjectFloorPlanService projectFloorPlanService;
	private static IProjectVrService projectVrService;
	private static IProjectAllviewService projectAllviewService;
	private static IProjectCctvService projectCctvService;
	private static IProjectMediaService projectMediaService;
	private static IProjectShowService projectShowService;
	private static IProjectNearbyService projectNearbyService;
	private static IProjectEventService projectEventService;


	static {
		projectGalleryService = SpringUtil.getBean(IProjectGalleryService.class);
		projectIntroductionService = SpringUtil.getBean(IProjectIntroductionService.class);
		projectSitePlanService = SpringUtil.getBean(IProjectSitePlanService.class);
		projectFloorPlanService = SpringUtil.getBean(IProjectFloorPlanService.class);
		projectVrService = SpringUtil.getBean(IProjectVrService.class);
		projectAllviewService = SpringUtil.getBean(IProjectAllviewService.class);
		projectCctvService = SpringUtil.getBean(IProjectCctvService.class);
		projectMediaService = SpringUtil.getBean(IProjectMediaService.class);
		projectShowService = SpringUtil.getBean(IProjectShowService.class);
		projectNearbyService = SpringUtil.getBean(IProjectNearbyService.class);
		projectEventService = SpringUtil.getBean(IProjectEventService.class);
	}

    public static ProjectInfoWrapper build() {
        return new ProjectInfoWrapper();
    }

	@Override
	public ProjectInfoVO entityVO(ProjectInfo projectInfo) {
		ProjectInfoVO projectInfoVO = BeanUtil.copy(projectInfo, ProjectInfoVO.class);

		//1.项目相册列表
		List<ProjectGallery> projectGalleryList = projectGalleryService.list(Wrappers.<ProjectGallery>query().lambda()
			.eq(ProjectGallery::getProjectId,projectInfoVO.getId())
			.orderByDesc(ProjectGallery::getPlayTime)
		);
		projectInfoVO.setProjectGalleryList(projectGalleryList);

		//2.专家介绍列表
		List<ProjectIntroduction> projectIntroductionList = projectIntroductionService.list(Wrappers.<ProjectIntroduction>query().lambda().eq(ProjectIntroduction::getProjectId,projectInfoVO.getId()));
		projectInfoVO.setProjectIntroductionList(projectIntroductionList);

		//3.楼盘地图列表
		List<ProjectSitePlan> projectSitePlanList = projectSitePlanService.list(Wrappers.<ProjectSitePlan>query().lambda().eq(ProjectSitePlan::getProjectId,projectInfoVO.getId()));
		projectInfoVO.setProjectSitePlanVOList(ProjectSitePlanWrapper.build().listVO(projectSitePlanList));

		//4.楼层平面图列表
		List<ProjectFloorPlan> projectFloorPlanList = projectFloorPlanService.list(Wrappers.<ProjectFloorPlan>query().lambda().eq(ProjectFloorPlan::getProjectId,projectInfoVO.getId()));
		projectInfoVO.setProjectFloorPlanList(projectFloorPlanList);

		//5.项目VR列表
		List<ProjectVr> projectVrList = projectVrService.list(Wrappers.<ProjectVr>query().lambda().eq(ProjectVr::getProjectId,projectInfoVO.getId()));
		projectInfoVO.setProjectVrList(projectVrList);

		//6.项目360全景照片列表
		List<ProjectAllview> projectAllviewList = projectAllviewService.list(Wrappers.<ProjectAllview>query().lambda().eq(ProjectAllview::getProjectId,projectInfoVO.getId()));
		projectInfoVO.setProjectAllviewList(projectAllviewList);

		//7.项目CCTV远程视频列表
		List<ProjectCctv> projectCctvList = projectCctvService.list(Wrappers.<ProjectCctv>query().lambda().eq(ProjectCctv::getProjectId,projectInfoVO.getId()));
		projectInfoVO.setProjectCctvList(projectCctvList);

		//8.项目媒体库列表
		List<ProjectMedia> projectMediaList = projectMediaService.list(Wrappers.<ProjectMedia>query().lambda().eq(ProjectMedia::getProjectId,projectInfoVO.getId()));
		projectInfoVO.setProjectMediaList(projectMediaList);

		//9.项目首页的轮播图
		List<ProjectShow> projectShowList = projectShowService.list(Wrappers.<ProjectShow>query().lambda().eq(ProjectShow::getProjectId,projectInfoVO.getId()));
		projectInfoVO.setProjectShowList(projectShowList);

		//10.项目的附近信息列表
		List<ProjectNearby> projectNearbyList = projectNearbyService.list(Wrappers.<ProjectNearby>query().lambda().eq(ProjectNearby::getProjectId,projectInfoVO.getId()));
		projectInfoVO.setProjectNearbyList(projectNearbyList);

		//11.项目活动的列表
		List<ProjectEvent> projectEventList = projectEventService.list(Wrappers.<ProjectEvent>query().lambda().eq(ProjectEvent::getProjectId,projectInfoVO.getId()));
		projectInfoVO.setProjectEventList(projectEventList);

		return projectInfoVO;
	}

}
