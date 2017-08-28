package com.songxinjing.flyfish.controller;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.songxinjing.flyfish.controller.base.BaseController;
import com.songxinjing.flyfish.excel.ExcelTemp;
import com.songxinjing.flyfish.excel.ExcelUtil;
import com.songxinjing.flyfish.util.ConfigUtil;

/**
 * 主页控制类
 * 
 * @author songxinjing
 * 
 */
@Controller
public class ExcelController extends BaseController {

	@RequestMapping(value = "excel/export/flyfish", method = RequestMethod.POST)
	public void export(Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Excel导出飞鱼模版数据");
		// 获取查询条件

		// 根据查询条件筛选数据

		Map<Integer, JSONObject> data = new HashMap<Integer, JSONObject>();

		//

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
	
	@RequestMapping(value = "excel/import/flyfish", method = RequestMethod.POST)
	public void load(Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Excel导出飞鱼模版数据");
		// 获取查询条件

		// 根据查询条件筛选数据

		Map<Integer, JSONObject> data = new HashMap<Integer, JSONObject>();

		//

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

}
