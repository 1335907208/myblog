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
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 预约看房预约单实体类
 *
 * @author Blade
 * @since 2021-01-25
 */
@Data
@TableName("house_appointment_data")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AppointmentData对象", description = "预约看房预约单")
public class AppointmentData extends BaseEntity {

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
     * 排班ID
     */
    @ApiModelProperty(value = "排班ID")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long schedulingId;
    /**
     * 班次ID
     */
    @ApiModelProperty(value = "班次ID")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long shiftDataId;
    /**
     * 预约日期
     */
    @ApiModelProperty(value = "预约日期")
    private LocalDate date;
    /**
     * 参观人数
     */
    @ApiModelProperty(value = "参观人数")
    private Integer applyVistorNum;
    /**
     * 预约人姓名
     */
    @ApiModelProperty(value = "预约人姓名")
    private String name;
    /**
     * 预约人电话
     */
    @ApiModelProperty(value = "预约人电话")
    private String tel;
    /**
     * 预约人邮箱
     */
    @ApiModelProperty(value = "预约人邮箱")
    private String email;
    /**
     * 预约人NRIC后4位
     */
    @ApiModelProperty(value = "预约人NRIC后4位")
    private String nric;
    /**
     * 实际到访人员数量
     */
    @ApiModelProperty(value = "实际到访人员数量")
    private Integer enterNum;
    /**
     * Check In时间
     */
    @ApiModelProperty(value = "Check In时间")
    private LocalDateTime checkInTime;
    /**
     * Check Out时间
     */
    @ApiModelProperty(value = "Check Out时间")
    private LocalDateTime checkOutTime;
    /**
     * agent/buyer
     */
    @ApiModelProperty(value = "agent/buyer")
    private String userType;

}
