/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.client.entity.UserApplication;
import org.springblade.client.util.MaillUtill;
import org.springblade.client.vo.UserApplicationVO;
import org.springblade.client.mapper.UserApplicationMapper;
import org.springblade.client.service.IUserApplicationService;
import org.springblade.webuser.entity.UserAgent;
import org.springblade.webuser.entity.UserBuyer;
import org.springblade.webuser.mapper.UserBuyerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2020-09-15
 */
@Service
public class UserApplicationServiceImpl extends ServiceImpl<UserApplicationMapper, UserApplication> implements IUserApplicationService {
	@Autowired
	private UserBuyerMapper buyerMapperer;
	@Override
	public IPage<UserApplicationVO> selectUserApplicationPage(IPage<UserApplicationVO> page, UserApplicationVO userApplication) {
		return page.setRecords(baseMapper.selectUserApplicationPage(page, userApplication));
	}

	@Override
	public Integer agentConfirm(UserApplication application) {
//		QueryWrapper<UserApplication> qw=new QueryWrapper<>();
//		qw.eq("code",code);
//		UserAgent one=baseMapper.selectOne(qw);
//		UpdateWrapper<UserApplication> uw=new UpdateWrapper<>();
//		uw.eq("status",1);
		application.setAgentConfirm(1);
		int coun=baseMapper.updateById(application);
		return coun;
	}

	@Override
	public synchronized Integer mccConfirm(UserApplication application) {
		QueryWrapper<UserApplication> qw=new QueryWrapper<>();
		qw.eq("id",application.getId());
		UserApplication one=baseMapper.selectOne(qw);
		if(one.getAgentConfirm()==0){
			return 2;
		}
		one.setMccConfirm(1);
		String preNumber=getpreNumber();
		String fourmumber=getFourNum();
		boolean b=true;
		while (b){
			List<UserApplication> applications=baseMapper.selectList(new QueryWrapper<UserApplication>().eq("EOINo4",fourmumber).eq("id",application.getId()));
			if(applications.size()>0){
				//如果后四位相同，则再次获取
				fourmumber=getFourNum();
			}
			if (applications.size()==0){
				one.setEOINo(preNumber+fourmumber);
				one.setEOINo4(fourmumber);
				b=false;
			}
		}
		int coun=baseMapper.updateById(one);
		QueryWrapper<UserBuyer> qwu=new QueryWrapper<>();
		qwu.eq("id",one.getUserid());
		UserBuyer user= buyerMapperer.selectOne(qwu);
		new MaillUtill().send(user.getUsername(),preNumber+fourmumber);
		return coun;
	}
	public String getpreNumber(){
		String preNumber="";
		//生成抽签码
		String nowTime = LocalDateTime.now().toString();
		String on=nowTime.substring(0,4);
		String two=nowTime.substring(5,7);
		String thr=nowTime.substring(8,10);
		String number=nowTime.split("T")[1];
		String fou = number.substring(0,2);
		String fiv = number.substring(3,5);
		String six = number.substring(6,8);
		preNumber=on+two+thr+fou+fiv+six;
		return preNumber;
	}
	public String getFourNum() {
		Random r=new Random();
		int tag[]={0,0,0,0,0,0,0,0,0,0};
		String four="";
		int temp=0;
		while(four.length()!=4){
			temp=r.nextInt(10);//随机获取0~9的数字
			if(tag[temp]==0){
				four+=temp;
				tag[temp]=1;
			}
		}
		return four;
	}

}
