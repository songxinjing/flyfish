package com.songxinjing.flyfish.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.songxinjing.flyfish.constant.Constant;
import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.csv.CSVTemp;
import com.songxinjing.flyfish.csv.CSVUtil;
import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.domain.GoodsPlat;
import com.songxinjing.flyfish.domain.User;
import com.songxinjing.flyfish.excel.ExcelTemp;
import com.songxinjing.flyfish.excel.ExcelUtil;
import com.songxinjing.flyfish.service.GoodsPlatService;
import com.songxinjing.flyfish.service.GoodsService;
import com.songxinjing.flyfish.util.ConfigUtil;
import com.songxinjing.flyfish.util.ReflectionUtil;

/**
 * 主页控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class ExcelController extends BaseController {

	@Autowired
	GoodsService goodsService;

	@Autowired
	GoodsPlatService goodsPlatService;

	/**
	 * 普源数据导入
	 * 
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "excel/import/common", method = RequestMethod.POST)
	public String load(HttpServletRequest request, MultipartFile file) {
		logger.info("Excel导入common模版数据");

		if (!file.isEmpty()) {
			try {
				List<Map<String, String>> data = ExcelUtil.readExcel(file.getInputStream());
				for (Map<String, String> obj : data) {
					if (StringUtils.isNotEmpty(obj.get("SKU"))) {
						Goods goods = goodsService.find(obj.get("SKU"));
						if (goods == null) {
							goods = new Goods();
						}
						for (String key : ExcelTemp.COMMON_FIELD.keySet()) {
							if (!StringUtils.isEmpty(ExcelTemp.COMMON_FIELD.get(key))) {
								ReflectionUtil.setFieldValue(goods, ExcelTemp.COMMON_FIELD.get(key), obj.get(key));
							}
						}
						// 获取用户登录信息
						User user = (User) request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
						goods.setModifyId(user.getUserId());
						goods.setModifyer(user.getName());
						goods.setModifyTm(new Timestamp(System.currentTimeMillis()));
						if (goodsService.find(obj.get("SKU")) == null) {
							goodsService.save(goods);
						} else {
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

	/**
	 * 普源数据导出
	 * 
	 * @param response
	 */
	@RequestMapping(value = "excel/export/common", method = RequestMethod.GET)
	public void export(HttpServletResponse response) {
		logger.info("Excel导出模版数据");

		List<Goods> goodses = goodsService.find();

		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (Goods goods : goodses) {
			Map<String, String> map = new HashMap<String, String>();
			for (String key : ExcelTemp.COMMON_FIELD.keySet()) {
				if (!StringUtils.isEmpty(ExcelTemp.COMMON_FIELD.get(key))) {
					Object obj = ReflectionUtil.getFieldValue(goods, ExcelTemp.COMMON_FIELD.get(key));
					map.put(key, obj.toString());
				}
			}
			data.add(map);
		}

		String temp = ConfigUtil.getValue("/config.properties", "workDir") + ExcelTemp.COMMON;

		try {
			Workbook workbook = ExcelUtil.writeExcel(new FileInputStream(temp), data);
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("商品信息.xls", "UTF-8"));
			OutputStream os = new BufferedOutputStream(response.getOutputStream());
			workbook.write(os);
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

	@RequestMapping(value = "csv/import/wish", method = RequestMethod.POST)
	public String loadWish(HttpServletRequest request, MultipartFile file) {
		logger.info("CSV导入Wish模版数据");
		User user = (User) request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		if (!file.isEmpty()) {
			try {
				String[] headers = CSVTemp.WISH_FIELD.keySet().toArray(new String[] {});
				List<Map<String, String>> data = CSVUtil.readCSV(file.getInputStream(), headers);
				int i = 0;
				for (Map<String, String> obj : data) {
					if (StringUtils.isNotEmpty(obj.get("*Unique ID"))
							&& goodsPlatService.find(obj.get("*Unique ID")) == null) {
						GoodsPlat goodsPlat = new GoodsPlat();
						for (String key : CSVTemp.WISH_FIELD.keySet()) {
							if (!StringUtils.isEmpty(CSVTemp.WISH_FIELD.get(key))) {
								ReflectionUtil.setFieldValue(goodsPlat, CSVTemp.WISH_FIELD.get(key), obj.get(key));
							}
						}
						goodsPlat.setModifyId(user.getUserId());
						goodsPlat.setModifyer(user.getName());
						goodsPlat.setModifyTm(new Timestamp(System.currentTimeMillis()));
						goodsPlatService.save(goodsPlat);
					}
					i++;
					if (i == 100) {
						Thread.sleep(1000);
						i = 0;
					}
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		return "redirect:/goods/list.html";
	}

	@RequestMapping(value = "csv/export/wish", method = RequestMethod.GET)
	public void exportWish(HttpServletResponse response) {
		logger.info("导出Wish模版数据");
		List<GoodsPlat> goodses = goodsPlatService.find();
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (GoodsPlat goods : goodses) {
			Map<String, String> map = new HashMap<String, String>();
			for (String key : CSVTemp.WISH_FIELD.keySet()) {
				if (!StringUtils.isEmpty(CSVTemp.WISH_FIELD.get(key))) {
					Object obj = ReflectionUtil.getFieldValue(goods, CSVTemp.WISH_FIELD.get(key));
					map.put(key, obj.toString());
				}
			}
			data.add(map);
		}
		String file = ConfigUtil.getValue("/config.properties", "workDir") + CSVTemp.WISH;
		String[] headers = CSVTemp.WISH_FIELD.keySet().toArray(new String[] {});
		File csvFile = CSVUtil.writeCSV(file, data, headers);
		try {
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode("商品信息-WISH.csv", "UTF-8"));
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

	@RequestMapping(value = "csv/import/joom", method = RequestMethod.POST)
	public String loadJoom(HttpServletRequest request, MultipartFile file) {
		logger.info("CSV导入Joom模版数据");
		User user = (User) request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		if (!file.isEmpty()) {
			try {
				String[] headers = CSVTemp.JOOM_FIELD.keySet().toArray(new String[] {});
				List<Map<String, String>> data = CSVUtil.readCSV(file.getInputStream(), headers);
				int i = 0;
				for (Map<String, String> obj : data) {
					if (StringUtils.isNotEmpty(obj.get("SKU")) && goodsPlatService.find(obj.get("SKU")) == null) {
						GoodsPlat goodsPlat = new GoodsPlat();
						for (String key : CSVTemp.JOOM_FIELD.keySet()) {
							if (!StringUtils.isEmpty(CSVTemp.JOOM_FIELD.get(key))) {
								ReflectionUtil.setFieldValue(goodsPlat, CSVTemp.JOOM_FIELD.get(key), obj.get(key));
							}
						}
						goodsPlat.setModifyId(user.getUserId());
						goodsPlat.setModifyer(user.getName());
						goodsPlat.setModifyTm(new Timestamp(System.currentTimeMillis()));
						goodsPlatService.save(goodsPlat);
					}
					i++;
					if (i == 100) {
						Thread.sleep(1000);
						i = 0;
					}
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		return "redirect:/goods/list.html";
	}

	@RequestMapping(value = "csv/export/joom", method = RequestMethod.GET)
	public void exportJoom(HttpServletResponse response) {
		logger.info("导出Joom模版数据");
		List<GoodsPlat> goodses = goodsPlatService.find();
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (GoodsPlat goods : goodses) {
			Map<String, String> map = new HashMap<String, String>();
			for (String key : CSVTemp.JOOM_FIELD.keySet()) {
				if (!StringUtils.isEmpty(CSVTemp.JOOM_FIELD.get(key))) {
					Object obj = ReflectionUtil.getFieldValue(goods, CSVTemp.JOOM_FIELD.get(key));
					map.put(key, obj.toString());
				}
			}
			data.add(map);
		}
		String file = ConfigUtil.getValue("/config.properties", "workDir") + CSVTemp.JOOM;
		String[] headers = CSVTemp.JOOM_FIELD.keySet().toArray(new String[] {});
		File csvFile = CSVUtil.writeCSV(file, data, headers);
		try {
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode("商品信息-JOOM.csv", "UTF-8"));
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
