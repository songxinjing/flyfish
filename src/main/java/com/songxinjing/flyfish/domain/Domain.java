package com.songxinjing.flyfish.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 域名信息表实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class Domain implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 域名名称
	 */
	@Id
	@Column(length = 64)
	private String name;

	/**
	 * 域名排序
	 */
	@Column
	private int orderNum;

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

}