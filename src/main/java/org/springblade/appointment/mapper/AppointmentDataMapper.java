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
package org.springblade.appointment.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Param;
import org.springblade.appointment.entity.AppointmentData;
import org.springblade.appointment.vo.AppointmentDataVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import java.util.Map;

/**
 * 预约看房预约单 Mapper 接口
 *
 * @author Blade
 * @since 2021-01-25
 */
public interface AppointmentDataMapper extends BaseMapper<AppointmentData> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param appointmentData
	 * @return
	 */
	List<AppointmentDataVO> selectAppointmentDataPage(IPage page, AppointmentDataVO appointmentData);

	List<AppointmentDataVO> selectAppointmentDataWithShift(IPage page, AppointmentDataVO appointmentData,@Param("ew")QueryWrapper wrapper);

	AppointmentDataVO selectAppointmentDataWithShift(@Param("ew")QueryWrapper wrapper);

	List<Map<String,Object>> appointmentDataCount(@Param("ew")QueryWrapper wrapper);

}
