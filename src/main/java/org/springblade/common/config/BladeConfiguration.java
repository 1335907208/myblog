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
package org.springblade.common.config;


import org.springblade.core.secure.registry.SecureRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Blade配置
 *
 * @author Chill
 */
@Configuration
public class BladeConfiguration implements WebMvcConfigurer {

	@Bean
	public SecureRegistry secureRegistry() {
		SecureRegistry secureRegistry = new SecureRegistry();
		secureRegistry.setEnabled(true);
		secureRegistry.excludePathPatterns("/blade-auth/**");
		secureRegistry.excludePathPatterns("/blade-log/**");
		secureRegistry.excludePathPatterns("/blade-system/menu/auth-routes");
		secureRegistry.excludePathPatterns("/doc.html");
		secureRegistry.excludePathPatterns("/webjars/**");
		secureRegistry.excludePathPatterns("/swagger-resources/**");

		secureRegistry.excludePathPatterns("/css/**");
		secureRegistry.excludePathPatterns("/font/**");
		secureRegistry.excludePathPatterns("/img/**");
		secureRegistry.excludePathPatterns("/media/**");
		secureRegistry.excludePathPatterns("/plugins/**");
		secureRegistry.excludePathPatterns("/js/**");

		//wfc 加入到不鉴权的服务前缀
		secureRegistry.excludePathPatterns("/project/**");
		secureRegistry.excludePathPatterns("/webuser/**");
		secureRegistry.excludePathPatterns("/client/**");
		secureRegistry.excludePathPatterns("/open/**");
		secureRegistry.excludePathPatterns("/units/**");
		secureRegistry.excludePathPatterns("/index/**");
		secureRegistry.excludePathPatterns("/");
		secureRegistry.excludePathPatterns("/chooseFloors");
		secureRegistry.excludePathPatterns("/chooseUnits");
		secureRegistry.excludePathPatterns("/lookunits");
		secureRegistry.excludePathPatterns("/userCenter/**");
		secureRegistry.excludePathPatterns("/finance/**");
		secureRegistry.excludePathPatterns("/agentActive/**");
		secureRegistry.excludePathPatterns("/userCenter.html");
		secureRegistry.excludePathPatterns("/project/projectinfo/upload");
		secureRegistry.excludePathPatterns("/H5Application");
		return secureRegistry;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("doc.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
