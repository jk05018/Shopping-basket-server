package org.prgms.shoppingbasket.server.common.exception;

/**
 * JDBC를 사용할때 발생하는 예외들을 처리하는 사용자 Exception
 */
public class DatabaseException extends RuntimeException{
	public DatabaseException() {
		super();
	}

	public DatabaseException(String message) {
		super(message);
	}

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseException(Throwable cause) {
		super(cause);
	}

	protected DatabaseException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
