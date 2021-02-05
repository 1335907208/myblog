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
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.appointment.entity.AppointmentShiftData;
import org.springblade.appointment.vo.AppointmentShiftDataVO;
import org.springblade.appointment.wrapper.AppointmentShiftDataWrapper;
import org.springblade.appointment.service.IAppointmentShiftDataService;
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
@RequestMapping("appointment/shift/data")
@Api(value = "预约看房场次", tags = "预约看房场次接口")
public class AppointmentShiftDataController extends BladeController {

	private IAppointmentShiftDataService appointmentShiftDataService;

	private IAppointmentDataService appointmentDataService;

	/**
	 * 详情
	 */
/*	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入appointmentShiftData")
	public R<AppointmentShiftDataVO> detail(AppointmentShiftData appointmentShiftData) {
		AppointmentShiftData detail = appointmentShiftDataService.getOne(Condition.getQueryWrapper(appointmentShiftData));
		return R.data(AppointmentShiftDataWrapper.build().entityVO(detail));
	}*/

	/**
	 * 分页 预约看房场次
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "场次列表带分页", notes = "传入appointmentShiftData")
	public R<IPage<AppointmentShiftDataVO>> list(AppointmentShiftData appointmentShiftData, Query query) {
		IPage<AppointmentShiftData> pages = appointmentShiftDataService.page(Condition.getPage(query), Condition.getQueryWrapper(appointmentShiftData));
		return R.data(AppointmentShiftDataWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 预约看房场次
	 */
/*	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入appointmentShiftData")
	public R<IPage<AppointmentShiftDataVO>> page(AppointmentShiftDataVO appointmentShiftData, Query query) {
		IPage<AppointmentShiftDataVO> pages = appointmentShiftDataService.selectAppointmentShiftDataPage(Condition.getPage(query), appointmentShiftData);
		return R.data(pages);
	}*/

	/**
	 * 新增 预约看房场次
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增场次", notes = "传入appointmentShiftData")
	public R save(@Valid @RequestBody AppointmentShiftData appointmentShiftData) {
		return R.status(appointmentShiftDataService.save(appointmentShiftData));
	}

	/**
	 * 修改 预约看房场次
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改场次", notes = "传入appointmentShiftData")
	public R update(@Valid @RequestBody AppointmentShiftData appointmentShiftData) {
		return R.status(appointmentShiftDataService.updateById(appointmentShiftData));
	}


	/**
	 * 新增或修改 预约看房场次
	 */
/*	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入appointmentShiftData")
	public R submit(@Valid @RequestBody AppointmentShiftData appointmentShiftData) {
		return R.status(appointmentShiftDataService.saveOrUpdate(appointmentShiftData));
	}*/


	/**
	 * 删除 预约看房场次
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除场次逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		 List<AppointmentShiftData> shiftDataList = appointmentShiftDataService.listByIds(Func.toLongList(ids));

		 for (AppointmentShiftData shiftData : shiftDataList){
			 int count = appointmentDataService.countByShiftId(shiftData.getId());
			 if (count > 0){
				 return R.success("该场次已有预约,暂不能删除!");
			 }
		 }

		return R.status(appointmentShiftDataService.removeByIds(Func.toLongList(ids)));
	}


}
