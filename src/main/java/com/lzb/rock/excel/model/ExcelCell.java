package com.lzb.rock.excel.model;

import java.lang.reflect.Method;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellType;

import com.lzb.rock.excel.converters.Converter;

import lombok.Data;

@Data
public class ExcelCell {

	/**
	 * 内容样式
	 */
	HSSFCellStyle bodyHSSFCellStyle;
	/**
	 * 头部样式
	 */
	HSSFCellStyle headHSSFCellStyle;
	/**
	 * 列宽，20 表示20 个字符宽带
	 */
	Integer columnWidth = 20;
	/**
	 * 字段名
	 */
	String fieldName;

	/**
	 * 字段get方法，反射取值
	 */
	Method methodGet;

	/**
	 * excl值类型
	 */
	CellType type;

	/**
	 * 表头名称
	 */
	String[] headName;

	/**
	 * 转换器
	 */
	Converter converter;
	/**
	 * 字段类型
	 */
	Class<?> clazz;
}
