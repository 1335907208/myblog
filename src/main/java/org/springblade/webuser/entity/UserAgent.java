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
package org.springblade.webuser.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * user_agent实体类
 *
 * @author Blade
 * @since 2020-06-24
 */
@Data
@TableName("house_user_agent")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserAgent对象", description = "user_agent")
public class UserAgent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using= ToStringSerializer.class)
    private Long id;
	/**
	 * 账号
	 */
	@ApiModelProperty(value = "账号")
	private String username;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;
	/**
	 * 验证码
	 */
	@ApiModelProperty(value = "验证码")
	private String code;
	/**
	 * postcode
	 */
	@ApiModelProperty(value = "postcode")
	private String postcode;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private Long gender;
    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private Integer age;
    /**
     * 国家
     */
    @ApiModelProperty(value = "国家")
    private String country;
    /**
     * 身份类型
     */
    @ApiModelProperty(value = "身份类型")
    private String indentify;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String mobile;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;
    /**
     * 邮箱地址
     */
    @ApiModelProperty(value = "邮箱地址")
    private String emailAddress;
    /**
     * CEA号码
     */
    @ApiModelProperty(value = "CEA号码")
    private String ceaNo;
    /**
     * 所属公司
     */
    @ApiModelProperty(value = "所属公司")
    private String company;
    /**
     * 认证结果
     */
    @ApiModelProperty(value = "认证结果")
    private String authResult;
    /**
     * 认证时间
     */
    @ApiModelProperty(value = "认证时间")
    private LocalDateTime authTime;
    /**
     * 认证信息
     */
    @ApiModelProperty(value = "认证信息")
    private String authInfo;
	/**
	 * avator
	 */
	@ApiModelProperty(value = "avator")
	private String avator;
	/**
	 * remark
	 */
	@ApiModelProperty(value = "remark")
	private String remark;
	/**
	 * idcopy
	 */
	@ApiModelProperty(value = "idcopy")
	private String idcopy;
	/**
	 * NRIC编号最后四位数字
	 */
	@ApiModelProperty(value = "NRIC编号最后四位数字")
	private String nricNo;



}
