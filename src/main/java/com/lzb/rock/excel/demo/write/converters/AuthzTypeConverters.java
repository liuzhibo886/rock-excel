package com.lzb.rock.excel.demo.write.converters;

import java.util.HashMap;
import java.util.Map;

import com.lzb.rock.excel.converters.Converter;

public class AuthzTypeConverters implements Converter {
	Map<Object, String> map = new HashMap<Object, String>();

	public AuthzTypeConverters() {
		map.put(0, "菜单");
		map.put(1, "按钮");
		map.put(2, "字段");
	}

	@Override
	public String convertToExcelData(Object obj) {
		return map.get(obj);
	}

}
