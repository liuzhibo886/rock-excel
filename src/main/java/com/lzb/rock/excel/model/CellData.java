package com.lzb.rock.excel.model;

import lombok.Data;

/**
 * excl内容对象
 * 
 * @author liuzhibo
 *
 * @param <T>
 */
@Data
public class CellData {
	/**
	 * java对象中的值
	 */
	private Object javaValue;
	/**
	 * excl表格中的值
	 */
	private String exclValue;

}
