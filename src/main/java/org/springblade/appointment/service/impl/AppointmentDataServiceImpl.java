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
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.springblade.appointment.entity.AppointmentData;
import org.springblade.appointment.vo.AppointmentDataVO;
import org.springblade.appointment.mapper.AppointmentDataMapper;
import org.springblade.appointment.service.IAppointmentDataService;
import org.springblade.common.utils.CommonUtil;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 预约看房预约单 服务实现类
 *
 * @author Blade
 * @since 2021-01-25
 */
@Service
public class AppointmentDataServiceImpl extends BaseServiceImpl<AppointmentDataMapper, AppointmentData> implements IAppointmentDataService {
	@Override
	public AppointmentDataVO selectOneById(Long id) {
		Long projectId = CommonUtil.build().getProjectId();
		QueryWrapper<AppointmentDataVO> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("a.id",id);
		queryWrapper.eq("a.project_id",projectId);
		queryWrapper.eq("a.is_deleted",0);
		return baseMapper.selectAppointmentDataWithShift(queryWrapper);
	}

	@Override
	public IPage<AppointmentDataVO> selectAppointmentDataPage(IPage<AppointmentDataVO> page, AppointmentDataVO appointmentData) {
		Long projectId = CommonUtil.build().getProjectId();
		QueryWrapper<AppointmentDataVO> queryWrapper = new QueryWrapper<>();
		//根据项目id过滤
		queryWrapper.eq("a.project_id",projectId);
		queryWrapper.eq("a.is_deleted",0);
		if ((appointmentData.getStartDate() != null && !"".equals(appointmentData.getStartDate()) && (appointmentData.getEndDate() != null && !"".equals(appointmentData.getEndDate())))){
			queryWrapper.between("a.date",appointmentData.getStartDate(),appointmentData.getEndDate());
		}
		if (appointmentData.getShiftDataId() !=  null && !"".equals(appointmentData.getShiftDataId())){
			queryWrapper.eq("a.shift_data_id",appointmentData.getShiftDataId());
		}

		if (appointmentData.getName() !=  null && !"".equals(appointmentData.getName())){
			queryWrapper.like("a.name",appointmentData.getName());
		}

		if (appointmentData.getNric() !=  null && !"".equals(appointmentData.getNric())){
			queryWrapper.eq("a.nric",appointmentData.getNric());
		}

		if (appointmentData.getEmail() !=  null && !"".equals(appointmentData.getEmail())){
			queryWrapper.eq("a.email",appointmentData.getEmail());
		}

		if (appointmentData.getTel() !=  null && !"".equals(appointmentData.getTel())){
			queryWrapper.eq("a.tel",appointmentData.getTel());
		}

		if (appointmentData.getStatus() !=  null && !"".equals(appointmentData.getStatus())){
			queryWrapper.eq("a.status",appointmentData.getStatus());
		}

		return page.setRecords(baseMapper.selectAppointmentDataWithShift(page,appointmentData,queryWrapper));
//		return page.setRecords(baseMapper.selectAppointmentDataPage(page, appointmentData));
	}

	public int countByShiftId(Long shiftId){
		QueryWrapper<AppointmentData> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("shift_data_id",shiftId);

		return baseMapper.selectCount(queryWrapper);
	}


	public int countBySchedulingId(Long schedulingId){
		QueryWrapper<AppointmentData> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("scheduling_id",schedulingId);
		return baseMapper.selectCount(queryWrapper);
	}


	public List<Map<String ,Object>> appointmentDataCount(String startDate , String endDate){
		Long projectId = CommonUtil.build().getProjectId();
		QueryWrapper<AppointmentData> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("a.project_id",projectId);
		queryWrapper.eq("a.is_deleted",0);
		queryWrapper.ne("a.status",0);//状态为取消的不统计
		if ((startDate != null && !"".equals(startDate)) && (endDate != null && !"".equals(endDate))){
			queryWrapper.between("a.date",startDate,endDate);
		}

		return baseMapper.appointmentDataCount(queryWrapper);
	}

	public List<Map<String ,Object>> appointmentDataCount(Long createUser,String startDate , String endDate){
		Long projectId = CommonUtil.build().getProjectId();
//		Long projectId = 1277846730104135682L;
		QueryWrapper<AppointmentData> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("a.project_id",projectId);
		queryWrapper.eq("a.is_deleted",0);
		queryWrapper.ne("a.status",0);//状态为取消的不统计
		queryWrapper.eq("a.create_user",createUser);
		if ((startDate != null && !"".equals(startDate)) && (endDate != null && !"".equals(endDate))){
			queryWrapper.between("a.date",startDate,endDate);
		}

		return baseMapper.appointmentDataCount(queryWrapper);
	}

}
