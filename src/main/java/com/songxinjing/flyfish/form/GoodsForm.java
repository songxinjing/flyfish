package com.songxinjing.flyfish.form;

import java.io.Serializable;
import java.math.BigDecimal;

import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.domain.GoodsImg;

public class GoodsForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Goods goods;

	private GoodsImg goodsImg;

	private String listingSku;

	private String listingParentSku;

	private String platformTitle;

	private boolean titleRed;

	private BigDecimal wishPrice;

	private BigDecimal aliExpressPrice;

	private BigDecimal dhPrice;

	private BigDecimal joomPrice;

	private BigDecimal amazonPrice;

	private BigDecimal lazadaPrice;

	private BigDecimal ebayPrice;

	private BigDecimal cdPrice;

	private BigDecimal shopeePrice;

	private BigDecimal platformPrice;

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public GoodsImg getGoodsImg() {
		return goodsImg;
	}

	public void setGoodsImg(GoodsImg goodsImg) {
		this.goodsImg = goodsImg;
	}

	public String getListingSku() {
		return listingSku;
	}

	public void setListingSku(String listingSku) {
		this.listingSku = listingSku;
	}

	public BigDecimal getWishPrice() {
		return wishPrice;
	}

	public void setWishPrice(BigDecimal wishPrice) {
		this.wishPrice = wishPrice;
	}

	public BigDecimal getAliExpressPrice() {
		return aliExpressPrice;
	}

	public void setAliExpressPrice(BigDecimal aliExpressPrice) {
		this.aliExpressPrice = aliExpressPrice;
	}

	public BigDecimal getDhPrice() {
		return dhPrice;
	}

	public void setDhPrice(BigDecimal dhPrice) {
		this.dhPrice = dhPrice;
	}

	public BigDecimal getJoomPrice() {
		return joomPrice;
	}

	public void setJoomPrice(BigDecimal joomPrice) {
		this.joomPrice = joomPrice;
	}

	public BigDecimal getAmazonPrice() {
		return amazonPrice;
	}

	public void setAmazonPrice(BigDecimal amazonPrice) {
		this.amazonPrice = amazonPrice;
	}

	public BigDecimal getLazadaPrice() {
		return lazadaPrice;
	}

	public void setLazadaPrice(BigDecimal lazadaPrice) {
		this.lazadaPrice = lazadaPrice;
	}

	public BigDecimal getEbayPrice() {
		return ebayPrice;
	}

	public void setEbayPrice(BigDecimal ebayPrice) {
		this.ebayPrice = ebayPrice;
	}

	public BigDecimal getCdPrice() {
		return cdPrice;
	}

	public void setCdPrice(BigDecimal cdPrice) {
		this.cdPrice = cdPrice;
	}

	public BigDecimal getShopeePrice() {
		return shopeePrice;
	}

	public void setShopeePrice(BigDecimal shopeePrice) {
		this.shopeePrice = shopeePrice;
	}

	public String getListingParentSku() {
		return listingParentSku;
	}

	public void setListingParentSku(String listingParentSku) {
		this.listingParentSku = listingParentSku;
	}

	public BigDecimal getPlatformPrice() {
		return platformPrice;
	}

	public void setPlatformPrice(BigDecimal platformPrice) {
		this.platformPrice = platformPrice;
	}

	public String getPlatformTitle() {
		return platformTitle;
	}

	public void setPlatformTitle(String platformTitle) {
		this.platformTitle = platformTitle;
	}

	public boolean isTitleRed() {
		return titleRed;
	}

	public void setTitleRed(boolean titleRed) {
		this.titleRed = titleRed;
	}

}
