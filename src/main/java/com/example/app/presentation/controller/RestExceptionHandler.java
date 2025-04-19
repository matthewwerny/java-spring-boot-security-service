package com.example.app.presentation.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.app.presentation.bind.ErrorInfo;

/**
 * Global exception handler for REST API. This class handles exceptions thrown
 * by the application and returns appropriate HTTP responses. Please note that
 * this is a simplified example. Things such as exposing exception details,
 * logging, and security should be handled with care and not exposed to the
 * client in a production application.
 */
@ControllerAdvice
public class RestExceptionHandler {

	/**
	 * Handles BadCredentialsException.
	 * @param e the exception
	 * @return the response entity
	 */
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentials(final BadCredentialsException e) {
		final LocalDateTime now = LocalDateTime.now();
		final int status = HttpStatus.UNAUTHORIZED.value();
		final String error = "Unauthorized";
		final String message = e.getMessage();
		final ErrorInfo errorInfo = new ErrorInfo(now, status, error, message);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorInfo);
	}

	/**
	 * Handles all other exceptions.
	 * @param e the exception
	 * @return the response entity
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleOtherExceptions(final Exception e) {
		final LocalDateTime now = LocalDateTime.now();
		final int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
		final String error = "Internal Server Error";
		final String message = e.getMessage();
		final ErrorInfo errorInfo = new ErrorInfo(now, status, error, message);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorInfo);
	}

}