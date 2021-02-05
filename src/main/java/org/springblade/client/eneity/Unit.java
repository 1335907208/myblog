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
package org.springblade.client.eneity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目全景照片表实体类
 *
 * @author Blade
 * @since 2020-09-18
 */
@Data
@TableName("house_project_unit")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Unit对象", description = "项目全景照片表")
public class Unit extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 项目ID
     */
    @ApiModelProperty(value = "项目ID")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
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
     * 户型id
     */
    @ApiModelProperty(value = "户型id")
    private String typeId;
    /**
     * 售房状态（0：可卖，1：已选，2，当前选房人选中的）
     */
    @ApiModelProperty(value = "售房状态（0：可卖，1：已选，2，当前选房人选中的）")
    private Integer saleStatus;
    /**
     * 房屋状态（0：期房，1：现房）
     */
    @ApiModelProperty(value = "房屋状态（0：期房，1：现房）")
    private Integer houseStatus;
    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private BigDecimal price;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String remark;


}
