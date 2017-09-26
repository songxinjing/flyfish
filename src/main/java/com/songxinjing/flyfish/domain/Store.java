package com.songxinjing.flyfish.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 店铺信息表实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class Store implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 店铺ID
	 */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * 店铺名称
	 */
	@Column(length = 32)
	private String name;

	/**
	 * 店铺排序
	 */
	@Column
	private int orderNum;

	/**
	 * 该店铺所属平台
	 */
	@ManyToOne
	@JoinColumn
	private Platform platform;

	/**
	 * 店铺关联域名
	 */
	@Column(length = 64)
	private String domainName;

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

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

}