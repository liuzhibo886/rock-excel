package com.lzb.rock.excel;

import java.io.OutputStream;

import com.lzb.rock.excel.builder.ExcelWriterBuilder;

/**
 * excl 导出入口
 * 
 * @author liuzhibo
 *
 */
public class ExcelWriter {

	public static ExcelWriterBuilder write(OutputStream outputStream) {
		  ExcelWriterBuilder excelWriterBuilder=new ExcelWriterBuilder(outputStream); 
		  return excelWriterBuilder;
	  }
	

}
