package org.bingo.bomb.commons.exception;

/**
 * 数据删除异常
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:14:49
 * @since JDK 1.7
 */
public class DataFindException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataFindException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataFindException(String message) {
		super(message);
	}

	public DataFindException(Throwable cause) {
		super(cause);
	}
}
