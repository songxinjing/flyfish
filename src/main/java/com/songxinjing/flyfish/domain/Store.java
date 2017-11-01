package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.util.Set;

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
	private Integer id;

	/**
	 * 店铺名称
	 */
	@Column(length = 32)
	private String name;

	/**
	 * 店铺排序
	 */
	@Column
	private Integer orderNum;

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

	/**
	 * 店铺ListingSKU变换位移
	 */
	@Column
	private Integer move;

	/**
	 * 该店铺关联商品
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
	private Set<StoreGoods> storeGoodses;

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

	public Set<StoreGoods> getStoreGoodses() {
		return storeGoodses;
	}

	public void setStoreGoodses(Set<StoreGoods> storeGoodses) {
		this.storeGoodses = storeGoodses;
	}

	public Integer getMove() {
		return move;
	}

	public void setMove(Integer move) {
		this.move = move;
	}

}