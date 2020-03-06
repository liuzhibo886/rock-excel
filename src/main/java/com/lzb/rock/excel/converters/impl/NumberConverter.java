package com.lzb.rock.excel.converters.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.lzb.rock.excel.converters.Converter;
import com.lzb.rock.excel.model.CellData;

import lombok.Data;

/**
 * 数字格式化
 * 
 * @author liuzhibo
 *
 */
@Data
public class NumberConverter implements Converter {

	private int newScale = 2;

	private int roundingMode = BigDecimal.ROUND_HALF_UP;

	private String defaultValue = "";

	@Override
	public String convertToExcelData(Object obj) {
		if (obj != null) {
			BigDecimal javaValue = null;
			if (obj instanceof BigDecimal) {
				javaValue = (BigDecimal) obj;
			} else {
				javaValue = new BigDecimal(obj.toString());
			}
			return javaValue.setScale(newScale, roundingMode).toString();
		}
		return defaultValue;
	}

}
