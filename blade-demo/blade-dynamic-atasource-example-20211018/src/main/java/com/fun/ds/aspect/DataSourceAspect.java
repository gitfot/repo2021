//package com.fun.ds.aspect;
//
//import com.fun.ds.annotations.SelectDataSource;
//import com.fun.ds.config.DynamicDataSourceContextHolder;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//import java.util.Objects;
//
///**
// * @author wanwan 2021/10/18
// */
//
//@Aspect
//@Component
//@Slf4j
//public class DataSourceAspect {
//
//    @Pointcut("@annotation(com.fun.ds.annotations.SelectDataSource)")
//    public void dataSourcePointCut() {
//
//    }
//
//    @Around("dataSourcePointCut()")
//    public Object around(ProceedingJoinPoint point) throws Throwable {
//        MethodSignature signature = (MethodSignature) point.getSignature();
//        Class<?> targetClass = Class.forName(signature.getDeclaringTypeName());
//        Method method = signature.getMethod();
//        //优先从方法上获取
//		SelectDataSource annotation = method.getAnnotation(SelectDataSource.class);
//        if (Objects.isNull(annotation)) {
//            annotation = targetClass.getAnnotation(SelectDataSource.class);
//        }
//
//		DynamicDataSourceContextHolder.setDateSourceType(Objects.isNull(annotation) ? null : annotation.value());
//        try {
//            return point.proceed();
//        } finally {
//            log.info("清除的数据库是{}", annotation.value());
//			DynamicDataSourceContextHolder.clearDateSourceType();
//        }
//    }
//}
