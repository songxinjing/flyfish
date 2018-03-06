package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 国家信息表实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class Country implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 国家ID
	 */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 国家名称
	 */
	@Column(length = 32)
	private String name;

	/**
	 * 国家排序
	 */
	@Column
	private Integer orderNum;

	/**
	 * 该国家物流方式列表
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
	private Set<Logis> logises;

	/**
	 * 该国家权重列表
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
	private Set<Weight> weights;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Set<Logis> getLogises() {
		return logises;
	}

	public void setLogises(Set<Logis> logises) {
		this.logises = logises;
	}

	public Set<Weight> getWeights() {
		return weights;
	}

	public void setWeights(Set<Weight> weights) {
		this.weights = weights;
	}

}