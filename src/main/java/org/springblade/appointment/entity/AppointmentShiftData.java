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

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import org.springblade.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 预约看房班次实体类
 *
 * @author Blade
 * @since 2021-01-25
 */
@Data
@TableName("house_appointment_shift_data")
@ApiModel(value = "AppointmentShiftData对象", description = "预约看房班次")
public class AppointmentShiftData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
  private Long id;
    /**
     * 班次id
     */
    @ApiModelProperty(value = "班次id")
    private Long shiftId;
    /**
     * 开始时间（格式HH:mm）
     */
    @ApiModelProperty(value = "开始时间（格式HH:mm）")
    private String startTime;
    /**
     * 结束时间（格式HH:mm）
     */
    @ApiModelProperty(value = "结束时间（格式HH:mm）")
    private String endTime;


}
