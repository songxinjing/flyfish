package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 汇率信息表实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class Exchange implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 汇率代码
	 */
	@Id
	@Column(length = 16)
	private String code;
	
	/**
	 * 汇率名称
	 */
	@Column(length = 16)
	private String name;
	
	/**
	 * 汇率（%）
	 */
	@Column(precision = 7, scale = 4)
	private BigDecimal rate;

	/**
	 * 汇率排序
	 */
	@Column
	private Integer orderNum;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

}