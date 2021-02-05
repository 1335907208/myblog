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
 * 项目楼盘地图热点信息表实体类
 *
 * @author Blade
 * @since 2020-07-01
 */
@Data
@TableName("house_project_site_plan_hot_pot")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ProjectSitePlanHotPot对象", description = "项目楼盘地图热点信息表")
public class ProjectSitePlanHotPot extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 项目楼层地图ID
     */
    @ApiModelProperty(value = "项目楼层地图ID")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 热点形状
     */
    @ApiModelProperty(value = "热点形状")
    private Integer shapeType;
    /**
     * 热点坐标
     */
    @ApiModelProperty(value = "热点坐标")
    private String coords;
    /**
     * 平图URL
     */
    @ApiModelProperty(value = "平图URL")
    private String url;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String remark;


}
