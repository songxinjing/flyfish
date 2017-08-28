package com.songxinjing.flyfish.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.alibaba.fastjson.JSONObject;

public class ExcelUtil {

	/**
	 * 读取excel列表数据
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static Map<Integer, JSONObject> readExcel(InputStream temp) {
		Workbook workbook = null;
		Row row = null;
		Cell cell = null;
		Map<Integer, JSONObject> contJson = new LinkedHashMap<Integer, JSONObject>();
		try {
			workbook = WorkbookFactory.create(temp);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
		// 读取第一个sheet内容
		Sheet sheet = workbook.getSheetAt(0);
		row = sheet.getRow(0);// 读取表头
		int rowNum = sheet.getPhysicalNumberOfRows();// 获取总行数
		List<String> titles = readTitles(row);

		for (int i = 1; i < rowNum; i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			for (int j = 0; j < titles.size(); j++) {
				cell = row.getCell(j);
				Object cellVal = getCellValue(cell);
				map.put(titles.get(j), cellVal);
			}
			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
			contJson.put(i, jsonObject);
		}
		return contJson;
	}

	/**
	 * 写入excel列表数据
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static Workbook writeExcel(InputStream temp, Map<Integer, JSONObject> data) {
		Workbook workbook = null;
		Row row = null;
		try {
			workbook = WorkbookFactory.create(temp);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
		// 读取第一个sheet内容
		Sheet sheet = workbook.getSheetAt(0);
		row = sheet.getRow(0);// 读取表头
		List<String> titles = readTitles(row);
		for (int key : data.keySet()) {
			JSONObject json = data.get(key);
			row = sheet.createRow(key);
			for (int i = 0; i < titles.size(); i++) {
				String title = titles.get(i);
				if (StringUtils.isEmpty(title)) {
					continue;
				}
				row.createCell(i).setCellValue(json.get(title).toString());
			}
		}

		return workbook;

	}

	/**
	 * 读取excel列表表头信息
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static List<String> readTitles(Row titleRow) {
		Cell cell = null;
		int colNum = titleRow.getPhysicalNumberOfCells();// 每行列数
		List<String> titles = new ArrayList<String>();
		for (int i = 0; i < colNum; i++) {
			cell = titleRow.getCell(i);
			String cellVal = cell.getStringCellValue();
			if (StringUtils.isEmpty(cellVal)) {
				continue;
			}
			titles.add(i, cellVal);
		}
		return titles;
	}

	/**
	 * 获取单元格内容值
	 * 
	 * @param cell
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Object getCellValue(Cell cell) {
		switch (cell.getCellTypeEnum()) {
		case STRING:
			return cell.getRichStringCellValue().getString();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return cell.getNumericCellValue();
			}
		case BOOLEAN:
			return cell.getBooleanCellValue();
		case FORMULA:
			return cell.getCellFormula();
		case BLANK:
			return "";
		default:
			return null;
		}
	}

}
