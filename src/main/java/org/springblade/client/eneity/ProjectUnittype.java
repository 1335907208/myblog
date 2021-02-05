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

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("house_project_unittype")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ProjectUnittype对象", description = "项目全景照片表")
public class ProjectUnittype extends BaseEntity {

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
    private Long projectId;
    /**
     * 户型名称
     */
    @ApiModelProperty(value = "户型名称")
    private String typeName;
    /**
     * 房间类型（匹配项目属性和查询BR）
     */
    @ApiModelProperty(value = "房间类型（匹配项目属性和查询BR）")
    private String bedroom;
    /**
     * 房屋面积(平米)
     */
    @ApiModelProperty(value = "房屋面积(平米)")
    private Integer floorAreaSqm;
    /**
     * 房屋面积(平方尺)
     */
    @ApiModelProperty(value = "房屋面积(平方尺)")
    private Integer floorAreaSqft;
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


}
