package com.songxinjing.flyfish.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 商品信息图片表实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class GoodsImg implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * SKU
	 */
	@Id
	@Column(length = 32)
	private String sku;

	/**
	 * main image URL
	 */
	@Column(length = 255)
	private String mainImgUrl;

	/**
	 * variant main image URL
	 */
	@Column(length = 255)
	private String vMainImgUrl;

	/**
	 * extra image URL
	 */
	@Column(length = 255)
	private String eImgUrl;

	/**
	 * extra image URL1
	 */
	@Column(length = 255)
	private String eImgUrl1;

	/**
	 * extra image URL2
	 */
	@Column(length = 255)
	private String eImgUrl2;

	/**
	 * extra image URL3
	 */
	@Column(length = 255)
	private String eImgUrl3;

	/**
	 * extra image URL4
	 */
	@Column(length = 255)
	private String eImgUrl4;

	/**
	 * extra image URL5
	 */
	@Column(length = 255)
	private String eImgUrl5;

	/**
	 * extra image URL6
	 */
	@Column(length = 255)
	private String eImgUrl6;

	/**
	 * extra image URL7
	 */
	@Column(length = 255)
	private String eImgUrl7;

	/**
	 * extra image URL8
	 */
	@Column(length = 255)
	private String eImgUrl8;

	/**
	 * extra image URL9
	 */
	@Column(length = 255)
	private String eImgUrl9;

	/**
	 * extra image URL10
	 */
	@Column(length = 255)
	private String eImgUrl10;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getMainImgUrl() {
		return mainImgUrl;
	}

	public void setMainImgUrl(String mainImgUrl) {
		this.mainImgUrl = mainImgUrl;
	}

	public String getvMainImgUrl() {
		return vMainImgUrl;
	}

	public void setvMainImgUrl(String vMainImgUrl) {
		this.vMainImgUrl = vMainImgUrl;
	}

	public String geteImgUrl() {
		return eImgUrl;
	}

	public void seteImgUrl(String eImgUrl) {
		this.eImgUrl = eImgUrl;
	}

	public String geteImgUrl1() {
		return eImgUrl1;
	}

	public void seteImgUrl1(String eImgUrl1) {
		this.eImgUrl1 = eImgUrl1;
	}

	public String geteImgUrl2() {
		return eImgUrl2;
	}

	public void seteImgUrl2(String eImgUrl2) {
		this.eImgUrl2 = eImgUrl2;
	}

	public String geteImgUrl3() {
		return eImgUrl3;
	}

	public void seteImgUrl3(String eImgUrl3) {
		this.eImgUrl3 = eImgUrl3;
	}

	public String geteImgUrl4() {
		return eImgUrl4;
	}

	public void seteImgUrl4(String eImgUrl4) {
		this.eImgUrl4 = eImgUrl4;
	}

	public String geteImgUrl5() {
		return eImgUrl5;
	}

	public void seteImgUrl5(String eImgUrl5) {
		this.eImgUrl5 = eImgUrl5;
	}

	public String geteImgUrl6() {
		return eImgUrl6;
	}

	public void seteImgUrl6(String eImgUrl6) {
		this.eImgUrl6 = eImgUrl6;
	}

	public String geteImgUrl7() {
		return eImgUrl7;
	}

	public void seteImgUrl7(String eImgUrl7) {
		this.eImgUrl7 = eImgUrl7;
	}

	public String geteImgUrl8() {
		return eImgUrl8;
	}

	public void seteImgUrl8(String eImgUrl8) {
		this.eImgUrl8 = eImgUrl8;
	}

	public String geteImgUrl9() {
		return eImgUrl9;
	}

	public void seteImgUrl9(String eImgUrl9) {
		this.eImgUrl9 = eImgUrl9;
	}

	public String geteImgUrl10() {
		return eImgUrl10;
	}

	public void seteImgUrl10(String eImgUrl10) {
		this.eImgUrl10 = eImgUrl10;
	}

}