package com.songxinjing.flyfish.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.songxinjing.flyfish.constant.Constant;
import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.domain.GoodsImg;
import com.songxinjing.flyfish.domain.GoodsPlat;
import com.songxinjing.flyfish.domain.Platform;
import com.songxinjing.flyfish.domain.Store;
import com.songxinjing.flyfish.domain.User;
import com.songxinjing.flyfish.excel.ExcelTemp;
import com.songxinjing.flyfish.excel.ExcelUtil;
import com.songxinjing.flyfish.exception.AppException;
import com.songxinjing.flyfish.service.GoodsImgService;
import com.songxinjing.flyfish.service.GoodsPlatService;
import com.songxinjing.flyfish.service.GoodsService;
import com.songxinjing.flyfish.service.StoreService;
import com.songxinjing.flyfish.util.BaseUtil;
import com.songxinjing.flyfish.util.ConfigUtil;
import com.songxinjing.flyfish.util.ReflectionUtil;

/**
 * 商品导入导出控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class ExcelController extends BaseController {

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GoodsPlatService goodsPlatService;

	@Autowired
	private GoodsImgService goodsImgService;

	@Autowired
	private StoreService storeService;

	// @Autowired
	// private SftpService sftpService;

	@RequestMapping(value = "excel/import/common", method = RequestMethod.POST)
	public String load(HttpServletRequest request, MultipartFile file) throws AppException {
		logger.info("Excel导入普源模版数据");
		try {
			if (!file.isEmpty()) {
				logger.info("文件检查成功，开始导入！");
				try {
					List<Map<String, String>> data = ExcelUtil.readExcel(file.getInputStream());
					for (Map<String, String> obj : data) {
						String sku = obj.get("SKU");
						if (StringUtils.isEmpty(sku)) {
							sku = obj.get("sku");
						}
						if (StringUtils.isNotEmpty(sku)) {
							logger.info("开始导入商品SKU：" + sku);
							Goods goods = goodsService.find(sku);
							if (goods == null) {
								goods = new Goods();
							}
							for (String key : ExcelTemp.COMMON_FIELD.keySet()) {
								if (StringUtils.isNotEmpty(ExcelTemp.COMMON_FIELD.get(key)) && obj.containsKey(key)) {
									ReflectionUtil.setFieldValue(goods, ExcelTemp.COMMON_FIELD.get(key), obj.get(key));
								}
							}
							// 获取用户登录信息
							User user = (User) request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
							goods.setModifyId(user.getUserId());
							goods.setModifyer(user.getName());
							goods.setModifyTm(new Timestamp(System.currentTimeMillis()));
							if (goodsService.find(sku) == null) {
								goodsService.save(goods);
							} else {
								goodsService.update(goods);
							}

							// 更新带*SKU
							for (Goods moreGoods : goodsService.findMoreSku(sku + "*")) {
								String skuMore = moreGoods.getSku();
								int num = Integer.parseInt(skuMore.split("*")[1]);
								for (String key : ExcelTemp.COMMON_FIELD.keySet()) {
									if (StringUtils.isNotEmpty(ExcelTemp.COMMON_FIELD.get(key)) && obj.containsKey(key)
											&& !"sku".equals(ExcelTemp.COMMON_FIELD.get(key))) {
										if ("weight".equals(ExcelTemp.COMMON_FIELD.get(key))) {
											BigDecimal bgWeight = new BigDecimal(obj.get(key));
											moreGoods.setWeight(bgWeight.multiply(new BigDecimal(num)).toString());
										} else if ("costPrice".equals(ExcelTemp.COMMON_FIELD.get(key))) {
											BigDecimal bgCostPrice = new BigDecimal(obj.get(key));
											moreGoods
													.setCostPrice(bgCostPrice.multiply(new BigDecimal(num)).toString());
										} else {
											ReflectionUtil.setFieldValue(moreGoods, ExcelTemp.COMMON_FIELD.get(key),
													obj.get(key));
										}
									}
								}
								moreGoods.setModifyId(user.getUserId());
								moreGoods.setModifyer(user.getName());
								moreGoods.setModifyTm(new Timestamp(System.currentTimeMillis()));
								goodsService.update(moreGoods);
							}
						}
					}
				} catch (IOException e) {
					logger.error("读取文件失败", e);
				}
			}
		} catch (Exception e) {
			logger.error("系统错误", e);
			throw new AppException();
		}
		return "redirect:/goods/list.html";
	}

	@RequestMapping(value = "csv/import/wish", method = RequestMethod.POST)
	public String loadWish(HttpServletRequest request, MultipartFile file) throws AppException {
		logger.info("CSV导入Wish模版数据");
		try {
			if (!file.isEmpty()) {
				logger.info("文件检查成功，开始导入！");
				try {
					String[] headers = ExcelTemp.WISH_FIELD.keySet().toArray(new String[] {});
					List<Map<String, String>> data = ExcelUtil.readCSV(file.getInputStream(), headers);
					for (Map<String, String> obj : data) {
						String sku = obj.get("*Unique ID");
						try {
							if (StringUtils.isNotEmpty(sku)) {
								logger.info("开始导入商品SKU：" + sku);
								// 去除"\"部分
								if (sku.contains("\\")) {
									String skuH = sku.split("\\\\")[0];
									String skuB = sku.split("\\\\")[1];
									if (skuB.contains("*")) {
										sku = skuH + "*" + skuB.split("\\*")[1];
									} else {
										sku = skuH;
									}
								}

								Goods goods = goodsService.find(sku);
								if (sku.contains("*")) { // 包含"*"
									if (goods != null) { // 带*SKU存在
										this.wishUpdate(sku, obj); // 更新已存在带*SKU
									} else { // 带*SKU不存在
										String mainSku = sku.split("\\*")[0];
										int num = 1;
										try {
											num = Integer.parseInt(sku.split("\\*")[1]);
										} catch (NumberFormatException e) {
											logger.error("带*SKU格式错误" + sku, e);
											continue;
										} catch (ArrayIndexOutOfBoundsException e) {
											logger.error("带*SKU格式错误" + sku, e);
											continue;
										}
										Goods mainGoods = goodsService.find(mainSku);
										if (mainGoods != null) { // 去*SKU存在，新增带*SKU，复制信息
											this.wishNewStar(mainGoods, num, obj);
										} else { // 去*SKU不存在
											String hql = "from Goods where relaSkus like ?";
											List<Goods> list = goodsService.findHql(hql, "%" + mainSku + "%");
											if (!list.isEmpty()) { // 关联SKU存在
												goods = list.get(0);
												String skuStar = goods.getSku() + "*" + num;
												Goods starGoods = goodsService.find(skuStar);
												if (starGoods != null) { // 带*SKU存在
													this.wishUpdate(skuStar, obj); // 更新已存在带*SKU
												} else { // 带*SKU不存在，新增带*SKU，复制信息
													this.wishNewStar(goods, num, obj);
												}
											}
										}
									}
								} else { // 不包含"*"
									if (goods != null) { // SKU存在
										this.wishUpdate(sku, obj); // 更新已存在SKU
									} else { // SKU不存在
										String hql = "from Goods where relaSkus like ?";
										List<Goods> list = goodsService.findHql(hql, "%" + sku + "%");
										if (!list.isEmpty()) { // 关联SKU存在
											goods = list.get(0);
											this.wishUpdate(goods.getSku(), obj);
										}
									}
								}

							}

						} catch (Exception e) {
							logger.error("SKU导入错误：" + sku, e);
							continue;
						}
					}
				} catch (IOException e) {
					logger.error("读取文件失败", e);
				}
			}
		} catch (Exception e) {
			logger.error("系统错误", e);
			throw new AppException();
		}
		return "redirect:/goods/list.html";
	}

	@RequestMapping(value = "export", method = RequestMethod.GET)
	public void export(HttpServletResponse response, Integer storeId, String batchNo) throws AppException {
		logger.info("导出刊登");
		try {
			Store store = storeService.find(storeId);
			Platform platform = store.getPlatform();
			String hql = "select goods from Goods as goods left join goods.storeGoodses as sg left join sg.store as store where store.id = ? and sg.batchNo = ? ";
			List<Goods> goodses = goodsService.findHql(hql, storeId, batchNo);
			Map<String, String> tempFiledMap = ExcelTemp.PLATFORM_TEMP_FIELD.get(platform.getName());
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();

			for (Goods goods : goodses) {
				GoodsPlat goodsPlat = goodsPlatService.find(goods.getSku());
				GoodsImg goodsImg = goodsImgService.find(goods.getSku());
				if (goodsPlat == null || goodsImg == null) {
					continue;
				}

				String platformTitle = "";
				boolean titleRed = false;
				if (platform.getName().equals(Constant.Wish)) {
					platformTitle = goodsPlat.getTitle();
				} else if (platform.getName().equals(Constant.Ebay)) {
					platformTitle = goodsPlat.getEbayTitle();
					if (StringUtils.isNotEmpty(platformTitle) && platformTitle.length() > 75) {
						titleRed = true;
					}
				} else {
					platformTitle = goodsPlat.getOtherTitle();
					if (StringUtils.isNotEmpty(platformTitle) && platformTitle.length() > 90) {
						titleRed = true;
					}
				}

				if (StringUtils.isNotEmpty(goods.getWeight()) && StringUtils.isNotEmpty(goods.getCostPrice())
						&& StringUtils.isNotEmpty(platformTitle) && !titleRed
						&& StringUtils.isNotEmpty(goodsImg.getMainImgUrl())) {
					String listingSku = BaseUtil.changeSku(goods.getSku(), store.getMove());
					String listingParentSku = BaseUtil.changeSku(goods.getParentSku(), store.getMove());
					Map<String, String> map = new HashMap<String, String>();
					for (String key : tempFiledMap.keySet()) {
						if (!StringUtils.isEmpty(tempFiledMap.get(key))) {
							if (goodsImg != null && (key.contains("image") || key.contains("Image"))) {
								String img = (String) ReflectionUtil.getFieldValue(goodsImg, tempFiledMap.get(key));
								if (StringUtils.isNotEmpty(img)) {
									map.put(key, "http://" + store.getDomainName() + "/" + img);
								}
							} else if (goodsPlat != null) {
								Object obj = ReflectionUtil.getFieldValue(goodsPlat, tempFiledMap.get(key));
								if (obj != null) {
									map.put(key, obj.toString());
								}
							}
						}
					}

					String weight = goods.getWeight();
					if (StringUtils.isEmpty(weight)) {
						weight = "0";
					}

					BigDecimal shippingPrice = goodsService.getShippingPrice(platform, goods);
					if (shippingPrice == null) {
						shippingPrice = new BigDecimal(0);
					}
					BigDecimal price = goodsService.getPrice(platform, goods, shippingPrice);
					BigDecimal msrp = price.multiply(new BigDecimal(10)).setScale(2, RoundingMode.HALF_UP);

					if (Constant.Wish.equals(platform.getName())) {
						if (StringUtils.isEmpty(map.get("*Quantity"))) {
							map.put("*Quantity", "9999");
						}
						if (StringUtils
								.isEmpty(map.get("Shipping Time(enter without \" \", just the estimated days )"))) {
							map.put("Shipping Time(enter without \" \", just the estimated days )", "15-35");
						}
						map.put("*Shipping", "1");
						map.put("*Price", price.toString());
						map.put("*Unique ID", listingSku);
						map.put("Parent Unique ID", listingParentSku);
						map.put("*Product Name", platformTitle);
					} else if (Constant.Joom.equals(platform.getName())) {
						if (StringUtils.isEmpty(map.get("inventory"))) {
							map.put("inventory", "9999");
						}
						if (StringUtils.isEmpty(map.get("shipping days"))) {
							map.put("shipping days", "15-35");
						}
						map.put("shipping price", "0");
						map.put("price", price.toString());
						map.put("msrp", msrp.toString());
						map.put("SKU", listingSku);
						map.put("Parent SKU", listingParentSku);
						String smallTitel = platformTitle.toLowerCase();
						map.put("product name", smallTitel);
					}
					data.add(map);
				}
			}
			String file = ConfigUtil.getValue("/config.properties", "workDir")
					+ ExcelTemp.PLATFORM_TEMP_FILE.get(platform.getName());
			String[] headers = tempFiledMap.keySet().toArray(new String[] {});
			File csvFile = ExcelUtil.writeCSV(file, data, headers);
			try {
				response.setContentType("multipart/form-data");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(store.getName() + "-" + batchNo + ".csv", "UTF-8"));
				OutputStream os = new BufferedOutputStream(response.getOutputStream());
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(csvFile));
				byte[] buff = new byte[2048];
				while (true) {
					int bytesRead;
					if (-1 == (bytesRead = bis.read(buff, 0, buff.length)))
						break;
					os.write(buff, 0, bytesRead);
				}
				bis.close();
				os.flush();
				os.close();
			} catch (IOException e) {
				logger.error("导出文件失败", e);
			}
		} catch (Exception e) {
			logger.error("系统错误", e);
			throw new AppException();
		}
	}

	@RequestMapping(value = "excel/export/virtsku", method = RequestMethod.GET)
	public void exportVirtsku(HttpServletResponse response, Integer storeId, String batchNo) {
		logger.info("导出虚拟SKU列表");
		String hql = "from Goods where length(virtSkus) > 0";
		List<Goods> goodses = goodsService.findHql(hql);

		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (Goods goods : goodses) {
			Set<String> temp = new HashSet<String>();
			if (StringUtils.isNotEmpty(goods.getVirtSkus())) {
				CollectionUtils.addAll(temp, goods.getVirtSkus().split(","));
			}
			for (String virtsku : temp) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("sku", goods.getSku());
				map.put("关联SKU", virtsku);
				data.add(map);
			}
		}

		String temp = ConfigUtil.getValue("/config.properties", "workDir") + ExcelTemp.VIRTSKU;

		try {
			Workbook workbook = ExcelUtil.writeExcel(new FileInputStream(temp), data);
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode("虚拟SKU列表.xlsx", "UTF-8"));
			OutputStream os = new BufferedOutputStream(response.getOutputStream());
			workbook.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			logger.error("导出文件失败", e);
		}
	}

	@RequestMapping(value = "excel/import/relasku", method = RequestMethod.POST)
	public String loadRelaSku(HttpServletRequest request, MultipartFile file) {
		logger.info("导入关联SKU列表");
		if (!file.isEmpty()) {
			try {
				List<Map<String, String>> data = ExcelUtil.readExcel(file.getInputStream());
				Map<String, Set<String>> relaSkuMap = new HashMap<String, Set<String>>();
				for (Map<String, String> obj : data) {
					String sku = obj.get("sku");
					String relaSku = obj.get("关联SKU");
					if (StringUtils.isNotEmpty(sku) && StringUtils.isNotEmpty(relaSku)) {
						if (relaSkuMap.containsKey(sku)) {
							relaSkuMap.get(sku).add(relaSku);
						} else {
							Set<String> set = new HashSet<String>();
							set.add(relaSku);
							relaSkuMap.put(sku, set);
						}
					}
				}
				for (String sku : relaSkuMap.keySet()) {
					Goods goods = goodsService.find(sku);
					if (goods != null) {
						Set<String> temp = new HashSet<String>();
						if (StringUtils.isNotEmpty(goods.getRelaSkus())) {
							CollectionUtils.addAll(temp, goods.getRelaSkus().split(","));
						}
						temp.addAll(relaSkuMap.get(sku));
						String relaSkus = StringUtils.join(temp, ",");
						goods.setRelaSkus(relaSkus);
						goodsService.update(goods);
					}
				}
			} catch (IOException e) {
				logger.error("导出文件失败", e);
			}
		}
		return "redirect:/goods/relasku.html";
	}

	// 根据Excel数据新增或更新GoodsPlat和GoodsImg
	private void wishUpdate(String sku, Map<String, String> obj) {

		GoodsPlat goodsPlat = goodsPlatService.find(sku);
		if (goodsPlat == null) {
			goodsPlat = new GoodsPlat();
		}
		for (String key : ExcelTemp.WISH_FIELD.keySet()) {
			if (StringUtils.isNotEmpty(ExcelTemp.WISH_FIELD.get(key)) && obj.containsKey(key)) {
				if (ExcelTemp.WISH_FIELD.get(key).equals("sku")) {
					ReflectionUtil.setFieldValue(goodsPlat, "sku", sku);
				} else if (ExcelTemp.WISH_FIELD.get(key).equals("tags")) {
					if (StringUtils.isEmpty(goodsPlat.getTags())) {
						ReflectionUtil.setFieldValue(goodsPlat, "tags", obj.get(key));
					}
				} else {
					ReflectionUtil.setFieldValue(goodsPlat, ExcelTemp.WISH_FIELD.get(key), obj.get(key));
				}
			}
		}
		if (goodsPlatService.find(sku) == null) {
			goodsPlat.setEbayTitle(goodsPlat.getTitle());
			goodsPlat.setOtherTitle(goodsPlat.getTitle());
			goodsPlat.setIsUpload(0);
			goodsPlatService.save(goodsPlat);
		} else {
			goodsPlatService.update(goodsPlat);
		}

		// 更新父SKU
		if (StringUtils.isNotEmpty(obj.get("Parent Unique ID"))) {
			Goods goods = goodsService.find(sku);
			String parentSku = obj.get("Parent Unique ID");
			// 去除"\"部分
			if (parentSku.contains("\\")) {
				parentSku = parentSku.split("\\\\")[0];
			}
			goods.setParentSku(parentSku);
			goodsService.save(goods);
		}
	}

	// 根据SKU复制新增带*SKU
	private void wishNewStar(Goods goods, int num, Map<String, String> obj) {
		String skuStar = goods.getSku() + "*" + num;
		Goods temp = new Goods();
		BeanUtils.copyProperties(goods, temp);
		temp.setSku(skuStar);
		BigDecimal bgWeight = new BigDecimal(goods.getWeight());
		temp.setWeight(bgWeight.multiply(new BigDecimal(num)).toString());
		BigDecimal bgCostPrice = new BigDecimal(goods.getCostPrice());
		temp.setCostPrice(bgCostPrice.multiply(new BigDecimal(num)).toString());
		temp.setStoreGoodses(null);
		temp.setRelaSkus("");
		temp.setVirtSkus("");
		temp.setModifyTm(new Timestamp(System.currentTimeMillis()));
		goodsService.save(temp);
		this.wishUpdate(skuStar, obj);
	}

}
