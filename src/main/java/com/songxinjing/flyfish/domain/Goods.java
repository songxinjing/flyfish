package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
	 * 商品SKU
	 */
	@Id
	@Column(length = 32)
	private String sku;

	/**
	 * 父SKU
	 */
	@Column(length = 32)
	private String parentSku;

	/**
	 * 商品名称
	 */
	@Column(length = 128)
	private String name;

	/**
	 * 商品重量（G）
	 */
	@Column
	private int weight;

	/**
	 * 成本价（RMB）
	 */
	@Column(precision = 12, scale = 2)
	private BigDecimal costPrice;

	/**
	 * 中文申报名
	 */
	@Column(length = 64)
	private String reportNameCn;

	/**
	 * 英文申报名
	 */
	@Column(length = 64)
	private String reportNameEn;

	/**
	 * 业绩归属人
	 */
	@Column(length = 32)
	private String bussOwner;

	/**
	 * 采购员
	 */
	@Column(length = 32)
	private String buyer;

	/**
	 * 网页URL
	 */
	@Column(length = 128)
	private String url;

	/**
	 * 最后修改人ID
	 */
	@Column(length = 16)
	private String modifyUserId;

	/**
	 * 最后修改人
	 */
	@Column(length = 16)
	private String modifyUserName;

	/**
	 * 最后修改时间
	 */
	@Column
	private Timestamp modifyTime;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
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

	public String getBussOwner() {
		return bussOwner;
	}

	public void setBussOwner(String bussOwner) {
		this.bussOwner = bussOwner;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

}