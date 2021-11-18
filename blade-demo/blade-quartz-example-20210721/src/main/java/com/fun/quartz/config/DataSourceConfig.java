package com.fun.quartz.config;

import org.springframework.context.annotation.Configuration;

/**
 * 配置数据源
 * 注意：当yml里配置了多个数据源的时候才需要当前配置
 * @author zz
 * @date 2021/7/23
 */
@Configuration
public class DataSourceConfig {

	/*@Bean
	@Primary
	@ConfigurationProperties("spring.datasource.primary")
	public DataSourceProperties primaryDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource.primary.configuration")
	public HikariDataSource firstDataSource() {
		return primaryDataSourceProperties().initializeDataSourceBuilder()
			.type(HikariDataSource.class).build();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.quartz")
	public DataSourceProperties quartzDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@QuartzDataSource
	@ConfigurationProperties("spring.datasource.quartz.configuration")
	public HikariDataSource quartzDataSource() {
		return quartzDataSourceProperties().initializeDataSourceBuilder()
			.type(HikariDataSource.class).build();
	}*/
}
