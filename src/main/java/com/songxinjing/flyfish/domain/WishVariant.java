package com.songxinjing.flyfish.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * WISH商品实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class WishVariant implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(length = 32)
	private String sku;
	
	@Column(length = 32)
	private String wishId;
	
	@Column(length = 2048)
	private String allImages;
	
	@Column(length = 8)
	private String price;
	
	@Column(length = 8)
	private String enabled;
	
	@Column(length = 8)
	private String shipping;
	
	@Column(length = 8)
	private String inventory;
	
	@Column(length = 8)
	private String size;
	
	@Column(length = 8)
	private String msrp;
	
	@Column(length = 8)
	private String shippingTime;
	
	@ManyToOne
	@JoinColumn
	private WishProduct product;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getWishId() {
		return wishId;
	}

	public void setWishId(String wishId) {
		this.wishId = wishId;
	}

	public String getAllImages() {
		return allImages;
	}

	public void setAllImages(String allImages) {
		this.allImages = allImages;
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

	public String getMsrp() {
		return msrp;
	}

	public void setMsrp(String msrp) {
		this.msrp = msrp;
	}

	public String getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}

	public WishProduct getProduct() {
		return product;
	}

	public void setProduct(WishProduct product) {
		this.product = product;
	}
	
}