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
 * user_buyer实体类
 *
 * @author Blade
 * @since 2020-09-30
 */
@Data
@TableName("house_user_join")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserJoin对象", description = "user_buyer")
public class UserJoin extends BaseEntity {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
    private Long id;
    /**
     * EOINo
     */
    @ApiModelProperty(value = "EOINo")
    @TableField("EOINo")
  private String EOINo;
    /**
     * userid
     */
    @ApiModelProperty(value = "userid")
    private Long userid;
	/**
	 * projectid
	 */
	@ApiModelProperty(value = "projectid")
	private Long projectid;
    /**
     * 是否参加过抽奖0:未参加 1：参加过
     */
    @ApiModelProperty(value = "是否参加过抽奖0:未参加 1：参加过")
	@JsonSerialize(using = ToStringSerializer.class)
    private Integer joinStatus;


}
