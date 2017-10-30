package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.util.List;

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
	private int id;

	/**
	 * 物流产品名称
	 */
	@Column(length = 32)
	private String name;

	/**
	 * 物流产品排序
	 */
	@Column
	private int orderNum;

	/**
	 * 该产品物流方式列表
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prod")
	private List<Logis> logises;

	/**
	 * 物流策略选择该产品的平台列表
	 */
	@OneToMany(mappedBy = "prodStrategy")
	private List<Platform> platforms;

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

	public List<Platform> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(List<Platform> platforms) {
		this.platforms = platforms;
	}

}