package com.fun.ds.consts;

/**
 * ehsop新网站常量类
 */
public interface Constants {

    /**
     *  数据库连接自动提交标志，开启事务时使用
     */
    ThreadLocal<Boolean> TRANSATION_EFFECTIVE = new ThreadLocal<>();


}
