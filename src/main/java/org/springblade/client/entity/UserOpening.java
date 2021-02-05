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
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author Blade
 * @since 2020-09-28
 */
@Data
@TableName("house_user_opening")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserOpening对象", description = "UserOpening对象")
public class UserOpening extends BaseEntity {

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
     * 上一位
     */
    @ApiModelProperty(value = "上一位")
    private String prev;
    /**
     * 当前
     */
    @ApiModelProperty(value = "当前")
    private String curent;
    /**
     * 下一位
     */
    @ApiModelProperty(value = "下一位")
    private String nexte;
    /**
     * 下下一位
     */
    @ApiModelProperty(value = "下下一位")
    private String nextnexte;


}
