package com.songxinjing.flyfish.plugin.page;

import java.util.ArrayList;
import java.util.List;

import com.songxinjing.flyfish.constant.Constant;

/**
 * 分页组件
 * 
 * @author songxinjing
 *
 * @param <T>
 *            分页内容对象
 */
public class PageModel<T> {

	/**
	 * 记录总数
	 */
	private int recTotal;

	/**
	 * 分页总数
	 */
	private int pageTotal;

	/**
	 * 每页记录数
	 */
	private int pageSize = Constant.PAGE_SIZE;

	/**
	 * 当前页码
	 */
	private int currPage = 1;

	/**
	 * 最大显示页码数（页面可直接点击的页面）
	 */
	private int showPageNum = 5;

	/**
	 * 显示页码开始页（页面可直接点击的页面）
	 */
	private int showPageFrom;

	/**
	 * 显示页码结束页（页面可直接点击的页面）
	 */
	private int showPageTo;

	/**
	 * 记录结果集
	 */
	public List<T> recList = new ArrayList<T>();

	/**
	 * 数据请求URL
	 */
	private String url;

	/**
	 * 数据请求参数（?key1=value1&key2=value2&...&）
	 */
	private String para;

	/**
	 * 当前页第一条记录的游标
	 */
	private int recFrom;

	public int getRecTotal() {
		return recTotal;
	}

	public void setRecTotal(int recTotal) {
		this.recTotal = recTotal;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getShowPageNum() {
		return showPageNum;
	}

	public void setShowPageNum(int showPageNum) {
		this.showPageNum = showPageNum;
	}

	public int getShowPageFrom() {
		return showPageFrom;
	}

	public void setShowPageFrom(int showPageFrom) {
		this.showPageFrom = showPageFrom;
	}

	public int getShowPageTo() {
		return showPageTo;
	}

	public void setShowPageTo(int showPageTo) {
		this.showPageTo = showPageTo;
	}

	public List<T> getRecList() {
		return recList;
	}

	public void setRecList(List<T> recList) {
		this.recList = recList;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public int getRecFrom() {
		return recFrom;
	}

	public void setRecFrom(int recFrom) {
		this.recFrom = recFrom;
	}

	/**
	 * 分页初始化
	 * 
	 * @param page
	 * @param total
	 */
	public void init(int currPage, int recTotal) {
		this.recTotal = recTotal;
		pageTotal = ((this.recTotal % pageSize) == 0) ? (this.recTotal / pageSize) : ((this.recTotal / pageSize) + 1);
		if(pageTotal == 0){
			pageTotal = 1;
		}
		if (currPage <= pageTotal) {
			this.currPage = currPage;
		} else {
			this.currPage = pageTotal;
		}
		recFrom = (this.currPage - 1) * pageSize;
		if (pageTotal <= showPageNum) {
			showPageFrom = 1;
			showPageTo = pageTotal;
		} else {
			int showPageNumPrev = (showPageNum - 1) / 2;
			int showPageNumNext = showPageNum - showPageNumPrev - 1;
			if (this.currPage <= showPageNumPrev) {
				showPageFrom = 1;
				showPageTo = showPageNum;
			} else if (this.currPage >= (pageTotal - showPageNumNext + 1)) {
				showPageTo = pageTotal;
				showPageFrom = pageTotal - showPageNum + 1;
			} else {
				showPageFrom = this.currPage - showPageNumPrev;
				showPageTo = this.currPage + showPageNumNext;
			}
		}

	}

}
