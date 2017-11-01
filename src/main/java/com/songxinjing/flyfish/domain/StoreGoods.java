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
public class StoreGoods implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 关系ID
	 */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 所属店铺
	 */
	@ManyToOne
	@JoinColumn
	private Store store;
	
	/**
	 * 所属商品
	 */
	@ManyToOne
	@JoinColumn
	private Goods goods;
	
	/**
	 * 批次号
	 */
	@Column
	private String batchNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

}