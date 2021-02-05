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
package org.springblade.index.entity;

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
 * 首页轮播设置表实体类
 *
 * @author Blade
 * @since 2020-07-07
 */
@Data
@TableName("house_index_show")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "IndexShow对象", description = "首页轮播设置表")
public class IndexShow extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 轮播名称
     */
    @ApiModelProperty(value = "轮播名称")
    private String name;
	/**
	 * idcopy
	 */
	@ApiModelProperty(value = "idcopy")
	private String pidCopy;
    /**
     * url类型
     */
    @ApiModelProperty(value = "url类型")
    private Integer urlType;
    /**
     * urlImg
     */
    @ApiModelProperty(value = "url")
    private String url;
	/**
	 * urlText
	 */
	@ApiModelProperty(value = "urlText")
	private String urlText;
    /**
     * 备注1
     */
    @ApiModelProperty(value = "备注1")
    private String memo1;
    /**
     * 备注2
     */
    @ApiModelProperty(value = "备注2")
    private String memo2;
    /**
     * 备注3
     */
    @ApiModelProperty(value = "备注3")
    private String memo3;
    /**
     * 备注4
     */
    @ApiModelProperty(value = "备注4")
    private String memo4;
    /**
     * 备注5
     */
    @ApiModelProperty(value = "备注5")
    private String memo5;
    /**
     * 项目ID
     */
    @ApiModelProperty(value = "项目ID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long projectId;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;
	/**
	 * 是否推送
	 */
	@ApiModelProperty(value = "是否推送 0:是 1：不推送")
	private Integer isPush;

	/**
	 * 显示
	 */
	@ApiModelProperty(value = "显示（1=显示 0=不显示）")
	private Integer btnShow;
	/**
	 * 按钮文字
	 */
	@ApiModelProperty(value = "按钮文字")
	private String btnText;
	/**
	 * 跳转模式
	 */
	@ApiModelProperty(value = "跳转模式（1：项目跳转，2：链接跳转, 3：弹窗）")
	private Integer linkModel;
	/**
	 * 链接地址
	 */
	@ApiModelProperty(value = "链接地址")
	private String linkUrl;
	/**
	 * 视频时长(秒)
	 */
	@ApiModelProperty(value = "视频时长")
	private Integer videoDuration;

}
