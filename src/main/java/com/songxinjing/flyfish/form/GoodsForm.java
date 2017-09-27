package com.songxinjing.flyfish.form;

import java.io.Serializable;

import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.domain.GoodsPlat;

public class GoodsForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Goods goods;
	
	private GoodsPlat goodsPlat;

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public GoodsPlat getGoodsPlat() {
		return goodsPlat;
	}

	public void setGoodsPlat(GoodsPlat goodsPlat) {
		this.goodsPlat = goodsPlat;
	}

}
