package org.springblade.client.eneity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springblade.core.mp.base.BaseEntity;

import java.util.List;
@Data
@ApiModel(value = "栋数对象", description = "栋数信息")
public class Blocks extends BaseEntity {
	private List<Floors> floors;
	private List<String> floorsName;
}
