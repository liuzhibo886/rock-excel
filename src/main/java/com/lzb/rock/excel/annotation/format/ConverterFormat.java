package com.lzb.rock.excel.annotation.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lzb.rock.excel.converters.Converter;

/**
 * 自定义转换器，优先级别最高
 * 
 * @author liuzhibo
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ConverterFormat {

	Class<? extends Converter> value();
}
