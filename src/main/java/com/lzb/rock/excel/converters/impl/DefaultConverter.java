package com.lzb.rock.excel.converters.impl;

import com.lzb.rock.excel.converters.Converter;
import com.lzb.rock.excel.model.CellData;

import lombok.Data;

/**
 * 默认转换器
 * 
 * @author liuzhibo
 *
 */
@Data
public class DefaultConverter implements Converter {

	private String defaultValue = "";

	@Override
	public String convertToExcelData(Object obj) {

		if (obj != null) {
			return obj.toString();
		}
		return defaultValue;
	}

}
