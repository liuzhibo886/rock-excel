package com.lzb.rock.excel.demo.model;

import java.util.Date;

import com.lzb.rock.excel.annotation.ColumnWidth;
import com.lzb.rock.excel.annotation.ExcelProperty;
import com.lzb.rock.excel.annotation.format.ConverterFormat;
import com.lzb.rock.excel.annotation.format.DateTimeFormat;
import com.lzb.rock.excel.converters.impl.DateTimeConverter;
import com.lzb.rock.excel.demo.write.converters.AuthzTypeConverters;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author lzb
 * @since 2020-02-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Authz implements Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelProperty(value = { "权限ID" })
	private Long authzId;

	@ExcelProperty(value = { "权限编码" })
	private String authzCode;

	@ExcelProperty(value = { "权限父编码" })
	private String authzParentCode;

	@ExcelProperty(value = { "权限名称" })
	private String authzName;

	@ExcelProperty(value = { "权限图片，菜单权限专用" })
	private String authzIcon;

	@ExcelProperty(value = { "权限url地址，菜单权限专用" })
	@ColumnWidth(60)
	private String authzUrl;

	@ExcelProperty(value = { "权限排序，升序" })
	private Integer authzSort;

	@ExcelProperty(value = { "权限类型，0菜单，1按钮;2字段" })
	@ConverterFormat(AuthzTypeConverters.class)
	private Integer authzType;

	@ExcelProperty(value = { "备注" })
	private String authzTips;

	@ExcelProperty(value = { "菜单状态 :  0:启用   1:不启用" })
	private Integer authzStatus;

	@ExcelProperty(value = { "是否打开:    1:打开   0:不打开；菜单权限专用" })
	private Integer authzIsOpen;

	@ExcelProperty(value = { "创建时间" })
	@DateTimeFormat("yyyy-MM-dd")
	private Date createTime;

	@ExcelProperty(value = { "最后修改时间" })
	@DateTimeFormat("yyyy-MM-dd")
	private Date lastTime;

	@ExcelProperty(value = { "最后修改人" })
	private String lastUser;

	@ExcelProperty(value = { "是否删除0正常 1 删除" })
	private Integer isDel;

}
