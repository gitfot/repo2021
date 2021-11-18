package com.fun.ds.Interceptor;

import com.fun.ds.annotations.SelectDataSource;
import com.fun.ds.config.DynamicDataSourceContextHolder;
import com.fun.ds.dsenum.DataSourceEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Mybatis数据源转换插件类
 *
 * <p>拦截以下3个方法
 * <p>org.apache.ibatis.executor.Executor#update(org.apache.ibatis.mapping.MappedStatement, java.lang.Object)
 * <p>org.apache.ibatis.executor.Executor#query(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler)
 * <p>org.apache.ibatis.executor.Executor#queryCursor(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds)
 *
 * @author LSY
 * @since 2020/6/9
 */
@Slf4j
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "queryCursor", args = {MappedStatement.class, Object.class, RowBounds.class})
})
public class ExecutorInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        SelectDataSource(invocation);
        return invocation.proceed();
    }

    /**
     * 根据mapper的id找到对应的类和方法以获取@SelectDataSource注解信息，并修改数据源
     *
     * <p>优先使用方法上指定的数据源
     *
     * @param invocation 调用方
     */
    private void SelectDataSource(Invocation invocation){
        Object[] args = Optional.ofNullable(invocation)
                .map(Invocation::getArgs)
                .orElse(null);
        if (Objects.isNull(args)){
            log.error("ExecutorInterceptor can't get args of {}",invocation);
            return;
        }

        MappedStatement mappedStatement = Arrays.stream(args)
                .filter(arg -> arg instanceof MappedStatement)
                .map(arg -> (MappedStatement) arg)
                .findFirst()
                .orElse(null);

        //获取对应类
        String classType = Optional.ofNullable(mappedStatement)
                .map(MappedStatement::getId)
                .map(id -> id.substring(0, id.lastIndexOf(".")))
                .orElse(null);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(classType);
        } catch (ClassNotFoundException e) {
            log.error("ExecutorInterceptor can't get clazz by mapper id : ",e);
            return;
        }

        //获取对应方法
        Method method = null;
        String methodName = Optional.ofNullable(mappedStatement)
                .map(MappedStatement::getId)
                .map(id -> id.substring(id.lastIndexOf(".") + 1))
                .orElse(null);
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (StringUtils.equals(declaredMethod.getName(),methodName)){
                method = declaredMethod;
                break;
            }
        }

        //获取方法或类上的注解
        DataSourceEnum dataSourceEnum = Optional.ofNullable(method)
                .map(m -> m.getAnnotation(SelectDataSource.class))
                .map(SelectDataSource::value)
                .orElse(null);
        if (Objects.isNull(dataSourceEnum)){
            dataSourceEnum = Optional.of(clazz)
                .map(c -> c.getAnnotation(SelectDataSource.class))
                .map(SelectDataSource::value)
                .orElse(null);
        }

        //修改数据源
        if (Objects.nonNull(dataSourceEnum)) {
			DynamicDataSourceContextHolder.setDateSourceType(dataSourceEnum);
        }
		else {//设置默认数据源
			DynamicDataSourceContextHolder.setDateSourceType(DataSourceEnum.DS2);
		}
    }

    /***
     * 定义拦截的类 Executor、ParameterHandler、StatementHandler、ResultSetHandler当中的一个
     * @param target 需要拦截的类
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;

    }


}
