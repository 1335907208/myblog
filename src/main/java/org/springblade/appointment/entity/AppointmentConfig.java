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
package org.springblade.appointment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springblade.core.mp.base.BaseEntity;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.project.entity.ProjectInfo;

/**
 * 预约看房配置表实体类
 *
 * @author Blade
 * @since 2021-01-25
 */
@Data
@TableName("house_appointment_config")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AppointmentConfig对象", description = "预约看房配置表")
public class AppointmentConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 项目ID
     */
    @ApiModelProperty(value = "项目ID")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
    /**
     * 开始日期
     */
    @ApiModelProperty(value = "开始日期")
    private LocalDate startDate;
    /**
     * 结束日期
     */
    @ApiModelProperty(value = "结束日期")
    private LocalDate endDate;
    /**
     * 单次最大进入人数
     */
    @ApiModelProperty(value = "单次最大进入人数")
    private Integer maxEnter;
    /**
     * 单次预约最多人数
     */
    @ApiModelProperty(value = "单次预约最多人数")
    private Integer maxApply;

	/**
	 * 项目信息
	 */
	@ApiModelProperty(value = "项目信息")
	@TableField(exist = false)
    private ProjectInfo projectInfo;


}
