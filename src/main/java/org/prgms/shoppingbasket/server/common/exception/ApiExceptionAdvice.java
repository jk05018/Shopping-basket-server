package org.prgms.shoppingbasket.server.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

	@ExceptionHandler({ApiException.class})
	public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final ApiException e) {
		return ResponseEntity
			.status(e.getError().getStatus())
			.body(ApiExceptionEntity.builder()
				.errorCode(e.getError().getCode())
				.errorMessage(e.getError().getMessage())
				.build());
	}

	@ExceptionHandler({RuntimeException.class})
	public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final RuntimeException e) {
		return ResponseEntity
			.status(ExceptionEnum.RUNTIME_EXCEPTION.getStatus())
			.body(ApiExceptionEntity.builder()
				.errorCode(ExceptionEnum.RUNTIME_EXCEPTION.getCode())
				.errorMessage(e.getMessage())
				.build());
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final Exception e) {
		return ResponseEntity
			.status(ExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
			.body(ApiExceptionEntity.builder()
				.errorCode(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
				.errorMessage(e.getMessage())
				.build());
	}

}
