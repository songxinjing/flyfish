package com.songxinjing.flyfish.controller;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.excel.ExcelTemp;
import com.songxinjing.flyfish.excel.ExcelUtil;
import com.songxinjing.flyfish.service.GoodsService;
import com.songxinjing.flyfish.util.ConfigUtil;

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

	@RequestMapping(value = "excel/export/flyfish", method = RequestMethod.GET)
	public void export(HttpServletResponse response) {
		logger.info("Excel导出飞鱼模版数据");

		List<Goods> goodses = goodsService.find();

		Map<Integer, JSONObject> data = new HashMap<Integer, JSONObject>();
		int i = 1;
		for (Goods goods : goodses) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("商品SKU", goods.getSku());
			map.put("商品父SKU", goods.getParentSku());
			map.put("商品名称", goods.getName());
			map.put("商品重量（G）", goods.getWeight());
			map.put("成本价（RMB）", goods.getCostPrice());
			map.put("中文申报名", goods.getReportNameCn());
			map.put("英文申报名", goods.getReportNameEn());
			map.put("业绩归宿人", goods.getBussOwner());
			map.put("采购员", goods.getBuyer());
			map.put("网页URL", goods.getUrl());
			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
			data.put(i, jsonObject);
			i++;
		}

		String temp = ConfigUtil.getValue("/config.properties", "workDir") + ExcelTemp.FLYFISH;

		try {
			Workbook workbook = ExcelUtil.writeExcel(new FileInputStream(temp), data);
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode("商品信息-飞鱼.xlsx", "UTF-8"));
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

	@RequestMapping(value = "excel/temp/flyfish", method = RequestMethod.GET)
	public void temp(HttpServletResponse response) {
		logger.info("下载模版：商品信息模版-飞鱼.xlsx");

		String temp = ConfigUtil.getValue("/config.properties", "workDir") + ExcelTemp.FLYFISH;

		try {
			Workbook workbook = WorkbookFactory.create(new FileInputStream(temp));
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode("商品信息模版-飞鱼.xlsx", "UTF-8"));
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
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "excel/import/flyfish", method = RequestMethod.POST)
	public String load(MultipartFile file, int same) {
		logger.info("Excel导入飞鱼模版数据");

		if (!file.isEmpty()) {
			try {
				Map<Integer, JSONObject> data = ExcelUtil.readExcel(file.getInputStream());
				for (JSONObject obj : data.values()) {
					if (goodsService.find(obj.getString("商品SKU")) == null) {
						Goods goods = new Goods();
						goods.setSku(obj.getString("商品SKU"));
						goods.setParentSku(obj.getString("商品父SKU"));
						goods.setName(obj.getString("商品名称"));
						goods.setWeight(obj.getIntValue("商品重量（G）"));
						goods.setCostPrice(obj.getBigDecimal("成本价（RMB）"));
						goods.setReportNameCn(obj.getString("中文申报名"));
						goods.setReportNameEn(obj.getString("英文申报名"));
						goods.setBussOwner(obj.getString("业绩归宿人"));
						goods.setBuyer(obj.getString("采购员"));
						goods.setUrl(obj.getString("网页URL"));
						goods.setModifyTime(new Timestamp(System.currentTimeMillis()));
						goodsService.save(goods);
					}

				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "redirect:/goods/list.html";

	}

}
