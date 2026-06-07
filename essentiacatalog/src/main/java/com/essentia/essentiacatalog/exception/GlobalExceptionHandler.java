package com.essentia.essentiacatalog.exception;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);


	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponse handleResourceNotFoundErrors (ResourceNotFoundException e) {
		logger.warn("Resource not found: {}", e.getMessage());

		
		ErrorResponse response = new ErrorResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setError("Risorsa non trovata");
		response.setMessage(e.getMessage());
		return response;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
    public ErrorResponse handleGenericException(Exception e) {
		logger.error("Unexpected error: {}", e.getMessage(), e);

        ErrorResponse response = new ErrorResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setError("Internal Server Error");
		response.setMessage(e.getMessage());
		return response;
    }

}
