package com.songxinjing.flyfish.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.songxinjing.flyfish.service.GoodsImgService;
import com.songxinjing.flyfish.service.GoodsPlatService;
import com.songxinjing.flyfish.service.GoodsService;
import com.songxinjing.flyfish.service.SftpService;
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

	@Autowired
	private SftpService sftpService;

	@RequestMapping(value = "excel/import/common", method = RequestMethod.POST)
	public String load(HttpServletRequest request, MultipartFile file) {
		logger.info("Excel导入普源模版数据");
		if (!file.isEmpty()) {
			try {
				List<Map<String, String>> data = ExcelUtil.readExcel(file.getInputStream());
				for (Map<String, String> obj : data) {
					String sku = obj.get("SKU");
					if (StringUtils.isEmpty(sku)) {
						sku = obj.get("sku");
					}
					if (StringUtils.isNotEmpty(sku)) {
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
										moreGoods.setCostPrice(bgCostPrice.multiply(new BigDecimal(num)).toString());
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
				e.printStackTrace();
			}
		}
		return "redirect:/goods/list.html";
	}

	@RequestMapping(value = "csv/import/wish", method = RequestMethod.POST)
	public String loadWish(HttpServletRequest request, MultipartFile file) {
		logger.info("CSV导入Wish模版数据");
		User user = (User) request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		if (!file.isEmpty()) {
			try {
				String[] headers = ExcelTemp.WISH_FIELD.keySet().toArray(new String[] {});
				List<Map<String, String>> data = ExcelUtil.readCSV(file.getInputStream(), headers);
				for (Map<String, String> obj : data) {
					String sku = obj.get("*Unique ID");
					if (StringUtils.isNotEmpty(sku)) {
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
						if (goods == null) {
							if (sku.contains("*")) {
								String mainSku = sku.split("\\*")[0];
								int num = Integer.parseInt(sku.split("\\*")[1]);
								Goods mainGoods = goodsService.find(mainSku);
								if (mainGoods != null) {
									Goods temp = new Goods();
									BeanUtils.copyProperties(mainGoods, temp);
									temp.setSku(sku);
									BigDecimal bgWeight = new BigDecimal(mainGoods.getWeight());
									temp.setWeight(bgWeight.multiply(new BigDecimal(num)).toString());
									BigDecimal bgCostPrice = new BigDecimal(mainGoods.getCostPrice());
									temp.setCostPrice(bgCostPrice.multiply(new BigDecimal(num)).toString());
									temp.setStoreGoodses(null);
									goodsService.save(temp);
									goods = goodsService.find(sku);
								}
							}
						}
						if (goods != null) {
							GoodsPlat goodsPlat = goodsPlatService.find(sku);
							if (goodsPlat == null) {
								goodsPlat = new GoodsPlat();
							}
							for (String key : ExcelTemp.WISH_FIELD.keySet()) {
								if (StringUtils.isNotEmpty(ExcelTemp.WISH_FIELD.get(key)) && obj.containsKey(key)) {
									if (ExcelTemp.WISH_FIELD.get(key).equals("sku")) {
										ReflectionUtil.setFieldValue(goodsPlat, "sku", sku);
									} else {
										ReflectionUtil.setFieldValue(goodsPlat, ExcelTemp.WISH_FIELD.get(key),
												obj.get(key));
									}
								}
							}

							String parentSku = obj.get("Parent Unique ID");
							if (parentSku.contains("\\")) {
								parentSku = parentSku.split("\\\\")[0];
							}
							goodsPlat.setParentSku(parentSku);

							if (goodsPlatService.find(sku) == null) {
								goodsPlatService.save(goodsPlat);
							} else {
								goodsPlatService.update(goodsPlat);
							}

							GoodsImg goodsImg = goodsImgService.find(sku);
							if (goodsImg == null) {
								goodsImg = new GoodsImg();
							}
							goodsImg.setSku(sku);
							for (String key : ExcelTemp.WISH_FIELD.keySet()) {
								if (!StringUtils.isEmpty(ExcelTemp.WISH_FIELD.get(key))) {
									if (ExcelTemp.WISH_FIELD.get(key).contains("Img")) {
										sftpService.startFTP(sku, ExcelTemp.WISH_FIELD.get(key), obj.get(key));
									}
								}
							}
							if (goodsImgService.find(sku) == null) {
								goodsImgService.save(goodsImg);
							} else {
								goodsImgService.update(goodsImg);
							}

							goods.setModifyId(user.getUserId());
							goods.setModifyer(user.getName());
							goods.setModifyTm(new Timestamp(System.currentTimeMillis()));
							goodsService.update(goods);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "redirect:/goods/list.html";
	}

	@RequestMapping(value = "export", method = RequestMethod.GET)
	public void export(HttpServletResponse response, Integer storeId, String batchNo) {

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
			if (StringUtils.isNotEmpty(goods.getWeight()) && StringUtils.isNotEmpty(goods.getCostPrice())
					&& StringUtils.isNotEmpty(goodsPlat.getTitle())
					&& StringUtils.isNotEmpty(goodsImg.getMainImgUrl())) {
				String listingSku = BaseUtil.changeSku(goods.getSku(), store.getMove());
				String listingParentSku = BaseUtil.changeSku(goodsPlat.getParentSku(), store.getMove());
				Map<String, String> map = new HashMap<String, String>();
				for (String key : tempFiledMap.keySet()) {
					if (!StringUtils.isEmpty(tempFiledMap.get(key))) {
						if (goodsImg != null && (key.contains("image") || key.contains("Image"))) {
							String img = (String) ReflectionUtil.getFieldValue(goodsImg, tempFiledMap.get(key));
							map.put(key, "http://" + store.getDomainName() + "/" + img);
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
					if (StringUtils.isEmpty(map.get("Shipping Time(enter without \" \", just the estimated days )"))) {
						map.put("Shipping Time(enter without \" \", just the estimated days )", "15-35");
					}
					map.put("*Shipping", "1");
					map.put("*Price", price.toString());
					map.put("*Unique ID", listingSku);
					map.put("Parent Unique ID", listingParentSku);
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
					String smallTitel = map.get("product name").toLowerCase();
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
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
