package com.songxinjing.flyfish.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.songxinjing.flyfish.domain.Logis;
import com.songxinjing.flyfish.domain.LogisProd;

public class LogisProdForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private LogisProd logisProd;

	private List<Logis> logises;

	private BigDecimal fee100ByWeight;

	private String remark;

	public LogisProd getLogisProd() {
		return logisProd;
	}

	public void setLogisProd(LogisProd logisProd) {
		this.logisProd = logisProd;
	}

	public List<Logis> getLogises() {
		return logises;
	}

	public void setLogises(List<Logis> logises) {
		this.logises = logises;
	}

	public BigDecimal getFee100ByWeight() {
		return fee100ByWeight;
	}

	public void setFee100ByWeight(BigDecimal fee100ByWeight) {
		this.fee100ByWeight = fee100ByWeight;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
