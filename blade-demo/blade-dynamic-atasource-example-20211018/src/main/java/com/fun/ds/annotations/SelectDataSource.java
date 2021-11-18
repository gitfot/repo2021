package com.fun.ds.annotations;

import com.fun.ds.dsenum.DataSourceEnum;

import java.lang.annotation.*;

/**
 * @author wanwan 2021/10/18
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@Inherited
public @interface SelectDataSource {

	DataSourceEnum value() default DataSourceEnum.DS1;
}
