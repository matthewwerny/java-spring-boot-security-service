package com.example.app.data.model;

import static java.util.Objects.requireNonNull;

/**
 * User model entity.
 */
public class User {

	private final String username;

	private final String password;

	/**
	 * Constructors a new {@link User} instance with the username and password
	 * provided.
	 * @param username the username
	 * @param password the password
	 */
	public User(final String username, final String password) {
		this.username = requireNonNull(username, "username shall not be null");
		this.password = requireNonNull(password, "password shall not be null");
	}

	/**
	 * Get the username.
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Get the password.
	 * @return the password
	 */
	public String getPassword() {
		return password;

	}

}