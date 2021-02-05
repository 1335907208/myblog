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
package org.springblade.appointment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springblade.appointment.entity.AppointmentScheduling;
import org.springblade.appointment.vo.AppointmentSchedulingVO;
import org.springblade.appointment.mapper.AppointmentSchedulingMapper;
import org.springblade.appointment.service.IAppointmentSchedulingService;
import org.springblade.common.utils.CommonUtil;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.Func;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.rmi.ServerException;
import java.util.List;
import java.util.Map;

/**
 * 预约看房排班表 服务实现类
 *
 * @author Blade
 * @since 2021-01-25
 */
@Service
public class AppointmentSchedulingServiceImpl extends BaseServiceImpl<AppointmentSchedulingMapper, AppointmentScheduling> implements IAppointmentSchedulingService {

	/**
	 * 获取班次名称 自定义分页
	 * @param page
	 * @param appointmentScheduling
	 * @return
	 */
	@Override
	public IPage<AppointmentSchedulingVO> selectAppointmentSchedulingPage(IPage<AppointmentSchedulingVO> page, AppointmentSchedulingVO appointmentScheduling) {
		//根据项目id过滤数据
		Long projectId = CommonUtil.build().getProjectId();
		QueryWrapper<AppointmentSchedulingVO>  queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("scheduling.project_id",projectId);
		queryWrapper.eq("scheduling.is_deleted",0);

		return page.setRecords(baseMapper.selectSchedulingWithShiftPage(page, appointmentScheduling,queryWrapper));
//		return page.setRecords(baseMapper.selectAppointmentSchedulingPage(page, appointmentScheduling));
	}

	public int verifyDuplication(AppointmentScheduling scheduling){
		QueryWrapper<AppointmentScheduling> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("date",scheduling.getDate());
		queryWrapper.eq("shift_id",scheduling.getShiftId());
		int count = baseMapper.selectCount(queryWrapper);
		return count;
	}


	public int countByShiftId(Long shiftId){
		QueryWrapper<AppointmentScheduling> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("shift_id",shiftId);
		int count = baseMapper.selectCount(queryWrapper);
		return count;
	}

	@Override
	public List<Map<String, Object>> schedulingInfo(String startDate, String endDate ,Long projectId) {
		//根据项目id过滤数据
//		Long projectId = CommonUtil.build().getProjectId();
		QueryWrapper<AppointmentSchedulingVO>  queryWrapper = new QueryWrapper<>();
		if (Func.isNotEmpty(projectId)){
			queryWrapper.eq("scheduling.project_id",projectId);
		}
		queryWrapper.eq("scheduling.is_deleted",0);
		queryWrapper.eq("shift.is_deleted",0);
		queryWrapper.eq("sdata.is_deleted",0);
		if ((startDate != null && !"".equals(startDate)) && (endDate != null && !"".equals(endDate))){
			queryWrapper.between("scheduling.date",startDate,endDate);
		}
		return baseMapper.selectSchedulingInfo(queryWrapper);
	}

}
