package com.lzb.rock.excel.converters.impl;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

import com.lzb.rock.excel.converters.Converter;

import lombok.Data;

/**
 * 日期格式化
 * 
 * @author liuzhibo
 *
 */
@Data
public class DateTimeConverter implements Converter {

	public SimpleDateFormat format = null;

	private String defaultValue = "";

	@Override
	public String convertToExcelData(Object obj) {

		if (obj != null) {
			return format.format(obj);
		}
		return defaultValue;
	}

	public void setFormat(String pattern) {
		if (StringUtils.isBlank(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		format = new SimpleDateFormat(pattern);
	}

}
