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
public class JoomVariant implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(length = 32)
	private String sku;
	
	@Column(length = 32)
	private String joomId;
	
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
	
	@Column(length = 16)
	private String size;
	
	@Column(length = 16)
	private String color;
	
	@Column(length = 8)
	private String msrp;
	
	@Column(length = 8)
	private String shippingTime;
	
	@Column(length = 64)
	private String declaredName;
	
	@Column(length = 64)
	private String declaredLocalName;
	
	@Column(length = 4)
	private String pieces;
	
	@Column(length = 8)
	private String height;
	
	@Column(length = 8)
	private String weight;
    
	@Column(length = 8)
	private String width;
	
	@Column(length = 8)
	private String length;
	
	@Column(length = 8)
	private String originCountry;
	
	@Column(length = 16)
	private String hscode;
	
	@Column(length = 8)
	private String declaredValue;
    
	@Column(length = 8)
	private String hasLiquid;

	@Column(length = 8)
	private String hasBattery;
	
	@Column(length = 8)
	private String hasPowder;
	
	@Column(length = 8)
	private String hasMetal;
	
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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

	public String getDeclaredName() {
		return declaredName;
	}

	public void setDeclaredName(String declaredName) {
		this.declaredName = declaredName;
	}

	public String getDeclaredLocalName() {
		return declaredLocalName;
	}

	public void setDeclaredLocalName(String declaredLocalName) {
		this.declaredLocalName = declaredLocalName;
	}

	public String getPieces() {
		return pieces;
	}

	public void setPieces(String pieces) {
		this.pieces = pieces;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getHscode() {
		return hscode;
	}

	public void setHscode(String hscode) {
		this.hscode = hscode;
	}

	public String getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	public String getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(String declaredValue) {
		this.declaredValue = declaredValue;
	}

	public String getHasLiquid() {
		return hasLiquid;
	}

	public void setHasLiquid(String hasLiquid) {
		this.hasLiquid = hasLiquid;
	}

	public String getHasBattery() {
		return hasBattery;
	}

	public void setHasBattery(String hasBattery) {
		this.hasBattery = hasBattery;
	}

	public String getHasPowder() {
		return hasPowder;
	}

	public void setHasPowder(String hasPowder) {
		this.hasPowder = hasPowder;
	}

	public String getHasMetal() {
		return hasMetal;
	}

	public void setHasMetal(String hasMetal) {
		this.hasMetal = hasMetal;
	}

	public JoomProduct getProduct() {
		return product;
	}

	public void setProduct(JoomProduct product) {
		this.product = product;
	}
	
}