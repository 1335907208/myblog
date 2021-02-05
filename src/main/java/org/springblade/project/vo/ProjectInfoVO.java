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
package org.springblade.project.vo;

import org.springblade.project.entity.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * 项目信息表视图实体类
 *
 * @author Blade
 * @since 2020-06-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ProjectInfoVO对象", description = "项目信息表")
public class ProjectInfoVO extends ProjectInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 1.相册列表
	 */
	private List<ProjectGallery> projectGalleryList;

	/**
	 * 2.专家介绍
	 */
	private List<ProjectIntroduction> projectIntroductionList;

	/**
	 * 3.楼盘地图
	 */
	private List<ProjectSitePlanVO> projectSitePlanVOList;

	/**
	 * 4.楼层平面图列表
	 */
	private List<ProjectFloorPlan> projectFloorPlanList;

	/**
	 * 5.项目VR列表
	 */
	private List<ProjectVr> projectVrList;

	/**
	 * 6.项目360全景照片列表
	 */
	private List<ProjectAllview> projectAllviewList;

	/**
	 * 7.项目CCTV远程视频列表
	 */
	private List<ProjectCctv> projectCctvList;

	/**
	 * 8.项目媒体库列表
	 */
	private List<ProjectMedia> projectMediaList;

	/**
	 * 9.项目首页轮播图
	 */
	private List<ProjectShow>  projectShowList;


	/**
	 * 10.项目附近信息列表
	 */
	private List<ProjectNearby>  projectNearbyList;

	/**
	 * 11.项目的活动列表
	 */
	private List<ProjectEvent>  projectEventList;

}
