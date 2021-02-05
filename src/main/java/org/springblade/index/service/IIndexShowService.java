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
package org.springblade.index.service;

import org.springblade.client.eneity.Quarters;
import org.springblade.index.entity.IndexShow;
import org.springblade.index.vo.IndexShowVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 首页轮播设置表 服务类
 *
 * @author Blade
 * @since 2020-07-07
 */
public interface IIndexShowService extends BaseService<IndexShow> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param indexShow
	 * @return
	 */
	IPage<IndexShowVO> selectIndexShowPage(IPage<IndexShowVO> page, IndexShowVO indexShow);

}
