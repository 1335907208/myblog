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

import org.springblade.common.utils.CommonUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.ConvertUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.appointment.entity.AppointmentConfig;
import org.springblade.appointment.vo.AppointmentConfigVO;
import org.springblade.appointment.wrapper.AppointmentConfigWrapper;
import org.springblade.appointment.service.IAppointmentConfigService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 预约看房配置表 控制器
 *
 * @author Blade
 * @since 2021-01-25
 */
@RestController
@AllArgsConstructor
@RequestMapping("appointment/config")
@Api(value = "预约看房配置表", tags = "预约看房配置表接口")
public class AppointmentConfigController extends BladeController {

	private IAppointmentConfigService appointmentConfigService;

	/**
	 * 配置详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "配置详情", notes = "传入appointmentConfig")
	public R<AppointmentConfigVO> detail(AppointmentConfig appointmentConfig,Long projectId) {
		if (projectId == null){
			return R.success("暂无数据！");
		}
		appointmentConfig.setProjectId(projectId);
		AppointmentConfig detail = appointmentConfigService.getOne(Condition.getQueryWrapper(appointmentConfig));
		if (detail == null ){
			return R.success("暂无数据！");
		}

		return R.data(AppointmentConfigWrapper.build().entityVO(detail));

	}

	/**
	 * 分页 预约看房配置表
	 */
/*	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入appointmentConfig")
	public R<IPage<AppointmentConfigVO>> list(AppointmentConfig appointmentConfig, Query query) {
		IPage<AppointmentConfig> pages = appointmentConfigService.page(Condition.getPage(query), Condition.getQueryWrapper(appointmentConfig));
		return R.data(AppointmentConfigWrapper.build().pageVO(pages));
	}*/


	/**
	 * 自定义分页 预约看房配置表
	 */
/*	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入appointmentConfig")
	public R<IPage<AppointmentConfigVO>> page(AppointmentConfigVO appointmentConfig, Query query) {
		IPage<AppointmentConfigVO> pages = appointmentConfigService.selectAppointmentConfigPage(Condition.getPage(query), appointmentConfig);
		return R.data(pages);
	}*/

	/**
	 * 新增 预约看房配置表
	 */
/*	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入appointmentConfig")
	public R save(@Valid @RequestBody AppointmentConfig appointmentConfig) {
		return R.status(appointmentConfigService.save(appointmentConfig));
	}*/

	/**
	 * 修改 预约看房配置表
	 */
/*	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入appointmentConfig")
	public R update(@Valid @RequestBody AppointmentConfig appointmentConfig) {
		return R.status(appointmentConfigService.updateById(appointmentConfig));
	}*/

	/**
	 * 添加或修改配置 预约看房配置表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "添加或修改配置", notes = "传入appointmentConfig")
	public R submit(@Valid @RequestBody AppointmentConfig appointmentConfig) {
		Long projectId = 0L;
		if (appointmentConfig.getProjectId() == null || "".equals(appointmentConfig.getProjectId())){
			return  R.success("项目ID不能为空!");
		}
		if (appointmentConfig.getProjectId() != null && !"".equals(appointmentConfig.getProjectId())){
			 projectId = appointmentConfig.getProjectId();
		}

		System.out.println(appointmentConfig.getId());
		if (appointmentConfig.getId() == null || "".equals(appointmentConfig.getId())){
			int count = appointmentConfigService.countByProjectID(projectId);
			if (count >0 ){
				return  R.success("项目配置数据重复!");
			}
		}
		appointmentConfig.setProjectId(projectId);

		return R.status(appointmentConfigService.saveOrUpdate(appointmentConfig));
	}


	/**
	 * 删除 预约看房配置表
	 */
/*
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(appointmentConfigService.deleteLogic(Func.toLongList(ids)));
	}
*/


}
