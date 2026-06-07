package com.essentia.essentiaadministration.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
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

    // ── 400 Bad Request ───────────────────────────────────────────────────────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleValidationErrors(MethodArgumentNotValidException e, HttpServletRequest request) {
        logger.warn("Validation error on {}: {}", request.getRequestURI(), e.getMessage());

        List<Violation> violations = new ArrayList<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            violations.add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        ErrorResponse response = new ErrorResponse();
        response.setStatus(400);
        response.setTimestamp(LocalDateTime.now());
        response.setError("Validation error");
        response.setMessage("One or more fields failed validation");
        response.setPath(request.getRequestURI());
        response.setViolations(violations);
        return response;
    }

    // ── 403 Forbidden ─────────────────────────────────────────────────────────

    @ExceptionHandler(ForbiddenActionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleForbiddenAction(ForbiddenActionException e, HttpServletRequest request) {
        logger.warn("Forbidden action on {}: {}", request.getRequestURI(), e.getMessage());

        ErrorResponse response = new ErrorResponse();
        response.setStatus(403);
        response.setTimestamp(LocalDateTime.now());
        response.setError("Forbidden");
        response.setMessage(e.getMessage());
        response.setPath(request.getRequestURI());
        return response;
    }

    // ── 409 Conflict — entity still in use ───────────────────────────────────

    @ExceptionHandler(EntityInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleEntityInUse(EntityInUseException e, HttpServletRequest request) {
        logger.warn("Entity in use on {}: {}", request.getRequestURI(), e.getMessage());

        ErrorResponse response = new ErrorResponse();
        response.setStatus(409);
        response.setTimestamp(LocalDateTime.now());
        response.setError("Entity in use");
        response.setMessage(e.getMessage());
        response.setPath(request.getRequestURI());
        return response;
    }

    // ── 404 Not Found ─────────────────────────────────────────────────────────

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleResourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        logger.warn("Resource not found on {}: {}", request.getRequestURI(), e.getMessage());

        ErrorResponse response = new ErrorResponse();
        response.setStatus(404);
        response.setTimestamp(LocalDateTime.now());
        response.setError("Resource not found");
        response.setMessage(e.getMessage());
        response.setPath(request.getRequestURI());
        return response;
    }

    // ── 500 Internal Server Error ──────────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleGenericException(Exception e, HttpServletRequest request) {
        logger.error("Unexpected error on {}: {}", request.getRequestURI(), e.getMessage(), e);

        ErrorResponse response = new ErrorResponse();
        response.setStatus(500);
        response.setTimestamp(LocalDateTime.now());
        response.setError("Internal server error");
        response.setMessage("An unexpected error occurred. Please try again later.");
        response.setPath(request.getRequestURI());
        return response;
    }
}
