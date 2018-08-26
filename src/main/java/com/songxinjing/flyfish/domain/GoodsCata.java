package com.songxinjing.flyfish.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 商品分类信息表实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class GoodsCata implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 分类数值
	 */
	@Id
	@Column
	private Long id;
	
	/**
	 * 分类名称
	 */
	@Column(length = 128)
	private String name;

	/**
	 * 全路径名称
	 */
	@Column(length = 1024)
	private String fullName;

	/**
	 * 是否末级
	 */
	@Column
	private Integer isLeaf;
	
	/**
	 * 上级分类数值
	 */
	@Column
	private Long parentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}