package com.songxinjing.flyfish.form;

import java.io.Serializable;

public class GoodsEditForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * SKU
	 */
	private String sku;

	/**
	 * 商品编码
	 */
	private String parentSku;
	
	/**
	 * 多款式（是、否）
	 */
	private String isMoreSytle;
	
	/**
	 * 是否有样品（是、否）
	 */
	private String hasSample;
	
	/**
	 * 样品数量
	 */
	private String sampleNum;
	
	/**
	 * 大类名称
	 */
	private String bigCataName;
	
	/**
	 * 小类名称
	 */
	private String smallCataName;

	/**
	 * 商品名称
	 */
	private String name;
	
	/**
	 * 当前状态（在售、停售）
	 */
	private String state;

	/**
	 * 重量（G）
	 */
	private String weight;

	/**
	 * 成本单价（元）
	 */
	private String costPrice;
	
	/**
	 * 批发价格（美元）
	 */
	private String batchPrice;

	/**
	 * 中文申报名
	 */
	private String reportNameCn;

	/**
	 * 英文申报名
	 */
	private String reportNameEn;
	
	/**
	 * 申报价值（美元）
	 */
	private String reportPrice;
	
	/**
	 * 原产国代码
	 */
	private String prodCountryCd;
	
	/**
	 * 原产国
	 */
	private String prodCountryName;

	/**
	 * 业绩归属人1
	 */
	private String bussOwner1;
	
	/**
	 * 业绩归属人2
	 */
	private String bussOwner2;
	
	/**
	 * 开发日期
	 */
	private String bussDate;

	/**
	 * 采购员
	 */
	private String buyer;
	
	/**
	 * 采购到货天数
	 */
	private String buyDayNum;

	/**
	 * 网页URL
	 */
	private String url;
	
	/**
	 * 网页URL2
	 */
	private String url2;
	
	/**
	 * 网页URL3
	 */
	private String url3;
	
	/**
	 * 是否带电（是、否）
	 */
	private String isElectric;
	
	/**
	 * 商品SKU状态（在售、停售）
	 */
	private String skuState;
	
	/**
	 * 季节
	 */
	private String season;
	
	/**
	 * 是否粉末（是、否）
	 */
	private String isPowder;
	
	/**
	 * 是否液体（是、否）
	 */
	private String isLiquid;
	
	/**
	 * 责任归属人1
	 */
	private String respOwner1;
	
	/**
	 * 责任归属人2
	 */
	private String respOwner2;
	
	/**
	 * title
	 */
	private String title;
	
	/**
	 * descp
	 */
	private String descp;
	
	/**
	 * tags
	 */
	private String tags;
	
	/**
	 * msrp
	 */
	private String msrp;

	
	/**
	 * color
	 */
	private String color;

	
	/**
	 * size
	 */
	private String size;

	/**
	 * price
	 */
	private String price;

	/**
	 * shipPrice
	 */
	private String shipPrice;
	
	/**
	 * Quantity
	 */
	private String quantity;	

	/**
	 * inventory
	 */
	private String inventory;

	/**
	 * shipping days 
	 */
	private String shipDays;

	/**
	 * main image URL
	 */
	private String mainImgUrl;

	/**
	 * variant main image URL
	 */
	private String vMainImgUrl;
	
	/**
	 * extra image URL
	 */
	private String eImgUrl;
	
	/**
	 * extra image URL1
	 */
	private String eImgUrl1;
	
	/**
	 * extra image URL2
	 */
	private String eImgUrl2;
	
	/**
	 * extra image URL3
	 */
	private String eImgUrl3;
	
	/**
	 * extra image URL4
	 */
	private String eImgUrl4;
	
	/**
	 * extra image URL5
	 */
	private String eImgUrl5;
	
	/**
	 * extra image URL6
	 */
	private String eImgUrl6;
	
	/**
	 * extra image URL7
	 */
	private String eImgUrl7;
	
	/**
	 * extra image URL8
	 */
	private String eImgUrl8;
	
	/**
	 * extra image URL9
	 */
	private String eImgUrl9;
	
	/**
	 * extra image URL10
	 */
	private String eImgUrl10;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getParentSku() {
		return parentSku;
	}

	public void setParentSku(String parentSku) {
		this.parentSku = parentSku;
	}

	public String getIsMoreSytle() {
		return isMoreSytle;
	}

	public void setIsMoreSytle(String isMoreSytle) {
		this.isMoreSytle = isMoreSytle;
	}

	public String getHasSample() {
		return hasSample;
	}

	public void setHasSample(String hasSample) {
		this.hasSample = hasSample;
	}

	public String getSampleNum() {
		return sampleNum;
	}

	public void setSampleNum(String sampleNum) {
		this.sampleNum = sampleNum;
	}

	public String getBigCataName() {
		return bigCataName;
	}

	public void setBigCataName(String bigCataName) {
		this.bigCataName = bigCataName;
	}

	public String getSmallCataName() {
		return smallCataName;
	}

	public void setSmallCataName(String smallCataName) {
		this.smallCataName = smallCataName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}

	public String getBatchPrice() {
		return batchPrice;
	}

	public void setBatchPrice(String batchPrice) {
		this.batchPrice = batchPrice;
	}

	public String getReportNameCn() {
		return reportNameCn;
	}

	public void setReportNameCn(String reportNameCn) {
		this.reportNameCn = reportNameCn;
	}

	public String getReportNameEn() {
		return reportNameEn;
	}

	public void setReportNameEn(String reportNameEn) {
		this.reportNameEn = reportNameEn;
	}

	public String getReportPrice() {
		return reportPrice;
	}

	public void setReportPrice(String reportPrice) {
		this.reportPrice = reportPrice;
	}

	public String getProdCountryCd() {
		return prodCountryCd;
	}

	public void setProdCountryCd(String prodCountryCd) {
		this.prodCountryCd = prodCountryCd;
	}

	public String getProdCountryName() {
		return prodCountryName;
	}

	public void setProdCountryName(String prodCountryName) {
		this.prodCountryName = prodCountryName;
	}

	public String getBussOwner1() {
		return bussOwner1;
	}

	public void setBussOwner1(String bussOwner1) {
		this.bussOwner1 = bussOwner1;
	}

	public String getBussOwner2() {
		return bussOwner2;
	}

	public void setBussOwner2(String bussOwner2) {
		this.bussOwner2 = bussOwner2;
	}

	public String getBussDate() {
		return bussDate;
	}

	public void setBussDate(String bussDate) {
		this.bussDate = bussDate;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getBuyDayNum() {
		return buyDayNum;
	}

	public void setBuyDayNum(String buyDayNum) {
		this.buyDayNum = buyDayNum;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl2() {
		return url2;
	}

	public void setUrl2(String url2) {
		this.url2 = url2;
	}

	public String getUrl3() {
		return url3;
	}

	public void setUrl3(String url3) {
		this.url3 = url3;
	}

	public String getIsElectric() {
		return isElectric;
	}

	public void setIsElectric(String isElectric) {
		this.isElectric = isElectric;
	}

	public String getSkuState() {
		return skuState;
	}

	public void setSkuState(String skuState) {
		this.skuState = skuState;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getIsPowder() {
		return isPowder;
	}

	public void setIsPowder(String isPowder) {
		this.isPowder = isPowder;
	}

	public String getIsLiquid() {
		return isLiquid;
	}

	public void setIsLiquid(String isLiquid) {
		this.isLiquid = isLiquid;
	}

	public String getRespOwner1() {
		return respOwner1;
	}

	public void setRespOwner1(String respOwner1) {
		this.respOwner1 = respOwner1;
	}

	public String getRespOwner2() {
		return respOwner2;
	}

	public void setRespOwner2(String respOwner2) {
		this.respOwner2 = respOwner2;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getMsrp() {
		return msrp;
	}

	public void setMsrp(String msrp) {
		this.msrp = msrp;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getShipPrice() {
		return shipPrice;
	}

	public void setShipPrice(String shipPrice) {
		this.shipPrice = shipPrice;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getInventory() {
		return inventory;
	}

	public void setInventory(String inventory) {
		this.inventory = inventory;
	}

	public String getShipDays() {
		return shipDays;
	}

	public void setShipDays(String shipDays) {
		this.shipDays = shipDays;
	}

	public String getMainImgUrl() {
		return mainImgUrl;
	}

	public void setMainImgUrl(String mainImgUrl) {
		this.mainImgUrl = mainImgUrl;
	}

	public String getvMainImgUrl() {
		return vMainImgUrl;
	}

	public void setvMainImgUrl(String vMainImgUrl) {
		this.vMainImgUrl = vMainImgUrl;
	}

	public String geteImgUrl() {
		return eImgUrl;
	}

	public void seteImgUrl(String eImgUrl) {
		this.eImgUrl = eImgUrl;
	}

	public String geteImgUrl1() {
		return eImgUrl1;
	}

	public void seteImgUrl1(String eImgUrl1) {
		this.eImgUrl1 = eImgUrl1;
	}

	public String geteImgUrl2() {
		return eImgUrl2;
	}

	public void seteImgUrl2(String eImgUrl2) {
		this.eImgUrl2 = eImgUrl2;
	}

	public String geteImgUrl3() {
		return eImgUrl3;
	}

	public void seteImgUrl3(String eImgUrl3) {
		this.eImgUrl3 = eImgUrl3;
	}

	public String geteImgUrl4() {
		return eImgUrl4;
	}

	public void seteImgUrl4(String eImgUrl4) {
		this.eImgUrl4 = eImgUrl4;
	}

	public String geteImgUrl5() {
		return eImgUrl5;
	}

	public void seteImgUrl5(String eImgUrl5) {
		this.eImgUrl5 = eImgUrl5;
	}

	public String geteImgUrl6() {
		return eImgUrl6;
	}

	public void seteImgUrl6(String eImgUrl6) {
		this.eImgUrl6 = eImgUrl6;
	}

	public String geteImgUrl7() {
		return eImgUrl7;
	}

	public void seteImgUrl7(String eImgUrl7) {
		this.eImgUrl7 = eImgUrl7;
	}

	public String geteImgUrl8() {
		return eImgUrl8;
	}

	public void seteImgUrl8(String eImgUrl8) {
		this.eImgUrl8 = eImgUrl8;
	}

	public String geteImgUrl9() {
		return eImgUrl9;
	}

	public void seteImgUrl9(String eImgUrl9) {
		this.eImgUrl9 = eImgUrl9;
	}

	public String geteImgUrl10() {
		return eImgUrl10;
	}

	public void seteImgUrl10(String eImgUrl10) {
		this.eImgUrl10 = eImgUrl10;
	}

}
