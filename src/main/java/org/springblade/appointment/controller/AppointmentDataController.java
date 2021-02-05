/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.appointment.controller;

import io.swagger.annotations.*;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.common.utils.CommonUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.appointment.entity.AppointmentData;
import org.springblade.appointment.vo.AppointmentDataVO;
import org.springblade.appointment.wrapper.AppointmentDataWrapper;
import org.springblade.appointment.service.IAppointmentDataService;
import org.springblade.core.boot.ctrl.BladeController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 预约看房预约单 控制器
 *
 * @author Blade
 * @since 2021-01-25
 */
@RestController
@AllArgsConstructor
@RequestMapping("appointment/data")
@Api(value = "预约看房预约单", tags = "预约看房预约单接口")
public class AppointmentDataController extends BladeController {

	private IAppointmentDataService appointmentDataService;

	/**
	 * 预约详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "预约详情", notes = "传入id")
	public R<AppointmentDataVO> detail(@RequestParam Long id){
		return R.data(appointmentDataService.selectOneById(id));
	}
//	public R<AppointmentDataVO> detail(AppointmentData appointmentData) {
//		AppointmentData detail = appointmentDataService.getOne(Condition.getQueryWrapper(appointmentData));
//		return R.data(AppointmentDataWrapper.build().entityVO(detail));
//	}



	/**
	 * 预约列表分页 预约看房预约单
	 */
/*	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "预约列表分页", notes = "传入appointmentData")
	public R<IPage<AppointmentDataVO>> list(AppointmentData appointmentData, Query query) {
		Long projectId = CommonUtil.build().getProjectId();
		appointmentData.setProjectId(projectId);
		IPage<AppointmentData> pages = appointmentDataService.page(Condition.getPage(query), Condition.getQueryWrapper(appointmentData));
		return R.data(AppointmentDataWrapper.build().pageVO(pages));
	}*/


	/**
	 * 自定义分页 预约看房预约单
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入appointmentData")
	public R<IPage<AppointmentDataVO>> page(AppointmentDataVO appointmentData, Query query) {
		IPage<AppointmentDataVO> pages = appointmentDataService.selectAppointmentDataPage(Condition.getPage(query), appointmentData);
		return R.data(pages);
	}

	/**
	 * 新增 预约看房预约单
	 */
/*	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入appointmentData")
	public R save(@Valid @RequestBody AppointmentData appointmentData) {
		return R.status(appointmentDataService.save(appointmentData));
	}*/

	/**
	 * 修改预约 预约看房预约单
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改预约", notes = "传入appointmentData")
	public R update(@Valid @RequestBody AppointmentData appointmentData) {
		return R.status(appointmentDataService.updateById(appointmentData));
	}

	@PostMapping("/checkin")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "checkin签到", notes = "传入ID，enterNum")
	public R checkIn(@RequestParam Long id,@RequestParam int enterNum){
		AppointmentData data = appointmentDataService.getById(id);
		if (data == null){
			return R.success("暂无承载数据!");
		}
		if (data.getCheckInTime() != null && !"".equals(data.getCheckInTime())){
			return R.success("已经签到，不能重复签到！");
		}
		data.setStatus(5);//成功签到后状态改为已到访
		data.setEnterNum(enterNum);
		data.setCheckInTime(LocalDateTime.now());
		return R.status(appointmentDataService.updateById(data));
	}

	@PostMapping("/checkout")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "checkout签出", notes = "传入ID")
	public R checkOut(@RequestParam Long id){
		AppointmentData data = appointmentDataService.getById(id);
		if (data == null){
			return R.success("暂无承载数据!");
		}
		if (data.getStatus() != 5){
			return R.success("未签到，不能签出！");
		}
		if (data.getCheckOutTime() != null && !"".equals(data.getCheckOutTime())){
			return R.success("已经签出，不能重复签出！");
		}
		data.setStatus(9);//成功签到后状态改为参观结束
		data.setCheckOutTime(LocalDateTime.now());
		return R.status(appointmentDataService.updateById(data));
	}


	@GetMapping("/info")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "预约统计" , tags = "")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "startDate",value = "开始日期",required = false,dataType = "String"),
		@ApiImplicitParam(name = "endDate",value = "结束日期",required = false,dataType = "String")
	})
	public R appointmentDataInfo(String startDate,String endDate){

		List<Map<String ,Object>> data = appointmentDataService.appointmentDataCount(startDate,endDate);
//
		return R.data(data);
	}






	/**
	 * 新增或修改 预约看房预约单
	 */
/*	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入appointmentData")
	public R submit(@Valid @RequestBody AppointmentData appointmentData) {
		return R.status(appointmentDataService.saveOrUpdate(appointmentData));
	}*/


	/**
	 * 删除 预约看房预约单
	 */
/*	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(appointmentDataService.deleteLogic(Func.toLongList(ids)));
	}*/


}
