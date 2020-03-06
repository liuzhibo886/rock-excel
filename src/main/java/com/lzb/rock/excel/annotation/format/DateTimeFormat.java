package com.lzb.rock.excel.annotation.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日期格式化
 * 
 * @author liuzhibo
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DateTimeFormat {
	String value() default "yyyy-MM-dd";
}
