package com.songxinjing.flyfish.form;

import java.io.Serializable;

public class GoodsQueryForm implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * sku
	 */
	private String sku;

	/**
	 * sku列表（分号隔开）
	 */
	private String skus;

	/**
	 * 父sku列表（分号隔开）
	 */
	private String parentSkus;

	/**
	 * 关联sku列表（分号隔开）
	 */
	private String relaSkus;

	/**
	 * 虚拟sku列表（分号隔开）
	 */
	private String virtSkus;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 大类名称
	 */
	private String bigCataName;

	/**
	 * 小类名称
	 */
	private String smallCataName;

	/**
	 * 当前状态（在售、停售）
	 */
	private String state;

	/**
	 * 业绩归属人1
	 */
	private String bussOwner1;

	/**
	 * 业绩归属人2
	 */
	private String bussOwner2;

	/**
	 * 采购员
	 */
	private String buyer;

	/**
	 * 是否带电（是、否）
	 */
	private String isElectric;

	/**
	 * 创建时间（开始）
	 */
	private String createTmBegin;

	/**
	 * 创建时间（结束）
	 */
	private String createTmEnd;

	/**
	 * 排除店铺ID
	 */
	private int storeId;
	
	/**
	 * 导入状态
	 */
	private int impState;
	
	/**
	 * 分类查询状态
	 */
	private int cataState;
	
	/**
	 * 分类ID
	 */
	private Long cataId;
	
	/**
	 * 分类全路径名称
	 */
	private String cataFullName;

	public String getSkus() {
		return skus;
	}

	public void setSkus(String skus) {
		this.skus = skus;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getIsElectric() {
		return isElectric;
	}

	public void setIsElectric(String isElectric) {
		this.isElectric = isElectric;
	}

	public String getCreateTmBegin() {
		return createTmBegin;
	}

	public void setCreateTmBegin(String createTmBegin) {
		this.createTmBegin = createTmBegin;
	}

	public String getCreateTmEnd() {
		return createTmEnd;
	}

	public void setCreateTmEnd(String createTmEnd) {
		this.createTmEnd = createTmEnd;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getParentSkus() {
		return parentSkus;
	}

	public void setParentSkus(String parentSkus) {
		this.parentSkus = parentSkus;
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

	public int getImpState() {
		return impState;
	}

	public void setImpState(int impState) {
		this.impState = impState;
	}

	public int getCataState() {
		return cataState;
	}

	public void setCataState(int cataState) {
		this.cataState = cataState;
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
