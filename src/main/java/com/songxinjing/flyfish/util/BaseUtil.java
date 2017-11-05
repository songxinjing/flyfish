package com.songxinjing.flyfish.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseUtil {
	
	protected static final Logger logger = LoggerFactory.getLogger(BaseUtil.class);
	
	private static String charOfSku = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static String changeSku(String sku , int move) {
		String newSku = "";
		for( char c : sku.toCharArray()){
			int index = charOfSku.indexOf(c);
			if(index >= 0){
				int m1 = move/26;
				int m2 = move%26;
				String str1 = "";
				String str2 = "";
				if(m1 > 0){
					int newIndex1 = index + m1;
					if(newIndex1 >= charOfSku.length()){
						newIndex1 = newIndex1 - sku.length();
					}
					str1 = charOfSku.charAt(newIndex1) + "";
				}
				int newIndex2 = index + m2;
				if(newIndex2 >= charOfSku.length()){
					newIndex2 = newIndex2 - sku.length();
				}
				str2 = charOfSku.charAt(newIndex2) + "";
				newSku = newSku + str1 + str2;
			}else{
				newSku = newSku + c;
			}
		}
		return newSku;
	}
	
	public static void main(String[] args){
		System.out.println(changeSku("BELT-A002*4",56));
	}

}
