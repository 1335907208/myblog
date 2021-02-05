package org.springblade.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.client.entity.UserApplication;
import org.springblade.client.service.IUserApplicationService;
import org.springblade.client.util.MaillUtill;
import org.springblade.core.launch.props.BladeProperties;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.RandomType;
import org.springblade.purchaseIntent.entity.PurchaseIntent;
import org.springblade.purchaseIntent.entity.PurchaseOrder;
import org.springblade.purchaseIntent.service.IPurchaseIntentService;
import org.springblade.purchaseIntent.service.IPurchaseOrderService;
import org.springblade.purchaseIntent.vo.PurchaseIntentVO;
import org.springblade.purchaseIntent.wrapper.PurchaseIntentWrapper;
import org.springblade.webuser.entity.UserAgent;
import org.springblade.webuser.entity.UserBuyer;
import org.springblade.webuser.service.IUserAgentService;
import org.springblade.webuser.service.IUserBuyerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@AllArgsConstructor
@RequestMapping("client")
public class ClientController {
	private IUserBuyerService userBuyerService;
	private IUserApplicationService userApplicationService;
	private IUserAgentService userAgentService;
	private BladeProperties bladeProperties;
	private IPurchaseIntentService iPurchaseIntentService;
	private IPurchaseOrderService iPurchaseOrderService;
	public static final String EMAIL = "^\\w+([-+.]*\\w+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$";

	@RequestMapping("/byerRegister")
	public R byerRegister(UserBuyer userBuyer) {
		String username = userBuyer.getUsername();
		String pwd = userBuyer.getPassword();
		if(Func.isNotEmpty(username)&&Func.isNotEmpty(pwd)){
			Integer count = userBuyerService.count(
				new LambdaQueryWrapper<UserBuyer>()
					.eq(UserBuyer::getUsername, username)
			);
			if(count == 0){
				userBuyer.setPassword(DigestUtil.encrypt(pwd));
				userBuyer.setStatus(0);
				userBuyer.setCode(Func.randomUUID());
				if(userBuyerService.save(userBuyer)){
					Pattern pattern = Pattern.compile(EMAIL);
					Matcher matcher = pattern.matcher(username);
					if(matcher.matches()){
						new MaillUtill().send(username, userBuyer.getCode(), "active");
					}
					return R.success("注册成功");
				}else{
					return R.fail("注册失败");
				}
			}
		}
		return R.fail("用户已被注册,请重新注册！");
	}

	@RequestMapping("/buyerReSendMail")
	public R buyerReSendMail(UserBuyer userBuyer){
		String username = userBuyer.getUsername();
		if(Func.isNotEmpty(username)){
			UserBuyer buyer = userBuyerService.getOne(
				new LambdaQueryWrapper<UserBuyer>()
					.eq(UserBuyer::getUsername, username), false
			);
			if(Func.isNotEmpty(buyer)){
				buyer.setCode(Func.randomUUID());
				if(userBuyerService.updateById(buyer)){
					Pattern pattern = Pattern.compile(EMAIL);
					Matcher matcher = pattern.matcher(username);
					if(matcher.matches()){
						new MaillUtill().send(username, buyer.getCode(), "active");
					}
					return R.status(true);
				}
			}
		}
		return R.status(false);
	}

	@RequestMapping("/agentRegister")
	public R agentRegister(UserAgent userAgent) {
		String username = userAgent.getUsername();
		String pwd = userAgent.getPassword();
		if(Func.isNotEmpty(username)&&Func.isNotEmpty(pwd)){
			Integer count = userAgentService.count(
				new LambdaQueryWrapper<UserAgent>()
					.eq(UserAgent::getUsername,username)
			);
			if(count == 0){
				userAgent.setPassword(DigestUtil.encrypt(pwd));
				userAgent.setStatus(0);
				userAgent.setCode(Func.randomUUID());
				if(userAgentService.save(userAgent)){
					Pattern pattern = Pattern.compile(EMAIL);
					Matcher matcher = pattern.matcher(username);
					if(matcher.matches()){
						new MaillUtill().send(username, userAgent.getCode(), "agentActive");
					}
					return R.success("注册成功");
				}else{
					return R.fail("注册失败");
				}
			}
		}
		return R.fail("用户已被注册,请重新注册！");
	}

	@RequestMapping("/agentReSendMail")
	public R agentReSendMail(UserAgent userAgent){
		String username = userAgent.getUsername();
		if(Func.isNotEmpty(username)){
			UserAgent agent = userAgentService.getOne(
				new LambdaQueryWrapper<UserAgent>()
					.eq(UserAgent::getUsername, username), false
			);
			if(Func.isNotEmpty(agent)){
				agent.setCode(Func.randomUUID());
				if(userAgentService.updateById(agent)){
					Pattern pattern = Pattern.compile(EMAIL);
					Matcher matcher = pattern.matcher(username);
					if(matcher.matches()){
						new MaillUtill().send(username, userAgent.getCode(), "agentActive");
					}
					return R.status(true);
				}
			}
		}
		return R.status(false);
	}

	@RequestMapping("/byerLogin")
	public R byerLogin(HttpServletRequest request,UserBuyer userBuyer) {
		HttpSession session=request.getSession();
		QueryWrapper<UserBuyer> qw=new QueryWrapper<>();
		qw.eq("username",userBuyer.getUsername());
		qw.eq("password",DigestUtil.encrypt(userBuyer.getPassword()));
		//qw.and(wrapper->wrapper.eq("country",userBuyer.getCountry()).or().eq("mobile",userBuyer.getMobile()));
		UserBuyer one=userBuyerService.getOne(qw);
		Map map=new HashMap();
		if(one==null){
			map.put("statusCode",2);
			session.setAttribute("statusCode",0);
			return R.data(map,"Incorrect username or password!");
		}
		if(one.getStatus()==0){
			map.put("statusCode",1);
			session.setAttribute("statusCode",0);
			return R.data(map,"The account is not activated, please go to the mailbox to activate!");
		}
		one.setIdcopy(one.getId().toString());

		session.setAttribute("userid",one.getIdcopy());//在session存入用户id
		session.setAttribute("identity","buyer");//在session存入用户身份
		session.setAttribute("statusCode",1);//在session存入登录状态
		session.setAttribute("userInfo", one);
		map.put("statusCode",0);
		map.put("userid",one.getIdcopy());
		map.put("identity","buyer");
		return R.data(map,"Login Success!");
	}
	@RequestMapping("/agentLogin")
	public R agentLogin(HttpServletRequest request,UserAgent userAgent) {
		HttpSession session=request.getSession();
		QueryWrapper<UserAgent> qw=new QueryWrapper<>();
		qw.eq("username",userAgent.getUsername());
		qw.eq("password",DigestUtil.encrypt(userAgent.getPassword()));
		//qw.and(wrapper->wrapper.eq("country",userBuyer.getCountry()).or().eq("mobile",userBuyer.getMobile()));
		UserAgent one=userAgentService.getOne(qw);
		Map map=new HashMap();
		if(one==null){
			map.put("statusCode",2);
			session.setAttribute("statusCode",0);
			return R.data(map,"Incorrect username or password!");
		}
		if(one.getStatus()==0){
			map.put("statusCode",1);
			session.setAttribute("statusCode",0);
			return R.data(map,"The account is not activated, please go to the mailbox to activate!");
		}
		one.setIdcopy(one.getId().toString());
		session.setAttribute("identity","agent");//在session存入用户身份
		session.setAttribute("statusCode",1);//在session存入登录状态
		session.setAttribute("userid",one.getIdcopy());//在session存入用户id
		session.setAttribute("userInfo", one);
		map.put("statusCode",0);
		map.put("userid",one.getId());
		map.put("identity","agent");
		return R.data(map,"Login Success!");
	}

//	@RequestMapping("/active")
//	public String active(@RequestParam("code") String code){
//		String r="";
//		try
//		{
//			Integer active = userBuyerService.active(code);
//			if (active == 1) {
//				r = "激活成功";
//			}else if(active == -1) {
//				r = "用户已激活";
//			}else {
//				r = "激活失败";
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		return r;
//	}
//	@RequestMapping("/agentActive")
//	public String agentActive(@RequestParam("code") String code){
//		String r="";
//		try
//		{
//			Integer active = userAgentService.active(code);
//			if (active == 1) {
//				r = "激活成功";
//			}else if(active == -1){
//				r = "此用户已激活";
//			}else{
//				r = "激活失败";
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		return r;
//	}
	@CrossOrigin
	@PostMapping("/submitApplication")
	public R submitApplication(HttpServletRequest request,UserApplication application){
		HttpSession session = request.getSession();
		Object userId = session.getAttribute("userid");
		if(userId == null) {
			return R.data(0,"请重新登录！");
		}
		List<UserApplication>  applicationList=userApplicationService.list(new QueryWrapper<UserApplication>().eq("userid",(String)userId).eq("project_id",application.getProjectId()));
		if(applicationList.size()>0){
			return R.data(0,"您已在此项目提交过意向，请勿重复提交！");
		}
		application.setUserid(Long.parseLong((String)userId));
		application.setCreateTime(LocalDateTime.now());

		//生成订单号
		String nowTime = LocalDateTime.now().toString();
		String one=nowTime.substring(0,4);
		String two=nowTime.substring(5,7);
		String thr=nowTime.substring(8,10);
		String number=nowTime.split("T")[1];
		String fou = number.substring(0,2);
		String fiv = number.substring(3,5);
		String six = number.substring(6,8);
		String orderNumber=one+two+thr+fou+fiv+six;
		application.setAgentConfirm(0);
		application.setMccConfirm(0);
		application.setOrdernumber(orderNumber);
		boolean b=userApplicationService.save(application);
		return R.data(orderNumber,"意向提交成功");
	}

	/**
	 * 意向提交（重构）
	 * @param request
	 * @param application
	 * @return
	 */
	@CrossOrigin
	@PostMapping("/applicationSubmit")
	public R applicationSubmit(HttpServletRequest request,@RequestBody @Valid PurchaseIntentVO application){
		//判断用户
		HttpSession session = request.getSession();
		String userId =Func.toStr(session.getAttribute("userid"));
		if(Func.isEmpty(userId)) {
			return R.data(0,"请重新登录！");
		}
		List<PurchaseIntent> applicationList = iPurchaseIntentService
			.list(new LambdaQueryWrapper<PurchaseIntent>()
			.eq(PurchaseIntent::getCreateUser, userId));
		if(applicationList.size()>0){
			return R.data(0,"您已在此项目提交过意向，请勿重复提交！");
		}
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String orderNumber = pattern.format(LocalDateTime.now())+Func.random(4, RandomType.INT);
		application.setCreateUser(Long.parseLong(userId));
		application.setCreateUserType(Func.toStr(session.getAttribute("identity")));
		application.setOrderNumber(orderNumber);
		application.setType(1);
		if(iPurchaseIntentService.save(application)){
			R<String> data = R.data(orderNumber);
			data.setSuccess(!Func.equals(application.getDuplicate(),1));
			data.setMsg(data.isSuccess()?"意向提交成功":"意向自动审核失败");
			return data;
		}else {
			return R.fail("意向提交失败");
		}
	}

	/**
	 * 订单提交（重构）
	 * @param request
	 * @param application
	 * @return
	 */
	@CrossOrigin
	@PostMapping("/orderSubmit")
	public R orderSubmit(HttpServletRequest request,@RequestBody @Valid PurchaseIntentVO application){
		//判断用户
		HttpSession session = request.getSession();
		String userId =Func.toStr(session.getAttribute("userid"));
		if(Func.isEmpty(userId)) {
			return R.data(0,"请重新登录！");
		}
//		List<PurchaseIntent> applicationList = iPurchaseIntentService
//			.list(new LambdaQueryWrapper<PurchaseIntent>()
//				.eq(PurchaseIntent::getCreateUser, userId));
//		if(applicationList.size()>0){
//			return R.data(0,"您已在此项目提交过订单，请勿重复提交！");
//		}
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String orderNumber = pattern.format(LocalDateTime.now())+Func.random(4, RandomType.INT);
		application.setCreateUser(Long.parseLong(userId));
		application.setCreateUserType(Func.toStr(session.getAttribute("identity")));
		application.setOrderNumber(orderNumber);
		application.setType(2);
		if(iPurchaseIntentService.orderSave(application)){
			return R.data(orderNumber,"意向提交成功");
		}else {
			return R.fail("意向提交失败");
		}
	}

	/**
	 * 意向列表（重构）
	 * @param request
	 * @param application
	 * @return
	 */
	@CrossOrigin
	@PostMapping("/intentList")
	public R intentList(HttpServletRequest request,PurchaseIntentVO application){
		HttpSession session = request.getSession();
		String userId =Func.toStr(session.getAttribute("userid"));
		if(Func.isEmpty(userId)) {
			return R.data(0,"请重新登录！");
		}
		application.setCreateUser(Func.toLong(session.getAttribute("userid")));
		List<PurchaseIntentVO> list = iPurchaseIntentService.list(application);
		return R.data(list);
	}

	/**
	 * 订单列表（重构）
	 * @param request
	 * @param purchaseOrder
	 * @return
	 */
	@CrossOrigin
	@PostMapping("/orderList")
	public R orderList(HttpServletRequest request,PurchaseOrder purchaseOrder){
		HttpSession session = request.getSession();
		String userId =Func.toStr(session.getAttribute("userid"));
		if(Func.isEmpty(userId)) {
			return R.data(0,"请重新登录！");
		}
		List<PurchaseOrder> purchaseOrderList = iPurchaseOrderService.list(new LambdaQueryWrapper<PurchaseOrder>()
			.eq(PurchaseOrder::getCreateUser, Func.toLong(userId)));

//		application.setCreateUser(Func.toLong(session.getAttribute("userid")));
//		List<PurchaseIntentVO> list = iPurchaseIntentService.list(application);
		return R.data(purchaseOrderList);
	}

	/**
	 * 意向详情（重构）
	 * @param request
	 * @param application
	 * @return
	 */
	@CrossOrigin
	@PostMapping("/applicationDetail")
	public R applicationDetail(HttpServletRequest request,PurchaseIntentVO application) {
		HttpSession session = request.getSession();
		String userId =Func.toStr(session.getAttribute("userid"));
		if(Func.isEmpty(userId)) {
			return R.fail("请重新登录！");
		}
		PurchaseIntent purchaseIntent = iPurchaseIntentService.getById(application.getId());
		if(Func.isEmpty(purchaseIntent)){
			return R.fail("暂无意向！");
		}
		return R.data(PurchaseIntentWrapper.build().entityVO(purchaseIntent));
	}

	@RequestMapping("/agentConfirm")
	public R agentConfirm(HttpServletRequest request,UserApplication application){
		HttpSession session = request.getSession();

		UserApplication one=userApplicationService.getOne(new QueryWrapper<UserApplication>().eq("ordernumber",application.getOrdernumber()));
		if(Func.isEmpty(one)){
			return  R.fail("订单不存在!");
		}
		one.setAgentId(Func.toLong(session.getAttribute("userid")));
		one.setAgentConfirmTime(LocalDateTime.now());
		Integer count=userApplicationService.agentConfirm(one);
		return R.data(count,"确认成功");
	}

	/**
	 * 中介确认接口（重构）
	 * @param request
	 * @param application
	 * @return
	 */
	@RequestMapping("/agentConfirmApplication")
	public R agentConfirmApplication(HttpServletRequest request,PurchaseIntentVO application){
		PurchaseIntent one = iPurchaseIntentService.getOne(new LambdaQueryWrapper<PurchaseIntent>()
				.eq(PurchaseIntent::getOrderNumber, application.getOrderNumber()));
		if(Func.isEmpty(one)){
			return  R.fail("Order does not exist!");
		}
		HttpSession session = request.getSession();
		one.setAgentId(Func.toLong(session.getAttribute("userid")));
		one.setAgentConfirmTime(LocalDateTime.now());
		one.setChequePic(application.getChequePic());
		one.setStatus(5);
		return R.status(iPurchaseIntentService.updateById(one));
	}

	/**
	 * MCC确认接口（重构）
	 * @param request
	 * @param application
	 * @return
	 */
	@RequestMapping("/mccConfirmApplication")
	public R mccConfirmApplication(HttpServletRequest request,@RequestBody @Valid PurchaseIntentVO application){
		HttpSession session = request.getSession();
		PurchaseIntent one = iPurchaseIntentService.getOne(new LambdaQueryWrapper<PurchaseIntent>()
			.eq(PurchaseIntent::getOrderNumber, application.getOrderNumber()));
		if(Func.isEmpty(one)){
			return  R.fail("订单不存在!");
		}
		if(Func.equals(one.getStatus(),9)){
			return R.fail("订单已通过MCC确认!");
		}
		one.setMccUserId(Func.toLong(session.getAttribute("userid")));
		one.setMccConfirmTime(LocalDateTime.now());
		Integer count = iPurchaseIntentService.mccConfirm(one,application.getMccConfirm());
		return R.data(count,"确认成功");
	}

	/**
	 * 上传用户头像
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/uploadAvator")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "上传用户头像", notes = "用户头像上传接口")
	public R uploadAvator(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
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
			System.out.println("E:"+e);
			return R.status(false);
		}
		//将头像存进用户表中
		HttpSession session=request.getSession();
		String userid=session.getAttribute("userid").toString();
		String identity=session.getAttribute("identity").toString();
		if("agent".equals(identity)){
			UserAgent agent=userAgentService.getOne(new QueryWrapper<UserAgent>().eq("id",userid));
			agent.setAvator(url);
			userAgentService.updateById(agent);
		}
		if("buyer".equals(identity)){
			UserBuyer buyer=userBuyerService.getOne(new QueryWrapper<UserBuyer>().eq("id",userid));
			buyer.setAvator(url);
			userBuyerService.updateById(buyer);
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
	 * 上传订单照片，
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/uploadOrderPhoto")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "上传订单照片", notes = "订单图片上传接口")
	public R uploadOrderPhoto(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		// 获取文件名s
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
	@RequestMapping("/mccConfirm")
	public R mccConfirm(@Valid @RequestBody UserApplication application){
		Integer count=userApplicationService.mccConfirm(application);
		if(count==2){
			return R.data(2,"中介还未确认，无法确认");
		}
		return R.data(count,"确认成功");
	}
	@RequestMapping("/changePassword")
	public R changePassword(HttpServletRequest request,String oldPass, String newPass){
		boolean b=false;
		HttpSession session = request.getSession();
		Object userId = session.getAttribute("userid");
		Object identity = session.getAttribute("identity");
		if("agent".equals(identity)){
			UserAgent userAgent = userAgentService.getOne(
				new LambdaQueryWrapper<UserAgent>()
					.eq(UserAgent::getId, userId)
					.eq(UserAgent::getPassword, DigestUtil.encrypt(oldPass)),false
			);
			if(Func.isEmpty(userAgent)){
				return R.data(b,"原密码错误！");
			}
			userAgent.setPassword(DigestUtil.encrypt(newPass));
			b=userAgentService.updateById(userAgent);
		}
		if("buyer".equals(identity)){
			UserBuyer userBuyer=userBuyerService.getOne(
				new LambdaQueryWrapper<UserBuyer>()
					.eq(UserBuyer::getId, userId)
					.eq(UserBuyer::getPassword, DigestUtil.encrypt(oldPass)),false
			);
			if(Func.isEmpty(userBuyer)){
				return R.data(b,"原密码错误！");
			}
			userBuyer.setPassword(DigestUtil.encrypt(newPass));
			b=userBuyerService.updateById(userBuyer);
		}
		return R.data(b,"Password changed successfully!");
	}
	@RequestMapping("/buyerupdatedata")
	public R buyerupdatedata(HttpServletRequest request,UserBuyer userBuyer){
		boolean b=true;
		try{
			HttpSession session = request.getSession();
			Object userId = session.getAttribute("userid");
			Object identity = session.getAttribute("identity");
			if (userId != null && "buyer".equals(identity)) {
				userBuyer.setId(Long.parseLong((String)userId));
			} else {
				b = false;
				return R.data(b,"鉴权错误，修改失败！");
			}


			UserBuyer buyer=userBuyerService.getById(userBuyer);
			buyer.setName(userBuyer.getName());
			buyer.setCountry(userBuyer.getCountry());
			buyer.setMobile(userBuyer.getMobile());
			buyer.setEmailAddress(userBuyer.getEmailAddress());
			buyer.setAddress(userBuyer.getAddress());
			buyer.setPostcode(userBuyer.getPostcode());
			buyer.setRemark(userBuyer.getRemark());
			b=userBuyerService.updateById(buyer);
			if(b) {
				session.setAttribute("userInfo", buyer);
			}

		}catch (Exception e){
			b=false;
			return R.data(b,"修改失败！");
		}
		return R.data(b,"修改成功");
	}
	@RequestMapping("/agentupdatedata")
	public R agentupdatedata(HttpServletRequest request,UserAgent userAgent){
		boolean b=true;
		try{
			HttpSession session = request.getSession();
			Object userId = session.getAttribute("userid");
			Object identity = session.getAttribute("identity");
			if (userId != null && "agent".equals(identity)) {
				userAgent.setId(Long.parseLong((String)userId));
			} else {
				b = false;
				return R.data(b,"鉴权错误，修改失败！");
			}


			UserAgent agent=userAgentService.getById(userAgent);
			agent.setName(userAgent.getName());
			agent.setCountry(userAgent.getCountry());
			agent.setMobile(userAgent.getMobile());
			agent.setEmailAddress(userAgent.getEmailAddress());
			agent.setAddress(userAgent.getAddress());
			agent.setPostcode(userAgent.getPostcode());
			agent.setRemark(userAgent.getRemark());
			b=userAgentService.updateById(agent);
		}catch (Exception e){
			b=false;
			return R.data(b,"修改失败！");
		}

		return R.data(b,"修改成功");
	}

	@RequestMapping("/applicationList")
	public R applicationList(String userid){
		List<UserApplication> userApplicationList=userApplicationService.list(new QueryWrapper<UserApplication>().eq("userid",userid));
		if(userApplicationList.size()==0){
			return R.data(userApplicationList,"没有意向，请添加意向！");
		}
		return R.data(userApplicationList,"意向列表已返回！");
	}

	/**
	 * 发送修改密码邮件
	 * @param request
	 * @param username
	 * @return
	 */
	@RequestMapping("/mailResetPassword")
	public R mailResetPassword(HttpServletRequest request,@CookieValue("identity") String identity,  String username){
		boolean state = false;
		Pattern pattern = Pattern.compile(EMAIL);
		Matcher matcher = pattern.matcher(username);
		if("agent".equals(identity)&&matcher.matches()){
			UserAgent userAgent = userAgentService.getOne(
				new LambdaQueryWrapper<UserAgent>()
				.eq(UserAgent::getUsername, username),false
			);
			if(Func.isNotEmpty(userAgent)){
				userAgent.setCode(Func.randomUUID());
				state = userAgentService.updateById(userAgent);
				new MaillUtill().sendResetPasswordMail(username, userAgent.getCode(), identity);
			}
		}
		if("buyer".equals(identity)){
			UserBuyer userBuyer = userBuyerService.getOne(
				new LambdaQueryWrapper<UserBuyer>()
				.eq(UserBuyer::getUsername, username), false
			);
			if(Func.isNotEmpty(userBuyer)){
				userBuyer.setCode(Func.randomUUID());
				state = userBuyerService.updateById(userBuyer);
				new MaillUtill().sendResetPasswordMail(username, userBuyer.getCode(), identity);
			}
		}
		return R.status(state);
	}

	/**
	 * 重设密码
	 * @param identity
	 * @param username
	 * @return
	 */
	@RequestMapping("/resetPassword")
	public R resetPassword(String identity,String username,String code,String newPassword){
		Boolean state = false;
		Pattern pattern = Pattern.compile(EMAIL);
		Matcher matcher = pattern.matcher(username);
		if("agent".equals(identity)&&matcher.matches()&&Func.isNotEmpty(code)){
			UserAgent userAgent = userAgentService.getOne(
				new LambdaQueryWrapper<UserAgent>()
				.eq(UserAgent::getUsername, username)
				.eq(UserAgent::getCode,code),false
			);
			if(Func.isNotEmpty(userAgent)){
				userAgent.setPassword(DigestUtil.encrypt(newPassword));
				state = userAgentService.updateById(userAgent);
			}
		}
		if("buyer".equals(identity)&&matcher.matches()&&Func.isNotEmpty(code)){
			UserBuyer userBuyer = userBuyerService.getOne(
				new LambdaQueryWrapper<UserBuyer>()
					.eq(UserBuyer::getUsername, username)
					.eq(UserBuyer::getCode,code),false
			);
			if(Func.isNotEmpty(userBuyer)){
				userBuyer.setPassword(DigestUtil.encrypt(newPassword));
				state = userBuyerService.updateById(userBuyer);
			}
		}
		return R.status(state);
	}

}
