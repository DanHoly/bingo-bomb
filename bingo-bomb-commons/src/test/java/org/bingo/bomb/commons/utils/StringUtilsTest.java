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

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午12:35:59
 * @since JDK 1.7	
 */
public class StringUtilsTest {
	
	private static final Logger logger = Logger.getLogger(ObjectId.class);
	
	@Test
	public void isEmpty() {
		boolean result  = StringUtils.isEmpty("null", "");
		logger.info("isEmpty:" + result);
	}

}
