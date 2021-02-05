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
package org.springblade.lottery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author Blade
 * @since 2020-09-09
 */
@Data
@TableName("house_application_lottery")
@ApiModel(value = "开盘抽签表", description = "开盘抽签表")
public class Lottery  implements Serializable {
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
	 * applicationId
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "applicationId")
	private Long applicationId;

	/**
	 * 抽签号
	 */
	@ApiModelProperty(value = "抽签号")
	private String eoiNo;

	/**
	 * 选房次数
	 */
	@ApiModelProperty(value = "选房次数")
	private Integer sequence;

	/**
	 * 选房用户id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "选房用户id")
	private Long userId;

	/**
	 * 用户类型
	 */
	@ApiModelProperty(value = "用户类型")
	private String userType;

	/**
	 * 中签时间
	 */
	@ApiModelProperty(value = "中签时间")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private LocalDateTime lotteryTime;

	/**
	 * 首次选房开始的时间
	 */
	@ApiModelProperty(value = "首次选房开始的时间")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private LocalDateTime firstSelectionTime;

	/**
	 * 申请二次选房时间
	 */
	@ApiModelProperty(value = "申请二次选房时间")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private LocalDateTime secondSelectionApplyTime;

	/**
	 * 二次选房开始的时间
	 */
	@ApiModelProperty(value = "二次选房开始的时间")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private LocalDateTime secondSelectionTime;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer lotteryOrder;

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private Integer status;


}
