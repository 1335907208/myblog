package org.springblade.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.client.eneity.Blocks;
import org.springblade.client.eneity.Floors;
import org.springblade.client.eneity.Quarters;
import org.springblade.client.service.IindexService;
import org.springblade.project.entity.ProjectUnits;
import org.springblade.project.service.IProjectUnitsService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotEmpty;
import java.util.*;
import java.util.function.Function;

public class IindexServiceImpl implements IindexService {
	@Autowired
	IProjectUnitsService projectUnitsService;

//	@Override
//	public Quarters getQuarter(Long projectsId) {
//		QueryWrapper<ProjectUnits> qw=new QueryWrapper<>();
//		qw.eq("project_id",projectsId);
//		List<ProjectUnits> projectUnits=projectUnitsService.list(qw);
//		List<String> blocksName=new ArrayList<>();
//		for(ProjectUnits projectUnit:projectUnits){
//			blocksName.add(projectUnit.getBlock());//得到所有的栋数
//		}
//		HashSet setBlocksName = new HashSet(blocksName);//去重栋数
//		blocksName.clear();
//		blocksName.addAll(setBlocksName);//重新赋值去重之后的栋数
//		Quarters quarters=new Quarters();
//		quarters.setBlocksName(blocksName);//设置社区里面所有栋数的名称
//		List<Blocks> blocksList=new ArrayList<>();
//		quarters.setBlocks(blocksList);
//		for(ProjectUnits projectUnit:projectUnits){//遍历所有的房型
//			for (String blocks:blocksName){//遍历所有栋数
//				if(projectUnit.getBlock().equals(blocks)){//判断栋数是否等于去重之后的栋数
//					Blocks block=new Blocks();//新建栋数
//					blocksList.add(block);
//					List<String> floorsName=new ArrayList<>();
//					block.setFloorsName(floorsName);//设置每一栋里面所有楼层的名称
//					floorsName.add(projectUnit.getFloor());
//					List<Floors> floorsList=new ArrayList<>();//新建楼层list
//					for(String floors:floorsName){
//						if(projectUnit.getFloor().equals(floors)){
//							Floors floor=new Floors();
//							floorsList.add(floor);//楼层添加值
//							List<String> unitsName=new ArrayList<>();
//							floor.setUnitsName(unitsName);
//							unitsName.add(projectUnit.getUnit());
//						}
//					}
//				}
//			}
//		}
//		return quarters;
//	}

	@Override
	public List<List<List<ProjectUnits>>> getQuarter(Long projectsId) {
		QueryWrapper<ProjectUnits> qw = new QueryWrapper<>();
		qw.eq("project_id", projectsId);
		qw.orderByAsc("block");
		List<ProjectUnits> projectUnits = projectUnitsService.list(qw);
		List<String> blocksList = new ArrayList<>();
		for (ProjectUnits projectUnit : projectUnits) {
			blocksList.add(projectUnit.getBlock());//得到所有的栋数
		}
		HashSet setBlocksName = new HashSet(blocksList);//去重栋数
		blocksList.clear();
		blocksList.addAll(setBlocksName);//重新赋值去重之后的栋数
		List<List<List<ProjectUnits>>> all = new ArrayList<>();//表格所有信息
		for (String blocks : blocksList) {//遍历所有栋数
			QueryWrapper<ProjectUnits> qwFloors = new QueryWrapper<>();
			qwFloors.eq("project_id", projectsId);
			qwFloors.eq("block",blocks);
			qwFloors.orderByAsc("floor");
			List<ProjectUnits> UnitsFloors = projectUnitsService.list(qwFloors);
			List<List<ProjectUnits>> floorsList = new ArrayList<>();//新建楼层list
			for (ProjectUnits floors : UnitsFloors) {
				QueryWrapper<ProjectUnits> qwUnits = new QueryWrapper<>();
				qwUnits.eq("project_id", projectsId);
				qwUnits.eq("block",blocks);
				qwUnits.eq("floor",floors.getFloor());
				qwUnits.orderByAsc("unit");
				List<ProjectUnits> Units = projectUnitsService.list(qwUnits);
				floorsList.add(Units);
			}
			all.add(floorsList);
		}

		return all;
	}


	@Override
	public boolean deleteLogic(@NotEmpty List<Long> ids) {
		return false;
	}

	@Override
	public boolean saveBatch(Collection<ProjectUnits> entityList, int batchSize) {
		return false;
	}

	@Override
	public boolean saveOrUpdateBatch(Collection<ProjectUnits> entityList, int batchSize) {
		return false;
	}

	@Override
	public boolean updateBatchById(Collection<ProjectUnits> entityList, int batchSize) {
		return false;
	}

	@Override
	public boolean saveOrUpdate(ProjectUnits entity) {
		return false;
	}

	@Override
	public ProjectUnits getOne(Wrapper<ProjectUnits> queryWrapper, boolean throwEx) {
		return null;
	}

	@Override
	public Map<String, Object> getMap(Wrapper<ProjectUnits> queryWrapper) {
		return null;
	}

	@Override
	public <V> V getObj(Wrapper<ProjectUnits> queryWrapper, Function<? super Object, V> mapper) {
		return null;
	}

	@Override
	public BaseMapper<ProjectUnits> getBaseMapper() {
		return null;
	}
}
