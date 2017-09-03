package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 物流方式信息表实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class Logis implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 物流方式ID
	 */
	@Id
	@Column
	private int id;

	/**
	 * 物流方式名称
	 */
	@Column(length = 32)
	private String name;
	
	/**
	 * 该物流方式针对发货国家
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Country country;

	/**
	 * 计费模式（1：=Ax+B；2：if x < X, =C else =Dx）
	 */
	@Column
	private int method;
	
	/**
	 * 计费模式参数A
	 */
	@Column(precision = 6, scale = 2)
	private BigDecimal paraA;
	
	/**
	 * 计费模式参数B
	 */
	@Column(precision = 6, scale = 2)
	private BigDecimal paraB;
	
	/**
	 * 计费模式参数X
	 */
	@Column(precision = 6, scale = 2)
	private BigDecimal paraX;
	
	/**
	 * 计费模式参数C
	 */
	@Column(precision = 6, scale = 2)
	private BigDecimal paraC;
	
	/**
	 * 计费模式参数D
	 */
	@Column(precision = 6, scale = 2)
	private BigDecimal paraD;

	/**
	 * 最后修改人ID
	 */
	@Column(length = 16)
	private String modifyId;

	/**
	 * 最后修改人
	 */
	@Column(length = 16)
	private String modifyer;

	/**
	 * 最后修改时间
	 */
	@Column
	private Timestamp modifyTm;
	
	/**
	 * 100克费用
	 */
	@Transient
	private BigDecimal fee100;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	public BigDecimal getParaA() {
		return paraA;
	}

	public void setParaA(BigDecimal paraA) {
		this.paraA = paraA;
	}

	public BigDecimal getParaB() {
		return paraB;
	}

	public void setParaB(BigDecimal paraB) {
		this.paraB = paraB;
	}

	public BigDecimal getParaX() {
		return paraX;
	}

	public void setParaX(BigDecimal paraX) {
		this.paraX = paraX;
	}

	public BigDecimal getParaC() {
		return paraC;
	}

	public void setParaC(BigDecimal paraC) {
		this.paraC = paraC;
	}

	public BigDecimal getParaD() {
		return paraD;
	}

	public void setParaD(BigDecimal paraD) {
		this.paraD = paraD;
	}

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyer() {
		return modifyer;
	}

	public void setModifyer(String modifyer) {
		this.modifyer = modifyer;
	}

	public Timestamp getModifyTm() {
		return modifyTm;
	}

	public void setModifyTm(Timestamp modifyTm) {
		this.modifyTm = modifyTm;
	}

	public BigDecimal getFee100() {
		return fee100;
	}

	public void setFee100(BigDecimal fee100) {
		this.fee100 = fee100;
	}

}