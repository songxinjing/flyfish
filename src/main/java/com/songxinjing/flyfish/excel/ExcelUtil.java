package com.songxinjing.flyfish.excel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	/**
	 * 读取excel列表数据
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, String>> readExcel(InputStream temp) {
		Workbook workbook = null;
		Row row = null;
		Cell cell = null;
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

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 1; i < rowNum; i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			Map<String, String> map = new HashMap<String, String>();
			for (int j = 0; j < titles.size(); j++) {
				cell = row.getCell(j);

				if (cell != null) {
					String cellVal = "";
					if (CellType.NUMERIC == cell.getCellTypeEnum()) {
						cellVal = cell.getNumericCellValue() + "";
					} else if (CellType.STRING == cell.getCellTypeEnum()) {
						cellVal = cell.getStringCellValue().trim();
					}
					map.put(titles.get(j), cellVal);
				}
			}
			list.add(map);
		}
		return list;
	}

	/**
	 * 写入excel列表数据
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static Workbook writeExcel(InputStream temp, List<Map<String, String>> data) {
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
		int rowNum = 1;
		for (Map<String, String> map : data) {
			row = sheet.createRow(rowNum);
			for (int i = 0; i < titles.size(); i++) {
				String title = titles.get(i);
				if (StringUtils.isEmpty(title)) {
					continue;
				}
				row.createCell(i).setCellValue(map.get(title));
			}
			rowNum++;
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
	 * 读取csv列表数据
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, String>> readCSV(InputStream temp, String[] headers) {

		// 这里显式地配置一下CSV文件的Header，然后设置跳过Header（要不然读的时候会把头也当成一条记录）
		// CSVFormat format =
		// CSVFormat.DEFAULT.withHeader(headers).withSkipHeaderRecord();

		BufferedReader br = new BufferedReader(new InputStreamReader(temp));
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		// 创建CSVFormat（header mapping）
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(headers).withSkipHeaderRecord();

		CSVParser csvFileParser = null;
		try {
			csvFileParser = new CSVParser(br, csvFileFormat);
			List<CSVRecord> records = csvFileParser.getRecords();
			for (CSVRecord record : records) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < headers.length; i++) {
					map.put(headers[i], record.get(headers[i]).trim());
				}
				list.add(map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileParser.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 写入csv列表数据
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static File writeCSV(String file, List<Map<String, String>> data, String[] headers) {
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		CSVPrinter csvPrinter = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(headers);
		try {
			csvFile = new File(file);
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"),
					1024);
			csvPrinter = new CSVPrinter(csvFileOutputStream, csvFileFormat);
			
			for(Map<String, String> map : data){
				for(String header :headers){
					csvPrinter.print(map.get(header));
				}
				csvPrinter.println();// 换行
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileOutputStream.flush();
				csvFileOutputStream.close();
				csvPrinter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}

}
