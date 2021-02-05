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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.core.mp.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author Blade
 * @since 2020-09-09
 */
@Data
@TableName("house_application")
@ApiModel(value = "购房意向", description = "购房意向")
public class PurchaseIntent extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	private String tenantId;

	/**
	 * 项目ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "项目ID")
	private Long projectId;

	/**
	 * 抽签号
	 */
	@ApiModelProperty(value = "抽签号")
	private String eoiNo;

	/**
	 * 订单号
	 */
	@ApiModelProperty(value = "订单号")
	private String orderNumber;

	/**
	 * 订单类型
	 */
	@ApiModelProperty(value = "订单类型")
	private Integer type;

	/**
	 * 中介公司
	 */
	@ApiModelProperty(value = "中介公司")
	private String agency;

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
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 中介姓名
	 */
	@ApiModelProperty(value = "中介姓名")
	private String agent;

	/**
	 * 中介电话
	 */
	@ApiModelProperty(value = "中介电话")
	private String agentContact;

	/**
	 * 支票照片
	 */
	@ApiModelProperty(value = "支票照片")
	private String chequePic;

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
	 * 用户类型
	 */
	@ApiModelProperty(value = "用户类型")
	private String createUserType;

	/**
	 * 批量采购 0=否 1=是 默认否
	 */
	@ApiModelProperty(value = "批量采购 0=否 1=是 默认否")
	private Integer bulkPurchase;

	/**
	 * 疑似重复 0=不重复 1=疑似重复 同样项目 相同nric
	 */
	@ApiModelProperty(value = "疑似重复 0=不重复 1=疑似重复 同样项目 相同nric")
	private Integer duplicate;
}
