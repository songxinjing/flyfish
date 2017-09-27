package com.songxinjing.flyfish.csv;

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

public class CSVUtil {

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
