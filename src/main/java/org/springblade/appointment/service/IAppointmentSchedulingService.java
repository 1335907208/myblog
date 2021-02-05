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
package org.springblade.appointment.service;

import org.springblade.appointment.entity.AppointmentScheduling;
import org.springblade.appointment.vo.AppointmentSchedulingVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 预约看房排班表 服务类
 *
 * @author Blade
 * @since 2021-01-25
 */
public interface IAppointmentSchedulingService extends BaseService<AppointmentScheduling> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param appointmentScheduling
	 * @return
	 */
	IPage<AppointmentSchedulingVO> selectAppointmentSchedulingPage(IPage<AppointmentSchedulingVO> page, AppointmentSchedulingVO appointmentScheduling);

	/**
	 * 验证数据是否重复
	 * @param scheduling
	 * @return
	 */
	int verifyDuplication(AppointmentScheduling scheduling);

	/**
	 * 根据班次id查总数
	 * @param shiftId
	 * @return
	 */
	int countByShiftId(Long shiftId);


	/**
	 * 排班信息（班次和场次信息）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Map<String ,Object>> schedulingInfo(String startDate , String endDate ,Long projectId);

}
