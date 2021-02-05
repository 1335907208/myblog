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
package org.springblade.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springblade.core.mp.base.BaseEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目活动表实体类
 *
 * @author Blade
 * @since 2020-07-07
 */
@Data
@TableName("house_project_event")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ProjectEvent对象", description = "项目活动表")
public class ProjectEvent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)//序列化防止long精度丢失
    private Long id;
    /**
     * 项目ID
     */
    @ApiModelProperty(value = "项目ID")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    private String name;
    /**
     * 活动封面URL
     */
    @ApiModelProperty(value = "活动封面URL")
    private String url;
    /**
     * 活动日期
     */
    @ApiModelProperty(value = "活动日期")
    private LocalDate eventDate;
    /**
     * 直播地址中国
     */
    @ApiModelProperty(value = "直播地址中国")
    private String liveUrlChina;
    /**
     * 直播地址世界
     */
    @ApiModelProperty(value = "直播地址世界")
    private String liveUrlWorld;
    /**
     * 直播开始
     */
    @ApiModelProperty(value = "直播开始")
    private LocalDateTime liveBeginTime;
    /**
     * 直播结束
     */
    @ApiModelProperty(value = "直播结束")
    private LocalDateTime liveEndTime;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String remark;
	/**
	 * 活动标题
	 */
	@ApiModelProperty(value = "活动标题")
	private String title;


}
