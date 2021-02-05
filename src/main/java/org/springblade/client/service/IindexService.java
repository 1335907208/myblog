package org.springblade.client.service;

import org.springblade.client.eneity.Quarters;
import org.springblade.core.mp.base.BaseService;
import org.springblade.project.entity.ProjectUnits;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IindexService extends BaseService<ProjectUnits> {

	public List<List<List<ProjectUnits>>> getQuarter(Long projectsId);

}
