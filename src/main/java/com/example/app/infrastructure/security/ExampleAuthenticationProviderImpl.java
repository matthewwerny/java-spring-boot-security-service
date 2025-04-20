package com.example.app.infrastructure.security;

import java.util.List;
import java.util.Objects;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.app.data.dao.UserDao;
import com.example.app.data.model.User;

import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Example implementation of the {@link AuthenticationProvider} interface. It,
 * like the rest of the project, is implemented to illustrate the idea and
 * shouldn't be considered complete or audited for production use.
 */
@Named
public class ExampleAuthenticationProviderImpl implements AuthenticationProvider {

	private final UserDao userDao;

	/**
	 * Constructs a new {@link ExampleAuthenticationProviderImpl} instance with a
	 * provider userDao.
	 * @param userDao User DAO instance
	 */
	@Inject
	public ExampleAuthenticationProviderImpl(final UserDao userDao) {
		this.userDao = Objects.requireNonNull(userDao, "userDao shall not be null");
	}

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

		final String username = authentication.getName();
		final String password = authentication.getCredentials().toString();

		final User user = userDao.findByUsername(username);

		if (user == null || !user.getPassword().equals(password)) {
			throw new BadCredentialsException("Invalid username or password");
		}

		final List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

		return new UsernamePasswordAuthenticationToken(username, password, authorities);
	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
