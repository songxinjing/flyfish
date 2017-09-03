package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
	private int id;

	/**
	 * 国家名称
	 */
	@Column(length = 32)
	private String name;

	/**
	 * 国家排序
	 */
	@Column
	private int orderNum;

	/**
	 * 该国家物流方式列表
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
	private List<Logis> logises;

	/**
	 * 该国家权重列表
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
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

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public List<Logis> getLogises() {
		return logises;
	}

	public void setLogises(List<Logis> logises) {
		this.logises = logises;
	}

	public List<Weight> getWeights() {
		return weights;
	}

	public void setWeights(List<Weight> weights) {
		this.weights = weights;
	}

}