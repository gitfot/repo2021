package com.fun.ds.config;

import com.fun.ds.dsenum.DataSourceEnum;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDateSourceType();
    }

    public DataSourceEnum getCurrentDatasourceKey() {
        return (DataSourceEnum) determineCurrentLookupKey();
    }
}
