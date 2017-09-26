package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 商品信息（Wish）表实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class GoodsWish implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * SKU
	 */
	@Id
	@Column(length = 32)
	private String sku;

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

}