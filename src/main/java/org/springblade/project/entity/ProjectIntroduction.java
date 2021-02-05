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
package org.springblade.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目相册表实体类
 *
 * @author Blade
 * @since 2020-07-01
 */
@Data
@TableName("house_project_introduction")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ProjectIntroduction对象", description = "项目相册表")
public class ProjectIntroduction extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 项目ID
     */
    @ApiModelProperty(value = "项目ID")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
    /**
     * URL类型
     */
    @ApiModelProperty(value = "URL类型")
    private Long urlType;
    /**
     * 视频名称
     */
    @ApiModelProperty(value = "视频名称")
    private String name;
    /**
     * 视频URL
     */
    @ApiModelProperty(value = "视频URL")
    private String url;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String remark;

	/**
	 * 缩略图
	 */
	@ApiModelProperty(value = "缩略图")
	private String imageUrl;

	//	/**
//	 * 描述
//	 */
//	@ApiModelProperty(value = "是否推荐")
//	private Integer recommend;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;


}
