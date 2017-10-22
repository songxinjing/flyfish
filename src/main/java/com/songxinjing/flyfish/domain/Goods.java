package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 商品信息表实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class Goods implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * SKU
	 */
	@Id
	@Column(length = 32)
	private String sku;

	/**
	 * 商品编码
	 */
	@Column(length = 32)
	private String parentSku;

	/**
	 * 多款式（是、否）
	 */
	@Column(length = 8)
	private String isMoreSytle;

	/**
	 * 是否有样品（是、否）
	 */
	@Column(length = 8)
	private String hasSample;

	/**
	 * 样品数量
	 */
	@Column(length = 8)
	private String sampleNum;

	/**
	 * 大类名称
	 */
	@Column(length = 32)
	private String bigCataName;

	/**
	 * 小类名称
	 */
	@Column(length = 32)
	private String smallCataName;

	/**
	 * 商品名称
	 */
	@Column(length = 64)
	private String name;

	/**
	 * 当前状态（在售、停售）
	 */
	@Column(length = 8)
	private String state;

	/**
	 * 重量（G）
	 */
	@Column(length = 8)
	private String weight;

	/**
	 * 成本单价（元）
	 */
	@Column(length = 8)
	private String costPrice;

	/**
	 * 批发价格（美元）
	 */
	@Column(length = 8)
	private String batchPrice;

	/**
	 * 中文申报名
	 */
	@Column(length = 32)
	private String reportNameCn;

	/**
	 * 英文申报名
	 */
	@Column(length = 32)
	private String reportNameEn;

	/**
	 * 申报价值（美元）
	 */
	@Column(length = 8)
	private String reportPrice;

	/**
	 * 原产国代码
	 */
	@Column(length = 8)
	private String prodCountryCd;

	/**
	 * 原产国
	 */
	@Column(length = 16)
	private String prodCountryName;

	/**
	 * 业绩归属人1
	 */
	@Column(length = 16)
	private String bussOwner1;

	/**
	 * 业绩归属人2
	 */
	@Column(length = 16)
	private String bussOwner2;

	/**
	 * 开发日期
	 */
	@Column(length = 16)
	private String bussDate;

	/**
	 * 采购员
	 */
	@Column(length = 16)
	private String buyer;

	/**
	 * 采购到货天数
	 */
	@Column(length = 4)
	private String buyDayNum;

	/**
	 * 网页URL
	 */
	@Column(length = 128)
	private String url;

	/**
	 * 网页URL2
	 */
	@Column(length = 128)
	private String url2;

	/**
	 * 网页URL3
	 */
	@Column(length = 128)
	private String url3;

	/**
	 * 是否带电（False/True）
	 */
	@Column(length = 8)
	private String isElectric;

	/**
	 * 商品SKU状态（在售、停售）
	 */
	@Column(length = 8)
	private String skuState;

	/**
	 * 季节
	 */
	@Column(length = 16)
	private String season;

	/**
	 * 是否粉末（是、否）
	 */
	@Column(length = 8)
	private String isPowder;

	/**
	 * 是否液体（是、否）
	 */
	@Column(length = 8)
	private String isLiquid;

	/**
	 * 责任归属人1
	 */
	@Column(length = 16)
	private String respOwner1;

	/**
	 * 责任归属人2
	 */
	@Column(length = 16)
	private String respOwner2;

	/**
	 * 是否组合品
	 */
	@Column(length = 8)
	private String isJoin;

	/**
	 * 款式
	 */
	@Column(length = 16)
	private String style;

	/**
	 * 品牌
	 */
	@Column(length = 16)
	private String make;

	/**
	 * 所有库存数量
	 */
	@Column(length = 8)
	private String allNum;

	/**
	 * 供应商名称
	 */
	@Column(length = 32)
	private String supplyName;

	/**
	 * 普源编号
	 */
	@Column(length = 8)
	private String pyNum;

	/**
	 * 创建时间
	 */
	@Column(length = 32)
	private String createTm;
	
	/**
	 * 停售时间
	 */
	@Column(length = 32)
	private String stopTm;
	
	/**
	 * 停售原因
	 */
	@Column(length = 64)
	private String stopReson;

	/**
	 * 最低采购单价
	 */
	@Column(length = 16)
	private String minBuyPrice;
	
	/**
	 * 最小包装数
	 */
	@Column(length = 8)
	private String minNum;
	
	/**
	 * 外箱长
	 */
	@Column(length = 8)
	private String outLength;
	
	/**
	 * 外箱宽
	 */
	@Column(length = 8)
	private String outWidth;
	
	/**
	 * 外箱高
	 */
	@Column(length = 8)
	private String outHeight;

	/**
	 * 最后修改人ID
	 */
	@Column(length = 16)
	private String modifyId;

	/**
	 * 最后修改人
	 */
	@Column(length = 16)
	private String modifyer;

	/**
	 * 最后修改时间
	 */
	@Column
	private Timestamp modifyTm;

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

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyer() {
		return modifyer;
	}

	public void setModifyer(String modifyer) {
		this.modifyer = modifyer;
	}

	public Timestamp getModifyTm() {
		return modifyTm;
	}

	public void setModifyTm(Timestamp modifyTm) {
		this.modifyTm = modifyTm;
	}

	public String getIsJoin() {
		return isJoin;
	}

	public void setIsJoin(String isJoin) {
		this.isJoin = isJoin;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getAllNum() {
		return allNum;
	}

	public void setAllNum(String allNum) {
		this.allNum = allNum;
	}

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	public String getPyNum() {
		return pyNum;
	}

	public void setPyNum(String pyNum) {
		this.pyNum = pyNum;
	}

	public String getCreateTm() {
		return createTm;
	}

	public void setCreateTm(String createTm) {
		this.createTm = createTm;
	}

	public String getMinBuyPrice() {
		return minBuyPrice;
	}

	public void setMinBuyPrice(String minBuyPrice) {
		this.minBuyPrice = minBuyPrice;
	}

	public String getStopTm() {
		return stopTm;
	}

	public void setStopTm(String stopTm) {
		this.stopTm = stopTm;
	}

	public String getStopReson() {
		return stopReson;
	}

	public void setStopReson(String stopReson) {
		this.stopReson = stopReson;
	}

	public String getMinNum() {
		return minNum;
	}

	public void setMinNum(String minNum) {
		this.minNum = minNum;
	}

	public String getOutLength() {
		return outLength;
	}

	public void setOutLength(String outLength) {
		this.outLength = outLength;
	}

	public String getOutWidth() {
		return outWidth;
	}

	public void setOutWidth(String outWidth) {
		this.outWidth = outWidth;
	}

	public String getOutHeight() {
		return outHeight;
	}

	public void setOutHeight(String outHeight) {
		this.outHeight = outHeight;
	}
	
}