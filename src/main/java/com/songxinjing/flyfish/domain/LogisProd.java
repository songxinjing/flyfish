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
 * 物流产品信息表实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class LogisProd implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 物流产品ID
	 */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 物流产品名称
	 */
	@Column(length = 32)
	private String name;

	/**
	 * 物流产品排序
	 */
	@Column
	private Integer orderNum;

	/**
	 * 该产品物流方式列表
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prod")
	private Set<Logis> logises;

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

}