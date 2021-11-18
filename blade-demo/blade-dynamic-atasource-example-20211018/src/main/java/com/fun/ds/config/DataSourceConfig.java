package com.fun.ds.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.fun.ds.dsenum.DataSourceEnum;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

@Data
@Configuration
//@ConfigurationProperties(prefix = "spring.datasource.ds1")
public class DataSourceConfig {
    private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog("DruidConfiguration");
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*"); // 现在要进行druid监控的配置处理操作
        // IP白名单
        servletRegistrationBean.addInitParameter("allow", "172.19.16.71,172.19.0.119,127.0.0.1,0.0.0.0");
        // IP黑名单(共同存在时，deny优先于allow)
//        servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "admin");
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*"); // 所有请求进行监控处理
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    @Bean(name = "sqlSessionFactoryProperties")
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public MybatisConfiguration mybatisProperties(){
        return new MybatisConfiguration();
    }


    @ConfigurationProperties(prefix = "spring.datasource")
    @Data
    @Component(value = "datasourceProperties")
    public class DatasourceProperties{
        private int initialSize;
        private int minIdle;
        private int maxActive;
        private int maxWait;
        private int timeBetweenEvictionRunsMillis;
        private int minEvictableIdleTimeMillis;
        private int maxEvictableIdleTimeMillis;
        private String validationQuery;
        private boolean testWhileIdle;
        private boolean testOnBorrow;
        private boolean testOnReturn;
        private boolean poolPreparedStatements;
        private int maxPoolPreparedStatementPerConnectionSize;
        private String filters;
        private String connectionProperties;
        private int validationQueryTimeout;
    }

    @Primary
    @Bean(name = "DS2DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.ds1")
    public DataSourceProperties DS2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "DS1DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.ds2")
    public DataSourceProperties DS1DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "DS2DataSource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource DS2DataSource(@Qualifier("DS2DataSourceProperties") DataSourceProperties DS2DataSourceProperties,DatasourceProperties datasourceProperties) {
        DruidDataSource dataSource = new DruidDataSource();
        BeanUtils.copyProperties(datasourceProperties, dataSource);
        dataSource.setUrl(DS2DataSourceProperties.getUrl());
        dataSource.setDriverClassName(DS2DataSourceProperties.getDriverClassName());
        dataSource.setUsername(DS2DataSourceProperties.getUsername());
        dataSource.setPassword(DS2DataSourceProperties.getPassword());
        dataSource.setValidationQuery("select 1 from dual");
        dataSource.setValidationQueryTimeout(datasourceProperties.getValidationQueryTimeout());
        dataSource.setMaxWait(datasourceProperties.getMaxWait());
        dataSource.setMinEvictableIdleTimeMillis(datasourceProperties.getMinEvictableIdleTimeMillis());
        dataSource.setMaxEvictableIdleTimeMillis(datasourceProperties.getMaxEvictableIdleTimeMillis());
        dataSource.setTimeBetweenEvictionRunsMillis(datasourceProperties.getTimeBetweenEvictionRunsMillis());
        try {
            dataSource.setFilters(datasourceProperties.getFilters());
        } catch (SQLException e) {
            logger.error(e);
        }
        return dataSource;
    }


    @Bean(name = "DS1DataSource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource DS1DataSource(@Qualifier("DS1DataSourceProperties") DataSourceProperties DS1DataSource,DatasourceProperties datasourceProperties) {
        DruidDataSource dataSource = new DruidDataSource();
        BeanUtils.copyProperties(datasourceProperties, dataSource);
        dataSource.setUrl(DS1DataSource.getUrl());
        dataSource.setDriverClassName(DS1DataSource.getDriverClassName());
        dataSource.setUsername(DS1DataSource.getUsername());
        dataSource.setPassword(DS1DataSource.getPassword());
        dataSource.setValidationQuery("select 1");
        dataSource.setValidationQueryTimeout(datasourceProperties.getValidationQueryTimeout());
        dataSource.setMaxWait(datasourceProperties.getMaxWait());
        dataSource.setMinEvictableIdleTimeMillis(datasourceProperties.getMinEvictableIdleTimeMillis());
        dataSource.setMaxEvictableIdleTimeMillis(datasourceProperties.getMaxEvictableIdleTimeMillis());
        dataSource.setTimeBetweenEvictionRunsMillis(datasourceProperties.getTimeBetweenEvictionRunsMillis());
        try {
            dataSource.setFilters(datasourceProperties.getFilters());
        } catch (SQLException e) {
            logger.error(e);
        }
        return dataSource;
    }


    @Bean(name = "dynamicDataSource")
    public DynamicDataSource DataSource(@Qualifier("DS1DataSource") DataSource DS2DataSource,
                                        @Qualifier("DS2DataSource") DataSource DS1DataSource
    ) {
        Map<Object, Object> targetDataSource = Maps.newHashMapWithExpectedSize(4);

        targetDataSource.put(DataSourceEnum.DS1, DS2DataSource);
        targetDataSource.put(DataSourceEnum.DS2, DS1DataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);
        dataSource.setDefaultTargetDataSource(DS2DataSource);
        return dataSource;
    }

	/**
	 * 获取所有Mybatis插件
	 */
	@Bean(name = "mybatisInterceptors")
	public Interceptor[] mybatisInterceptors(@Autowired ApplicationContext applicationContext){
		Map<String, Interceptor> beansOfInterceptor = applicationContext.getBeansOfType(Interceptor.class);
		return Optional.of(beansOfInterceptor)
			.map(Map::values)
			.map(values -> values.toArray(new Interceptor[values.size()]))
			.orElse(null);
	}


    @Primary
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory CreatSqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource,
													@Qualifier("sqlSessionFactoryProperties")  MybatisConfiguration sqlSessionFactoryConfiguration,
													@Qualifier("mybatisInterceptors") Interceptor[] mybatisInterceptors) throws Exception {
        //SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
		// 注意这里替换成我们写的DynamicDataSource
        bean.setDataSource(dynamicDataSource);
		// 替换原有的事物工厂，不然无法切换数据库
        bean.setTransactionFactory(new MyTransactionsFactory());
        bean.setConfiguration(sqlSessionFactoryConfiguration);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] eshopResources = resolver.getResources("classpath:com/fun/ds/mapper/*.xml");
        Resource[] otherResources = resolver.getResources("classpath:com/fun/ds/**/mapper/*.xml");
        Resource[] resources = ArrayUtils.addAll(otherResources, eshopResources);
        bean.setMapperLocations(resources);
        //设置Mybatis插件
//        bean.setPlugins(new ExecutorInterceptor()); //单个注册
		bean.setPlugins(mybatisInterceptors);
        return bean.getObject();
    }
}
