package com.songxinjing.flyfish.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
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

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		BufferedReader br = new BufferedReader(new InputStreamReader(temp));
		try {
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(br);
			for (CSVRecord record : records) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < headers.length; i++) {
					map.put(headers[i], record.get(headers[i]));
				}
				list.add(map);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
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
	//public static Workbook writeCSV(InputStream temp, List<Map<String, String>> data) {
		
	//}


}
