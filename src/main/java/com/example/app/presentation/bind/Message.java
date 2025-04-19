package com.example.app.presentation.bind;

public class Message {

	private String body;

	public Message() {
		this(null);
	}

	public Message(final String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

}
