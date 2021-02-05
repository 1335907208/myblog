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

import org.springblade.appointment.entity.AppointmentScheduling;
import org.springblade.appointment.service.IAppointmentSchedulingService;
import org.springblade.common.utils.CommonUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.appointment.entity.AppointmentShift;
import org.springblade.appointment.vo.AppointmentShiftVO;
import org.springblade.appointment.wrapper.AppointmentShiftWrapper;
import org.springblade.appointment.service.IAppointmentShiftService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;

/**
 * 预约看房班次 控制器
 *
 * @author Blade
 * @since 2021-01-25
 */
@RestController
@AllArgsConstructor
@RequestMapping("appointment/shift")
@Api(value = "预约看房班次", tags = "预约看房班次接口")
public class AppointmentShiftController extends BladeController {

	private IAppointmentShiftService appointmentShiftService;
	private IAppointmentSchedulingService appointmentSchedulingService;

	/**
	 * 详情
	 */
/*	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入appointmentShift")
	public R<AppointmentShiftVO> detail(AppointmentShift appointmentShift) {
		AppointmentShift detail = appointmentShiftService.getOne(Condition.getQueryWrapper(appointmentShift));
		return R.data(AppointmentShiftWrapper.build().entityVO(detail));
	}*/

	/**
	 * 分页 预约看房班次
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "班次列表带分页", notes = "传入appointmentShift")
	public R<IPage<AppointmentShiftVO>> list(AppointmentShift appointmentShift, Query query) {
		Long projectId = CommonUtil.build().getProjectId();
		appointmentShift.setProjectId(projectId);
		appointmentShift.setIsDeleted(0);
		IPage<AppointmentShift> pages = appointmentShiftService.page(Condition.getPage(query), Condition.getQueryWrapper(appointmentShift));
		return R.data(AppointmentShiftWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 预约看房班次
	 */
/*	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入appointmentShift")
	public R<IPage<AppointmentShiftVO>> page(AppointmentShiftVO appointmentShift, Query query) {
		IPage<AppointmentShiftVO> pages = appointmentShiftService.selectAppointmentShiftPage(Condition.getPage(query), appointmentShift);
		return R.data(pages);
	}*/

	/**
	 * 新增 预约看房班次
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增班次", notes = "传入appointmentShift")
	public R save(@Valid @RequestBody AppointmentShift appointmentShift) {
		Long projectId = CommonUtil.build().getProjectId();
		appointmentShift.setProjectId(projectId);
		return R.status(appointmentShiftService.save(appointmentShift));
	}

	/**
	 * 修改 预约看房班次
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改班次", notes = "传入appointmentShift")
	public R update(@Valid @RequestBody AppointmentShift appointmentShift) {
		return R.status(appointmentShiftService.updateById(appointmentShift));
	}

	/**
	 * 停用启用 预约看房班次
	 */
	@PostMapping("/changeState")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "停用/启用班次", notes = "传入appointmentShift")
	public R changeState(@Valid @RequestParam int status,Long id) {
		AppointmentShift appointmentShift = appointmentShiftService.getById(id);
		appointmentShift.setStatus(status);
		return R.status(appointmentShiftService.updateById(appointmentShift));
	}

	/**
	 * 新增或修改 预约看房班次
	 */
/*	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入appointmentShift")
	public R submit(@Valid @RequestBody AppointmentShift appointmentShift) {
		return R.status(appointmentShiftService.saveOrUpdate(appointmentShift));
	}*/


	/**
	 * 删除 预约看房班次
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除班次逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		List<AppointmentShift> shiftList = appointmentShiftService.listByIds(Func.toLongList(ids));

		for (AppointmentShift shifts : shiftList){
			int count = appointmentSchedulingService.countByShiftId(shifts.getId());
			if (count > 0){
				return R.success("该班次已有预约,暂不能删除!");
			}
		}
		return R.status(appointmentShiftService.deleteLogic(Func.toLongList(ids)));
	}


}
