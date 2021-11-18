package com.fun.ds.config;

import com.fun.ds.consts.Constants;
import com.fun.ds.dsenum.DataSourceEnum;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MyManagedTransaction extends SpringManagedTransaction {

    private final DataSource dataSource;

    ConcurrentHashMap<DataSourceEnum, Connection> map = new ConcurrentHashMap<>();


    public MyManagedTransaction(DataSource dataSource) {
        super(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {//避免添加事务后，多数据源不切换的问题
        DynamicDataSource dynamicDatasource = (DynamicDataSource) dataSource;
        DataSourceEnum key = dynamicDatasource.getCurrentDatasourceKey();

        if (map.containsKey(key)) {
            return map.get(key);
        }
        Connection con = dataSource.getConnection();
        //对TRANSATION注解做了切面，根据常量的ThreadLocal进行连接自动提交处理
        if(Objects.nonNull(Constants.TRANSATION_EFFECTIVE.get()) && Constants.TRANSATION_EFFECTIVE.get()){
            con.setAutoCommit(false);
        }
        map.put(key, con);
        return con;
    }

    @Override
    public void close() {
        Collection<Connection> values = map.values();
        for (Connection value : values) {
            DataSourceUtils.releaseConnection(value, this.dataSource);
        }
    }

    @Override
    public void rollback() throws SQLException {
        Collection<Connection> values = map.values();
        for (Connection value : values) {
            value.rollback();
        }
    }

    @Override
    public void commit() throws SQLException {
        Collection<Connection> values = map.values();
        for (Connection value : values) {
            //如果连接自动提交打开，则不做任何处理
            if(!value.getAutoCommit()){
                value.commit();
            }
        }
    }

}
