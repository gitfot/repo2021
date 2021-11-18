package com.fun.ds.listener;

import com.fun.ds.Interceptor.ExecutorInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

/**
 * 当项目中使用了PageHelperExecutor时，需要启用改监听器
 *
 * @author wanwan 2021/10/19
 */
//@Component
public class SqlSessionListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

	/**
	 * 注册一个ApplicationListener监听器，监听 ContextRefreshedEvent 事件，
	 * 当所有的bean都初始化完成后（即PageHelper也已经注册好了），
	 * 再把我们的自定义 MyBatis 拦截器注册到 SqlSessionFactory 中
	 */
	@Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(new ExecutorInterceptor());
        }
    }
}
