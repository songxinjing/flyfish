package com.songxinjing.flyfish.controller;

import java.io.BufferedOutputStream;
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

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.domain.Goods;
import com.songxinjing.flyfish.excel.ExcelTemp;
import com.songxinjing.flyfish.excel.ExcelUtil;
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

	@RequestMapping(value = "excel/temp/common", method = RequestMethod.GET)
	public void temp(HttpServletResponse response) {
		logger.info("下载模版：商品信息新增导入模版.xls");

		String temp = ConfigUtil.getValue("/config.properties", "workDir") + ExcelTemp.COMMON;

		try {
			Workbook workbook = WorkbookFactory.create(new FileInputStream(temp));
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode("商品信息新增导入模版.xls", "UTF-8"));
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

	@RequestMapping(value = "excel/import/common", method = RequestMethod.POST)
	public String load(MultipartFile file, int same) {
		logger.info("Excel导入common模版数据");

		if (!file.isEmpty()) {
			try {
				List<Map<String, String>> data = ExcelUtil.readExcel(file.getInputStream());
				for (Map<String, String> obj : data) {
					if (StringUtils.isNotEmpty(obj.get("SKU")) && goodsService.find(obj.get("SKU")) == null) {
						Goods goods = new Goods();
						for (String key : ExcelTemp.COMMON_FIELD.keySet()) {
							if (!StringUtils.isEmpty(ExcelTemp.COMMON_FIELD.get(key))) {
								ReflectionUtil.setFieldValue(goods, ExcelTemp.COMMON_FIELD.get(key), obj.get(key));
							}
						}
						goods.setModifyTm(new Timestamp(System.currentTimeMillis()));
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
