package org.springblade.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.appointment.entity.AppointmentConfig;
import org.springblade.appointment.entity.AppointmentData;
import org.springblade.appointment.entity.AppointmentScheduling;
import org.springblade.appointment.entity.AppointmentShiftData;
import org.springblade.appointment.service.IAppointmentConfigService;
import org.springblade.appointment.service.IAppointmentDataService;
import org.springblade.appointment.service.IAppointmentSchedulingService;
import org.springblade.appointment.service.IAppointmentShiftDataService;
import org.springblade.appointment.vo.AppointmentDataVO;
import org.springblade.appointment.wrapper.AppointmentDataWrapper;
import org.springblade.client.eneity.ProjectUnittype;
import org.springblade.client.eneity.Unit;
import org.springblade.client.entity.UserApplication;
import org.springblade.client.service.IProjectUnittypeService;
import org.springblade.client.service.IUnitService;
import org.springblade.client.service.IUserApplicationService;
import org.springblade.client.vo.UserApplicationVO;
import org.springblade.client.wrapper.UserApplicationWrapper;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.launch.props.BladeProperties;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.utils.*;
import org.springblade.index.entity.IndexShow;
import org.springblade.index.service.IIndexShowService;
import org.springblade.modules.agency.entity.Agency;
import org.springblade.modules.agency.entity.AgencyProject;
import org.springblade.modules.agency.service.IAgencyProjectService;
import org.springblade.modules.agency.service.IAgencyService;
import org.springblade.modules.system.entity.Dict;
import org.springblade.modules.system.service.IDictService;
import org.springblade.project.entity.*;
import org.springblade.project.service.IProjectAllviewService;
import org.springblade.project.service.IProjectInfoService;
import org.springblade.project.service.IProjectShowService;
import org.springblade.project.service.IProjectUnitsService;
import org.springblade.project.vo.ProjectInfoVO;
import org.springblade.project.vo.ProjectSitePlanVO;
import org.springblade.project.wrapper.ProjectInfoWrapper;
import org.springblade.purchaseIntent.entity.PurchaseIntent;
import org.springblade.purchaseIntent.entity.PurchaseIntentUser;
import org.springblade.purchaseIntent.entity.PurchaseOrder;
import org.springblade.purchaseIntent.service.IPurchaseIntentService;
import org.springblade.purchaseIntent.service.IPurchaseIntentUserService;
import org.springblade.purchaseIntent.service.IPurchaseOrderService;
import org.springblade.purchaseIntent.vo.PurchaseIntentUserVO;
import org.springblade.purchaseIntent.vo.PurchaseIntentVO;
import org.springblade.purchaseIntent.vo.PurchaseOrderVO;
import org.springblade.purchaseIntent.wrapper.PurchaseIntentUserWrapper;
import org.springblade.purchaseIntent.wrapper.PurchaseIntentWrapper;
import org.springblade.purchaseIntent.wrapper.PurchaseOrderWrapper;
import org.springblade.webuser.entity.UserAgent;
import org.springblade.webuser.entity.UserBuyer;
import org.springblade.webuser.service.IUserAgentService;
import org.springblade.webuser.service.IUserBuyerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class IndexController {//项目访问控制器
	private IProjectInfoService projectInfoService;
	private IIndexShowService indexShowService;
	private IProjectShowService projectShowService;
	private IProjectUnitsService projectUnitsService;
	private IProjectAllviewService projectAllviewService;
	private IProjectUnittypeService projectUnittypeService;
	private IUserBuyerService userBuyerService;
	private IUserAgentService userAgentService;
	private IDictService dictService;
	private IUnitService unitService;
	private BladeProperties bladeProperties;
	private IUserApplicationService userApplicationService;
	private IPurchaseIntentService iPurchaseIntentService;
	private IPurchaseIntentUserService iPurchaseIntentUserService;
	private IPurchaseOrderService iPurchaseOrderService;
	private IAppointmentDataService iAppointmentDataService;
	private IAppointmentShiftDataService iAppointmentShiftDataService;
	private IAppointmentSchedulingService iAppointmentSchedulingService;
	private IAppointmentConfigService iAppointmentConfigService;
	private IAgencyService agencyService;
	private IAgencyProjectService agencyProjectService;


	private RedisUtil redisUtil;

	@RequestMapping("/")
	public String index(HttpServletRequest request,Model model) {//首页
		HttpSession session = request.getSession();
		//显示图片前缀
		String prefixUrl = bladeProperties.get("prefixUrl");
		String requestUrl = bladeProperties.get("requestUrl");

//		Map<String, Object> homepage = new HashMap<>();
//		if(redisUtil.hasKey(CacheNames.HOMEPAGE)) {
//			Map<Object,Object> _homepage = redisUtil.hmget(CacheNames.HOMEPAGE);
//
//
////			homepage = _homepage.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new Object(e.getValue())));
////			homepage = new HashMap<String, Object>(_homepage);
//			List<Object> centers = (List<Object>) _homepage.get("centers");
//			List<Object> headers = (List<Object>) _homepage.get("headers");
//			List<Object> listDataTemp = (List<Object>) _homepage.get("listDataTemp");
//
//		} else {
			List<ProjectInfo> centers = projectInfoService.list(Wrappers.<ProjectInfo>query().lambda().eq(ProjectInfo::getIsDeleted,0).eq(ProjectInfo::getIsPush,0));
			//		request.setAttribute("name", name);
			for(ProjectInfo project:centers){
				project.setIdCopy(project.getId().toString());
			}
//			homepage.put("centers", centers);
			//		redisUtil.lSet(CacheNames.HOMEPAGE + "centers", centers2);


			// 首页轮播图
			List<IndexShow> headers = indexShowService.list(Wrappers.<IndexShow>query().lambda().eq(IndexShow::getIsDeleted, 0).eq(IndexShow::getIsPush,0).orderByDesc(IndexShow::getSort));
			for(IndexShow index:headers){
				index.setPidCopy(index.getProjectId().toString());
			}
//			homepage.put("headers", headers);


			//地图返回信息
			List<ProjectInfo> listDataTemp = projectInfoService.list(Wrappers.<ProjectInfo>query().lambda().eq(ProjectInfo::getIsDeleted,0));

			for(ProjectInfo project:listDataTemp){
				Integer count = unitService.count(
					new LambdaQueryWrapper<Unit>()
						.eq(Unit::getProjectId, project.getId())
						.eq(Unit::getSaleStatus, 0)
						.eq(Unit::getIsDeleted, 0)
				);
				project.setCansaleunits(count);
			}

//			homepage.put("listDataTemp", listDataTemp);
//			redisUtil.hmset(CacheNames.HOMEPAGE, homepage);

//		}



		setAttrByProject(model);

		// 字典

		List<Dict> nationality = dictService.list(new QueryWrapper<Dict>().eq("code","nationality").ne("parent_id", 0).orderByAsc("sort"));
		List<Dict> locationType = dictService.getList("locationType");
		List<Dict> bedroomsType = dictService.getList("bedroomsType");
		List<Dict> statusType = dictService.getList("statusType");
		List<Dict> constructionType = dictService.getList("constructionType");

		//agency

		List<Agency> agencyIPage = agencyService.list(Wrappers.<Agency>query().lambda().eq(Agency::getIsDeleted,0));

		// model 数据注入
		model.addAttribute("model", "index");
		// 获取用户信息
		model.addAttribute("userid",session.getAttribute("userid"));
		model.addAttribute("identity", session.getAttribute("identity"));
		model.addAttribute("userInfo", session.getAttribute("userInfo"));
		//图片地址前缀
		model.addAttribute("prefixUrl",prefixUrl);
		//发送请求地址前缀
		model.addAttribute("requestUrl",requestUrl);
		//头部轮播图
		model.addAttribute("headers",headers);
		//中间项目轮播图
		model.addAttribute("centers",centers);

		model.addAttribute("listDataTemp",listDataTemp);

//		model.addAttribute("statusCode",0);
		// 字典数据
		model.addAttribute("nationality", nationality);
		model.addAttribute("locationType",locationType);
		model.addAttribute("bedroomsType",bedroomsType);
		model.addAttribute("statusType",statusType);
		model.addAttribute("constructionType",constructionType);
		model.addAttribute("agencyList", agencyIPage);

		return "index";
	}

	@RequestMapping("/project")
	public String project(ProjectInfo projectInfo,HttpServletRequest request, Model model) {//具体项目页
		HttpSession session = request.getSession();

		// 获取用户信息
		model.addAttribute("userid",session.getAttribute("userid"));
		model.addAttribute("identity", session.getAttribute("identity"));
		model.addAttribute("userInfo", session.getAttribute("userInfo"));

		ProjectInfo projectinfodetail = projectInfoService.getOne(new QueryWrapper<ProjectInfo>().eq("id",projectInfo.getId()));
		projectinfodetail.setIdCopy(projectinfodetail.getId().toString());
		QueryWrapper<ProjectShow> qw=new QueryWrapper<>();
		qw.eq("project_id",projectinfodetail.getId());

		List<ProjectShow> headerSwipers = projectShowService.list(qw);
		//头部轮播图
		model.addAttribute("headerSwipers",headerSwipers);
		//项目介绍
		model.addAttribute("projectinfodetail",projectinfodetail);
		String prefixUrl = bladeProperties.get("prefixUrl");
		String requestUrl = bladeProperties.get("requestUrl");
		//显示图片前缀
		model.addAttribute("prefixUrl",prefixUrl);
		//发送请求地址前缀
		model.addAttribute("requestUrl",requestUrl);
		ProjectInfoVO pvo= ProjectInfoWrapper.build().entityVO(projectinfodetail);
		List<ProjectGallery> projectGalleryList=pvo.getProjectGalleryList();
		//相册列表
		model.addAttribute("projectGalleryList",projectGalleryList);
		List<ProjectNearby>  projectNearbyList=pvo.getProjectNearbyList();
		//Location
		model.addAttribute("projectNearbyList",projectNearbyList);
		List<ProjectIntroduction> projectIntroductionList=pvo.getProjectIntroductionList();
		//专家介绍
		model.addAttribute("projectIntroductionList",projectIntroductionList);
		List<ProjectIntroduction> projectIntroductionListbehind=new ArrayList<>();
		for (int i=1;i<=projectIntroductionList.size()-1;i++){
			projectIntroductionListbehind.add(i - 1, projectIntroductionList.get(i));
		}

		model.addAttribute("projectIntroductionListbehind",projectIntroductionListbehind);
		List<ProjectSitePlanVO> projectSitePlanVOList=pvo.getProjectSitePlanVOList();
		//SitePlan
		model.addAttribute("projectSitePlanVOList",projectSitePlanVOList);
		List<ProjectEvent>  projectEventList=pvo.getProjectEventList();
		//活动列表
		model.addAttribute("projectEventList",projectEventList);
		//vr
		List<ProjectVr> projectVrList=pvo.getProjectVrList();
		model.addAttribute("projectVrList",projectVrList);
		//foolpan
		List<ProjectFloorPlan> projectFloorPlanList=pvo.getProjectFloorPlanList();
		model.addAttribute("projectFloorPlanList",projectFloorPlanList);

		//获取BR名称
//		QueryWrapper<ProjectUnits> qwUnits=new QueryWrapper<>();
//		qwUnits.eq("project_id",projectInfo.getId());
//		List<ProjectUnits> projectUnits=projectUnitsService.list(qwUnits);
//		List<String> blocksName=new ArrayList<>();
//		for(ProjectUnits projectUnit:projectUnits){
//			blocksName.add(projectUnit.getTypeName());//得到所有的栋数
//		}
//		HashSet setBlocksName = new HashSet(blocksName);//去重栋数
//		blocksName.clear();
//		blocksName.addAll(setBlocksName);//重新赋值去重之后的栋数
//		model.addAttribute("blocksName",blocksName);
		//获取BR字典名称（floorplan）
		List<ProjectUnittype> unittypeList=projectUnittypeService.list(new QueryWrapper<ProjectUnittype>().eq("project_id",projectInfo.getId()));
		List<Dict> dicts=dictService.list(new QueryWrapper<Dict>().eq("code","bedroomsType").orderByAsc("sort"));
		Integer[] integers = Func.toIntArray(projectinfodetail.getBedrooms());
		List<Dict> dicts1 = new ArrayList<>();
		dicts.forEach(dict -> {
			Integer dictKey = dict.getDictKey();
			if(Func.contains(integers,dictKey)){
				dicts1.add(dict);
			}
		});
		List<Unit> unitList=unitService.list(new QueryWrapper<Unit>().eq("project_id",projectInfo.getId()));
		model.addAttribute("unittype",unittypeList);
		model.addAttribute("dicts",dicts1);
		model.addAttribute("unitList",unitList);

		//360view
		List<ProjectAllview> ProjectAllviewlist=projectAllviewService.list(Wrappers.<ProjectAllview>query().lambda().eq(ProjectAllview::getIsDeleted,0).eq(ProjectAllview::getProjectId,projectinfodetail.getIdCopy()));
		model.addAttribute("ProjectAllviewlist",ProjectAllviewlist);

		model.addAttribute("model", "project");
		setAttrByProject(model);
		return "project";
	}


	@RequestMapping("/userCenter")
	public String userCenter(HttpServletRequest request,Model model) {//首页
		String prefixUrl = bladeProperties.get("prefixUrl");
		String requestUrl = bladeProperties.get("requestUrl");
		//显示图片前缀
		model.addAttribute("prefixUrl",prefixUrl);
		//发送请求地址前缀
		model.addAttribute("requestUrl",requestUrl);
		HttpSession session=request.getSession();
		//填报意向时，获取意向下拉列表
		List<ProjectInfo> projectInfoList= projectInfoService.list();
		List<Map<String,String>> projectinfonames=new ArrayList<>();
		for (ProjectInfo projectInfo:projectInfoList){
			Map<String,String> mapinfo=new HashMap<>();
			mapinfo.put(projectInfo.getName(),projectInfo.getId().toString());
			projectinfonames.add(mapinfo);
		}
		model.addAttribute("projectinfonames",projectinfonames);
		if(session.getAttribute("userid")!=null){
			model.addAttribute("userid",session.getAttribute("userid"));
		}
		if(session.getAttribute("identity")!=null){
			model.addAttribute("identity",session.getAttribute("identity"));
		}
		if(session.getAttribute("statusCode")!=null){
			model.addAttribute("statusCode",session.getAttribute("statusCode"));
		}
		if(session.getAttribute("identity")!=null){
			if(session.getAttribute("identity").equals("agent")){
				model.addAttribute("userinfo",userAgentService.getOne(new QueryWrapper<UserAgent>().eq("id",session.getAttribute("userid"))) );
				return "userCenter";
			}
			if (session.getAttribute("identity").equals("buyer")) {
				model.addAttribute("userinfo",userBuyerService.getOne(new QueryWrapper<UserBuyer>().eq("id",session.getAttribute("userid"))) );
			}
		}

		model.addAttribute("model", "usercenter");
		setAttrByProject(model);
		return "userCenter";
	}

	@RequestMapping("/userCenter/logout")
	public String logout(HttpServletRequest request,Model model) {//首页

		HttpSession session=request.getSession();

//		session.removeAttribute("userid");
//		session.removeAttribute("identity");
//		session.removeAttribute("statusCode");
//		session.removeAttribute("userInfo");
		session.invalidate();
		setAttrByProject(model);
		return "redirect:/";
	}

	@RequestMapping("/project/logout")
	public String projectLogout(ProjectInfo projectInfo,HttpServletRequest request,Model model) {//首页

		HttpSession session=request.getSession();

//		session.removeAttribute("userid");
//		session.removeAttribute("identity");
//		session.removeAttribute("statusCode");
//		session.removeAttribute("userInfo");
		session.invalidate();
		return project(projectInfo,request,model);
	};

	@RequestMapping("/userCenter/profile")
	public String profile(HttpServletRequest request,Model model) {//个人信息

		if (existUser(request, model)) return "redirect:/";

		// 以上部分每个用户中心的功能基本一样


		// 设置页面标识
		model.addAttribute("model", "usercenter");
		model.addAttribute("page", "profile");


		setAttrByProject(model);
		return "userCenter/profile";
	}

	@RequestMapping("/userCenter/changePassword")
	public String changePassword(HttpServletRequest request,Model model) {//个人信息

		if (existUser(request, model)) return "redirect:/";

		// 以上部分每个用户中心的功能基本一样


		// 设置页面标识
		model.addAttribute("model", "usercenter");
		model.addAttribute("page", "changePassword");


		setAttrByProject(model);
		return "userCenter/changePassword";
	}
//	@RequestMapping("/userCenter/myApplication")
	public String myApplication(String id, HttpServletRequest request,Model model) {//个人信息

		if (existUser(request, model)) return "redirect:/";

		// 以上部分每个用户中心的功能基本一样


		// 设置页面标识
		model.addAttribute("model", "usercenter");
		model.addAttribute("page", "myApplication");

		HttpSession session = request.getSession();

		if(id == null) {
			List<UserApplication> userApplicationList = userApplicationService.list(new QueryWrapper<UserApplication>().eq("userid",session.getAttribute("userid")));
			model.addAttribute("applicationList", UserApplicationWrapper.build().listVO(userApplicationList));

			return "userCenter/myApplication";
		} else {
			UserApplication userApplication = userApplicationService.getOne(new QueryWrapper<UserApplication>().eq("id", id));
			// 如果查不出项目，返回选择项目页
			if(userApplication == null) {
				return "redirect:/userCenter/myApplication";
			}
			UserApplicationVO copy = Func.copy(userApplication, UserApplicationVO.class);
			copy.setChoiceFormat(new HashMap<>(4));
			Map<Integer,List<String>> map = new HashMap(4);
			map.put(1,Func.toStrList("-",userApplication.getChoice01()));
			map.put(2,Func.toStrList("-",userApplication.getChoice02()));
			map.put(3,Func.toStrList("-",userApplication.getChoice03()));
			for (int i = 1; i <4 ; i++) {
				int num = 0;
				copy.getChoiceFormat().put("choice0"+i+"Block","");
				copy.getChoiceFormat().put("choice0"+i+"Level","");
				copy.getChoiceFormat().put("choice0"+i+"Unit","");
				copy.getChoiceFormat().put("choice0"+i+"Type","");
				for (String str : map.get(i)) {
					num++;
					switch (num) {
						case 1:
							copy.getChoiceFormat().put("choice0"+i+"Block",str);
							break;
						case 2:
							copy.getChoiceFormat().put("choice0"+i+"Level",str);
							break;
						case 3:
							copy.getChoiceFormat().put("choice0"+i+"Unit",str);
							break;
						case 4:
							copy.getChoiceFormat().put("choice0"+i+"Type",str);
							break;
					}
				}
			}


			model.addAttribute("detail", copy);

			setAttrByProject(model);
			return "userCenter/myApplicationShow";
		}


	}
	@RequestMapping("/userCenter/myApplication")
	public String myApplication2(String id, HttpServletRequest request,Model model) {//个人信息
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		if (existUser(request, model)) return "redirect:/";

		setAttrByProject(model);
		// 以上部分每个用户中心的功能基本一样


		// 设置页面标识
		model.addAttribute("model", "usercenter");
		model.addAttribute("page", "myApplication");

		HttpSession session = request.getSession();

		if(id == null) {
			//我的客户
			PurchaseIntentVO purchaseIntentVO = new PurchaseIntentVO();
			purchaseIntentVO.setCreateUser(Func.toLong(session.getAttribute("userid")));
			List<PurchaseIntentVO> applicationList = iPurchaseIntentService.list(purchaseIntentVO);
			model.addAttribute("applicationList", applicationList);
//			List<UserApplication> userApplicationList = userApplicationService.list(new QueryWrapper<UserApplication>().eq("userid",session.getAttribute("userid")));
//			model.addAttribute("applicationList", UserApplicationWrapper.build().listVO(userApplicationList));

			return "userCenter/myApplication2";
		} else {
			PurchaseIntent purchaseIntent = iPurchaseIntentService.getOne(new QueryWrapper<PurchaseIntent>().eq("id", id),false);
			// 如果查不出项目，返回选择项目页
			if(purchaseIntent == null) {
				return "redirect:/userCenter/myApplication2";
			}
			PurchaseIntentVO detailPro = PurchaseIntentWrapper.build().entityVO(purchaseIntent);
			model.addAttribute("detail", detailPro);
//			LocalDateTime mccConfirmTime = detailPro.getMccConfirmTime();
//			String s = mccConfirmTime.toString();
//			String s1 = DateTimeUtil.formatDateTime(mccConfirmTime);
//			LocalDateTime parse = LocalDateTime.parse(Func.toStr(detailPro.getMccConfirmTime()), df);
//			model.addAttribute("mccConfirmTime", LocalDateTime.parse(Func.toStr(detailPro.getMccConfirmTime()),df) );
			Date createTime = detailPro.getCreateTime();
			model.addAttribute("createTime", Func.formatDateTime(createTime));

			return "userCenter/myApplicationShow2";
		}


	}



	/**
	 * 我的预约（id为空展示列表 不为空则显示详情）
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("userCenter/myAppointment")
	public String myAppointment(String id, HttpServletRequest request,Model model){
		if (existUser(request, model)) return "redirect:/";

		setAttrByProject(model);
		// 以上部分每个用户中心的功能基本一样


		HttpSession session = request.getSession();

		if(id == null) {

			Long createUser = Func.toLong(session.getAttribute("userid"));
//			Long createUser = 1123598821738675201L;
			List<AppointmentData> appointmentDatas = iAppointmentDataService.list(new QueryWrapper<AppointmentData>().eq("create_user", createUser));
			List<AppointmentDataVO> appointmentDataVOS = AppointmentDataWrapper.build().listVO(appointmentDatas);
			for (AppointmentDataVO appointmentDataVO : appointmentDataVOS){
				AppointmentShiftData appointmentShiftData = iAppointmentShiftDataService.getById(appointmentDataVO.getShiftDataId());
				AppointmentScheduling scheduling = iAppointmentSchedulingService.getById(appointmentDataVO.getSchedulingId());
				ProjectInfo projectInfo = projectInfoService.getOne(new QueryWrapper<ProjectInfo>().eq("id", appointmentDataVO.getProjectId()));
				appointmentDataVO.setProjectName(projectInfo.getName());
				appointmentDataVO.setAppointmentShiftData(appointmentShiftData);
				appointmentDataVO.setAppointmentScheduling(scheduling);
			}
			model.addAttribute("appointmentList", appointmentDataVOS);

			return "userCenter/myAppointmentList";
		} else {

			AppointmentData appointmentData = iAppointmentDataService.getOne(new QueryWrapper<AppointmentData>().eq("id", id));

			if(appointmentData == null){
				return "redirect:/userCenter/myAppointment2";
			}
			AppointmentDataVO appointmentDataVO = AppointmentDataWrapper.build().entityVO(appointmentData);
			AppointmentShiftData appointmentShiftData = iAppointmentShiftDataService.getById(appointmentDataVO.getShiftDataId());
			AppointmentScheduling scheduling = iAppointmentSchedulingService.getById(appointmentDataVO.getSchedulingId());
			ProjectInfo projectinfodetail = projectInfoService.getOne(new QueryWrapper<ProjectInfo>().eq("id",appointmentDataVO.getProjectId()));
			appointmentDataVO.setProjectName(projectinfodetail.getName());
			appointmentDataVO.setAppointmentShiftData(appointmentShiftData);
			appointmentDataVO.setAppointmentScheduling(scheduling);
			model.addAttribute("detail", appointmentDataVO);

			return "userCenter/myAppointmentDetail";
		}

	}

	@RequestMapping("/userCenter/apponitmentSelect")
	public String apponitmentSelect(Long projectId, HttpServletRequest request,Model model,String startDate, String endDate) {
		if (existUser(request, model)) return "redirect:/";
		setAttrByProject(model);
		// 以上部分每个用户中心的功能基本一样
		// 设置页面标识
		model.addAttribute("model", "usercenter");
		model.addAttribute("page", "appointment");
		if(projectId == null) {
			List<ProjectInfo> projectList= projectInfoService.list(new QueryWrapper<ProjectInfo>().eq("open_status", 4));
			model.addAttribute("projectList", projectList);
			return "userCenter/apponitmentSelect";
		} else {
			if (startDate != null && endDate != null){
				List<Map<String ,Object>> list = iAppointmentSchedulingService.schedulingInfo(startDate, endDate ,projectId);
				model.addAttribute("shiftlist", list);

			}
			return "userCenter/myAppointmentListDate";
		}
	}

//	@RequestMapping("/userCenter/appointmentDaily")
//	public String appointmentDaily(ProjectInfo projectInfo,HttpServletRequest request,Model model) {//个人信息
//
//		if (existUser(request, model)) return "redirect:/";
//		setAttrByProject(model);
//		return "userCenter/appointmentDaily";
//	}

	/**
	 * 预约看房
	 * @param projectId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("userCenter/appointment")
	public String appointment(Integer id, String projectId, HttpServletRequest request,Model model){
		if (existUser(request, model)) return "redirect:/";

		setAttrByProject(model);
		HttpSession session = request.getSession();
		// 以上部分每个用户中心的功能基本一样
		model.addAttribute("model", "usercenter");
		model.addAttribute("page", "appointment");
		if(projectId == null) {
			Long createUser = Func.toLong(session.getAttribute("userid"));
//			Long createUser = 1123598821738675201L;
			List<AppointmentData> appointmentDatas = iAppointmentDataService.list(new QueryWrapper<AppointmentData>().eq("create_user", createUser));
			List<AppointmentDataVO> appointmentDataVOS = AppointmentDataWrapper.build().listVO(appointmentDatas);
			for (AppointmentDataVO appointmentDataVO : appointmentDataVOS){
				AppointmentShiftData appointmentShiftData = iAppointmentShiftDataService.getById(appointmentDataVO.getShiftDataId());
				AppointmentScheduling scheduling = iAppointmentSchedulingService.getById(appointmentDataVO.getSchedulingId());
				ProjectInfo projectInfo = projectInfoService.getOne(new QueryWrapper<ProjectInfo>().eq("id", appointmentDataVO.getProjectId()));
				appointmentDataVO.setProjectName(projectInfo.getName());
				appointmentDataVO.setAppointmentShiftData(appointmentShiftData);
 				appointmentDataVO.setAppointmentScheduling(scheduling);
			}
			model.addAttribute("appointmentList", appointmentDataVOS);
			if(id != null){
				AppointmentData appointmentData = iAppointmentDataService.getOne(new QueryWrapper<AppointmentData>().eq("id", id));

				if(appointmentData == null){
					return "redirect:/userCenter/appointment";
				}
				AppointmentDataVO appointmentDataVO = AppointmentDataWrapper.build().entityVO(appointmentData);
				AppointmentShiftData appointmentShiftData = iAppointmentShiftDataService.getById(appointmentDataVO.getShiftDataId());
				AppointmentScheduling scheduling = iAppointmentSchedulingService.getById(appointmentDataVO.getSchedulingId());
				ProjectInfo projectinfodetail = projectInfoService.getOne(new QueryWrapper<ProjectInfo>().eq("id",appointmentDataVO.getProjectId()));
				appointmentDataVO.setProjectName(projectinfodetail.getName());
				appointmentDataVO.setAppointmentShiftData(appointmentShiftData);
				appointmentDataVO.setAppointmentScheduling(scheduling);
				model.addAttribute("detail", appointmentDataVO);
				String ID = appointmentDataVO.getId().toString();
				model.addAttribute("ID", ID);
				return "userCenter/appointmentShow";
			}
			return "userCenter/appointment";
		} else {
			List<AppointmentConfig> config = iAppointmentConfigService.selectAll(new QueryWrapper<AppointmentConfig>().eq("a.project_id", projectId));
			// 如果查不出项目，返回选择项目页
			if(config == null) {
				return "redirect:/userCenter/myAppointmentList";
			}

			model.addAttribute("appointmentProject", config);
			return "userCenter/appointment";
		}


	}

	/**
	 * 排班列表（含班次场次信息）
	 * @param request
	 * @param model
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	@RequestMapping("userCenter/appointmentScheduling")
	public String schedulingInfo(HttpServletRequest request,Model model, String startDate, String endDate,Long projectId){
		if (existUser(request, model)) return "redirect:/";

		setAttrByProject(model);
		// 以上部分每个用户中心的功能基本一样

		List<Map<String ,Object>> list = iAppointmentSchedulingService.schedulingInfo(startDate, endDate ,projectId);
		model.addAttribute("schedulingInfo",list);

		return "userCenter/appointmentScheduling";
	}

	@RequestMapping("/userCenter/myAppointmentCount")
	public String myAppointmentCount(HttpServletRequest request,Model model, String startDate, String endDate){

		if (existUser(request, model)) return "redirect:/";

		setAttrByProject(model);
		// 以上部分每个用户中心的功能基本一样

		HttpSession session = request.getSession();

		Long  createUser = Func.toLong(session.getAttribute("userid"));
//		Long createUser =  1123598821738675201L;

		List<Map<String, Object>> list = iAppointmentDataService.appointmentDataCount(createUser, startDate, endDate);
		model.addAttribute("appointmentCount",list);
		return "userCenter/myAppointmentCount";
	}

	@RequestMapping("/userCenter/myProperties")
	public String myProperties(String id,HttpServletRequest request,Model model) {//个人信息

		if (existUser(request, model)) return "redirect:/";

		setAttrByProject(model);
		HttpSession session = request.getSession();
		// 以上部分每个用户中心的功能基本一样

		// 设置页面标识
		model.addAttribute("model", "usercenter");
		model.addAttribute("page", "myProperties");


		if(id == null) {
			List<PurchaseOrder> purchaseOrderList = iPurchaseOrderService.list(new LambdaQueryWrapper<PurchaseOrder>()
				.eq(PurchaseOrder::getCreateUser, Func.toLong(session.getAttribute("userid"))));
			model.addAttribute("orderList", PurchaseOrderWrapper.build().listVO(purchaseOrderList));
			return "userCenter/myProperty";
		} else {
			PurchaseOrder purchaseOrder = iPurchaseOrderService.getOne(new QueryWrapper<PurchaseOrder>().eq("id", id));
			// 如果查不出项目，返回选择项目页
			if(purchaseOrder == null) {
				List<PurchaseOrder> purchaseOrderList = iPurchaseOrderService.list(new LambdaQueryWrapper<PurchaseOrder>()
					.eq(PurchaseOrder::getCreateUser, Func.toLong(session.getAttribute("userid"))));
				model.addAttribute("orderList", PurchaseOrderWrapper.build().listVO(purchaseOrderList));
				return "userCenter/myProperty";
			}
			PurchaseOrderVO orderVO = PurchaseOrderWrapper.build().entityVO(purchaseOrder);
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
			model.addAttribute("detail", orderVO);
			Date date = orderVO.getCreateTime();
			String createTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
//			String formatStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			model.addAttribute("createTime", createTime);
			return "userCenter/orderShow";
		}

	}

	@RequestMapping("/userCenter/myClients")
	public String myClients(String id, String name,Long projectId, HttpServletRequest request,Model model,Integer size,Integer current) {//个人信息

		if (existUser(request, model)) return "redirect:/";

		setAttrByProject(model);
		HttpSession session = request.getSession();
		// 以上部分每个用户中心的功能基本一样
		// 设置页面标识
		model.addAttribute("model", "usercenter");
		model.addAttribute("page", "myClients");
		String option = "请选择项目";
		if(id == null) {
			IPage<PurchaseIntentUserVO> page = Condition.getPage(new Query().setSize(size).setCurrent(current));
			PurchaseIntentUserVO userVO = new PurchaseIntentUserVO();
			if(name != null){
				userVO.setName(name);
			}
			if(projectId != null){
				userVO.setProjectId(projectId);
			}
			userVO.setAgentId(Func.toLong(session.getAttribute("userid")));
			page = iPurchaseIntentUserService.selectByAgent(page, userVO);
			if(projectId != null){
				List<PurchaseIntentUserVO> optionVO = page.getRecords();
				for (int i = 0; i < optionVO.size(); i++) {
					option = optionVO.get(i).getProjectName();
				}
//				option = page.getRecords().get(0).getProjectName();
			}
			Integer allPage = Func.toInt(page.getTotal()/page.getSize())+1;
			List<ProjectInfo> infoList= projectInfoService.list();
			model.addAttribute("infoList", infoList);
			model.addAttribute("option", option);
			model.addAttribute("Clients", page);
			model.addAttribute("allPage", allPage);
			return "userCenter/myClients";
		}else {
//			PurchaseIntentUser purchaseIntentUser = iPurchaseIntentUserService.getOne(new QueryWrapper<PurchaseIntentUser>().eq("id", id));
			// 如果查不出项目，返回选择项目页
			PurchaseIntentUser purchaseIntentUser = iPurchaseIntentUserService.getById(id);
			if(purchaseIntentUser == null) {
				return "redirect:/userCenter/myClients";
			}
			model.addAttribute("detail", purchaseIntentUser);
			return "userCenter/myClientsShow";
		}
	}

//	@RequestMapping("/userCenter/application")
	public String application(String projectId, HttpServletRequest request,Model model) {//个人信息


		if (existUser(request, model)) return "redirect:/";

		setAttrByProject(model);
		// 以上部分每个用户中心的功能基本一样

		// 设置页面标识
		model.addAttribute("model", "application2");

		if(projectId == null) {
			List<ProjectInfo> projectList= projectInfoService.list(new QueryWrapper<ProjectInfo>().eq("open_status", 4));
			model.addAttribute("projectList", projectList);
			return "userCenter/selectApplicationProject";
		} else {
			ProjectInfo project = projectInfoService.getOne(new QueryWrapper<ProjectInfo>().eq("id", projectId));
			// 如果查不出项目，返回选择项目页
			if(project == null) {
				return "redirect:/userCenter/application";
			}
			List<ProjectUnittype> unittypeList=projectUnittypeService.list(new QueryWrapper<ProjectUnittype>().eq("project_id",projectId));
			model.addAttribute("unitType", unittypeList);

			List<Unit> unitList=unitService.list(new QueryWrapper<Unit>().eq("project_id", projectId));
			model.addAttribute("unitList",unitList);

			List<Dict> nationality = dictService.list(new QueryWrapper<Dict>().eq("code","nationality").ne("parent_id", 0).orderByAsc("sort"));
			model.addAttribute("nationality", nationality);

			model.addAttribute("project", project);
			return "userCenter/application2";
		}

	}
	@RequestMapping("/userCenter/application")
	public String application2(String projectId, HttpServletRequest request,Model model) {//个人信息

		if (existUser(request, model)) return "redirect:/";

		setAttrByProject(model);
		// 以上部分每个用户中心的功能基本一样

		// 设置页面标识
		model.addAttribute("model", "application2");

		if(projectId == null) {
			List<ProjectInfo> projectList= projectInfoService.list(new QueryWrapper<ProjectInfo>().eq("open_status", 4));
			model.addAttribute("projectList", projectList);
			return "userCenter/selectApplicationProject";
		} else {
			ProjectInfo project = projectInfoService.getOne(new QueryWrapper<ProjectInfo>().eq("id", projectId));
			// 如果查不出项目，返回选择项目页
			if(project == null) {
				return "redirect:/userCenter/application";
			}
			List<ProjectUnittype> unittypeList=projectUnittypeService.list(new QueryWrapper<ProjectUnittype>().eq("project_id",projectId));
			model.addAttribute("unitType", unittypeList);

			List<Unit> unitList=unitService.list(new QueryWrapper<Unit>().eq("project_id", projectId));
			model.addAttribute("unitList",unitList);

			List<Dict> nationality = dictService.list(new QueryWrapper<Dict>().eq("code","nationality").ne("parent_id", 0).orderByAsc("sort"));
			model.addAttribute("nationality", nationality);

			model.addAttribute("project", project);

//			List<Dict> agencylist = dictService.getList("Agency");
//			String agency = project.getAgency();
//			List agencys = new ArrayList();
//			if(Func.isNotEmpty(agency)){
//				String [] agencyslist = agency.split(",");
//				for (int i = 0; i <agencyslist.length ; i++) {
//					Integer agencyint =Integer.parseInt(agencyslist[i]);
//					agencys.add(agencylist.get(agencyint - 1));
//				}
//			}

			List<AgencyProject> agencyProjectList = agencyProjectService.list(
				new LambdaQueryWrapper<AgencyProject>().eq(AgencyProject::getProjectId, projectId)
			);
			List<Agency> agencyList = new ArrayList<>();
			if(Func.isNotEmpty(agencyProjectList)){
				agencyList = agencyService.list(new LambdaQueryWrapper<Agency>()
					.in(Agency::getId,agencyProjectList.stream().map(AgencyProject::getAgencyId).collect(Collectors.toList()))
				);
			}
			model.addAttribute("agency",agencyList);
			return "userCenter/application2";
		}


	}

//	@RequestMapping("/userCenter/agentConfirm")
	public String agentConfirm(String projectId, HttpServletRequest request,Model model) {//个人信息

		if (existUser(request, model)) return "redirect:/";

		setAttrByProject(model);
		// 以上部分每个用户中心的功能基本一样


		// 设置页面标识
		model.addAttribute("model", "usercenter");
		model.addAttribute("page", "agentConfirm");


		return "userCenter/confirmApplication";
	}

	@RequestMapping("/userCenter/agentConfirm")
	public String agentConfirm2(String projectId, HttpServletRequest request,Model model) {//个人信息

		if (existUser(request, model)) return "redirect:/";

		setAttrByProject(model);
		// 以上部分每个用户中心的功能基本一样


		// 设置页面标识
		model.addAttribute("model", "usercenter");
		model.addAttribute("page", "agentConfirm2");


		return "userCenter/confirmApplication2";
	}



	@RequestMapping("/userCenter/buy")
	public String buy(HttpServletRequest request,Model model) {//购房列表

		if (existUser(request, model)) return "redirect:/";
		// 以上部分每个用户中心的功能基本一样

		setAttrByProject(model);
		// 获取项目列表
		List<ProjectInfo> projectList= projectInfoService.list(new QueryWrapper<ProjectInfo>().in("open_status", 1,2));
		model.addAttribute("projectList", projectList);

		model.addAttribute("model", "buy");


		return "userCenter/buy";
	}

	@RequestMapping("/userCenter/disclaimer")
	public String disclaimer(HttpServletRequest request,Model model) {//购房列表

		if (existUser(request, model)) return "redirect:/";
		// 以上部分每个用户中心的功能基本一样

		setAttrByProject(model);
		// 获取项目列表
		List<ProjectInfo> projectList= projectInfoService.list(new QueryWrapper<ProjectInfo>().in("open_status", 1,2));
		model.addAttribute("projectList", projectList);

		model.addAttribute("model", "disclaimer");


		return "userCenter/disclaimer";
	}

	@RequestMapping("/finance")
	public String finance(HttpServletRequest request,Model model) {//金融
		HttpSession session = request.getSession();

		// 获取用户信息
		model.addAttribute("userid",session.getAttribute("userid"));
		model.addAttribute("identity", session.getAttribute("identity"));
		model.addAttribute("userInfo", session.getAttribute("userInfo"));

		//显示图片前缀
		String prefixUrl = bladeProperties.get("prefixUrl");
		String requestUrl = bladeProperties.get("requestUrl");
		model.addAttribute("prefixUrl",prefixUrl);
		model.addAttribute("requestUrl",requestUrl);

		model.addAttribute("model", "finance");
		setAttrByProject(model);
		return "finance";
	}
	@RequestMapping("/userCenter/openingQuotation")
	public String openingQuotation(String id, HttpServletRequest request, Model model) {//个人信息

		if (existUser(request, model)) return "redirect:/";

		setAttrByProject(model);
		// 以上部分每个用户中心的功能基本一样

		ProjectInfo projectinfodetail = projectInfoService.getOne(new QueryWrapper<ProjectInfo>().eq("id",id));

		ProjectInfoVO pvo= ProjectInfoWrapper.build().entityVO(projectinfodetail);
		List<ProjectEvent>  projectEventList=pvo.getProjectEventList();
		String prefixUrl = bladeProperties.get("prefixUrl");
		//活动列表
		model.addAttribute("projectEventList",projectEventList);
		// 设置页面标识
		model.addAttribute("model", "usercenter");
		model.addAttribute("page", "openingQuotation");
		model.addAttribute("projectId", id);
		model.addAttribute("prefixUrl",prefixUrl);
		model.addAttribute("project", projectinfodetail);


		return "userCenter/openingQuotation";
	}

	@RequestMapping("/userCenter/daily")
	public String daily(ProjectInfo projectInfo,HttpServletRequest request,Model model) {//个人信息

		if (existUser(request, model)) return "redirect:/";

		setAttrByProject(model);

		// 设置页面标识
		model.addAttribute("model", "usercenter");
		model.addAttribute("page", "daily");

		List<ProjectUnittype> unittypeList=projectUnittypeService.list(new QueryWrapper<ProjectUnittype>().eq("project_id",projectInfo.getId()));
		List<Unit> unitList=unitService.list(new QueryWrapper<Unit>().eq("project_id", projectInfo.getId()));
		String prefixUrl = bladeProperties.get("prefixUrl");
		ProjectInfo project = projectInfoService.getOne(new QueryWrapper<ProjectInfo>().eq("id",projectInfo.getId()));
		List<Dict> nationality = dictService.list(new QueryWrapper<Dict>().eq("code","nationality").ne("parent_id", 0).orderByAsc("sort"));
//		List<Dict> agencylist = dictService.getList("Agency");
//		String agency = project.getAgency();
//		String [] agencyslist = agency.split(",");
//		List agencys = new ArrayList();
//		for (int i = 0; i <agencyslist.length ; i++) {
//			Integer agencyint =Integer.parseInt(agencyslist[i]);
//			agencys.add(agencylist.get(agencyint - 1));
//		}

		List<AgencyProject> agencyProjectList = agencyProjectService.list(
			new LambdaQueryWrapper<AgencyProject>().eq(AgencyProject::getProjectId, projectInfo.getId())
		);
		List<Agency> agencyList = new ArrayList<>();
		if(Func.isNotEmpty(agencyProjectList)){
			agencyList = agencyService.list(new LambdaQueryWrapper<Agency>()
				.in(Agency::getId,agencyProjectList.stream().map(AgencyProject::getAgencyId).collect(Collectors.toList()))
			);
		}
		model.addAttribute("agency",agencyList);
//		model.addAttribute("agency",agencys);
		model.addAttribute("unittype",unittypeList);
		model.addAttribute("unitList",unitList);
		model.addAttribute("prefixUrl",prefixUrl);
		model.addAttribute("nationality", nationality);
		model.addAttribute("project",project);


		return "userCenter/daily";
	}
	@RequestMapping("/client/active")
	public String active(String code,HttpServletRequest request, Model model) {//激活
		Integer active = userBuyerService.active(code);
		return activeFunc(active,model);
	}
	@RequestMapping("/client/agentActive")
	public String agentActive(String code,HttpServletRequest request, Model model) {//激活
		Integer active = userAgentService.active(code);
		return activeFunc(active,model);
	}

	@RequestMapping("/index/resetPassword")
	public String resetPassword(HttpServletRequest request, Model model,String code ,String username,String identity) {
		model.addAttribute("code",code);
		model.addAttribute("username",username);
		model.addAttribute("identity",identity);
		model.addAttribute("PAGEID","register");
		return index2(request,model,identity);
	}

	@RequestMapping("/H5Application")
	public String H5Application(PurchaseIntent purchaseIntent, HttpServletRequest request, Model model) {
		PurchaseIntent detail = iPurchaseIntentService.getOne(Condition.getQueryWrapper(purchaseIntent));
		PurchaseIntentVO detailPro = PurchaseIntentWrapper.build().entityVO(detail);
		model.addAttribute("data",detailPro);
		return "index/H5Application";
	}

	public String index2(HttpServletRequest request,Model model,String identity) {//首页
		HttpSession session = request.getSession();
		//显示图片前缀
		String prefixUrl = bladeProperties.get("prefixUrl");
		String requestUrl = bladeProperties.get("requestUrl");

//		Map<String, Object> homepage = new HashMap<>();
//		if(redisUtil.hasKey(CacheNames.HOMEPAGE)) {
//			Map<Object,Object> _homepage = redisUtil.hmget(CacheNames.HOMEPAGE);
//
//
////			homepage = _homepage.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new Object(e.getValue())));
////			homepage = new HashMap<String, Object>(_homepage);
//			List<Object> centers = (List<Object>) _homepage.get("centers");
//			List<Object> headers = (List<Object>) _homepage.get("headers");
//			List<Object> listDataTemp = (List<Object>) _homepage.get("listDataTemp");
//
//		} else {
		List<ProjectInfo> centers = projectInfoService.list(Wrappers.<ProjectInfo>query().lambda().eq(ProjectInfo::getIsDeleted,0).eq(ProjectInfo::getIsPush,0));
		//		request.setAttribute("name", name);
		for(ProjectInfo project:centers){
			project.setIdCopy(project.getId().toString());
		}
//			homepage.put("centers", centers);
		//		redisUtil.lSet(CacheNames.HOMEPAGE + "centers", centers2);


		// 首页轮播图
		List<IndexShow> headers = indexShowService.list(Wrappers.<IndexShow>query().lambda().eq(IndexShow::getIsDeleted, 0).eq(IndexShow::getIsPush,0).orderByDesc(IndexShow::getSort));
		for(IndexShow index:headers){
			index.setPidCopy(index.getProjectId().toString());
		}
//			homepage.put("headers", headers);


		//地图返回信息
		List<ProjectInfo> listDataTemp = projectInfoService.list(Wrappers.<ProjectInfo>query().lambda().eq(ProjectInfo::getIsDeleted,0));

		for(ProjectInfo project:listDataTemp){
			Integer count = unitService.count(
				new LambdaQueryWrapper<Unit>()
					.eq(Unit::getProjectId, project.getId())
					.eq(Unit::getSaleStatus, 0)
					.eq(Unit::getIsDeleted, 0)
			);
			project.setCansaleunits(count);
		}

//			homepage.put("listDataTemp", listDataTemp);
//			redisUtil.hmset(CacheNames.HOMEPAGE, homepage);

//		}



		setAttrByProject(model);

		// 字典

		List<Dict> nationality = dictService.list(new QueryWrapper<Dict>().eq("code","nationality").ne("parent_id", 0).orderByAsc("sort"));
		List<Dict> locationType = dictService.getList("locationType");
		List<Dict> bedroomsType = dictService.getList("bedroomsType");
		List<Dict> statusType = dictService.getList("statusType");
		List<Dict> constructionType = dictService.getList("constructionType");



		// model 数据注入
		model.addAttribute("model", "index");
		// 获取用户信息
		model.addAttribute("userid",session.getAttribute("userid"));
		model.addAttribute("identity", identity);
		model.addAttribute("userInfo", session.getAttribute("userInfo"));
		//图片地址前缀
		model.addAttribute("prefixUrl",prefixUrl);
		//发送请求地址前缀
		model.addAttribute("requestUrl",requestUrl);
		//头部轮播图
		model.addAttribute("headers",headers);
		//中间项目轮播图
		model.addAttribute("centers",centers);

		model.addAttribute("listDataTemp",listDataTemp);

//		model.addAttribute("statusCode",0);
		// 字典数据
		model.addAttribute("nationality", nationality);
		model.addAttribute("locationType",locationType);
		model.addAttribute("bedroomsType",bedroomsType);
		model.addAttribute("statusType",statusType);
		model.addAttribute("constructionType",constructionType);


		return "index";
	}

	private String activeFunc(Integer active, Model model){

		String mes="";

		try
		{
			if (active == 1) {
				mes = "Activation succeeded";
			}else if(active == -1){
				mes = "This user has already been activated";
			}else{
				mes = "Activation failed";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// 设置页面标识
		model.addAttribute("model", "usercenter");
		model.addAttribute("page", "daily");
		model.addAttribute("message", mes);


		setAttrByProject(model);
		return "userCenter/agentActive";
	}
	private boolean existUser(HttpServletRequest request, Model model, Boolean inAgent) {
		HttpSession session = request.getSession();
		// 判断用户权限，如果没有权限暂时跳转到首页
		if(session.getAttribute("userid") == null){
			return true;
		}

		if(inAgent && session.getAttribute("identity") != "agent") {
			return true;
		}

		// 从session中获取用户信息
		model.addAttribute("userid",session.getAttribute("userid"));
		model.addAttribute("identity", session.getAttribute("identity"));
		model.addAttribute("userInfo", session.getAttribute("userInfo"));
		String prefixUrl = bladeProperties.get("prefixUrl");
		model.addAttribute("prefixUrl", prefixUrl);

		// 以上部分每个用户中心的功能基本一样
		return false;
	}
	private boolean existUser(HttpServletRequest request, Model model) {
		return this.existUser(request, model, false);
	}

	private void setAttrByProject(Model model){
		List<ProjectInfo> projects = projectInfoService.list(Wrappers.<ProjectInfo>query().lambda().eq(ProjectInfo::getIsDeleted,0));
		model.addAttribute("projects",projects);
	}
}
