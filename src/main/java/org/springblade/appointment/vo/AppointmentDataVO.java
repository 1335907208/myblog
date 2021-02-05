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
package org.springblade.appointment.vo;

import io.swagger.annotations.ApiModelProperty;
import org.springblade.appointment.entity.AppointmentData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import org.springblade.appointment.entity.AppointmentScheduling;
import org.springblade.appointment.entity.AppointmentShiftData;

/**
 * 预约看房预约单视图实体类
 *
 * @author Blade
 * @since 2021-01-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AppointmentDataVO对象", description = "预约看房预约单")
public class AppointmentDataVO extends AppointmentData {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "查询时间范围-开始时间")
	private String startDate;

	@ApiModelProperty(value = "查询时间范围-结束时间")
	private String endDate;

	@ApiModelProperty(value = "班次名称")
	private String shiftName;

	@ApiModelProperty(value = "项目名称")
	private String projectName;

	@ApiModelProperty(value = "场次信息")
	private AppointmentShiftData appointmentShiftData;

	@ApiModelProperty(value = "排班信息")
	private AppointmentScheduling appointmentScheduling;

	@ApiModelProperty(value = "场次开始时间")
	private String startTime;

	@ApiModelProperty(value = "场次结束时间")
	private String endTime;

	@ApiModelProperty(value = "场次时间区间")
	private String dateTime;

}
