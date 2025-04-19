package com.example.app.common.security;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.example.app.presentation.bind.ErrorInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Named
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper mapper = new ObjectMapper();

	public RestAuthenticationEntryPoint() {
		mapper.registerModule(new JavaTimeModule());
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
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

		mapper.writeValue(response.getWriter(), errorInfo);
	}
}