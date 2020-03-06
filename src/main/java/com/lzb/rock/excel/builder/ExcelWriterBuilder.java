package com.lzb.rock.excel.builder;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import com.lzb.rock.excel.annotation.ColumnWidth;
import com.lzb.rock.excel.annotation.ExcelProperty;
import com.lzb.rock.excel.annotation.format.ConverterFormat;
import com.lzb.rock.excel.annotation.format.DateTimeFormat;
import com.lzb.rock.excel.annotation.format.NumberFormat;
import com.lzb.rock.excel.converters.Converter;
import com.lzb.rock.excel.converters.impl.DateTimeConverter;
import com.lzb.rock.excel.converters.impl.DefaultConverter;
import com.lzb.rock.excel.converters.impl.NumberConverter;
import com.lzb.rock.excel.model.ExcelCell;

/**
 * 导出实现类
 * 
 * @author liuzhibo
 *
 */
public class ExcelWriterBuilder {
	/**
	 * Excel工作簿对象,一个excl唯一对象
	 */
	private HSSFWorkbook workbook;

	/**
	 * 输出流
	 */
	private OutputStream outputStream;

	/**
	 * 导出表格基础信息
	 */
	List<ExcelCell> excelCells;

	float HeightInPoints = 20;

	public ExcelWriterBuilder(OutputStream outputStream) {
		this.workbook = new HSSFWorkbook();
		this.outputStream = outputStream;
	}

	/**
	 * 导出数据
	 * 
	 * @param sheetName 工作表名称
	 * @param data      数据集
	 * @throws IOException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public ExcelWriterBuilder doWrite(String sheetName, List<?> data)
			throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		HSSFSheet sheet = this.workbook.createSheet(sheetName);
		Integer rownum = 0;
		// 生成表头
		rownum = writeHead(sheet, rownum);
		// 生成内容
		for (Object obj : data) {
			HSSFRow row = sheet.createRow(rownum);
			row.setHeightInPoints(HeightInPoints);
			Integer columnIndex = 0;
			for (ExcelCell excelCell : excelCells) {
				HSSFCell cell = row.createCell(columnIndex);
				// HSSFCell cell = row.createCell(columnIndex, CellType.NUMERIC);
				String exclValue = excelCell.getConverter().convertToExcelData(excelCell.getMethodGet().invoke(obj));
				cell(excelCell.getBodyHSSFCellStyle(), exclValue, cell);
				columnIndex++;
			}
			rownum++;
		}

		return this;
	}

	/**
	 * 关闭流
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		workbook.write(outputStream);
		outputStream.flush();
		if (outputStream != null) {
			outputStream.close();
		}

	}

	/**
	 * 生成表头
	 * 
	 * @param sheet
	 * @param rownum
	 */
	public Integer writeHead(HSSFSheet sheet, Integer rownum) {
		HSSFRow row = sheet.createRow(rownum);
		Integer columnIndex = 0;
		for (ExcelCell excelCell : excelCells) {
			sheet.setColumnWidth(columnIndex, 256 * excelCell.getColumnWidth() + 184);
			HSSFCell cell = row.createCell(columnIndex, CellType.NUMERIC);
			cell(excelCell.getHeadHSSFCellStyle(), excelCell.getHeadName()[0], cell);
			columnIndex++;
		}
		rownum++;
		return rownum;
	}

	/**
	 * 获取ExcelCell 对象
	 * 
	 * @param clazz
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public ExcelWriterBuilder clazz(Class<?> clazz) throws NoSuchMethodException, SecurityException {
		Field[] fields = clazz.getDeclaredFields();
		List<ExcelCell> excelCells = new ArrayList<ExcelCell>(fields.length);

		for (Field field : fields) {
			ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
			if (excelProperty == null) {
				continue;
			}
			ExcelCell excelCell = new ExcelCell();
			// 设置表内容样式
			excelCell.setBodyHSSFCellStyle(getBodyStyle(this.workbook));
			// 设置表头样式
			excelCell.setHeadHSSFCellStyle(getHeadStyle(this.workbook));
			// 设置表头
			excelCell.setHeadName(excelProperty.value());
			// 设置转换器
			excelCell.setConverter(getConverter(field));
			// 设置字段类型
			excelCell.setClazz(field.getType());
			// 表格宽度
			ColumnWidth columnWidth = field.getAnnotation(ColumnWidth.class);
			if (columnWidth != null) {
				excelCell.setColumnWidth(columnWidth.value());
			}

			String fieldName = field.getName();
			excelCell.setFieldName(fieldName);
			String methodName = "get"
					+ fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
			Method methodGet = clazz.getMethod(methodName);
			excelCell.setMethodGet(methodGet);
			excelCells.add(excelCell);
		}

		this.excelCells = excelCells;
		return this;
	}

	/**
	 * 获取转换器
	 * 
	 * @param field
	 * @return
	 */
	public Converter getConverter(Field field) {

		// 自定义转换器
		ConverterFormat converterFormat = field.getAnnotation(ConverterFormat.class);
		if (converterFormat != null) {
			Class<? extends Converter> clazz = converterFormat.value();
			try {
				return clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 数字格式化
		NumberFormat numberFormat = field.getAnnotation(NumberFormat.class);
		if (numberFormat != null) {
			NumberConverter numberConverter = new NumberConverter();
			numberConverter.setNewScale(numberFormat.value());
			numberConverter.setRoundingMode(numberFormat.roundingMode());
			return numberConverter;
		}
		// 日期格式化
		DateTimeFormat dateTimeFormat = field.getAnnotation(DateTimeFormat.class);
		if (dateTimeFormat != null) {
			DateTimeConverter dateTimeConverter = new DateTimeConverter();
			dateTimeConverter.setFormat(dateTimeFormat.value());
			return dateTimeConverter;
		}

		return new DefaultConverter();
	}

	/**
	 * 获取表头格式
	 * 
	 * @param wb
	 * @return
	 */
	public HSSFCellStyle getHeadStyle(HSSFWorkbook wb) {
		// 创建单元格样式
		HSSFCellStyle style = wb.createCellStyle();
		// 设置字体
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 14); // 字体高度
		font.setFontName("黑体 "); // 字体
		font.setBold(true);// 粗体显示
		font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		style.setBorderBottom(BorderStyle.THIN); // 下边框
		style.setBorderLeft(BorderStyle.THIN);// 左边框
		style.setBorderTop(BorderStyle.THIN);// 上边框
		style.setBorderRight(BorderStyle.THIN);// 右边框
		// 水平居中
		style.setAlignment(HorizontalAlignment.CENTER);
		// 垂直居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		// 自动换行
		style.setWrapText(true);
		style.setFont(font);
		return style;
	}

	/**
	 * 获取表内容单元格式
	 * 
	 * @param wb
	 * @return
	 */
	public HSSFCellStyle getBodyStyle(HSSFWorkbook wb) {
		// 创建单元格样式
		HSSFCellStyle style = wb.createCellStyle();
		// 设置字体
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 10); // 字体高度
		font.setFontName("黑体 "); // 字体
		font.setBold(false);// 粗体显示
		font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		// 设置对齐方式
		style.setAlignment(HorizontalAlignment.LEFT);
		// 垂直居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setWrapText(false);
		style.setFont(font);
		return style;
	}

	public void cell(HSSFCellStyle style, String text, HSSFCell cell) {
		// 设置cell样式
		cell.setCellStyle(style);
		// 设置cell文本内容
		if (text == null) {
			text = "";
		}
		cell.setCellValue(new HSSFRichTextString(text));
	}

}
