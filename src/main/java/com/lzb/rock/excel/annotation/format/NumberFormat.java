package com.lzb.rock.excel.annotation.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;

/**
 * 数字格式化
 * 
 * @author liuzhibo
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface NumberFormat {

	/**
	 * 保留小数位
	 * 
	 * @author liuzhibo
	 * @date 2019年12月24日 上午9:22:10
	 * @return
	 */
	int value() default 2;

	/**
	 * 取舍方式，默认四舍五入
	 * 
	 * @author liuzhibo
	 * @date 2019年12月24日 上午9:22:24
	 * @return
	 */
	int roundingMode() default BigDecimal.ROUND_HALF_UP;

}
