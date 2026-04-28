package com.sports.events.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Global exception handler that provides consistent error responses
 * across the application. Returns JSON for API requests and renders
 * an error page for Thymeleaf (browser) requests.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors from @Valid annotated request bodies.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidationErrors(MethodArgumentNotValidException ex,
                                         HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        if (isApiRequest(request)) {
            ErrorResponse response = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Validation failed",
                    errors
            );
            return ResponseEntity.badRequest().body(response);
        }

        return buildErrorView(HttpStatus.BAD_REQUEST, "Validation failed", errors);
    }

    /**
     * Handles constraint violation errors from entity validation.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Object handleConstraintViolation(ConstraintViolationException ex,
                                            HttpServletRequest request) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();

        if (isApiRequest(request)) {
            ErrorResponse response = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Constraint violation",
                    errors
            );
            return ResponseEntity.badRequest().body(response);
        }

        return buildErrorView(HttpStatus.BAD_REQUEST, "Constraint violation", errors);
    }

    /**
     * Handles invalid number format in request parameters (e.g., non-numeric sportTypeId).
     */
    @ExceptionHandler(NumberFormatException.class)
    public Object handleNumberFormat(NumberFormatException ex,
                                     HttpServletRequest request) {
        List<String> errors = List.of("Invalid number format: " + ex.getMessage());

        if (isApiRequest(request)) {
            ErrorResponse response = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid input",
                    errors
            );
            return ResponseEntity.badRequest().body(response);
        }

        return buildErrorView(HttpStatus.BAD_REQUEST, "Invalid input", errors);
    }

    /**
     * Catch-all handler for unexpected exceptions.
     */
    @ExceptionHandler(Exception.class)
    public Object handleGenericException(Exception ex,
                                         HttpServletRequest request) {
        List<String> errors = List.of("An unexpected error occurred. Please try again later.");

        if (isApiRequest(request)) {
            ErrorResponse response = new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal server error",
                    errors
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return buildErrorView(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", errors);
    }

    /**
     * Determines if the request is an API call (expecting JSON) or a browser request.
     */
    private boolean isApiRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String accept = request.getHeader("Accept");
        return uri.startsWith("/api/") ||
               (accept != null && accept.contains("application/json"));
    }

    /**
     * Builds a Thymeleaf error view with status, message, and error details.
     */
    private ModelAndView buildErrorView(HttpStatus status, String message, List<String> errors) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("status", status.value());
        mav.addObject("message", message);
        mav.addObject("errors", errors);
        mav.setStatus(status);
        return mav;
    }
}
