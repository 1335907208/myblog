package org.springblade.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import org.springblade.client.eneity.ProjectUnittype;
import org.springblade.client.eneity.Unit;
import org.springblade.client.entity.*;
import org.springblade.client.service.*;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.RedisUtil;
import org.springblade.project.entity.ProjectInfo;
import org.springblade.project.service.IProjectInfoService;
import org.springblade.webuser.entity.UserBuyer;
import org.springblade.webuser.service.IUserBuyerService;
import org.springblade.webuser.vo.UserBuyerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("open")
public class OpenChooseController implements CommandLineRunner {
	@Autowired
	private IUserApplicationService userApplicationService;
	@Autowired
	private IProjectInfoService projectInfoService;
	@Autowired
	private IUserBuyerService userBuyerService;
	@Autowired
	private IUnitService unitService;
	@Autowired
	private IUserJackpotService userJackpotService;
	@Autowired
	private IUserOpeningService userOpeningService;
	@Autowired
	private IUserOrderService userOrderService;
	@Autowired
	private IUserJoinService userJoinService;
	@Autowired
	private RedisUtil redisUtil;

	//本项目全部可卖房子总数
	private Integer totalUnits;
	//本项目剩余可卖房子总数
	private Integer remainderUnits;
	//上一个选房的买家
	private String PREVSELECTIONNo;
	//当前选房的买家
	private String CURRENTSELECTIONNo;
	//下一个选房的买家
	private String NEXTSELECTIONNo;
	//xia下一个选房的买家
	private String NEXTNEXTSELECTIONNo;
	//剩余待抽的号码
	private Integer RemainderSELECTIONNo;
	//当前登录买家的抽签码
	private String  YourSelectionNo;
	//当前登录买家意向房间
	private List<String> YourInterestedUnits;
	//此项目本次预定抽奖人数
	private Integer Numberofdraw;
	//参加此项目抽签的所有用户
	private List<UserBuyerVO> allJoinDrawBuyerVOList;
	//抽出的号码的列表
	private List<String> queueDraw;
	//选出的抽签列表
	private List<String> allEoino4;


	/**
	 * 获取所有项目
	 */
	@GetMapping("/getAllProjects")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取所有项目", notes = "")
	public R getAllProjects(){
		//填报意向时，获取意向下拉列表
		List<ProjectInfo> projectInfoList= projectInfoService.list();
		List<Map<String,String>> projectinfonames=new ArrayList<>();
		for (ProjectInfo projectInfo:projectInfoList){
			Map<String,String> mapinfo=new HashMap<>();
			mapinfo.put("value",projectInfo.getId().toString());
			mapinfo.put("label",projectInfo.getName());
			projectinfonames.add(mapinfo);
		}
		return R.data(projectinfonames,"项目列表获取成功！");
	}

	public R selectJackpot(UserApplication application,HttpServletRequest request){
		List<UserApplication> userApplicationList=userApplicationService.list(new QueryWrapper<UserApplication>().eq("project_id",application.getProjectId()).isNotNull("EOINo"));
		List<UserBuyerVO> buyerList=new ArrayList<>();
		for(UserApplication userApplication:userApplicationList){
			UserBuyerVO buyer=new UserBuyerVO();
			buyer.setId(userApplication.getUserid());
			buyer.setEOINo(userApplication.getEOINo());
			buyerList.add(buyer);
		}
		return R.data(buyerList);
	}
	/**
	 * 开盘开始
	 */
	@GetMapping("/start")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "start", notes = "传入项目projectId,预定抽奖人数")
	public R start(HttpServletRequest request){
		Long projectid=Long.valueOf(request.getParameter("projectid"));
		String Numberofdraw=request.getParameter("Numberofdraw");
		HttpSession session=request.getSession();
		Integer maxNumberofdraw=Integer.valueOf(Numberofdraw);
		session.setAttribute("maxNumberofdraw",maxNumberofdraw);
		session.setAttribute("projectid",projectid);
		List<Unit> unitList=unitService.list(Wrappers.<Unit>query().lambda().eq(Unit::getProjectId,projectid).eq(Unit::getSaleStatus,0));
		Integer totalUnits=unitList.size();
		session.setAttribute("totalUnits",totalUnits);
		ProjectInfo projectInfo=projectInfoService.getOne(new QueryWrapper<ProjectInfo>().eq("id",projectid));
		projectInfo.setOpenStatus(1);
		projectInfoService.updateById(projectInfo);
		UserApplication application=new UserApplication();
		application.setProjectId(projectid);
		List<UserBuyerVO> allJoinDrawBuyerVOList=(List<UserBuyerVO>)selectJackpot(application,request).getData();
		session.setAttribute("allJoinDrawBuyerVOList",allJoinDrawBuyerVOList);
		Map<String,Object> data=new HashMap<>();
		data.put("allJoinDrawBuyerVOList",allJoinDrawBuyerVOList);
		data.put("projectid",projectid);
		UserOpening open=new UserOpening();
		open.setProjectId(projectid);
		UserOpening check=userOpeningService.getOne(new QueryWrapper<UserOpening>().eq("project_id",projectid));
		if(check==null){
			userOpeningService.save(open);
		}

		return R.data(data,"信息返回成功");
	}
	/**
	 * 点击抽签
	 */
	@GetMapping("/clickDraw")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "clickDraw", notes = "传入项目projectId")
	public R clickDraw(ProjectInfo info,HttpServletRequest request){
		HttpSession session=request.getSession();
		if(!session.getAttribute("projectid").equals(info.getId())){
			return null;
		}
		Map<String,Object> mess=(Map<String,Object>)luckDraw(request).getData();
		String choosenumber=mess.get("msg").toString();
		boolean b=(boolean)mess.get("b");
//		Queue<String> queueDraw = new LinkedList<String>();
//		queueDraw.offer(choosenumber);
//		session.setAttribute("queueDraw",queueDraw);
		Map<String,Object> data=new HashMap<>();
		data.put("choosenumber",choosenumber);
//		data.put("queueDraw",queueDraw);
//		List<String> lineList=new ArrayList<>();
//		session.setAttribute("lineList",lineList);
		return R.data(b,choosenumber);
	}
	/**
	 * 选出下一位选房的人，准备选房
	 */
	@GetMapping("/wait")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "wait", notes = "jackpot")
	public R wait(HttpServletRequest request){
		Long projectid=Long.valueOf(request.getParameter("projectid").toString());
		String EOINo4=request.getParameter("EOINo4");
		HttpSession session=request.getSession();
		if(!session.getAttribute("projectid").equals(projectid)){
			return null;
		}
//		Queue<String> queueDraw=(Queue<String>)session.getAttribute("queueDraw");
		UserOpening open=userOpeningService.getOne(new QueryWrapper<UserOpening>().eq("project_id",projectid));
		if(open.getCurent()==null&&open.getNexte()==null&&open.getNextnexte()==null){
			open.setCurent(EOINo4);
			userOpeningService.updateById(open);
			return R.data(open,"操作成功");
		}
		if(open.getCurent()!=null&&open.getNexte()==null&&open.getNextnexte()==null){
			open.setNexte(EOINo4);
			userOpeningService.updateById(open);
			return R.data(open,"操作成功");
		}
		if(open.getCurent()!=null&&open.getNexte()!=null&&open.getNextnexte()==null){
			open.setNextnexte(EOINo4);
			userOpeningService.updateById(open);
			return R.data(open,"操作成功");
		}
		if(open.getCurent()!=null&&open.getNexte()!=null&&open.getNextnexte()!=null){
			open.setNextnexte(EOINo4);
			userOpeningService.updateById(open);
			return R.data(open,"操作成功!");
		}
		return R.data(open,"操作失败");
	}
	/**
	 * 获取pre current next nextnext
	 */
	@GetMapping("/waitLine")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "wait", notes = "jackpot")
	public R waitLine(HttpServletRequest request){
		Long projectid=Long.valueOf(request.getParameter("projectid").toString());
		HttpSession session=request.getSession();
		if(!session.getAttribute("projectid").equals(projectid)){
			return null;
		}
		UserOpening open=userOpeningService.getOne(new QueryWrapper<UserOpening>().eq("project_id",projectid));

		return R.data(open,"数据返回成功");
	}

	/**
	 * 开始选房
	 */
	@GetMapping("/startChooseUnit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "startChooseUnit", notes = "jackpot")
	public R startChooseUnit(HttpServletRequest request){
		Long projectid=Long.valueOf(request.getParameter("projectid").toString());
		String EOINo4=request.getParameter("EOINo4");
		List<UserApplication> applicationList=userApplicationService.list(new QueryWrapper<UserApplication>().eq("project_id",projectid));
		for (UserApplication application1:applicationList){
			application1.setChooseStatus(0);
		}
		userApplicationService.updateBatchById(applicationList);
		UserApplication application=userApplicationService.getOne(new QueryWrapper<UserApplication>().eq("EOINo4",EOINo4).eq("project_id",projectid));
		application.setChooseStatus(1);
		userApplicationService.updateById(application);
		List<UserJackpot> jackpotList=userJackpotService.list(new QueryWrapper<UserJackpot>().eq("project_id",projectid).ne("EOINo4",EOINo4));
		for (UserJackpot jackpot1:jackpotList){
			jackpot1.setChooseStatus(0);
		}
		userJackpotService.updateBatchById(jackpotList);
		UserJackpot jackpot=userJackpotService.getOne(new QueryWrapper<UserJackpot>().eq("EOINo4",EOINo4).eq("project_id",projectid));
		jackpot.setChooseStatus(1);
		userJackpotService.updateById(jackpot);
		return R.data(EOINo4,"开始选房");
	}
	/**
	 * 结束选房
	 */
	@GetMapping("/endChooseUnit")
	@ApiOperationSupport(order =7)
	@ApiOperation(value = "nextChooseUnit", notes = "传入项目projectId")
	public R endChooseUnit(HttpServletRequest request){
		Long projectid=Long.valueOf(request.getParameter("projectid").toString());
		String EOINo4=request.getParameter("EOINo4");
		UserJackpot jackpot=userJackpotService.getOne(new QueryWrapper<UserJackpot>().eq("EOINo4",EOINo4).eq("project_id",projectid));
		jackpot.setChooseStatus(0);
		userJackpotService.updateById(jackpot);
		List<UserJackpot> jackpotList=userJackpotService.list(new QueryWrapper<UserJackpot>().eq("project_id",projectid));
		for (UserJackpot jackpot1:jackpotList){
			jackpot1.setChooseStatus(0);
		}
		userJackpotService.updateBatchById(jackpotList);
		List<UserApplication> applicationList=userApplicationService.list(new QueryWrapper<UserApplication>().eq("project_id",projectid));
		for (UserApplication application1:applicationList){
			application1.setChooseStatus(0);
		}
		userApplicationService.updateBatchById(applicationList);
		UserOpening open=userOpeningService.getOne(new QueryWrapper<UserOpening>().eq("project_id",projectid));
		open.setPrev(EOINo4);
		open.setCurent(open.getNexte());
		open.setNextnexte(null);
		userOpeningService.updateById(open);
		return R.data(open,"选房结束，数据返回");
	}
	/**
	 * 用户开盘界面数据展示开盘
	 */
	@GetMapping("/userDataShow")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "userDataShow", notes = "传入项目projectId")
	public R userDataShow(Unit unit,HttpServletRequest request){
		HttpSession session=request.getSession();
		List<Unit> unitList=unitService.list(Wrappers.<Unit>query().lambda().eq(Unit::getProjectId,unit.getProjectId()).eq(Unit::getSaleStatus,0));
		Integer remainderUnits=unitList.size();
		String userid=session.getAttribute("userid").toString();
		UserApplication applicationselect=new UserApplication();
		applicationselect.setId(unit.getProjectId());
		List<UserBuyerVO> allJoinDrawBuyerVOList=(List<UserBuyerVO>)selectJackpot(applicationselect,request).getData();
		session.setAttribute("allJoinDrawBuyerVOList",allJoinDrawBuyerVOList);
		//List<UserBuyerVO> allJoinDrawBuyerVOList=(List<UserBuyerVO>)session.getAttribute("allJoinDrawBuyerVOList");
		String YourSelectionNo="";

		UserApplication application=userApplicationService.getOne(Wrappers.<UserApplication>query().lambda().eq(UserApplication::getProjectId,unit.getProjectId()).eq(UserApplication::getUserid,userid));
		List<String> YourInterestedUnits=new ArrayList<>();
		if(!application.getChoice01().equals("")){
			YourInterestedUnits.add(application.getChoice01());
		}
		if(!application.getChoice02().equals("")){
			YourInterestedUnits.add(application.getChoice02());
		}
		if(!application.getChoice03().equals("")){
			YourInterestedUnits.add(application.getChoice03());
		}
		YourSelectionNo=application.getEOINo4();
		Long projectid=unit.getProjectId();
		UserOpening opening=userOpeningService.getOne(new QueryWrapper<UserOpening>().eq("project_id",projectid));
		List<Unit> units=unitService.list(Wrappers.<Unit>query().lambda().eq(Unit::getProjectId,projectid).eq(Unit::getSaleStatus,0));
		Integer totalUnits=unitList.size();
		Integer maxNumberofdraw=Integer.valueOf(session.getAttribute("maxNumberofdraw").toString());
		//返回界面显示的信息
		Map<String,Object> data=new HashMap<>();
		data.put("YourSelectionNo",YourSelectionNo);
		List<UserJackpot> co=userJackpotService.list();
		Integer RemainderSELECTIONNo=maxNumberofdraw-co.size();
		data.put("maxNumberofdraw",RemainderSELECTIONNo);
		data.put("totalUnits",totalUnits);
		data.put("remainderUnits",remainderUnits);
		data.put("YourInterestedUnits",YourInterestedUnits);
		data.put("waitinfo",opening);
		UserApplication application1=userApplicationService.getOne(new QueryWrapper<UserApplication>().eq("userid",userid).eq("project_id",projectid));
		String EoINo4=application1.getEOINo4();
		Integer choosestatus=application1.getChooseStatus();
		data.put("choosestatus",choosestatus);
		data.put("EoINo4",EoINo4);
		return R.data(data,"界面信息");
	}
	/**
	 * 买家选房
	 */
	@GetMapping("/chooseUnits")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "买家选房", notes = "传入unit")
	public R chooseUnits(HttpServletRequest request){
		Long projectId=Long.valueOf(request.getParameter("projectId"));
		Long unitId=Long.valueOf(request.getParameter("unitId"));
		HttpSession session=request.getSession();
		Unit unit=unitService.getOne(Wrappers.<Unit>query().lambda().eq(Unit::getId,unitId));
		Long userid=Long.valueOf(session.getAttribute("userid").toString());
		UserOrder order=new UserOrder();
		order.setUnitId(unitId);
		order.setUserId(userid);
		order.setBlock(unit.getBlock());
		order.setFloor(unit.getFloor());
		order.setUnit(unit.getUnit());
		UserApplication application=userApplicationService.getOne(new QueryWrapper<UserApplication>().eq("project_id",projectId).eq("userid",userid));
		order.setApplicationId(application.getId());
		order.setOrdermember(application.getOrdernumber());
		order.setEOINo(application.getEOINo());
		boolean b=userOrderService.save(order);
		String msg="";
		msg=b==true?"选房成功":"选房失败";
		return R.data(b,msg);
	}
	//在当前项目中从所有的四位抽签码中随机抽取一个号码
	public R luckDraw(HttpServletRequest request){
		HttpSession session=request.getSession();
		Long projectid=Long.valueOf(session.getAttribute("projectid").toString());
		Map<String,Object> data=new HashMap<>();
		UserJackpot jackpot=new UserJackpot();
		//List<UserBuyerVO> allJoinDrawBuyerVOLists=(List<UserBuyerVO>)session.getAttribute("allJoinDrawBuyerVOList");
		List<UserApplication> allJoinDrawBuyerVOLists=userApplicationService.list(new QueryWrapper<UserApplication>().eq("project_id",projectid).isNotNull("EOINo").eq("joinstatus",0));
		if(allJoinDrawBuyerVOLists.size()==0){
			data.put("b",false);
			data.put("msg","已抽完全部号码");
			return R.data(data,"数据返回！");
		}
		List<UserBuyerVO> buyerList=new ArrayList<>();
		for(UserApplication userApplication:allJoinDrawBuyerVOLists){
			UserBuyerVO buyer=new UserBuyerVO();
			buyer.setId(userApplication.getUserid());
			buyer.setEOINo(userApplication.getEOINo());
			buyerList.add(buyer);
		}
		List<UserJoin> joins=new ArrayList<>();
		allJoinDrawBuyerVOLists.forEach(lis ->{
			UserJoin join=new UserJoin();
			join.setUserid(lis.getId());
			join.setEOINo(lis.getEOINo());
			join.setJoinStatus(0);
			join.setProjectid(projectid);
			joins.add(join);
		});
		//boolean jo=userJoinService.saveOrUpdateBatch(joins);
		List<String> allEoino=new ArrayList<>();
		this.allEoino4=new ArrayList<>();
		//List<UserJoin> joinList=userJoinService.list(Wrappers.<UserJoin>query().lambda().eq(UserJoin::getProjectid,projectid).eq);
		for (UserApplication application:allJoinDrawBuyerVOLists){
			allEoino.add(application.getEOINo());
			allEoino4.add(application.getEOINo().substring(application.getEOINo().length()-4,application.getEOINo().length()));
		}
		int random = new Random().nextInt(allEoino4.size());
		String luck=allEoino4.remove(random);
		for(String eoino:allEoino){
			if(luck.equals(eoino.substring(eoino.length()-4,eoino.length()))){
				jackpot.setEOINo(eoino);
				UserApplication application=userApplicationService.getOne(new QueryWrapper<UserApplication>().eq("project_id",projectid).eq("EOINo",eoino));
				application.setJoinstatus(1);
				userApplicationService.updateById(application);
			}
		}
		jackpot.setEOINo4(luck);
		UserApplication application=userApplicationService.getOne(new QueryWrapper<UserApplication>().eq("project_id",projectid).eq("EOINo4",luck));
		Long userid=application.getUserid();

		jackpot.setProjectId(projectid);
		jackpot.setUserId(userid);
		jackpot.setApplicationId(application.getId());
		UserJackpot check=userJackpotService.getOne(new QueryWrapper<UserJackpot>().eq("EOINo4",luck));
		boolean b=true;
		if(check!=null){
			b=false;
			data.put("b",b);
			data.put("msg","已抽完全部号码");
			return R.data(data,"数据返回！");
		}
		UserJackpot one=userJackpotService.getOne(new QueryWrapper<UserJackpot>().eq("project_id",projectid).eq("user_id",userid).eq("application_id",application.getId()));
		if(one==null){
			userJackpotService.save(jackpot);
		}
		data.put("b",b);
		data.put("msg",luck);
		return R.data(data,"数据返回");
	}



	private R putLineUp(String line,HttpServletRequest request){
		Map<String,Object> data=new HashMap<>();
		HttpSession session=request.getSession();
		List<String> lineList=(List<String>)session.getAttribute("lineList");
		data.put("b",false);
		if(lineList.size()<4){
			lineList.add(line);
			data.put("line",lineList);
			return R.data(data,"操作成功！");
		}


		return R.data(data,"请稍后操作！");
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("正在加载项目信息。。。");
		System.out.println("加载项目信息成功！");
	}
}
