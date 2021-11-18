package com.fun.flowable.config;


import org.flowable.engine.*;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author liuxz
 * @date Created in 26-08-2019
 * @description activiti配置类
 */
@Configuration
public class FlowableConfig {
	@Bean(name = "processEngine")
	public ProcessEngine processEngine(DataSourceTransactionManager transactionManager, DataSource dataSource) throws IOException {
		SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
		//自动部署已有的流程文件
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources(ResourceLoader.CLASSPATH_URL_PREFIX + "processes/*.bpmn");
		configuration.setTransactionManager(transactionManager);
		// 执行工作流对应的数据源
		configuration.setDataSource(dataSource);
		// 是否自动创建流程引擎表
		configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE);
		configuration.setDeploymentResources(resources);
		// 流程图字体
		configuration.setActivityFontName("宋体");
		configuration.setAnnotationFontName("宋体");
		configuration.setLabelFontName("宋体");
		return configuration.buildProcessEngine();
	}

	/*
	 * 不需要单独对流程引擎中的 8 个核心服务做初始化，
	 * 是因为使用 flowable-spring-boot-starter 依赖，会自动帮忙注册好，不需要自己再注册，直接使用即可
	 * 如果你使用的依赖是 flowable-engine，你可能还需要下面的配置
	 */

    /*@Bean
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    @Bean
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    @Bean
    public HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }

    @Bean
    public ManagementService managementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }

    @Bean
    public IdentityService identityService(ProcessEngine processEngine) {
        return processEngine.getIdentityService();
    }

    @Bean
    public FormService formService(ProcessEngine processEngine) {
        return processEngine.getFormService();
    }*/
}
