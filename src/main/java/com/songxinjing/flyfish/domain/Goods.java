package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	 * 关联SKU
	 */
	@Column(length = 255)
	private String relaSkus;
	
	/**
	 * 虚拟SKU
	 */
	@Column(length = 255)
	private String virtSkus;

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
	@Column(length = 128)
	private String bigCataName;

	/**
	 * 小类名称
	 */
	@Column(length = 128)
	private String smallCataName;

	/**
	 * 商品名称
	 */
	@Column(length = 128)
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
	@Column(length = 128)
	private String reportNameCn;

	/**
	 * 英文申报名
	 */
	@Column(length = 128)
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
	@Column(length = 32)
	private String bussOwner1;

	/**
	 * 业绩归属人2
	 */
	@Column(length = 32)
	private String bussOwner2;

	/**
	 * 开发日期
	 */
	@Column(length = 32)
	private String bussDate;

	/**
	 * 采购员
	 */
	@Column(length = 32)
	private String buyer;

	/**
	 * 采购到货天数
	 */
	@Column(length = 4)
	private String buyDayNum;

	/**
	 * 网页URL
	 */
	@Column(length = 500)
	private String url;

	/**
	 * 网页URL2
	 */
	@Column(length = 500)
	private String url2;

	/**
	 * 网页URL3
	 */
	@Column(length = 500)
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
	@Column(length = 32)
	private String respOwner1;

	/**
	 * 责任归属人2
	 */
	@Column(length = 32)
	private String respOwner2;

	/**
	 * 是否组合品
	 */
	@Column(length = 8)
	private String isJoin;

	/**
	 * 款式
	 */
	@Column(length = 128)
	private String style;

	/**
	 * 品牌
	 */
	@Column(length = 128)
	private String make;

	/**
	 * 所有库存数量
	 */
	@Column(length = 8)
	private String allNum;

	/**
	 * 供应商名称
	 */
	@Column(length = 128)
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
	@Column(length = 128)
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
	
	/**
	 * 该店铺关联商品
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "goods")
	private Set<StoreGoods> storeGoodses;

	/**
	 * WISH title
	 */
	@Column(length = 500)
	private String title;

	/**
	 * Ebay title(75)
	 */
	@Column(length = 500)
	private String ebayTitle;

	/**
	 * other title(90)
	 */
	@Column(length = 500)
	private String otherTitle;

	/**
	 * descp
	 */
	@Column(length = 102400)
	private String descp;

	/**
	 * tags
	 */
	@Column(length = 102400)
	private String tags;

	/**
	 * titleWords
	 */
	@Column(length = 500)
	private String titleWords;

	/**
	 * color
	 */
	@Column(length = 32)
	private String color;

	/**
	 * size
	 */
	@Column(length = 32)
	private String size;

	/**
	 * Quantity
	 */
	@Column(length = 8)
	private String quantity;

	/**
	 * shipping days
	 */
	@Column(length = 8)
	private String shipDays;

	/**
	 * main image URL
	 */
	@Column(length = 500)
	private String mainImgUrl;

	/**
	 * variant main image URL
	 */
	@Column(length = 500)
	private String vMainImgUrl;

	/**
	 * extra image URL
	 */
	@Column(length = 500)
	private String eImgUrl;

	/**
	 * extra image URL1
	 */
	@Column(length = 500)
	private String eImgUrl1;

	/**
	 * extra image URL2
	 */
	@Column(length = 500)
	private String eImgUrl2;

	/**
	 * extra image URL3
	 */
	@Column(length = 500)
	private String eImgUrl3;

	/**
	 * extra image URL4
	 */
	@Column(length = 500)
	private String eImgUrl4;

	/**
	 * extra image URL5
	 */
	@Column(length = 500)
	private String eImgUrl5;

	/**
	 * extra image URL6
	 */
	@Column(length = 500)
	private String eImgUrl6;

	/**
	 * extra image URL7
	 */
	@Column(length = 500)
	private String eImgUrl7;

	/**
	 * extra image URL8
	 */
	@Column(length = 500)
	private String eImgUrl8;

	/**
	 * extra image URL9
	 */
	@Column(length = 500)
	private String eImgUrl9;

	/**
	 * extra image URL10
	 */
	@Column(length = 500)
	private String eImgUrl10;

	/**
	 * 是否上传图片
	 */
	@Column
	private Integer isUpload;
	
	/**
	 * 分类ID
	 */
	@Column
	private Long cataId;
	
	/**
	 * 分类全路径名称
	 */
	@Column(length = 1024)
	private String cataFullName;

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

	public Set<StoreGoods> getStoreGoodses() {
		return storeGoodses;
	}

	public void setStoreGoodses(Set<StoreGoods> storeGoodses) {
		this.storeGoodses = storeGoodses;
	}

	public String getRelaSkus() {
		return relaSkus;
	}

	public void setRelaSkus(String relaSkus) {
		this.relaSkus = relaSkus;
	}

	public String getVirtSkus() {
		return virtSkus;
	}

	public void setVirtSkus(String virtSkus) {
		this.virtSkus = virtSkus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEbayTitle() {
		return ebayTitle;
	}

	public void setEbayTitle(String ebayTitle) {
		this.ebayTitle = ebayTitle;
	}

	public String getOtherTitle() {
		return otherTitle;
	}

	public void setOtherTitle(String otherTitle) {
		this.otherTitle = otherTitle;
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

	public String getTitleWords() {
		return titleWords;
	}

	public void setTitleWords(String titleWords) {
		this.titleWords = titleWords;
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
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

	public Integer getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(Integer isUpload) {
		this.isUpload = isUpload;
	}

	public Long getCataId() {
		return cataId;
	}

	public void setCataId(Long cataId) {
		this.cataId = cataId;
	}

	public String getCataFullName() {
		return cataFullName;
	}

	public void setCataFullName(String cataFullName) {
		this.cataFullName = cataFullName;
	}
	
}