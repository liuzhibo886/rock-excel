package com.lzb.rock.excel.converters;

import com.lzb.rock.excel.model.CellData;

/**
 * 
 * @author liuzhibo
 *
 * @param <T>
 */
public interface Converter {

	/**
	 * java 转
	 * @param <T>
	 * 
	 * @param <T>
	 * @param t
	 * @return
	 */
	 String convertToExcelData(Object obj);

}
