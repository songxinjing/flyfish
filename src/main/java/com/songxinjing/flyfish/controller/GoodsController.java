package com.songxinjing.flyfish.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.songxinjing.flyfish.constant.Constant;
import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.domain.GoodsImg;
import com.songxinjing.flyfish.domain.Platform;
import com.songxinjing.flyfish.domain.Store;
import com.songxinjing.flyfish.domain.StoreGoods;
import com.songxinjing.flyfish.domain.User;
import com.songxinjing.flyfish.exception.AppException;
import com.songxinjing.flyfish.form.GoodsForm;
import com.songxinjing.flyfish.form.GoodsQueryForm;
import com.songxinjing.flyfish.plugin.cache.MapCache;
import com.songxinjing.flyfish.plugin.page.PageModel;
import com.songxinjing.flyfish.service.GoodsImgService;
import com.songxinjing.flyfish.service.GoodsService;
import com.songxinjing.flyfish.service.PlatformService;
import com.songxinjing.flyfish.service.StoreGoodsService;
import com.songxinjing.flyfish.service.StoreService;
import com.songxinjing.flyfish.util.BaseUtil;
import com.songxinjing.flyfish.util.ReflectionUtil;
import com.songxinjing.flyfish.util.SftpUtil;

/**
 * 商品管理控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class GoodsController extends BaseController {

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GoodsImgService goodsImgService;

	@Autowired
	private PlatformService platformService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private StoreGoodsService storeGoodsService;

	@RequestMapping(value = "goods/list")
	public String list(HttpServletRequest request, Model model, Integer page, Integer pageSize, Boolean isQuery,
			GoodsQueryForm form) throws AppException {
		try {
			logger.info("进入商品列表页面");
			if (page == null) {
				page = 1;
			}
			if (pageSize == null) {
				pageSize = Constant.PAGE_SIZE;
			}
			if (isQuery == null) {
				isQuery = false;
			}
			if (isQuery) {
				logger.info("商品查询，有传入查询条件，更新session");
				request.getSession().setAttribute(Constant.SESSION_GOODS_QUERY, form);
			} else {
				logger.info("商品查询，没有传入查询条件，从session中获取");
				form = (GoodsQueryForm) request.getSession().getAttribute(Constant.SESSION_GOODS_QUERY);
				if (form == null) {
					form = new GoodsQueryForm();
				}
			}

			String dataHqlPre = "select goods ";
			String countHqlPre = "select count(goods.sku) ";
			String hql = "";
			Map<String, Object> paraMap = new HashMap<String, Object>();
			if (form.getStoreId() == 0) {
				hql = "from Goods as goods where 1=1 ";
			} else if (form.getStoreId() == -1) {
				hql = "from Goods as goods left join goods.storeGoodses as sg left join sg.store as store where store.id is null ";
			} else {
				hql = "from Goods as goods left join goods.storeGoodses as sg left join sg.store as store where (store.id is null or store.id != :storeId) ";
				paraMap.put("storeId", form.getStoreId());
			}
			if (form.getImpState() == 1) {
				hql = hql + "and length(goods.title) > 0 ";
			} 
			if (StringUtils.isNotEmpty(form.getName())) {
				hql = hql + "and goods.name like :name ";
				paraMap.put("name", "%" + form.getName().trim() + "%");
			}
			if (StringUtils.isNotEmpty(form.getSku())) {
				hql = hql + "and goods.sku like :sku ";
				paraMap.put("sku", "%" + form.getSku().trim() + "%");
			}
			if (StringUtils.isNotEmpty(form.getBigCataName())) {
				hql = hql + "and goods.bigCataName = :bigCataName ";
				paraMap.put("bigCataName", form.getBigCataName().trim());
			}
			if (StringUtils.isNotEmpty(form.getSmallCataName())) {
				hql = hql + "and goods.smallCataName = :smallCataName ";
				paraMap.put("smallCataName", form.getSmallCataName().trim());
			}
			if (StringUtils.isNotEmpty(form.getBussOwner1())) {
				hql = hql + "and goods.bussOwner1 = :bussOwner1 ";
				paraMap.put("bussOwner1", form.getBussOwner1().trim());
			}
			if (StringUtils.isNotEmpty(form.getBussOwner2())) {
				hql = hql + "and goods.bussOwner2 = :bussOwner2 ";
				paraMap.put("bussOwner2", form.getBussOwner2().trim());
			}
			if (StringUtils.isNotEmpty(form.getBuyer())) {
				hql = hql + "and goods.buyer = :buyer ";
				paraMap.put("buyer", form.getBuyer().trim());
			}
			if (StringUtils.isNotEmpty(form.getState())) {
				hql = hql + "and goods.state = :state ";
				paraMap.put("state", form.getState().trim());
			}
			if (StringUtils.isNotEmpty(form.getIsElectric())) {
				hql = hql + "and goods.isElectric = :isElectric ";
				paraMap.put("isElectric", form.getIsElectric().trim());
			}

			if (StringUtils.isNotEmpty(form.getCreateTmBegin()) && StringUtils.isNotEmpty(form.getCreateTmEnd())) {
				hql = hql + "and goods.createTm >= :createTmBegin and goods.createTm <= :createTmEnd ";
				paraMap.put("createTmBegin", form.getCreateTmBegin().trim());
				paraMap.put("createTmEnd", form.getCreateTmEnd().trim());
			}

			if (StringUtils.isNotEmpty(form.getSkus())) {
				String skus = form.getSkus().replaceAll("，", ",");
				List<String> inSkus = new ArrayList<String>();
				for (String inSku : skus.split(",")) {
					inSkus.add(inSku.trim());
				}
				hql = hql + "and goods.sku in  (:inSkus) ";
				paraMap.put("inSkus", inSkus);
			}

			if (StringUtils.isNotEmpty(form.getParentSkus())) {
				String parentSkus = form.getParentSkus().replaceAll("，", ",");
				List<String> inParentSkus = new ArrayList<String>();
				for (String inParentSku : parentSkus.split(",")) {
					inParentSkus.add(inParentSku.trim());
				}
				hql = hql + "and goods.parentSku in  (:inParentSkus) ";
				paraMap.put("inParentSkus", inParentSkus);
			}

			if (StringUtils.isNotEmpty(form.getRelaSkus())) {
				hql = hql + "and ( 1=0 ";
				String relaSkus = form.getRelaSkus().replaceAll("，", ",");
				for (String relaSku : relaSkus.split(",")) {
					hql = hql + "or goods.relaSkus like '%" + relaSku + "%' ";
				}
				hql = hql + ") ";
			}

			if (StringUtils.isNotEmpty(form.getVirtSkus())) {
				hql = hql + "and ( 1=0 ";
				String virtSkus = form.getVirtSkus().replaceAll("，", ",");
				for (String virtSku : virtSkus.split(",")) {
					hql = hql + "or goods.virtSkus like '%" + virtSku + "%' ";
				}
				hql = hql + ") ";
			}
			int total = ((Long) goodsService.findHql(countHqlPre + hql, paraMap).get(0)).intValue();
			// 分页代码
			PageModel<GoodsForm> pageModel = new PageModel<GoodsForm>();
			pageModel.setPageSize(pageSize);
			pageModel.init(page, total);
			pageModel.setUrl("goods/list.html");
			pageModel.setPara("?pageSize=" + pageSize + "&");
			List<Goods> goodses = new ArrayList<Goods>();
			goodses = goodsService.findPage(dataHqlPre + hql, pageModel.getRecFrom(), pageModel.getPageSize(), paraMap);
			List<GoodsForm> list = new ArrayList<GoodsForm>();
			for (Goods goods : goodses) {
				GoodsForm goodsForm = new GoodsForm();
				goodsForm.setGoods(goods);
				goodsForm.setGoodsImg(goodsImgService.find(goods.getSku()));
				list.add(goodsForm);
			}
			pageModel.setRecList(list);
			model.addAttribute("pageModel", pageModel);
			model.addAttribute("page", pageModel.getCurrPage());
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("queryForm", form);

			model.addAttribute("bigCataNames", MapCache.get(Constant.CACHE_bigCataNames));
			model.addAttribute("smallCataNames", MapCache.get(Constant.CACHE_smallCataNames));
			model.addAttribute("bussOwner1s", MapCache.get(Constant.CACHE_bussOwner1s));
			model.addAttribute("bussOwner2s", MapCache.get(Constant.CACHE_bussOwner2s));
			model.addAttribute("buyers", MapCache.get(Constant.CACHE_buyers));
		} catch (Exception e) {
			logger.error("系统错误", e);
			throw new AppException();
		}
		return "goods/list";
	}

	@RequestMapping(value = "goods/edit", method = RequestMethod.GET)
	public String edit(Model model, String sku, Integer page, Integer pageSize) {
		logger.info("进入商品详情页面");
		GoodsForm form = new GoodsForm();
		Goods goods = goodsService.find(sku);
		form.setGoods(goods);
		form.setGoodsImg(goodsImgService.find(sku));

		Map<String, BigDecimal> platformShipPrice = new HashMap<String, BigDecimal>();
		for (Platform platform : platformService.find()) {
			BigDecimal shippingPrice = goodsService.getShippingPrice(platform, goods);
			platformShipPrice.put(platform.getName(), shippingPrice);
		}
		form.setEbayPrice(goodsService.getPrice(platformService.findByName(Constant.Ebay), goods,
				platformShipPrice.get(Constant.Ebay)));
		form.setAmazonPrice(goodsService.getPrice(platformService.findByName(Constant.Amazon), goods,
				platformShipPrice.get(Constant.Amazon)));
		form.setAliExpressPrice(goodsService.getPrice(platformService.findByName(Constant.AliExpress), goods,
				platformShipPrice.get(Constant.AliExpress)));
		form.setWishPrice(goodsService.getPrice(platformService.findByName(Constant.Wish), goods,
				platformShipPrice.get(Constant.Wish)));
		form.setJoomPrice(goodsService.getPrice(platformService.findByName(Constant.Joom), goods,
				platformShipPrice.get(Constant.Joom)));
		model.addAttribute("form", form);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		return "goods/edit";
	}

	@RequestMapping(value = "goods/delete", method = RequestMethod.GET)
	public String delete(String sku, Integer page, Integer pageSize) {
		logger.info("删除商品信息");
		goodsService.delete(sku);
		goodsImgService.delete(sku);
		return "redirect:/goods/list.html?pageSize=" + pageSize + "&page=" + page;
	}

	@RequestMapping(value = "goods/deleteall", method = RequestMethod.GET)
	public String deleteall(String skus, Integer page, Integer pageSize) {
		logger.info("批量删除商品信息");
		for (String sku : skus.split(",")) {
			if (StringUtils.isNotEmpty(sku)) {
				goodsService.delete(sku.trim());
				goodsImgService.delete(sku.trim());
			}
		}
		return "redirect:/goods/list.html?pageSize=" + pageSize + "&page=" + page;
	}

	@RequestMapping(value = "goods/save", method = RequestMethod.POST)
	@ResponseBody
	public boolean save(HttpServletRequest request, Model model, Goods goods) {
		logger.info("保存商品详情页面");

		// 获取用户登录信息
		User user = (User) request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		goods.setModifyId(user.getUserId());
		goods.setModifyer(user.getName());
		goods.setModifyTm(new Timestamp(System.currentTimeMillis()));

		goodsService.saveOrUpdate(goods);

		String hql = "from Goods where parentSku = :parentSku";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("parentSku", goods.getParentSku());
		@SuppressWarnings("unchecked")
		List<Goods> list = (List<Goods>) goodsService.findHql(hql, paraMap);
		for (Goods g : list) {
			logger.info("更新同一父SKU的标题和标签信息：" + goods.getParentSku() + " - " + g.getSku());
			g.setTitle(goods.getTitle());
			g.setEbayTitle(goods.getEbayTitle());
			g.setOtherTitle(goods.getOtherTitle());
			g.setTags(goods.getTags());
			g.setTitleWords(goods.getTitleWords());
			g.setBigCataName(goods.getBigCataName());
			g.setSmallCataName(goods.getSmallCataName());
			g.setBussOwner1(goods.getBussOwner1());
			g.setBussOwner2(goods.getBussOwner2());
			g.setState(goods.getState());
			g.setIsMoreSytle(goods.getIsMoreSytle());
			g.setIsElectric(goods.getIsElectric());
			g.setBuyer(goods.getBuyer());
			g.setBuyDayNum(goods.getBuyDayNum());
			g.setReportNameCn(goods.getReportNameCn());
			g.setReportNameEn(goods.getReportNameEn());
			g.setProdCountryCd(goods.getProdCountryCd());
			g.setProdCountryName(goods.getProdCountryName());
			g.setSeason(goods.getSeason());
			g.setReportPrice(goods.getReportPrice());
			g.setOutHeight(goods.getOutHeight());
			g.setOutLength(goods.getOutLength());
			g.setOutWidth(goods.getOutWidth());
			g.setSupplyName(goods.getSupplyName());
			g.setUrl(goods.getUrl());
			g.setUrl2(goods.getUrl2());
			g.setUrl3(goods.getUrl3());
			//目前发现有部分sku的款式是写在商品名称里面的 同步修改之后会有点麻烦
			//g.setName(goods.getName()); 
			goodsService.update(g);
		}
		return true;
	}

	@RequestMapping(value = "goods/add", method = RequestMethod.POST)
	@ResponseBody
	public boolean add(HttpServletRequest request, Model model, String sku) {
		logger.info("新增商品");
		Goods goods = goodsService.find(sku);
		if (goods != null) {
			return false;
		}
		goods = new Goods();
		goods.setSku(sku);
		// 获取用户登录信息
		User user = (User) request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		goods.setModifyId(user.getUserId());
		goods.setModifyer(user.getName());
		goods.setModifyTm(new Timestamp(System.currentTimeMillis()));
		goodsService.save(goods);
		return true;
	}

	@RequestMapping(value = "goods/uploadimg", method = RequestMethod.POST)
	@ResponseBody
	public boolean uploadimg(String sku, String imgName, MultipartFile file) {
		logger.info("上传图片");
		String tempSku = sku;
		if (tempSku.contains("*")) {
			tempSku = tempSku.replace("*", "_");
		}
		try {
			File temp = File.createTempFile("temp", ".jpg");
			file.transferTo(temp);
			GoodsImg goodsImg = goodsImgService.find(sku);
			if (imgName.equals("mainImgUrl")) {
				Goods goods = goodsService.find(sku);
				tempSku = goods.getParentSku();
			}
			String name = tempSku + "-" + imgName + ".jpg";
			SftpUtil.doFTP(name, temp);
			ReflectionUtil.setFieldValue(goodsImg, imgName, name);
			goodsImgService.update(goodsImg);
		} catch (IllegalStateException | IOException e) {
			logger.error("上传失败：" + sku + " " + imgName);
			return false;
		}
		return true;
	}

	@RequestMapping(value = "goods/setmain", method = RequestMethod.POST)
	@ResponseBody
	public boolean setmain(String sku, String imgName) {
		logger.info("设置主图");
		GoodsImg goodsImg = goodsImgService.find(sku);
		goodsImg.setvMainImgUrl(imgName);
		goodsImgService.save(goodsImg);
		return true;
	}

	@RequestMapping(value = "goods/joinstore", method = RequestMethod.POST)
	@ResponseBody
	public boolean joinStore(String skus, Integer storeId) {
		Store store = storeService.find(storeId);
		if (store != null) {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String batchNo = sdf.format(d);
			for (String sku : skus.split(",")) {
				if (StringUtils.isNotEmpty(sku)) {
					Goods goods = goodsService.find(sku.trim());
					if (goods != null) {
						String hql = "from StoreGoods where store.id = :storeId and goods.sku = :sku ";
						Map<String, Object> paraMap = new HashMap<String, Object>();
						paraMap.put("storeId", storeId);
						paraMap.put("sku", sku);
						StoreGoods sg = new StoreGoods();
						sg.setGoods(goods);
						sg.setStore(store);
						if (storeGoodsService.findHql(hql, paraMap).isEmpty()) {
							sg.setBatchNo(batchNo);
							storeGoodsService.save(sg);
							String listingSku = BaseUtil.changeSku(goods.getSku(), store.getMove());
							Set<String> temp = new HashSet<String>();
							if (StringUtils.isNotEmpty(goods.getVirtSkus())) {
								CollectionUtils.addAll(temp, goods.getVirtSkus().split(","));
							}
							temp.add(listingSku);
							String virtSkus = StringUtils.join(temp, ",");
							goods.setVirtSkus(virtSkus);
							goodsService.update(goods);
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	@RequestMapping(value = "goods/relasku", method = RequestMethod.GET)
	public String relasku(Model model, Integer page, Integer pageSize) {
		logger.info("进入关联SKU列表页面");

		if (page == null) {
			page = 1;
		}
		if (pageSize == null) {
			pageSize = Constant.PAGE_SIZE;
		}

		String hql = "from Goods where length(relaSkus) > 0";
		String countHql = "select count(sku) from Goods where length(relaSkus) > 0";

		int total = ((Long) goodsService.findHql(countHql).get(0)).intValue();

		// 分页代码
		PageModel<Goods> pageModel = new PageModel<Goods>();
		pageModel.setPageSize(pageSize);
		pageModel.init(page, total);
		pageModel.setUrl("goods/relasku.html");
		pageModel.setPara("?pageSize=" + pageSize + "&");
		List<Goods> list = goodsService.findPage(hql, pageModel.getRecFrom(), pageModel.getPageSize());
		pageModel.setRecList(list);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("page", pageModel.getCurrPage());

		return "goods/relasku";
	}

	@RequestMapping(value = "goods/virtsku", method = RequestMethod.GET)
	public String virtsku(Model model, Integer page, Integer pageSize) {
		logger.info("进入虚拟SKU列表页面");

		if (page == null) {
			page = 1;
		}
		if (pageSize == null) {
			pageSize = Constant.PAGE_SIZE;
		}

		String hql = "from Goods where length(virtSkus) > 0";
		String countHql = "select count(sku) from Goods where length(virtSkus) > 0";

		int total = ((Long) goodsService.findHql(countHql).get(0)).intValue();

		// 分页代码
		PageModel<Goods> pageModel = new PageModel<Goods>();
		pageModel.setPageSize(pageSize);
		pageModel.init(page, total);
		pageModel.setUrl("goods/virtsku.html");
		pageModel.setPara("?pageSize=" + pageSize + "&");
		List<Goods> list = goodsService.findPage(hql, pageModel.getRecFrom(), pageModel.getPageSize());
		pageModel.setRecList(list);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("page", pageModel.getCurrPage());

		return "goods/virtsku";
	}

}
