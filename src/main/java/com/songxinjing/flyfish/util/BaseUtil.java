package com.songxinjing.flyfish.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseUtil {
	
	protected static final Logger logger = LoggerFactory.getLogger(BaseUtil.class);
	
	private static String charOfSku = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static String changeSku(String sku , int move) {
		String newSku = "";
		for( char c : sku.toCharArray()){
			int index = charOfSku.indexOf(c);
			if(index >= 0){
				int newIndex = index + move;
				if(newIndex >= charOfSku.length()){
					newIndex = newIndex - sku.length();
				}
				newSku = newSku + charOfSku.charAt(newIndex);
			}else{
				newSku = newSku + c;
			}
		}
		return newSku;
	}
	
	public static void main(String[] args){
		changeSku("BELT-A002",1);
	}

}
