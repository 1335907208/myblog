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
package org.springblade.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.client.eneity.ProjectUnittype;
import org.springblade.client.eneity.Unit;
import org.springblade.client.service.IProjectUnittypeService;
import org.springblade.client.service.IUnitService;
import org.springblade.client.vo.ProjectUnittypeVO;
import org.springblade.client.vo.UnitVO;
import org.springblade.client.wrapper.ProjectUnittypeWrapper;
import org.springblade.client.wrapper.UnitWrapper;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.Dict;
import org.springblade.modules.system.service.IDictService;
import org.springblade.project.entity.ProjectInfo;
import org.springblade.project.entity.ProjectShow;
import org.springblade.project.entity.ProjectUnits;
import org.springblade.project.service.IProjectShowService;
import org.springblade.project.vo.ProjectInfoVO;
import org.springblade.project.vo.ProjectShowVO;
import org.springblade.project.wrapper.ProjectInfoWrapper;
import org.springblade.project.wrapper.ProjectShowWrapper;
import org.springblade.project.wrapper.ProjectUnitsWrapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目公用工具类
 *
 * @author Blade
 * @since 2020-07-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("project/tool")
@Api(value = "项目公用工具类", tags = "项目公用工具类")
public class ProjectToolController extends BladeController {

	private IDictService dictService;
	private IProjectUnittypeService projectUnittypeService;
	private IUnitService projectunitService;
	private IUnitService unitService;

	/**
	 * Location枚举列表
	 * @return
	 */
	@GetMapping("/location-list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "ProjectInfo列表", notes = "")
	public R<List<Dict>> locationList() {
		List<Dict> locationType = dictService.getList("locationType");
		return R.data(locationType);
	}

	/**
	 * 销售状态-枚举列表
	 * @return
	 */
	@GetMapping("/saletype-list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "saletype列表", notes = "")
	public R<List<Dict>> projectList() {
		List<Dict> dictList = dictService.getList("saleType");
		return R.data(dictList);
	}

	/**
	 * 楼盘类型-枚举列表
	 * @return
	 */
	@GetMapping("/constructionType-list")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "constructionType列表", notes = "")
	public R<List<Dict>> constructionType() {
		List<Dict> dictList = dictService.getList("constructionType");
		return R.data(dictList);
	}

	/**
	 * zeafva
	 * 卧室类型-枚举列表
	 * @return
	 */
	@GetMapping("/bedroomsType-list")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "constructionType列表", notes = "")
	public R<List<Dict>> bedroomsType() {
		List<Dict> dictList = dictService.getList("bedroomsType");
		return R.data(dictList);
	}

	/**
	 * 根据projectId获取bedroom-list列表
	 */
	@GetMapping("/bedroom-list")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "根据projectId获取bedroom-list列表", notes = "")
	public R<List<ProjectUnittypeVO>> getbedroomList(@Valid @RequestParam String projectId) {
		QueryWrapper<ProjectUnittype> projectUnittypeQueryWrapper = new QueryWrapper<>();

		if (!"".equals(projectId) && projectId != null){
			projectUnittypeQueryWrapper.eq("project_id", projectId);
		}
		projectUnittypeQueryWrapper.groupBy("bedroom");
		projectUnittypeQueryWrapper.select("bedroom");
		List<ProjectUnittype> projectUnittypeList = projectUnittypeService.list(projectUnittypeQueryWrapper);

		return R.data(ProjectUnittypeWrapper.build().listVO(projectUnittypeList));
	}

	/**
	 * 根据projectId和bedroom获取block-list列表
	 */
	@GetMapping("/block-list")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "获取block-list列表", notes = "")
	public R<List<UnitVO>> getBlockList(@RequestParam HashMap<String,Object> param) {
		//QueryWrapper<Unit> projectUnitQueryWrapper = new QueryWrapper<>();
		QueryWrapper<ProjectUnittype> projectUnittypeQueryWrapper = new QueryWrapper<>();

		String projectId = "";
		if(param.get("projectId") != null){
			projectId = param.get("projectId").toString();
		}
		String bedroom = "";
		if(param.get("bedroom") != null){
			bedroom = param.get("bedroom").toString();
		}
		if (!"".equals(projectId) && projectId != null){
			projectUnittypeQueryWrapper.eq("project_id", projectId);
		}
		if (!"".equals(bedroom) && bedroom != null){
			projectUnittypeQueryWrapper.eq("bedroom", bedroom);
		}

//		projectUnitQueryWrapper.groupBy("block");
//		projectUnitQueryWrapper.select("block");

		//根据project_id和bedroom查询出ProjectUnittype中有多少数据
		List<ProjectUnittype> projectUnittypeList = projectUnittypeService.list(projectUnittypeQueryWrapper);

		List typeIds = new ArrayList();
		for (ProjectUnittype projectUnittype : projectUnittypeList){
			typeIds.add(projectUnittype.getId());
		}

		//在根据projectUnittypeList数据的type_id查询Unit表中对应block集合
		QueryWrapper<Unit> projectUnitQueryWrapper = new QueryWrapper<>();
		projectUnitQueryWrapper.in("type_id", typeIds);
		projectUnitQueryWrapper.groupBy("block");
		projectUnitQueryWrapper.select("block");
		List<Unit> projectUnitList = projectunitService.list(projectUnitQueryWrapper);

		return R.data(UnitWrapper.build().listVO(projectUnitList));
	}

	/**
	 * 根据projectId、bedroom、block查询unitList
	 */
	@GetMapping("/unit-list")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "获取unit-list列表", notes = "")
	public R<List<UnitVO>> getUnitList(@RequestParam HashMap<String,Object> param) {
		//QueryWrapper<Unit> projectUnitQueryWrapper = new QueryWrapper<>();
		QueryWrapper<ProjectUnittype> projectUnittypeQueryWrapper = new QueryWrapper<>();

		String projectId = "";
		if(param.get("projectId") != null){
			projectId = param.get("projectId").toString();
		}
		String bedroom = "";
		if(param.get("bedroom") != null){
			bedroom = param.get("bedroom").toString();
		}


		if (!"".equals(projectId) && projectId != null){
			projectUnittypeQueryWrapper.eq("project_id", projectId);
		}
		if (!"".equals(bedroom) && bedroom != null){
			projectUnittypeQueryWrapper.eq("bedroom", bedroom);
		}


		//根据project_id和bedroom查询出ProjectUnittype中有多少数据
		List<ProjectUnittype> projectUnittypeList = projectUnittypeService.list(projectUnittypeQueryWrapper);

		List typeIds = new ArrayList();
		for (ProjectUnittype projectUnittype : projectUnittypeList){
			typeIds.add(projectUnittype.getId());
		}

		//在根据projectUnittypeList数据的type_id查询Unit表中对应block集合
		QueryWrapper<Unit> projectUnitQueryWrapper = new QueryWrapper<>();

		String block = "";
		if(param.get("block") != null){
			block = param.get("block").toString();
		}
		if (!"".equals(block) && block != null){
			projectUnitQueryWrapper.eq("block", block);
		}

		projectUnitQueryWrapper.eq("block", block);
		projectUnitQueryWrapper.in("type_id", typeIds);
		projectUnitQueryWrapper.groupBy("unit");
		projectUnitQueryWrapper.select("unit");
		List<Unit> projectUnitList = projectunitService.list(projectUnitQueryWrapper);

		return R.data(UnitWrapper.build().listVO(projectUnitList));
	}

	/**
	 * 根据projectId、bedroom、block查询unit-detail
	 */
	@GetMapping("/unit-detail")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "获取unit-detail", notes = "")
	public R<List<ProjectUnittypeVO>> getUnitdetail(@RequestParam HashMap<String,Object> param) {
		QueryWrapper<ProjectUnittype> projectUnittypeQueryWrapper = new QueryWrapper<>();

		String projectId = "";
		if(param.get("projectId") != null){
			projectId = param.get("projectId").toString();
		}
		String bedroom = "";
		if(param.get("bedroom") != null){
			bedroom = param.get("bedroom").toString();
		}


		if (!"".equals(projectId) && projectId != null){
			projectUnittypeQueryWrapper.eq("project_id", projectId);
		}
		if (!"".equals(bedroom) && bedroom != null){
			projectUnittypeQueryWrapper.eq("bedroom", bedroom);
		}
		//根据project_id和bedroom查询出ProjectUnittype中有多少数据
		List<ProjectUnittype> projectUnittypeList = projectUnittypeService.list(projectUnittypeQueryWrapper);


		List typeIds = new ArrayList();
		for (ProjectUnittype projectUnittype : projectUnittypeList){
			typeIds.add(projectUnittype.getId());
		}

		//在根据projectUnittypeList数据的type_id查询Unit表中对应block集合
		QueryWrapper<Unit> projectUnitQueryWrapper = new QueryWrapper<>();
		String block = "";
		if(param.get("block") != null){
			block = param.get("block").toString();
		}
		String unit = "";
		if(param.get("unit") != null){
			unit = param.get("unit").toString();
		}

		if (!"".equals(block) && block != null){
			projectUnitQueryWrapper.eq("block", block);
		}
		if (!"".equals(unit) && unit != null){
			projectUnitQueryWrapper.eq("unit", unit);
		}
		projectUnitQueryWrapper.in("type_id", typeIds);
		List<Unit> projectUnitList = projectunitService.list(projectUnitQueryWrapper);


		QueryWrapper<ProjectUnittype> projectUnittypeQueryWrapper2 = new QueryWrapper<>();
		String typeId = projectUnitList.get(0).getTypeId();
		projectUnittypeQueryWrapper2.eq("id", typeId);
		List<ProjectUnittype> projectUnittypeList1 = projectUnittypeService.list(projectUnittypeQueryWrapper2);


		return R.data(ProjectUnittypeWrapper.build().listVO(projectUnittypeList1));
	}


	/**
	 * 根据projectId获取floorplan-list列表
	 */
	@GetMapping("/floorplan-list")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "获取floorplan-list列表", notes = "")
	public R<Map> getFloorplanList(@Valid @RequestParam String projectId) {
		List<ProjectUnittype> unittypeList = projectUnittypeService.list(new QueryWrapper<ProjectUnittype>().eq("project_id", projectId));
		List<Dict> dictList = dictService.list(new QueryWrapper<Dict>().eq("code", "bedroomsType").orderByAsc("sort"));
		List<Unit> unitList = unitService.list(new QueryWrapper<Unit>().eq("project_id", projectId));

		Map map = new HashMap();
		map.put("unittype", unittypeList);
		map.put("dicts", dictList);
		map.put("unitList",unitList);

		return R.data(map);
	}


}
