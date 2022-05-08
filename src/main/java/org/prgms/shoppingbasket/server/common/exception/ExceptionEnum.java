package org.prgms.shoppingbasket.server.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

/** 에러 Enum Class **/
@Getter
@ToString
public enum ExceptionEnum {
	BINDING_EXCEPTION(HttpStatus.BAD_REQUEST,"E0000"),
	RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0003");

	private final HttpStatus status;
	private final String code;
	private String message;

	ExceptionEnum(HttpStatus status, String code) {
		this.status = status;
		this.code = code;
	}

	public ExceptionEnum withMessage(String message){
		this.message = message;
		return this;
	}
}
