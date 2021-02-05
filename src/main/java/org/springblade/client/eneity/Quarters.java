package org.springblade.client.eneity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@ApiModel(value = "社区对象", description = "社区信息")
public class Quarters {
	private List<Blocks> blocks;
	private List<String> blocksName;
}
