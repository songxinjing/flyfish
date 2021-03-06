package com.songxinjing.flyfish.excel;

import java.util.LinkedHashMap;
import java.util.Map;

import com.songxinjing.flyfish.constant.Constant;

public class ExcelTemp {

	/**
	 * 普通模版文件
	 */
	public static final String COMMON = "/excelTemp/common.xls";

	/**
	 * WISH模版文件
	 */
	public static final String WISH = "/excelTemp/wish.csv";

	/**
	 * JOOM模版文件
	 */
	public static final String JOOM = "/excelTemp/joom.csv";
	
	/**
	 * 综合导出模版文件
	 */
	public static final String COMMEXP = "/excelTemp/commexp.xlsx";
	
	
	/**
	 * 虚拟SKU模版文件
	 */
	public static final String VIRTSKU = "/excelTemp/virtsku.xlsx";

	/**
	 * 普通模版字段映射
	 */
	public static final Map<String, String> COMMON_FIELD = new LinkedHashMap<String, String>();

	static {
		COMMON_FIELD.put("操作类型", "");
		COMMON_FIELD.put("商品编码", "parentSku");
		COMMON_FIELD.put("SKU", "sku");
		COMMON_FIELD.put("sku", "sku");
		COMMON_FIELD.put("多款式", "isMoreSytle");
		COMMON_FIELD.put("是否有样品", "hasSample");
		COMMON_FIELD.put("样品数量", "sampleNum");
		COMMON_FIELD.put("大类名称", "bigCataName");
		COMMON_FIELD.put("管理类别", "bigCataName");
		COMMON_FIELD.put("小类名称", "smallCataName");
		COMMON_FIELD.put("商品名称", "name");
		COMMON_FIELD.put("当前状态", "state");
		COMMON_FIELD.put("停售", "state");
		COMMON_FIELD.put("材质", "");
		COMMON_FIELD.put("规格", "");
		COMMON_FIELD.put("型号", "");
		COMMON_FIELD.put("款式", "style");
		COMMON_FIELD.put("品牌", "make");
		COMMON_FIELD.put("单位", "");
		COMMON_FIELD.put("最小包装数", "minNum");
		COMMON_FIELD.put("重量(G)", "weight");
		COMMON_FIELD.put("重量(克)", "weight");
		COMMON_FIELD.put("采购渠道", "");
		COMMON_FIELD.put("供应商名称", "supplyName");
		COMMON_FIELD.put("成本单价(元)", "costPrice");
		COMMON_FIELD.put("成本价(RMB)", "costPrice");
		COMMON_FIELD.put("批发价格(美元)", "batchPrice");
		COMMON_FIELD.put("批发价格", "batchPrice");
		COMMON_FIELD.put("零售价格(美元)", "");
		COMMON_FIELD.put("零售价格", "");
		COMMON_FIELD.put("最低售价(美元)", "");
		COMMON_FIELD.put("最低售价($)", "");
		COMMON_FIELD.put("最高售价(美元)", "");
		COMMON_FIELD.put("最高售价", "");
		COMMON_FIELD.put("市场参考价(美元)", "");
		COMMON_FIELD.put("市场参考价", "");
		COMMON_FIELD.put("数量", "");
		COMMON_FIELD.put("备注", "");
		COMMON_FIELD.put("中文申报名", "reportNameCn");
		COMMON_FIELD.put("英文申报名", "reportNameEn");
		COMMON_FIELD.put("申报价值(美元)", "reportPrice");
		COMMON_FIELD.put("原产国代码", "prodCountryCd");
		COMMON_FIELD.put("原产国", "prodCountryName");
		COMMON_FIELD.put("库存上限", "");
		COMMON_FIELD.put("库存下限", "");
		COMMON_FIELD.put("业绩归属人1", "bussOwner1");
		COMMON_FIELD.put("业绩归属人", "bussOwner1");
		COMMON_FIELD.put("业绩归属人2", "bussOwner2");
		COMMON_FIELD.put("业绩归属2", "bussOwner2");
		COMMON_FIELD.put("包装规格", "");
		COMMON_FIELD.put("开发日期", "bussDate");
		COMMON_FIELD.put("SKU款式1", "");
		COMMON_FIELD.put("SKU款式2", "");
		COMMON_FIELD.put("SKU款式3", "");
		COMMON_FIELD.put("SKU描述", "");
		COMMON_FIELD.put("图片", "");
		COMMON_FIELD.put("图片URL", "");
		COMMON_FIELD.put("图片路径", "");
		COMMON_FIELD.put("采购员", "buyer");
		COMMON_FIELD.put("发货仓库", "");
		COMMON_FIELD.put("采购到货天数", "buyDayNum");
		COMMON_FIELD.put("内包装成本", "");
		COMMON_FIELD.put("外包装成本", "");
		COMMON_FIELD.put("网页URL", "url");
		COMMON_FIELD.put("LinkUrl", "url");
		COMMON_FIELD.put("网页URL2", "url2");
		COMMON_FIELD.put("LinkUrl2", "url2");
		COMMON_FIELD.put("网页URL3", "url3");
		COMMON_FIELD.put("LinkUrl3", "url3");
		COMMON_FIELD.put("最低采购价格", "minBuyPrice");
		COMMON_FIELD.put("最低采购单价", "minBuyPrice");
		COMMON_FIELD.put("海关编码", "");
		COMMON_FIELD.put("库存预警销售周期", "");
		COMMON_FIELD.put("采购最小订货量", "");
		COMMON_FIELD.put("内盒长", "");
		COMMON_FIELD.put("内盒宽", "");
		COMMON_FIELD.put("内盒高", "");
		COMMON_FIELD.put("内盒长(cm)", "");
		COMMON_FIELD.put("内盒宽(cm)", "");
		COMMON_FIELD.put("内盒高(cm)", "");
		COMMON_FIELD.put("内盒毛重", "");
		COMMON_FIELD.put("内盒净重", "");
		COMMON_FIELD.put("外箱长", "outLength");
		COMMON_FIELD.put("外箱宽", "outWidth");
		COMMON_FIELD.put("外箱高", "outHeight");
		COMMON_FIELD.put("外箱长(cm)", "outLength");
		COMMON_FIELD.put("外箱宽(cm)", "outWidth");
		COMMON_FIELD.put("外箱高(cm)", "outHeight");
		COMMON_FIELD.put("外箱毛重", "");
		COMMON_FIELD.put("外箱净重", "");
		COMMON_FIELD.put("商品URL", "");
		COMMON_FIELD.put("包装事项", "");
		COMMON_FIELD.put("是否带电", "isElectric");
		COMMON_FIELD.put("商品SKU状态", "skuState");
		COMMON_FIELD.put("工号权限", "");
		COMMON_FIELD.put("季节", "season");
		COMMON_FIELD.put("是否粉末", "isPowder");
		COMMON_FIELD.put("是否液体", "isLiquid");
		COMMON_FIELD.put("责任归属人1", "respOwner1");
		COMMON_FIELD.put("责任归属人2", "respOwner2");
		COMMON_FIELD.put("店铺名称", "");
		COMMON_FIELD.put("Shop名称", "");
		COMMON_FIELD.put("UPC码", "");
		COMMON_FIELD.put("ASIN码", "");
		COMMON_FIELD.put("网页URL4", "");
		COMMON_FIELD.put("网页URL5", "");
		COMMON_FIELD.put("网页URL6", "");
		COMMON_FIELD.put("LinkUrl4", "");
		COMMON_FIELD.put("LinkUrl5", "");
		COMMON_FIELD.put("LinkUrl6", "");
		COMMON_FIELD.put("商品属性", "");
		COMMON_FIELD.put("店铺运费", "");
		COMMON_FIELD.put("包装材料重量", "");
		COMMON_FIELD.put("包装规格重量", "");
		COMMON_FIELD.put("汇率", "");
		COMMON_FIELD.put("物流公司价格", "");
		COMMON_FIELD.put("交易费", "");
		COMMON_FIELD.put("毛利率", "");
		COMMON_FIELD.put("计算售价", "");

		COMMON_FIELD.put("停售时间", "stopTm");
		COMMON_FIELD.put("停售原因", "stopReson");
		COMMON_FIELD.put("是否带磁", "");
		COMMON_FIELD.put("功能", "");
		COMMON_FIELD.put("组合品", "isJoin");

		COMMON_FIELD.put("简拼", "");
		COMMON_FIELD.put("采购渠道", "");
		COMMON_FIELD.put("所有仓库库存数量", "allNum");
		COMMON_FIELD.put("编号", "pyNum");
		COMMON_FIELD.put("创建时间", "createTm");
	}

	/**
	 * WISH模版字段映射
	 */
	public static final Map<String, String> WISH_FIELD = new LinkedHashMap<String, String>();

	static {
		WISH_FIELD.put("Parent Unique ID", "");
		WISH_FIELD.put("*Product Name", "title");
		WISH_FIELD.put("Description", "descp");
		WISH_FIELD.put("*Tags", "tags");
		WISH_FIELD.put("*Unique ID", "");
		WISH_FIELD.put("Color", "color");
		WISH_FIELD.put("Size", "size");
		WISH_FIELD.put("*Quantity", "quantity");
		WISH_FIELD.put("*Price", "");
		WISH_FIELD.put("*Shipping", "");
		WISH_FIELD.put("Shipping Time(enter without \" \", just the estimated days )", "shipDays");
		WISH_FIELD.put("*Product Main Image URL", "mainImgUrl");
		WISH_FIELD.put("Variant Main Image URL", "vMainImgUrl");
		WISH_FIELD.put("Extra Image URL", "eImgUrl");
		WISH_FIELD.put("Extra Image URL 1", "eImgUrl1");
		WISH_FIELD.put("Extra Image URL 2", "eImgUrl2");
		WISH_FIELD.put("Extra Image URL 3", "eImgUrl3");
		WISH_FIELD.put("Extra Image URL 4", "eImgUrl4");
		WISH_FIELD.put("Extra Image URL 5", "eImgUrl5");
		WISH_FIELD.put("Extra Image URL 6", "eImgUrl6");
		WISH_FIELD.put("Extra Image URL 7", "eImgUrl7");
		WISH_FIELD.put("Extra Image URL 8", "eImgUrl8");
		WISH_FIELD.put("Extra Image URL 9", "eImgUrl9");
		WISH_FIELD.put("Extra Image URL 10", "eImgUrl10");
	}

	/**
	 * JOOM模版字段映射
	 */
	public static final Map<String, String> JOOM_FIELD = new LinkedHashMap<String, String>();

	static {
		JOOM_FIELD.put("Parent Unique ID", "");
		JOOM_FIELD.put("*Product Name", "title");
		JOOM_FIELD.put("Description", "descp");
		JOOM_FIELD.put("*Tags", "tags");
		JOOM_FIELD.put("*Unique ID", "");
		JOOM_FIELD.put("Color", "color");
		JOOM_FIELD.put("Size", "size");
		JOOM_FIELD.put("*Quantity", "quantity");
		JOOM_FIELD.put("*Price", "");
		JOOM_FIELD.put("*Shipping", "");
		JOOM_FIELD.put("MSRP", "");
		JOOM_FIELD.put("Shipping Time(enter without \" \", just the estimated days )", "shipDays");
		JOOM_FIELD.put("Shipping Weight", "weight");
		JOOM_FIELD.put("Shipping Length", "outLength");
		JOOM_FIELD.put("Shipping Width", "outWidth");
		JOOM_FIELD.put("Shipping Height", "outHeight");
		JOOM_FIELD.put("HS Code", "");		
		JOOM_FIELD.put("*Product Main Image URL", "mainImgUrl");
		JOOM_FIELD.put("Variant Main Image URL", "vMainImgUrl");
		JOOM_FIELD.put("Extra Image URL", "eImgUrl");
		JOOM_FIELD.put("Extra Image URL 1", "eImgUrl1");
		JOOM_FIELD.put("Extra Image URL 2", "eImgUrl2");
		JOOM_FIELD.put("Extra Image URL 3", "eImgUrl3");
		JOOM_FIELD.put("Extra Image URL 4", "eImgUrl4");
		JOOM_FIELD.put("Extra Image URL 5", "eImgUrl5");
		JOOM_FIELD.put("Extra Image URL 6", "eImgUrl6");
		JOOM_FIELD.put("Extra Image URL 7", "eImgUrl7");
		JOOM_FIELD.put("Extra Image URL 8", "eImgUrl8");
		JOOM_FIELD.put("Extra Image URL 9", "eImgUrl9");
		JOOM_FIELD.put("Extra Image URL 10", "eImgUrl10");
		JOOM_FIELD.put("Dangerous Kind", "");
	}
	
	/**
	 * 综合导出模版字段映射
	 */
	public static final Map<String, String> COMMEXP_FIELD = new LinkedHashMap<String, String>();

	static {
		COMMEXP_FIELD.put("Parent SKU", "parentSku");
		COMMEXP_FIELD.put("sku", "sku");
		COMMEXP_FIELD.put("虚拟商品编码", "");
		COMMEXP_FIELD.put("虚拟SKU", "");
		COMMEXP_FIELD.put("大类名称", "bigCataName");
		COMMEXP_FIELD.put("小类名称", "smallCataName");
		COMMEXP_FIELD.put("成本", "costPrice");
		COMMEXP_FIELD.put("重量（g）", "weight");
		COMMEXP_FIELD.put("款式", "style");
		COMMEXP_FIELD.put("标题（Wish）", "title");
		COMMEXP_FIELD.put("标题（Ebay）", "ebayTitle");
		COMMEXP_FIELD.put("标题（其他）", "otherTitle");
		COMMEXP_FIELD.put("标题可选词（逗号隔开）", "titleWords");
		COMMEXP_FIELD.put("产品描述", "descp");
		COMMEXP_FIELD.put("标签", "tags");
		COMMEXP_FIELD.put("颜色", "color");
		COMMEXP_FIELD.put("尺寸", "size");
		COMMEXP_FIELD.put("数量", "allNum");
		COMMEXP_FIELD.put("Wish平台售价", "");		
		COMMEXP_FIELD.put("速卖通平台售价", "");
		COMMEXP_FIELD.put("Joom平台售价", "");
		COMMEXP_FIELD.put("Ebay平台售价", "");
		COMMEXP_FIELD.put("Shopee平台售价", "");
		COMMEXP_FIELD.put("亚马逊平台售价", "");
		COMMEXP_FIELD.put("Listing主图", "mainImgUrl");
		COMMEXP_FIELD.put("子sku主图", "vMainImgUrl");
		COMMEXP_FIELD.put("附图1", "eImgUrl");
		COMMEXP_FIELD.put("附图2", "eImgUrl1");
		COMMEXP_FIELD.put("附图3", "eImgUrl2");
		COMMEXP_FIELD.put("附图4", "eImgUrl3");
		COMMEXP_FIELD.put("附图5", "eImgUrl4");
		COMMEXP_FIELD.put("附图6", "eImgUrl5");
		COMMEXP_FIELD.put("附图7", "eImgUrl6");
		COMMEXP_FIELD.put("附图8", "eImgUrl7");
		COMMEXP_FIELD.put("附图9", "eImgUrl8");
	}

	/**
	 * 平台模版Map
	 */
	public static final Map<String, Map<String, String>> PLATFORM_TEMP_FIELD = new LinkedHashMap<String, Map<String, String>>();
	static {
		PLATFORM_TEMP_FIELD.put("Common", COMMON_FIELD);
		PLATFORM_TEMP_FIELD.put(Constant.Ebay, null);
		PLATFORM_TEMP_FIELD.put(Constant.Amazon, null);
		PLATFORM_TEMP_FIELD.put(Constant.AliExpress, null);
		PLATFORM_TEMP_FIELD.put(Constant.Wish, WISH_FIELD);
		PLATFORM_TEMP_FIELD.put(Constant.Joom, JOOM_FIELD);
		PLATFORM_TEMP_FIELD.put(Constant.Shopee, null);
		PLATFORM_TEMP_FIELD.put(Constant.Commexp, COMMEXP_FIELD);
	}

	/**
	 * 平台模版Map
	 */
	public static final Map<String, String> PLATFORM_TEMP_FILE = new LinkedHashMap<String, String>();
	static {
		PLATFORM_TEMP_FILE.put(Constant.Ebay, "");
		PLATFORM_TEMP_FILE.put(Constant.Amazon, "");
		PLATFORM_TEMP_FILE.put(Constant.AliExpress, "");
		PLATFORM_TEMP_FILE.put(Constant.Wish, WISH);
		PLATFORM_TEMP_FILE.put(Constant.Joom, JOOM);
		PLATFORM_TEMP_FILE.put(Constant.Shopee, "");
		PLATFORM_TEMP_FILE.put(Constant.Commexp, COMMEXP);
	}
}
