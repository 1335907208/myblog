package org.springblade.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springblade.appointment.entity.AppointmentData;
import org.springblade.appointment.entity.AppointmentShiftData;
import org.springblade.appointment.service.IAppointmentDataService;
import org.springblade.appointment.service.IAppointmentShiftDataService;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("client")
public class AppointmentController {
	private IAppointmentDataService iAppointmentDataService;
	private IAppointmentShiftDataService iAppointmentShiftDataService;

	/**
	 * 我的预约单提交
	 * @param request
	 * @param data
	 * @return
	 */
	@PostMapping("/submitAppointment")
	public R submitAppointment(HttpServletRequest request, @RequestBody @Valid AppointmentData data){
		HttpSession session = request.getSession();
		String userId = Func.toStr(session.getAttribute("userid"));
		String userType = Func.toStr(session.getAttribute("identity"));
//		String userId = "1123598821738675201";
//		String userType = "buyer";
		if(Func.isEmpty(userId)) {
			return R.data(0,"请重新登录！");
		}
		if(Func.isEmpty(userType)){
			return R.data(0,"请重新确认身份！");
		}

		//普通买家不能重复预约
		if("buyer".equals(userType)){

			//当前用户下已经预约有今天以后的没有取消的数据则不能重复预约
			int count = iAppointmentDataService.count(new QueryWrapper<AppointmentData>().eq("create_user", userId)
				.ne("status", 0).ge("date",LocalDate.now()));

			if (count >0){
				return R.data(0,"您已经有过预约，不能重复预约！");
			}

		}

		data.setCreateUser(Long.parseLong((String)userId));
		data.setUserType(userType);
		if (iAppointmentDataService.save(data)){
			return R.data(0,"预约成功！");
		}else{
			return R.fail(0,"预约失败！");
		}

	}

	/**
	 * 我的预约单修改
	 * @param request
	 * @param appointmentData
	 * @return
	 */
	@PostMapping("/updateAppointment")
	public R updateAppointment(HttpServletRequest request, @RequestBody @Valid AppointmentData appointmentData){
		HttpSession session = request.getSession();
		String userId = Func.toStr(session.getAttribute("userid"));
//		String userId = "1123598821738675201";
		if(Func.isEmpty(userId)) {
			return R.data(0,"请重新登录！");
		}

		AppointmentData data = iAppointmentDataService.getOne(new QueryWrapper<AppointmentData>().eq("id", appointmentData.getId()));

		if (Func.isEmpty(data)){
			return R.data(0,"预约数据有误，修改无效！");
		}

		AppointmentShiftData shiftData = iAppointmentShiftDataService.getOne(new QueryWrapper<AppointmentShiftData>().eq("id",data.getShiftDataId()));
		if (Func.isEmpty(shiftData)){
			return R.data(0,"预约场次数据有误，修改无效！");
		}


		//预约日期  和 场次结束时间
		String sTime = data.getDate()+" "+shiftData.getEndTime()+":00";
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime endTime = LocalDateTime.parse(sTime, dtf2);

		//预约已经超时则不能再修改
		if (endTime.isAfter(LocalDateTime.now())){
			return R.data(0,"预约已经超时，修改无效！");

		}

		if (iAppointmentDataService.updateById(appointmentData)){
			return R.data(0,"修改成功！");
		}else {
			return R.data(0,"修改失败！");
		}


	}

	/**
	 * 我的预约单提交-取消预约
	 * @param request
	 * @param id
	 * @return
	 */
	@PostMapping("/cancelAppointment")
	public R cancelAppointment(HttpServletRequest request,String id){
		HttpSession session = request.getSession();
		String userId = Func.toStr(session.getAttribute("userid"));
//		String userId = "1123598821738675201";
		if(Func.isEmpty(userId)) {
			return R.data(0,"请重新登录！");
		}

		AppointmentData data = iAppointmentDataService.getOne(new QueryWrapper<AppointmentData>().eq("id", id));

		if (Func.isEmpty(data)){
			return R.data(0,"预约数据有误，请确认！");
		}

		data.setStatus(0);
		data.setUpdateUser(Long.parseLong((String)userId));
		if (iAppointmentDataService.updateById(data)){
			return R.data(0,"取消成功！");
		}else{
			return R.fail("取消失败！");
		}


	}

	public static void main(String[] args) {

//		LocalTime now = LocalTime.now();
// 设置时间
//		LocalTime localTime = LocalTime.of(13, 51, 10);


//		String str = "9:15";
//		System.out.println(str.substring(0,2));
//		System.out.println(str.substring(3,5));
//
//		LocalTime.now().isBefore(LocalTime.of(13, 30,30));

//		LocalDate date = LocalDate.now();
//		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		String dateStr = date.format(fmt);

//		String sTime = LocalDate.now()+" 15:12:13";
//		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		LocalDateTime startTime = LocalDateTime.parse(sTime, dtf2);
//		System.out.println("startTime==="+startTime);
//		System.out.println("now==="+LocalDateTime.now());
//		if (startTime.isBefore(LocalDateTime.now())){
//			System.out.println("true");
//		}

//		System.out.println(LocalDate.now());
	}
}
