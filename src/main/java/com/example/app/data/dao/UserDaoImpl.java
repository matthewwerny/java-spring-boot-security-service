package com.example.app.data.dao;

import java.util.HashMap;
import java.util.Map;

import com.example.app.data.model.User;

import jakarta.inject.Named;

/**
 * Implementation of the {@link UserDao} interface. This class simulates a data
 * access object for User entities.
 */
@Named
public class UserDaoImpl implements UserDao {

	private final Map<String, User> users = new HashMap<>();

	/**
	 * Constructs a new {@link UserDaoImpl} instance.
	 */
	public UserDaoImpl() {
		users.put("admin", new User("admin", "admin123"));
		users.put("user", new User("user", "user123"));
	}

	@Override
	public User findByUsername(final String username) {
		return (username == null) ? null : users.get(username);
	}

}
