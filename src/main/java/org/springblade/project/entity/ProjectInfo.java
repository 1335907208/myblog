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
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 项目信息表实体类
 *
 * @author Blade
 * @since 2020-06-27
 */
@Data
@TableName("house_project_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ProjectInfo对象", description = "项目信息表")
public class ProjectInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 查看预约配置时解决id重复
	 */
	@TableField(exist = false)
	private Long projectId;
	/**
	 * 建筑类型
	 */
	@ApiModelProperty(value = "建筑类型")
	private Long constructionType;
	/**
	 * idcopy
	 */
	@ApiModelProperty(value = "idcopy")
	private String idCopy;
	/**
	 * 装修类型
	 */
	@ApiModelProperty(value = "装修类型")
	private Long decorationType;
	/**
	 * 项目名称
	 */
	@ApiModelProperty(value = "项目名称")
	private String name;
	/**
	 * 楼盘地址
	 */
	@ApiModelProperty(value = "楼盘地址")
	private String address;
	/**
	 * 开发商
	 */
	@ApiModelProperty(value = "开发商")
	private String developer;
	/**
	 * 平均价格
	 */
	@ApiModelProperty(value = "平均价格")
	private BigDecimal avgPrice;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String remark;

	/**
	 * 经纬度
	 */
	@ApiModelProperty(value = "经纬度")
	private String location;

	/**
	 * 经纬度-谷歌
	 */
	@ApiModelProperty(value = "经纬度-谷歌")
	private String locationGoogle;

	/**
	 * 展示类型
	 */
	@ApiModelProperty(value = "展示类型")
	private String showType;

	/**
	 * 销售类型
	 */
	@ApiModelProperty(value = "销售类型")
	private Long saleType;


	/**
	 * 预览图（主图）地址
	 */
	@ApiModelProperty(value = "预览图")
	private String previewImage;

	/**
	 * 预览图（Logo）地址
	 */
	@ApiModelProperty(value = "预览图Logo")
	private String previewImageLogo;

	/**
	 * Land Area
	 */
	@ApiModelProperty(value = "Land Area")
	private String landArea;

	/**
	 * gfa
	 */
	@ApiModelProperty(value = "gfa")
	private String gfa;

	/**
	 * top
	 */
	@ApiModelProperty(value = "top")
	private String top;

	/**
	 * total
	 */
	@ApiModelProperty(value = "total")
	private String total;

	/**
	 * 主要描述
	 */
	@ApiModelProperty(value = "mainInfo")
	private String mainInfo;

	/**
	 * 次要描述
	 */
	@ApiModelProperty(value = "subInfo")
	private String subInfo;

	/**
	 * 项目楼书
	 */
	@ApiModelProperty(value = "bookUrl")
	private String bookUrl;

	/**
	 * 外部标识（用来数据同步用的，需要维护对照编码）
	 */
	@ApiModelProperty(value = "extendCode")
	private String extendCode;

	/**
	 * 区域类型
	 */
	@ApiModelProperty(value = "locationType")
	private Integer locationType;

	/**
	 * 楼盘状态类型
	 */
	@ApiModelProperty(value = "statusType")
	private Long statusType;

	/**
	 * 卧室类型（多选）
	 */
	@ApiModelProperty(value = "bedrooms")
	private String bedrooms;

	/**
	 * 建筑面积
	 */
	@ApiModelProperty(value = "siteArea")
	private String siteArea;

	/**
	 * 产权年限
	 */
	@ApiModelProperty(value = "tenure")
	private String tenure;

	/**
	 * 栋 数量
	 */
	@ApiModelProperty(value = "blocks")
	private Integer blocks;

	/**
	 * storeys 楼层
	 */
	@ApiModelProperty(value = "storeys")
	private Integer storeys;

	/**
	 * Carpark 车位
	 */
	@ApiModelProperty(value = "carpark")
	private Integer carpark;

	/**
	 * bicyclelots 自行车位
	 */
	@ApiModelProperty(value = "bicyclelots")
	private Integer bicyclelots;

	/**
	 * cansaleunits 可售房屋数量
	 */
	@ApiModelProperty(value = "cansaleunits")
	private Integer cansaleunits;
	/**
	 * 附近的URLlocation
	 */
	@ApiModelProperty(value = "附近的URLlocation")
	private String urlLocation;
	/**
	 * 是否推送
	 */
	@ApiModelProperty(value = "是否推送 0:是 1：不推送")
	private Integer isPush;
	/**
	 * 是否开盘
	 */
	@ApiModelProperty(value = "是否推送 0:开盘 1：未开盘")
	private Integer openStatus;
	/**
	 * 官网地址
	 */
	@ApiModelProperty(value = "官网地址")
	private String siteUrl;
	/**
	 * 连接类型
	 */
	@ApiModelProperty(value = "连接类型 0=项目页，1=官网")
	private Integer linkType;
	/**
	 * 代理公司（多值，逗号隔开）
	 */
	@ApiModelProperty(value = "代理公司（多值，逗号隔开）")
	private String agency;

}
