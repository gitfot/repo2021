package com.fun.ds.aspect;

import com.fun.ds.consts.Constants;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author ldk
 * @desc 多数据源注解事务切面
 */
@Aspect
@Component
public class TransactionAspect {

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void pointcut(){}


    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        Constants.TRANSATION_EFFECTIVE.set(true);
    }

    @After("pointcut()")
    public void after(){
        Constants.TRANSATION_EFFECTIVE.remove();
    }


}
