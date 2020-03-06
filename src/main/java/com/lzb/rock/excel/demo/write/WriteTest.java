package com.lzb.rock.excel.demo.write;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.lzb.rock.excel.ExcelWriter;
import com.lzb.rock.excel.demo.model.Authz;

public class WriteTest {

	public static List<Authz> getData() throws Exception {
		StringBuffer sb = new StringBuffer();
		File file = new File("src/main/resources/authz.json");
		FileInputStream inputStream = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String str = new String();
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}
		JSONArray jsonArr = (JSONArray) JSONArray.parse(sb.toString());
		List<Authz> data = jsonArr.toJavaList(Authz.class);
		return data;
	}

	public static void main(String[] args) throws Exception {
		String sheetName = "测试";
		File file = new File("G:\\text2.xls");
//		HSSFWorkbook workBook = new HSSFWorkbook();// 创建Excel工作簿对象
//		HSSFSheet sheet = workBook.createSheet(sheetName);// 创建Excel工作表对象
//		//画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
//		//HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//		workBook.write(file);

		if (file.exists()) {
			file.delete();
		}
		FileOutputStream out = new FileOutputStream(file, true);
		ExcelWriter.write(out).clazz(Authz.class).doWrite(sheetName, WriteTest.getData())
				.doWrite(sheetName+1, WriteTest.getData()).doWrite(sheetName+2, WriteTest.getData()).close();
	}

}
