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

import io.swagger.annotations.Api;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.appointment.service.IAppointmentDataService;
import org.springblade.common.utils.CommonUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.ConvertUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.appointment.entity.AppointmentScheduling;
import org.springblade.appointment.vo.AppointmentSchedulingVO;
import org.springblade.appointment.wrapper.AppointmentSchedulingWrapper;
import org.springblade.appointment.service.IAppointmentSchedulingService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;
import java.util.Map;

/**
 * 预约看房排班表 控制器
 *
 * @author Blade
 * @since 2021-01-25
 */
@RestController
@AllArgsConstructor
@RequestMapping("appointment/scheduling")
@Api(value = "预约看房排班表", tags = "预约看房排班表接口")
public class AppointmentSchedulingController extends BladeController {

	private IAppointmentSchedulingService appointmentSchedulingService;
	private IAppointmentDataService appointmentDataService;

	/**
	 * 详情
	 */
/*	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入appointmentScheduling")
	public R<AppointmentSchedulingVO> detail(AppointmentScheduling appointmentScheduling) {
		AppointmentScheduling detail = appointmentSchedulingService.getOne(Condition.getQueryWrapper(appointmentScheduling));
		return R.data(AppointmentSchedulingWrapper.build().entityVO(detail));
	}*/

	/**
	 * 分页 预约看房排班表
	 */
/*
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入appointmentScheduling")
	public R<IPage<AppointmentSchedulingVO>> list(AppointmentScheduling appointmentScheduling, Query query) {
		IPage<AppointmentScheduling> pages = appointmentSchedulingService.page(Condition.getPage(query), Condition.getQueryWrapper(appointmentScheduling));
		return R.data(AppointmentSchedulingWrapper.build().pageVO(pages));
	}
*/


	/**
	 * 自定义分页 预约看房排班表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "排班列表分页", notes = "传入appointmentScheduling")
	public R<IPage<AppointmentSchedulingVO>> page(AppointmentSchedulingVO appointmentScheduling, Query query) {
		IPage<AppointmentSchedulingVO> pages = appointmentSchedulingService.selectAppointmentSchedulingPage(Condition.getPage(query), appointmentScheduling);
		return R.data(pages);
	}

	/**
	 * 新增 预约看房排班表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增排班", notes = "传入appointmentScheduling")
	public R save(@Valid @RequestBody AppointmentScheduling appointmentScheduling) {
		int count = appointmentSchedulingService.verifyDuplication(appointmentScheduling);
		if (count > 0){
			return R.success("该班次已存在！");
		}
		Long projectId = CommonUtil.build().getProjectId();
		appointmentScheduling.setProjectId(projectId);
		return R.status(appointmentSchedulingService.save(appointmentScheduling));
	}

	/**
	 * 修改 预约看房排班表
	 */
/*	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入appointmentScheduling")
	public R update(@Valid @RequestBody AppointmentScheduling appointmentScheduling) {
		return R.status(appointmentSchedulingService.updateById(appointmentScheduling));
	}*/

	/**
	 * 新增或修改 预约看房排班表
	 */
/*	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入appointmentScheduling")
	public R submit(@Valid @RequestBody AppointmentScheduling appointmentScheduling) {
		return R.status(appointmentSchedulingService.saveOrUpdate(appointmentScheduling));
	}*/


	/**
	 * 删除 预约看房排班表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {

		List<AppointmentScheduling> appointmentSchedulings = appointmentSchedulingService.listByIds(Func.toLongList(ids));
		for (AppointmentScheduling scheduling : appointmentSchedulings){
			int count = appointmentDataService.countBySchedulingId(scheduling.getId());
			if (count > 0){
				return R.success("该排班已有预约,暂不能删除!");
			}
		}

		return R.status(appointmentSchedulingService.deleteLogic(Func.toLongList(ids)));
	}

	@GetMapping("/info")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "班次列表含时间段" , tags = "")
	public R schedulingDataInfo(String startDate,String endDate,Long projectId){

		List<Map<String ,Object>> data = appointmentSchedulingService.schedulingInfo(startDate,endDate,projectId);
//
		return R.data(data);
	}


}
