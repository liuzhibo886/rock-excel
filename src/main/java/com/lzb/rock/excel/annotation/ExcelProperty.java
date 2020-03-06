package com.lzb.rock.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 导出字段
 * 
 * @author liuzhibo
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelProperty {
	/**
	 * 表头
	 * @return
	 */
	String[] value();

	/**
	 * 排序
	 * @return
	 */
	int index() default Integer.MIN_VALUE;
}
