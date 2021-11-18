package org.springblade.payment.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StopWatch;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * <pre>
 * Swagger配置类
 * Created by Binary Wang on 2018/9/27.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Slf4j
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	@Bean
	public Docket docket() {
		//最重要的就是这里，定义了/test/.*开头的rest接口都分在了test分组里，可以通过/v2/api-docs?group=test得到定义的json
		log.info("Starting Swagger");
		StopWatch watch = new StopWatch(); //计时器
		watch.start();
		Docket docket = new Docket(DocumentationType.SWAGGER_2) //DocumentationType.SWAGGER_2 固定的，代表swagger2
			.groupName("pay")
			.apiInfo(this.apiInfo()) // 用于生成API信息
			.select()
			.apis(RequestHandlerSelectors.any()) // 用于指定扫描哪个包下的接口
			.paths(regex("/pay/.*")) // 只选择pay路径生成文档，要选择所有的API用PathSelectors.any()
			.build();
		watch.stop();
		log.info("Started Swagger in {} ms", watch.getTotalTimeMillis());
		return docket;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("微信支付Demo")  //  可以用来自定义API的主标题
			.description("微信支付演示接口") // 可以用来描述整体的API
			.contact(new Contact("Binary Wang", null, null))
			.license("Apache 2.0")
			.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
			.version("1.0.0")
			.build();
	}
}
