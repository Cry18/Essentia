package com.essentia.essentiauser.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleValidationErrors (MethodArgumentNotValidException e) {
		
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

	@ExceptionHandler(ForbiddenActionException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorResponse handleForbiddenActionException(Exception e) {
		ErrorResponse response = new ErrorResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setError("Forbidden");
		response.setMessage(e.getMessage());
		return response;
	}

	@ExceptionHandler(DuplicateReviewException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
    public ErrorResponse handleDuplicateReviewException(Exception e) {
        ErrorResponse response = new ErrorResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setError("Conflict, duplicate Review");
		response.setMessage(e.getMessage());
		return response;
    }
}
