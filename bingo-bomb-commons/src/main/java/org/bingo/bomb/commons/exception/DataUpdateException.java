package org.bingo.bomb.commons.exception;

/**
 * 数据删除异常
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:14:49
 * @since JDK 1.7
 */
public class DataUpdateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataUpdateException(String message) {
		super(message);
	}

	public DataUpdateException(Throwable cause) {
		super(cause);
	}
}
