//package com.fun.ds.dsenum;
//
//import lombok.Data;
//
///**
// * @author ldk
// */
//
//@Data
//public class DataBaseType {
//
//    private static ThreadLocal<DataSourceEnum> dataSourceType = new ThreadLocal();
//
//    public static DataSourceEnum getDataBaseType(){
//        return dataSourceType.get();
//    }
//
//    public static void setDataSourceType(DataSourceEnum dataSourceTypeEnum){
//        dataSourceType.remove();
//        dataSourceType.set(dataSourceTypeEnum);
//    }
//
//}
