package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 平台信息表实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class Platform implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 平台ID
	 */
	@Id
	@Column
	private int id;

	/**
	 * 平台名称
	 */
	@Column(length = 32)
	private String name;

	/**
	 * 平台费率（%）
	 */
	@Column(precision = 5, scale = 2)
	private BigDecimal rate;

	/**
	 * 该平台权重列表
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "platform")
	private List<Weight> weights;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public List<Weight> getWeights() {
		return weights;
	}

	public void setWeights(List<Weight> weights) {
		this.weights = weights;
	}

}