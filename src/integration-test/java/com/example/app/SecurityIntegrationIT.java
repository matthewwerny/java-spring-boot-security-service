package com.example.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.app.presentation.bind.ErrorInfo;
import com.example.app.presentation.bind.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityIntegrationIT {

	@LocalServerPort
	private int port;

	/**
	 * Test the public endpoint without authentication.
	 * @throws Exception
	 */
	@Test
	public void testPublicEndpointWithoutAuth() throws Exception {
		final String url = "http://localhost:" + port + "/public";
		final HttpHeaders headers = new HttpHeaders();
		final HttpEntity<Void> request = new HttpEntity<>(headers);

		final ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		final Message entity = createMapper().readValue(response.getBody(), Message.class);

		assertNotNull(entity);
		assertEquals("This is a public endpoint.", entity.getBody());
	}

	/**
	 * Test the private endpoint without authentication.
	 * @throws Exception
	 */
	@Test
	public void testPrivateEndpointWithoutAuth() throws Exception {
		final String url = "http://localhost:" + port + "/private";
		final HttpHeaders headers = new HttpHeaders();
		final HttpEntity<Void> request = new HttpEntity<>(headers);

		HttpClientErrorException thrown = null;

		try {
			new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);
		} catch (final HttpClientErrorException e) {
			thrown = e;
		}

		assertNotNull(thrown);
		assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
		final ErrorInfo errorEntity = createMapper().readValue(thrown.getResponseBodyAsString(), ErrorInfo.class);

		assertNotNull(errorEntity);
		assertEquals(HttpStatus.UNAUTHORIZED.value(), errorEntity.getStatus());
		assertEquals("Unauthorized", errorEntity.getError());
		assertEquals("Full authentication is required to access this resource", errorEntity.getMessage());
	}

	/**
	 * Test the private endpoint with valid Basic Authentication credentials.
	 * @throws Exception
	 */
	@Test
	public void testPrivateEndpointWithValidBasicAuth() throws Exception {
		final String url = "http://localhost:" + port + "/private";
		final String username = "admin";
		final String password = "admin123";

		final String encodedCredentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Basic " + encodedCredentials);

		final HttpEntity<Void> request = new HttpEntity<>(headers);

		final ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		final Message entity = createMapper().readValue(response.getBody(), Message.class);

		assertNotNull(entity);
		assertEquals("This is a protected endpoint.", entity.getBody());
	}

	/**
	 * Test the private endpoint with invalid Basic Authentication credentials.
	 * @throws Exception
	 */
	@Test
	public void testPrivateEndpointWithInvalidBasicAuth() throws Exception {
		final String url = "http://localhost:" + port + "/private";
		final String username = "admin";
		final String password = "this is invalid";

		final String encodedCredentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Basic " + encodedCredentials);

		final HttpEntity<Void> request = new HttpEntity<>(headers);

		HttpClientErrorException thrown = null;

		try {
			new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);
		} catch (final HttpClientErrorException e) {
			thrown = e;
		}

		assertNotNull(thrown);
		assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
		final ErrorInfo errorEntity = createMapper().readValue(thrown.getResponseBodyAsString(), ErrorInfo.class);

		assertNotNull(errorEntity);
		assertEquals(HttpStatus.UNAUTHORIZED.value(), errorEntity.getStatus());
		assertEquals("Unauthorized", errorEntity.getError());
		assertEquals("Invalid username or password", errorEntity.getMessage());
	}

	private ObjectMapper createMapper() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

		return mapper;
	}

}