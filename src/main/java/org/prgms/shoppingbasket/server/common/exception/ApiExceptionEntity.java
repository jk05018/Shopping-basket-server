package org.prgms.shoppingbasket.server.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/** 에러 응답 모델 **/
@Getter
@ToString
public class ApiExceptionEntity {
	private String errorCode;
	private String errorMessage;

	@Builder
	public ApiExceptionEntity(HttpStatus status, String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
