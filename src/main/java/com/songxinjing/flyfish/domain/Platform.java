package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * 平台名称
	 */
	@Column(length = 32)
	private String name;
	
	/**
	 * 平台排序
	 */
	@Column
	private int orderNum;

	/**
	 * 平台费率（%）
	 */
	@Column(precision = 5, scale = 2)
	private BigDecimal rate;

	/**
	 * 平台利润率（%）
	 */
	@Column(precision = 5, scale = 2)
	private BigDecimal profitRate;
	
	/**
	 * 平台折扣率（%）
	 */
	@Column(precision = 5, scale = 2)
	private BigDecimal cutRate;
	
	/**
	 * 物流策略，重量临界点
	 */
	@Column(precision = 6, scale = 2)
	private BigDecimal weightStrategy;
	
	/**
	 * 物流策略，选择物流产品
	 */
	@ManyToOne
	@JoinColumn
	private LogisProd prodStrategy;
	
	
	/**
	 * 该平台权重列表
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "platform")
	private List<Weight> weights;
	
	/**
	 * 该平台店铺列表
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "platform")
	private List<Store> stores;


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

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}

	public BigDecimal getCutRate() {
		return cutRate;
	}

	public void setCutRate(BigDecimal cutRate) {
		this.cutRate = cutRate;
	}

	public List<Weight> getWeights() {
		return weights;
	}

	public void setWeights(List<Weight> weights) {
		this.weights = weights;
	}

	public List<Store> getStores() {
		return stores;
	}

	public void setStores(List<Store> stores) {
		this.stores = stores;
	}

	public BigDecimal getWeightStrategy() {
		return weightStrategy;
	}

	public void setWeightStrategy(BigDecimal weightStrategy) {
		this.weightStrategy = weightStrategy;
	}

	public LogisProd getProdStrategy() {
		return prodStrategy;
	}

	public void setProdStrategy(LogisProd prodStrategy) {
		this.prodStrategy = prodStrategy;
	}
	
}