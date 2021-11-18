//package com.fun.ds.config;//package com.cmcc.hsh.cmhk.mall.config;
//
//import com.cmcc.hsh.cmhk.mall.annotation.ChangeDataSource;
//import com.cmcc.hsh.cmhk.mall.mallenum.DataBaseType;
//import com.cmcc.hsh.cmhk.mall.mallenum.DataSourceEnum;
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
//	/**
//	 * 改切面只能获取到方法的上的注释，遂弃用
//	 */
//
//    @Pointcut("@annotation(com.cmcc.hsh.cmhk.mall.annotation.ChangeDataSource)")
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
//        ChangeDataSource annotation = method.getAnnotation(ChangeDataSource.class);
//        if (Objects.isNull(annotation)) {
//            annotation = targetClass.getAnnotation(ChangeDataSource.class);
//        }
//
//        DataBaseType.setDataSourceType(Objects.isNull(annotation) ? null : annotation.dataSourceName());
//        try {
//            return point.proceed();
//        } finally {
//            log.info("清除的数据库是{}", annotation.dataSourceName());
//            DataBaseType.setDataSourceType(null);
//        }
//    }
//}
