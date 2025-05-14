package com.essentia.essentiaadministration.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleValidationErrors (MethodArgumentNotValidException e) {
		logger.error("Validation error: {}", e.getMessage(), e);
		
		ErrorResponse response = new ErrorResponse();
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		List<Violation>violations = new ArrayList<>();

		for(FieldError fieldError : fieldErrors) {
			Violation violation = new Violation(fieldError.getField(), fieldError.getDefaultMessage());
			violations.add(violation);
		}
		response.setViolations(violations);
		response.setTimestamp(LocalDateTime.now());
		response.setError("Errore di validazione");
		response.setMessage("Uno o più campi non rispettano i criteri di validazione");
		return response;
		
	}

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