package org.springblade.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.client.eneity.ProjectUnittype;
import org.springblade.client.eneity.Unit;
import org.springblade.client.service.IProjectUnittypeService;
import org.springblade.client.service.IUnitService;
import org.springblade.client.vo.UnitVO;
import org.springblade.client.wrapper.UnitWrapper;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.RedisUtil;
import org.springblade.project.entity.ProjectInfo;
import org.springblade.project.entity.ProjectUnits;
import org.springblade.project.service.IProjectUnitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.lang.model.type.UnionType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
@RestController
@RequestMapping("/units")
public class FloorPlanController implements CommandLineRunner {
	@Autowired
	private IProjectUnitsService projectUnitsService;
	@Autowired
	private IUnitService unitService;
	@Autowired
	private IProjectUnittypeService projectUnittypeService;
	@Autowired
	private RedisUtil redisUtil;
	/**
	 * 用户网站项目页选择楼层
	 * @param units projectId block
	 * @return
	 */
	@CrossOrigin
	@GetMapping("/chooseFloors")
	public List<String> chooseFloors(ProjectUnits units){
		Long projectId=units.getProjectId();
		String block=units.getBlock();
		QueryWrapper<ProjectUnits> qwUnits=new QueryWrapper<>();
		qwUnits.eq("project_id",projectId);
		qwUnits.eq("block",block);
		List<ProjectUnits> projectUnitList=projectUnitsService.list(qwUnits);
		List<String> floorsList=new ArrayList<>();
		for(ProjectUnits Unit:projectUnitList){
			floorsList.add(Unit.getFloor());
		}
		HashSet setBlocksName = new HashSet(floorsList);//去重栋数
		floorsList.clear();
		floorsList.addAll(setBlocksName);//重新赋值去重之后的栋数
		return floorsList;
	}

	/**
	 * 用户网站项目页选择房型
	 * @param units projectId block floor
	 * @return
	 */
	@CrossOrigin
	@GetMapping("/chooseUnits")
	public List<String> chooseUnits(ProjectUnits units){
		Long projectId=units.getProjectId();
		String block=units.getBlock();
		String floor=units.getFloor();
		QueryWrapper<ProjectUnits> qwUnits=new QueryWrapper<>();
		qwUnits.eq("project_id",projectId);
		qwUnits.eq("block",block);
		qwUnits.eq("type_name",floor);
		List<ProjectUnits> projectUnitList=projectUnitsService.list(qwUnits);
		List<String> unitsList=new ArrayList<>();
		for(ProjectUnits Unit:projectUnitList){
			unitsList.add(Unit.getUnit());
		}
		return unitsList;
	}

	/**
	 * 用户网站项目页选择房型
	 * @param units projectId block floor
	 * @return
	 */
	@CrossOrigin
	@GetMapping("/lookunits")
	public ProjectUnits lookunits(ProjectUnits units){
		Long projectId=units.getProjectId();
		String block=units.getBlock();
		String floor=units.getFloor();
		String unit=units.getUnit();
		QueryWrapper<ProjectUnits> qwUnit=new QueryWrapper<>();
		qwUnit.eq("project_id",projectId);
		qwUnit.eq("block",block);
		qwUnit.eq("type_name",floor);
		qwUnit.eq("unit",unit);
		List<ProjectUnits> projectUnitList=projectUnitsService.list(qwUnit);

		return projectUnitList.get(0);
	}

//	/**
//	 * 选房界面获项目的取所有房型图
//	 * @param unit 项目id
//	 * @return
//	 */
//	@CrossOrigin
//	@GetMapping("/getProjectAllUnits")
//	public R getProjectAllUnits(Unit unit){
//		Long projectId=unit.getProjectId();
//		QueryWrapper<Unit> qw = new QueryWrapper<>();
//		qw.eq("project_id", projectId);
//		qw.orderByAsc("block");
//		List<Unit> projectUnits = unitService.list(qw);
//		List<String> blocksList = new ArrayList<>();
//		for (Unit projectUnit : projectUnits) {
//			blocksList.add(projectUnit.getBlock());//得到所有的栋数
//		}
//		HashSet setBlocksName = new HashSet(blocksList);//去重栋数
//		blocksList.clear();
//		blocksList.addAll(setBlocksName);//重新赋值去重之后的栋数
//		List<List<List<UnitVO>>> all = new ArrayList<>();//表格所有信息
//		for (String blocks : blocksList) {//遍历所有栋数
//			QueryWrapper<Unit> qwFloors = new QueryWrapper<>();
//			qwFloors.eq("project_id", projectId);
//			qwFloors.eq("block",blocks);
//			qwFloors.orderByAsc("floor");
//			List<Unit> UnitsFloors = unitService.list(qwFloors);
//			List<List<UnitVO>> floorsList = new ArrayList<>();//新建楼层list
//			List<String> floorsEach=new ArrayList<>();
//			for (Unit UnitsFloor:UnitsFloors){
//				floorsEach.add(UnitsFloor.getFloor());
//			}
//			HashSet setFloorsName = new HashSet(floorsEach);//去重层数
//			floorsEach.clear();
//			floorsEach.addAll(setFloorsName);
//			for (String floor : floorsEach) {//遍历每一栋的楼层
//				QueryWrapper<Unit> qwUnits = new QueryWrapper<>();
//				qwUnits.eq("project_id", projectId);
//				qwUnits.eq("block",blocks);
//				qwUnits.eq("floor",floor);
//				qwUnits.orderByAsc("unit");
//				List<Unit> Units = unitService.list(qwUnits);
//				List<UnitVO> unitVOS=new ArrayList<>();
//				for (Unit un:Units){
//					unitVOS.add(UnitWrapper.build().entityVO(un));
//				}
//				floorsList.add(unitVOS);
//			}all.add(floorsList);
//		}
//		return R.data(all,"项目楼层信息获取成功！");
//	}
	/**
	 	 * 选房界面获项目的取所有房型图
	 	 * @param unit 项目id
	 	 * @return
	 	 */
	@CrossOrigin
	@GetMapping("/getProjectAllUnits")
	public R getProjectAllUnits(Unit unit){
		//连接缓存
		//查询缓存
		List<List<List<UnitVO>>> re=(List<List<List<UnitVO>>>)redisUtil.get(unit.getProjectId().toString());
		if(re!=null){
			//如果有，则返回
			return R.data(re,"项目楼层信息获取成功!");
		}
		//没有则查询，存入缓存
		Long projectId=unit.getProjectId();
		//List<Unit> unitList =(List<Unit>)redisUtil.get("units");
		QueryWrapper<Unit> qw = new QueryWrapper<>();
		qw.eq("project_id", projectId);
		qw.orderByAsc("block");
		List<Unit> projectUnits = unitService.list(qw);
		List<ProjectUnittype> unittypes=projectUnittypeService.list();
		List<String> blocksList = new ArrayList<>();
		projectUnits.forEach(projectUnit->{
			blocksList.add(projectUnit.getBlock());//得到所有的栋数
		});
		HashSet setBlocksName = new HashSet(blocksList);//去重栋数
		blocksList.clear();
		blocksList.addAll(setBlocksName);//重新赋值去重之后的栋数
		List<List<List<UnitVO>>> all = new ArrayList<>();//表格所有信息
		for (String blocks : blocksList) {//遍历所有栋数
			List<Unit> UnitsFloors = new ArrayList<>();
			projectUnits.forEach(unit1 -> {
				if(projectId.equals(unit1.getProjectId())&&blocks.equals(unit1.getBlock())){
					UnitsFloors.add(unit1);
				}
			});
			List<List<UnitVO>> floorsList = new ArrayList<>();//新建楼层list
			List<String> floorsEach=new ArrayList<>();
			UnitsFloors.forEach(UnitsFloor->{
				floorsEach.add(UnitsFloor.getFloor());
			});
			HashSet setFloorsName = new HashSet(floorsEach);//去重层数
			floorsEach.clear();
			floorsEach.addAll(setFloorsName);
			for (String floor : floorsEach) {//遍历每一栋的楼层
				List<Unit> Units = new ArrayList<>();
				projectUnits.forEach(unit1 -> {
					if(projectId.equals(unit1.getProjectId())&&blocks.equals(unit1.getBlock())&&floor.equals(unit1.getFloor())){
						Units.add(unit1);
					}
				});
				List<UnitVO> unitVOS=new ArrayList<>();
				Units.forEach(un -> {
					//unitVOS.add(UnitWrapper.build().entityVO(un));
						unittypes.forEach(typs ->{
							if(un.getTypeId().equals(typs.getId().toString())){
								UnitVO unitVO = BeanUtil.copy(un, UnitVO.class);
								unitVO.setTypeUrl(typs.getTypeUrl());
								unitVOS.add(unitVO);
							}
						});
				});

				floorsList.add(unitVOS);
			}
			all.add(floorsList);
		}
		redisUtil.set(unit.getProjectId().toString(),all);
		return R.data(all,"项目楼层信息获取成功！");
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("正在加载项目信息。。。");
		List<Unit> units =unitService.list();
		redisUtil.set("units",units);
		System.out.println("加载项目信息成功！");
	}
}
