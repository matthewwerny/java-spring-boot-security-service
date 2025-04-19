package com.example.app.data.dao;

import com.example.app.data.model.User;

/**
 * Data Access Object for User entity.
 */
public interface UserDao {

	/**
	 * Finds a user by their username.
	 * @param username the username of the user
	 * @return the User object if found, null otherwise
	 */
	User findByUsername(String username);

}
