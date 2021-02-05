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
package org.springblade.purchaseIntent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author Blade
 * @since 2020-10-09
 */
@Data
@TableName("house_application_purchaser")
@ApiModel(value = "购房意向人员", description = "购房意向人员")
public class PurchaseIntentUser implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	 * 关联购房意向ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "关联购房意向ID")
	private Long applicationId;

	/**
	 * 买主姓名
	 */
	@ApiModelProperty(value = "买主姓名")
	private String name;

	//`passport_no` varchar(100) DEFAULT NULL COMMENT '最后四位数NRIC/护照号码_01',passportNo
	/**
	 * 最后四位数 护照号码
	 */
	@ApiModelProperty(value = "最后四位数 护照号码")
	private String passportNo;

	//`nationality` varchar(255) DEFAULT NULL COMMENT '国籍',
	/**
	 * 国籍
	 */
	@ApiModelProperty(value = "国籍")
	private String nationality;

	//`gender` tinyint(1) DEFAULT NULL COMMENT '性别1=男 0=女',
	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别")
	private Integer gender;

	//`contact` varchar(30) DEFAULT NULL COMMENT '电话',
	/**
	 * 电话
	 */
	@ApiModelProperty(value = "电话")
	private String contact;

	//`email` varchar(100) DEFAULT NULL COMMENT 'Email',
	/**
	 * Email
	 */
	@ApiModelProperty(value = "Email")
	private String email;

	//`address` varchar(255) DEFAULT NULL COMMENT 'Address',
	/**
	 * Address
	 */
	@ApiModelProperty(value = "Address")
	private String address;

	//`postal_code` varchar(10) DEFAULT NULL COMMENT '邮编',postalCode
	/**
	 * 邮编
	 */
	@ApiModelProperty(value = "邮编")
	private String postalCode;

	//`user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
	/**
	 * 用户id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "用户id")
	private Long userId;

}
