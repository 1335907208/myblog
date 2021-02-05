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
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springblade.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author Blade
 * @since 2020-09-27
 */
@Data
@TableName("house_user_jackpot")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserJackpot对象", description = "UserJackpot对象")
public class UserJackpot extends BaseEntity {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
  	private Long id;
    /**
     * 项目id
     */
    @ApiModelProperty(value = "项目id")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
    /**
     * 开盘活动id
     */
    @ApiModelProperty(value = "开盘活动id")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long openingId;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
	/**
	 * application_id
	 */
	@ApiModelProperty(value = "application_id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long applicationId;

    /**
     * 抽签码
     */
    @ApiModelProperty(value = "抽签码")
    @TableField("EOINo")
  private String EOINo;
    /**
     * 抽签码后四位
     */
    @ApiModelProperty(value = "抽签码后四位")
    @TableField("EOINo4")
  private String EOINo4;
    /**
     * 选房状态:0:等待选房,1:下下一位选房,2:下一位选房,3:当前选房,4:上一位选房,5:选过房子
     */
    @ApiModelProperty(value = "选房状态:0:等待选房,1:下下一位选房,2:下一位选房,3:当前选房,4:上一位选房,5:选过房子")
    private Integer chooseStatus;
    /**
     * 是否二次选房 0：不是 1：是
     */
    @ApiModelProperty(value = "是否二次选房 0：不是 1：是")
    private Integer twoChoose;
    /**
     * 是否举手:0:未举手,1:举手
     */
    @ApiModelProperty(value = "是否举手:0:未举手,1:举手")
    private Integer raiseHands;


}
