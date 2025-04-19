package com.example.app.presentation.bind;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ErrorInfo bind class for representing error responses.
 */
public class ErrorInfo {

	private final LocalDateTime timestamp;

	private final int status;

	private final String error;

	private final String message;

	/**
	 * Default constructor.
	 */
	public ErrorInfo() {
		this(null, 0, null, null);
	}

	/**
	 * Constructors a new {@link ErrorInfo} instance.
	 * @param timestamp Tinestamp of when the error occurred
	 * @param status    HTTP status code
	 * @param error     Error
	 * @param message   Error message details
	 */
	public ErrorInfo(final LocalDateTime timestamp, final int status, final String error, final String message) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
	}

	/**
	 * Gets the timestamp.
	 * @return the timestamp
	 */
	@JsonProperty("timestamp")
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * Gets the status.
	 * @return the status
	 */
	@JsonProperty("status")
	public int getStatus() {
		return status;
	}

	/**
	 * Gets the error.
	 * @return the error
	 */
	@JsonProperty("error")
	public String getError() {
		return error;
	}

	/**
	 * Gets the message.
	 * @return the message
	 */
	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

}
