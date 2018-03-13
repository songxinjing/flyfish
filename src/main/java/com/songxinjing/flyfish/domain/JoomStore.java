package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Joom店铺实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class JoomStore implements Serializable {

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
	 * 客户端ID
	 */
	@Column(length = 16)
	private String clientId;

	/**
	 * 客户端密钥
	 */
	@Column(length = 32)
	private String clientSecret;

	/**
	 * merchantId
	 */
	@Column(length = 64)
	private String merchantId;

	/**
	 * accessToken
	 */
	@Column(length = 1024)
	private String accessToken;

	/**
	 * refreshToken
	 */
	@Column(length = 1024)
	private String refreshToken;

	/**
	 * token过期时间
	 */
	@Column
	private Timestamp expiryTime;

	/**
	 * 同步状态 0：空闲；1：请求同步；2：正在同步
	 */
	@Column
	private Integer state;

	/**
	 * 请求同步任务时间
	 */
	@Column
	private Timestamp applyJobTime;

	/**
	 * 最后同步时间
	 */
	@Column
	private Timestamp lastSyncTime;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
	private Set<JoomProduct> products;

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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Timestamp getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Timestamp expiryTime) {
		this.expiryTime = expiryTime;
	}

	public Set<JoomProduct> getProducts() {
		return products;
	}

	public void setProducts(Set<JoomProduct> products) {
		this.products = products;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Timestamp getApplyJobTime() {
		return applyJobTime;
	}

	public void setApplyJobTime(Timestamp applyJobTime) {
		this.applyJobTime = applyJobTime;
	}

	public Timestamp getLastSyncTime() {
		return lastSyncTime;
	}

	public void setLastSyncTime(Timestamp lastSyncTime) {
		this.lastSyncTime = lastSyncTime;
	}

}