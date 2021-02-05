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
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 预约看房排班表实体类
 *
 * @author Blade
 * @since 2021-01-25
 */
@Data
@TableName("house_appointment_scheduling")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AppointmentScheduling对象", description = "预约看房排班表")
public class AppointmentScheduling extends BaseEntity {

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
    private Long projectId;
    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private LocalDate date;
    /**
     * 班次ID
     */
    @ApiModelProperty(value = "班次ID")
    private Long shiftId;


}
