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

import org.springblade.appointment.entity.AppointmentScheduling;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 预约看房排班表视图实体类
 *
 * @author Blade
 * @since 2021-01-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AppointmentSchedulingVO对象", description = "预约看房排班表")
public class AppointmentSchedulingVO extends AppointmentScheduling {
	private static final long serialVersionUID = 1L;

	/**
	 * 班次名称
	 */
	private String shiftName;

	/**
	 * 场次开始时间
	 */
	private String startTime;

	/**
	 * 场次结束时间
	 */
	private String endTime;

	/**
	 * 场次id
	 */
	private String shiftDataId;

}
