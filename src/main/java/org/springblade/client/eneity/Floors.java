package org.springblade.client.eneity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springblade.core.mp.base.BaseEntity;
import org.springblade.project.entity.ProjectUnits;

import java.util.List;
@Data
@ApiModel(value = "楼层对象", description = "楼层信息")
public class Floors extends BaseEntity {
	private List<ProjectUnits> units;
	private List<String> unitsName;
}
