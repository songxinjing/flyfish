package com.songxinjing.flyfish.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.songxinjing.flyfish.domain.GoodsPlat;
import com.songxinjing.flyfish.domain.Platform;
import com.songxinjing.flyfish.domain.Store;
import com.songxinjing.flyfish.domain.StoreGoods;
import com.songxinjing.flyfish.domain.User;
import com.songxinjing.flyfish.form.GoodsEditForm;
import com.songxinjing.flyfish.form.GoodsForm;
import com.songxinjing.flyfish.form.GoodsQueryForm;
import com.songxinjing.flyfish.plugin.page.PageModel;
import com.songxinjing.flyfish.service.DomainService;
import com.songxinjing.flyfish.service.GoodsImgService;
import com.songxinjing.flyfish.service.GoodsPlatService;
import com.songxinjing.flyfish.service.GoodsService;
import com.songxinjing.flyfish.service.LogisProdService;
import com.songxinjing.flyfish.service.PlatformService;
import com.songxinjing.flyfish.service.StoreGoodsService;
import com.songxinjing.flyfish.service.StoreService;
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
	private GoodsPlatService goodsPlatService;

	@Autowired
	private GoodsImgService goodsImgService;

	@Autowired
	private DomainService domainService;

	@Autowired
	private PlatformService platformService;

	@Autowired
	private LogisProdService logisProdService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private StoreGoodsService storeGoodsService;

	@RequestMapping(value = "goods/list")
	public String list(Model model, Integer page, Integer pageSize, GoodsQueryForm form) {
		logger.info("进入商品列表页面");

		if (page == null) {
			page = 1;
		}
		if (pageSize == null) {
			pageSize = Constant.PAGE_SIZE;;
		}
		int total = 0;

		String bigCataName = "";
		String smallCataName = "";
		String bussOwner1 = "";
		String bussOwner2 = "";
		String buyer = "";
		String state = "";
		String isElectric = "";
		String createTmBegin = "";
		String createTmEnd = "";
		String parentSkus = "";

		int storeId = form.getStoreId();
		String hql = "";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if (storeId == 0) {
			hql = "select goods from Goods as goods where 1=1 ";
		} else if (storeId == -1) {
			hql = "select goods from Goods as goods left join goods.storeGoodses as sg left join sg.store as store where store.id is null ";
		} else {
			hql = "select goods from Goods as goods left join goods.storeGoodses as sg left join sg.store as store where (store.id is null or store.id != :storeId) ";
			paraMap.put("storeId", storeId);
		}

		if (StringUtils.isNotEmpty(form.getBigCataName())) {
			bigCataName = form.getBigCataName().trim();
			hql = hql + "and goods.bigCataName like :bigCataName ";
			paraMap.put("bigCataName", "%" + bigCataName + "%");
		}
		if (StringUtils.isNotEmpty(form.getSmallCataName())) {
			smallCataName = form.getSmallCataName().trim();
			hql = hql + "and goods.smallCataName like :smallCataName ";
			paraMap.put("smallCataName", "%" + smallCataName + "%");
		}

		if (StringUtils.isNotEmpty(form.getBussOwner1())) {
			bussOwner1 = form.getBussOwner1().trim();
			hql = hql + "and goods.bussOwner1 like :bussOwner1 ";
			paraMap.put("bussOwner1", "%" + bussOwner1 + "%");
		}

		if (StringUtils.isNotEmpty(form.getBussOwner2())) {
			bussOwner2 = form.getBussOwner2().trim();
			hql = hql + "and goods.bussOwner2 like :bussOwner2 ";
			paraMap.put("bussOwner2", "%" + bussOwner2 + "%");
		}

		if (StringUtils.isNotEmpty(form.getBuyer())) {
			buyer = form.getBuyer().trim();
			hql = hql + "and goods.buyer like :buyer ";
			paraMap.put("buyer", "%" + buyer + "%");
		}

		if (StringUtils.isNotEmpty(form.getState())) {
			state = form.getState().trim();
			hql = hql + "and goods.state = :state ";
			paraMap.put("state", state);
		}

		if (StringUtils.isNotEmpty(form.getIsElectric())) {
			isElectric = form.getIsElectric().trim();
			hql = hql + "and goods.isElectric = :isElectric ";
			paraMap.put("isElectric", isElectric);
		}

		if (StringUtils.isNotEmpty(form.getCreateTmBegin()) && StringUtils.isNotEmpty(form.getCreateTmEnd())) {
			createTmBegin = form.getCreateTmBegin().trim();
			createTmEnd = form.getCreateTmEnd().trim();
			hql = hql + "and goods.createTm >= :createTmBegin and goods.createTm <= :createTmEnd ";
			paraMap.put("createTmBegin", createTmBegin);
			paraMap.put("createTmEnd", createTmEnd);
		}

		if (StringUtils.isNotEmpty(form.getParentSkus())) {
			parentSkus = form.getParentSkus().replaceAll("，", ",");
			List<String> inParas = new ArrayList<String>();
			for (String inPara : parentSkus.split(",")) {
				inParas.add(inPara.trim());
			}
			hql = hql + "and goods.parentSku in  (:inParas) ";
			paraMap.put("inParas", inParas);
		}
		total = goodsService.findHql(hql, paraMap).size();

		// 分页代码
		PageModel<GoodsForm> pageModel = new PageModel<GoodsForm>();
		pageModel.setPageSize(pageSize);
		pageModel.init(page, total);
		pageModel.setUrl("goods/list.html");
		pageModel.setPara("?bigCataName=" + bigCataName + "&smallCataName=" + smallCataName + "&bussOwner1="
				+ bussOwner1 + "&bussOwner2=" + bussOwner2 + "&buyer=" + buyer + "&state=" + state + "&isElectric="
				+ isElectric + "&createTmBegin=" + createTmBegin + "&createTmEnd=" + createTmEnd + "&parentSkus="
				+ parentSkus + "&pageSize=" + pageSize + "&");

		List<Goods> goodses = new ArrayList<Goods>();
		goodses = goodsService.findPage(hql, pageModel.getRecFrom(), pageModel.getPageSize(), paraMap);
		List<GoodsForm> list = new ArrayList<GoodsForm>();
		for (Goods goods : goodses) {
			GoodsForm goodsForm = new GoodsForm();
			goodsForm.setGoods(goods);
			goodsForm.setGoodsPlat(goodsPlatService.find(goods.getSku()));
			goodsForm.setGoodsImg(goodsImgService.find(goods.getSku()));
			list.add(goodsForm);
		}

		pageModel.setRecList(list);

		model.addAttribute("pageModel", pageModel);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("queryForm", form);
		model.addAttribute("platforms", platformService.find());
		model.addAttribute("domains", domainService.find());
		model.addAttribute("prods", logisProdService.find());
		return "goods/list";
	}

	@RequestMapping(value = "goods/edit", method = RequestMethod.GET)
	public String edit(Model model, String sku) {
		logger.info("进入商品详情页面");
		GoodsForm form = new GoodsForm();
		Goods goods = goodsService.find(sku);
		form.setGoods(goods);
		form.setGoodsPlat(goodsPlatService.find(sku));
		form.setGoodsImg(goodsImgService.find(sku));
		Platform platform = platformService.findByName(Constant.Joom);
		BigDecimal shippingPrice = goodsService.getShippingPrice(platform, goods);
		form.setJoomPrice(goodsService.getPrice(platform, goods, shippingPrice));
		model.addAttribute("form", form);
		return "goods/edit";
	}

	@RequestMapping(value = "goods/delete", method = RequestMethod.GET)
	public String delete(String sku) {
		logger.info("删除商品信息");
		goodsService.delete(sku);
		goodsPlatService.delete(sku);
		goodsImgService.delete(sku);
		return "redirect:/goods/list.html";
	}
	
	@RequestMapping(value = "goods/deleteall", method = RequestMethod.GET)
	public String deleteall(String skus) {
		logger.info("批量删除商品信息");
		for (String sku : skus.split(",")) {
			if (StringUtils.isNotEmpty(sku)) {
				goodsService.delete(sku.trim());
				goodsPlatService.delete(sku.trim());
				goodsImgService.delete(sku.trim());
			}
		}
		return "redirect:/goods/list.html";
	}

	@RequestMapping(value = "goods/save", method = RequestMethod.POST)
	@ResponseBody
	public boolean save(HttpServletRequest request, Model model, GoodsEditForm form) {
		logger.info("保存商品详情页面");
		Goods goods = goodsService.find(form.getSku());
		if (goods == null) {
			goods = new Goods();
			goods.setSku(form.getSku());
			goodsService.save(goods);
			goods = goodsService.find(form.getSku());
		}
		GoodsPlat goodsPlat = goodsPlatService.find(form.getSku());
		if (goodsPlat == null) {
			goodsPlat = new GoodsPlat();
			goodsPlat.setSku(form.getSku());
			goodsPlatService.save(goodsPlat);
			goodsPlat = goodsPlatService.find(form.getSku());
		}

		Field[] fields = form.getClass().getDeclaredFields();
		for (Field field : fields) {
			if ("serialVersionUID".equals(field.getName())) {
				continue;
			}
			Object value = ReflectionUtil.getFieldValue(form, field.getName());
			if (value != null) {
				try {
					goods.getClass().getDeclaredField(field.getName());
					ReflectionUtil.setFieldValue(goods, field.getName(), value);
				} catch (NoSuchFieldException e) {
					logger.debug("Goods不存在字段：" + field.getName());
				}
				try {
					goodsPlat.getClass().getDeclaredField(field.getName());
					ReflectionUtil.setFieldValue(goodsPlat, field.getName(), value);
				} catch (NoSuchFieldException e) {
					logger.debug("GoodsPlat不存在字段：" + field.getName());
				}
			}
		}

		// 获取用户登录信息
		User user = (User) request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		goods.setModifyId(user.getUserId());
		goods.setModifyer(user.getName());
		goods.setModifyTm(new Timestamp(System.currentTimeMillis()));

		goodsPlat.setModifyId(user.getUserId());
		goodsPlat.setModifyer(user.getName());
		goodsPlat.setModifyTm(new Timestamp(System.currentTimeMillis()));

		goodsService.update(goods);
		goodsPlatService.update(goodsPlat);
		return true;
	}

	@RequestMapping(value = "goods/reget", method = RequestMethod.POST)
	@ResponseBody
	public boolean reget(String sku, String imgName) {
		logger.info("重新获取图片");
		GoodsPlat goodsPlat = goodsPlatService.find(sku);
		String imgUrl = (String) ReflectionUtil.getFieldValue(goodsPlat, imgName);
		SftpUtil.startFTP(imgUrl, sku + "-" + imgName + ".jpg");
		return true;
	}

	@RequestMapping(value = "goods/uploadimg", method = RequestMethod.POST)
	@ResponseBody
	public boolean uploadimg(String sku, String imgName, MultipartFile file) {
		logger.info("上传图片");
		GoodsImg goodsImg = goodsImgService.find(sku);
		String name = sku + "-" + imgName + System.currentTimeMillis() + ".jpg";
		ReflectionUtil.setFieldValue(goodsImg, imgName, name);
		goodsImgService.save(goodsImg);

		try {
			File temp = File.createTempFile("temp", ".jpg");
			file.transferTo(temp);
			SftpUtil.doFTP(name, temp);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
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
						String hql = "from StoreGoods where store.id = ? and goods.sku = ? ";
						StoreGoods sg = new StoreGoods();
						sg.setGoods(goods);
						sg.setStore(store);
						if (storeGoodsService.findHql(hql, storeId, sku).isEmpty()) {
							sg.setBatchNo(batchNo);
							storeGoodsService.save(sg);
						}
					}
				}
			}
			return true;
		}
		return false;
	}

}
