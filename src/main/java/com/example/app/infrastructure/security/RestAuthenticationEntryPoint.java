package com.example.app.infrastructure.security;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.example.app.presentation.bind.ErrorInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Named
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Inject
	public RestAuthenticationEntryPoint(final ObjectMapper objectMapper) {
		this.objectMapper = requireNonNull(objectMapper, "objectMapper shall not be null");
	}

	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException e) throws IOException {

		final LocalDateTime now = LocalDateTime.now();
		final int status = HttpStatus.UNAUTHORIZED.value();
		final String error = "Unauthorized";
		final String message = e.getMessage();
		final ErrorInfo errorInfo = new ErrorInfo(now, status, error, message);

		response.setStatus(status);
		response.setContentType("application/json");

		objectMapper.writeValue(response.getWriter(), errorInfo);
	}
}