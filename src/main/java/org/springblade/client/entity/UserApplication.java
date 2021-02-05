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
package org.springblade.client.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author Blade
 * @since 2020-09-15
 */
@Data
@TableName("house_user_application")
@ApiModel(value = "UserApplication对象", description = "UserApplication对象")
public class UserApplication {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * 项目ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "项目ID")
	private Long projectId;
	/**
	 * 用户id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "userid")
	private Long userid;
	/**
	 * EOI 号码
	 */
	@ApiModelProperty(value = "EOI 号码")
	@TableField("EOINo")
	private String EOINo;
	/**
	 * EOI 号码后四位
	 */
	@ApiModelProperty(value = "EOI 号码后四位")
	@TableField("EOINo4")
	private String EOINo4;
	/**
	 * 买主1姓名
	 */
	@ApiModelProperty(value = "买主1姓名")
	private String purchaser1;
	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别1")
	private Integer gender1;
	/**
	 * 最后四位数NRIC/护照号码_01
	 */
	@ApiModelProperty(value = "最后四位数NRIC/护照号码_01")
	private String passportNo1;
	/**
	 * 国籍
	 */
	@ApiModelProperty(value = "国籍1")
	private String nationality1;
	/**
	 * 电话
	 */
	@ApiModelProperty(value = "电话1")
	private String contact1;
	/**
	 * Email
	 */
	@ApiModelProperty(value = "Email1")
	private String email1;
	/**
	 * Address
	 */
	@ApiModelProperty(value = "Address")
	private String address;
	/**
	 * 邮编
	 */
	@ApiModelProperty(value = "邮编")
	private String postalCode;
	/**
	 * 买主2姓名
	 */
	@ApiModelProperty(value = "买主2姓名")
	private String purchaser2;
	/**
	 * 性别2
	 */
	@ApiModelProperty(value = "性别2")
	private Integer gender2;
	/**
	 * 最后四位数NRIC/护照号码_02
	 */
	@ApiModelProperty(value = "最后四位数NRIC/护照号码_02")
	private String passportNo2;
	/**
	 * 国籍2
	 */
	@ApiModelProperty(value = "国籍2")
	private String nationality2;
	/**
	 * 电话2
	 */
	@ApiModelProperty(value = "电话2")
	private String contact2;
	/**
	 * email_02
	 */
	@ApiModelProperty(value = "email_02")
	private String email2;
	/**
	 * 中介公司
	 */
	@ApiModelProperty(value = "中介公司")
	@TableField("Agency")
	private String Agency;
	/**
	 * 中介姓名
	 */
	@ApiModelProperty(value = "中介姓名")
	@TableField("Agent")
	private String Agent;
	/**
	 * 中介电话
	 */
	@ApiModelProperty(value = "中介电话")
	@TableField("Agent_contact")
	private String agentContact;
	/**
	 * 意向1
	 */
	@ApiModelProperty(value = "意向1")
	private String choice01;
	/**
	 * 意向2
	 */
	@ApiModelProperty(value = "意向2")
	private String choice02;
	/**
	 * 意向3
	 */
	@ApiModelProperty(value = "意向3")
	private String choice03;
	/**
	 * 支票状态
	 */
	@ApiModelProperty(value = "支票状态")
	private Integer chequeStatus;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
	/**
	 * 中介确认
	 */
	@ApiModelProperty(value = "中介确认")
	private Integer agentConfirm;/**
	 * mcc确认
	 */
	@ApiModelProperty(value = "mcc确认")
	private Integer mccConfirm;
	/*
	 * choose_status
	 */
	@ApiModelProperty(value = "choose_status")
	private Integer chooseStatus;
	/**
	 * ordernumber订单号
	 */
	@ApiModelProperty(value = "ordernumber")
	private String ordernumber;
	/**
	 * photo_url订单照片
	 */
	@ApiModelProperty(value = "photo_url")
	private String photoUrl;

	/**
	 * create_time 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private LocalDateTime createTime;

	/**
	 * 确认支票的中介ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "确认支票的中介ID")
	private Long agentId;

	/**
	 * 中介确认的时间
	 */
	@ApiModelProperty(value = "中介确认的时间")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private LocalDateTime agentConfirmTime;

	/**
	 * MCC确认用户ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "MCC确认用户ID")
	private Long mccUserId;

	/**
	 * MCC确认的时间
	 */
	@ApiModelProperty(value = "MCC确认的时间")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private LocalDateTime mccConfirmTime;
	/**
	 * 是否参加过抽奖0:未参加 1：参加过
	 */
	@ApiModelProperty(value = "是否参加过抽奖0:未参加 1：参加过")
	@JsonSerialize(using = ToStringSerializer.class)
	private Integer joinstatus;

}
