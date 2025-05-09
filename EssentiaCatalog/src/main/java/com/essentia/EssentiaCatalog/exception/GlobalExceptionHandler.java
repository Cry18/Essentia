package com.essentia.essentiacatalog.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponse handleResourceNotFoundErrors (ResourceNotFoundException e) {
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
        ErrorResponse response = new ErrorResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setError("Internal Server Error");
		response.setMessage(e.getMessage());
		return response;
    }

}
