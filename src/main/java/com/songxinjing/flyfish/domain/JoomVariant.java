package com.songxinjing.flyfish.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Joom商品实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class JoomVariant implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(length = 32)
	private String sku;
	
	@Column(length = 64)
	private String joomId;
	
	@Column(length = 8)
	private String price;
	
	@Column(length = 8)
	private String enabled;
	
	@Column(length = 8)
	private String shipping;
	
	@Column(length = 8)
	private String inventory;
	
	@Column(length = 32)
	private String size;
	
	@Column(length = 32)
	private String color;
	
	@Column(length = 8)
	private String shippingTime;
	
	@ManyToOne
	@JoinColumn
	private JoomProduct product;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getJoomId() {
		return joomId;
	}

	public void setJoomId(String joomId) {
		this.joomId = joomId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public String getInventory() {
		return inventory;
	}

	public void setInventory(String inventory) {
		this.inventory = inventory;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}

	public JoomProduct getProduct() {
		return product;
	}

	public void setProduct(JoomProduct product) {
		this.product = product;
	}
	
}