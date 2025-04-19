package com.example.app.presentation.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.presentation.bind.Message;

@RestController()
public class RootController {

	@GetMapping(value = "/public", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> publicEndpoint() {
		return ResponseEntity.ok(new Message("This is a public endpoint."));
	}

	@GetMapping(value = "/private", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> privateEndpoint() {
		return ResponseEntity.ok(new Message("This is a protected endpoint."));
	}

}