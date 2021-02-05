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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springblade.core.launch.props.BladeProperties;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.RandomType;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.index.entity.IndexShow;
import org.springblade.project.entity.ProjectFloorPlan;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.project.entity.ProjectInfo;
import org.springblade.project.vo.ProjectInfoVO;
import org.springblade.project.wrapper.ProjectInfoWrapper;
import org.springblade.project.service.IProjectInfoService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 项目信息表 控制器
 *
 * @author Blade
 * @since 2020-06-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("project/projectinfo")
@Api(value = "项目信息表", tags = "项目信息表接口")
public class ProjectInfoController extends BladeController {

	private IProjectInfoService projectInfoService;
	private BladeProperties bladeProperties;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入projectInfo")
	public R<ProjectInfoVO> detail(ProjectInfo projectInfo) {
		ProjectInfo detail = projectInfoService.getOne(Condition.getQueryWrapper(projectInfo));
		ProjectInfoVO pvo=ProjectInfoWrapper.build().entityVO(detail);
		return R.data(pvo);
	}

	/**
	 * 分页 项目信息表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入projectInfo")
	public R<IPage<ProjectInfoVO>> list(ProjectInfo projectInfo, Query query) {
		IPage<ProjectInfo> pages = projectInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(projectInfo));
		return R.data(ProjectInfoWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 项目信息表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入projectInfo")
	public R<IPage<ProjectInfoVO>> page(ProjectInfoVO projectInfo, Query query) {
		IPage<ProjectInfoVO> pages = projectInfoService.selectProjectInfoPage(Condition.getPage(query), projectInfo);
		return R.data(pages);
	}

	/**
	 * 新增 项目信息表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入projectInfo")
	public R save(@Valid @RequestBody ProjectInfo projectInfo) {
		return R.status(projectInfoService.save(projectInfo));
	}

	/**
	 * 修改 项目信息表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入projectInfo")
	public R update(@Valid @RequestBody ProjectInfo projectInfo) {
		return R.status(projectInfoService.updateById(projectInfo));
	}

	/**
	 * 新增或修改 项目信息表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入projectInfo")
	public R submit(@Valid @RequestBody ProjectInfo projectInfo) {
		return R.status(projectInfoService.saveOrUpdate(projectInfo));
	}


	/**
	 * 删除 项目信息表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(projectInfoService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 项目列表ProjectInfo
	 *
	 * @return
	 */
	@GetMapping("/project-list")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "ProjectInfo列表", notes = "")
	public R<List<ProjectInfoVO>> projectList() {
		List<ProjectInfo> list = projectInfoService.list(Wrappers.<ProjectInfo>query().lambda().eq(ProjectInfo::getIsDeleted,0));
		//转换成VO类型的list
		return R.data(ProjectInfoWrapper.build().listVO(list));
	}


	/**
	 * 文件上传接口，上传文件成功直接新增文件的记录
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/upload")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "上传文件", notes = "项目相关的文件和图片上传接口")
	public R uploadfile(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		
		/**
		 * 文件存放路径
		 */
		//文件存储的名字
		String newname = UUID.randomUUID().toString();
		fileName = newname + suffixName;


		//文件存放路径
		String nowTime = LocalDateTime.now().toString();
		//目录
		String one = nowTime.substring(0, 4);
		String two = nowTime.substring(5, 7);
		String thr = nowTime.substring(8, 10);

		String uploadPath = bladeProperties.get("upload-path");

		//设置文件存储路径
		String path = uploadPath +  "upload/"+one+"/"+two+"/"+thr+"/"+fileName;
		String url = "/upload/"+one+"/"+two+"/"+thr+"/"+fileName;

		java.io.File dest = new java.io.File(path);
		// 检测是否存在目录
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}else{
			dest.delete();
		}
		try {
			file.transferTo(dest);
		} catch (IOException e) {
			e.printStackTrace();
			return R.status(false);
		}

		//文件保存成功，需要添加文件的记录


		Map<String,Object> rst = new HashMap<String,Object>();
		rst.put("data","上传成功");
		rst.put("url",url);
		rst.put("size",dest.length());
		rst.put("fileName",fileName);

		return R.data(rst);
	}

	/**
	 * 搜索
	 *
	 * @return
	 */
	@PostMapping("/project-search")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "搜索", notes = "")
	public R<List<ProjectInfoVO>> projectSearchList(@Valid @RequestBody HashMap<String,Object> param) {
		List<ProjectInfo> list = projectInfoService.list(Wrappers.<ProjectInfo>query().lambda().eq(ProjectInfo::getIsDeleted,0));

		String location = "";
		if (param.get("location") != null){
			location = param.get("location").toString();
		}
		String status = "";
		if(param.get("status") != null){
			status = param.get("status").toString();
		}
		String type = "";
		if (param.get("type") != null){
			type = param.get("type").toString();
		}
		String bedrooms = "";
		if(param.get("bedrooms") != null){
			bedrooms = param.get("bedrooms").toString();
		}
		String price = "";
		if (param.get("price") != null){
			price = param.get("price").toString();
		}

		QueryWrapper<ProjectInfo> projectInfoQueryWrapper = new QueryWrapper<>();
		if (!"".equals(location) && !".".equals(location)){
			projectInfoQueryWrapper.eq("location_type", location);
		}
		if (!"".equals(status) && !".".equals(status)){
			projectInfoQueryWrapper.eq("sale_type", status);
		}
		if (!"".equals(type) && !".".equals(type)){
			projectInfoQueryWrapper.eq("construction_type", type);
		}
		if (!"".equals(bedrooms) && !".".equals(bedrooms)){
			projectInfoQueryWrapper.eq("bedrooms", bedrooms);
		}
		if (!"".equals(price) && !".".equals(price)){
			projectInfoQueryWrapper.ge("avg_price", price);
		}
		List<ProjectInfo> projectInfoList = projectInfoService.list(projectInfoQueryWrapper);

		return R.data(ProjectInfoWrapper.build().listVO(projectInfoList));
	}


}
