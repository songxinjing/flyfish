package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 平台发货国家权重信息表实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class Weight implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 权重ID
	 */
	@Id
	@Column
	private int id;

	/**
	 * 权重值（%）
	 */
	@Column(precision = 5, scale = 2)
	private BigDecimal rate;

	/**
	 * 该权重所属发货国家
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Country country;

	/**
	 * 该权重所属平台
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Platform platform;
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
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