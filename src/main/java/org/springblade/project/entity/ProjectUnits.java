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
 * 项目全景照片表实体类
 *
 * @author Blade
 * @since 2020-07-28
 */
@Data
@TableName("house_project_units")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ProjectUnits对象", description = "项目全景照片表")
public class ProjectUnits extends BaseEntity {

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
     * Block栋
     */
    @ApiModelProperty(value = "Block栋")
    private String block;
    /**
     * Floor层
     */
    @ApiModelProperty(value = "Floor层")
    private String floor;
    /**
     * Unit房
     */
    @ApiModelProperty(value = "Unit房")
    private String unit;
    /**
     * 户型
     */
    @ApiModelProperty(value = "户型名称")
    private String typeName;
    /**
     * 户型图URL
     */
    @ApiModelProperty(value = "户型图URL")
    private String typeUrl;
    /**
     * 户型楼书URL
     */
    @ApiModelProperty(value = "户型楼书URL")
    private String bookUrl;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String remark;

	/**
	 * 售房状态
	 */
	@ApiModelProperty(value = "售房状态")
	private Integer saleStatus;
	/**
	 * 房屋状态
	 */
	@ApiModelProperty(value = "房屋状态")
	private Integer houseStatus;
	/**
	 * 价格
	 */
	@ApiModelProperty(value = "价格")
	private Integer price;
	/**
	 * 卧室的类型（对应首页查询）
	 */
	@ApiModelProperty(value = "卧室的类型")
	private String bedrooms;
	/**
	 * 房间面积
	 */
	@ApiModelProperty(value = "房间面积")
	private Integer area;


}
