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
package org.springblade.project.service.impl;

import org.springblade.project.entity.ProjectCctv;
import org.springblade.project.vo.ProjectCctvVO;
import org.springblade.project.mapper.ProjectCctvMapper;
import org.springblade.project.service.IProjectCctvService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 项目CCTV表 服务实现类
 *
 * @author Blade
 * @since 2020-07-02
 */
@Service
public class ProjectCctvServiceImpl extends BaseServiceImpl<ProjectCctvMapper, ProjectCctv> implements IProjectCctvService {

	@Override
	public IPage<ProjectCctvVO> selectProjectCctvPage(IPage<ProjectCctvVO> page, ProjectCctvVO projectCctv) {
		return page.setRecords(baseMapper.selectProjectCctvPage(page, projectCctv));
	}

}
