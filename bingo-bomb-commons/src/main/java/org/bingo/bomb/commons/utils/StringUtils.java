/*
 * Copyright (C) 2013 Shanghai bingo soft Co., Ltd
 *
 * All copyrights reserved by bingo.com
 * Any copying, transferring or any other usage is prohibited.
 * Or else, Shanghai bingo possesses the right to require legal 
 * responsibilities from the violator.
 * All third-party contributions are distributed under license by
 * bingo soft Co., Ltd.
 */
package org.bingo.bomb.commons.utils;

/**
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午12:29:55
 * @since JDK 1.7	
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{
	
	/**
	 * 判断多个字符串是否为空
	 * @param args
	 * @return
	 */
	public static boolean isEmpty(String... args ){
		for(String str : args){
			if(isEmpty(str)){
				return false;
			}
		}
		return true;
	}
}
