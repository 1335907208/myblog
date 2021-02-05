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
 * @since 2020-09-29
 */
@Data
@TableName("house_user_order")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserOrder对象", description = "UserOrder对象")
public class UserOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
  private Long id;
    /**
     * 房间id
     */
	@JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "房间id")
    private Long unitId;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 申请书id
     */
    @ApiModelProperty(value = "申请书id")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long applicationId;
    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String ordermember;
    /**
     * EOINo号码
     */
    @ApiModelProperty(value = "EOINo号码")
    @TableField("EOINo")
  private String EOINo;
  private String block;
  private String floor;
  private String unit;


}
