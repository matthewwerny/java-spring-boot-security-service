package com.example.app.infrastructure.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.config.BeanPostProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.inject.Named;

/**
 * Bean post processor for {@link ObjectMapper} objects from the Jackson JSON
 * marshaling library.
 * <p>
 * The purpose of this post processor is to configure any {@link ObjectMapper}
 * instances registered with the Spring context so that they include support for
 * the Java 8+ java.time temporal support.
 */
@Named
public class ObjectMapperConfigurationPostProcessor implements BeanPostProcessor {

	private static final String ISO_8601_UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	/**
	 * Post-processes the candidate beans that are {@link ObjectMapper} instances to
	 * configure them.
	 */
	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String beanName) {
		if (bean instanceof final ObjectMapper mapper) {
			final JavaTimeModule module = new JavaTimeModule();
			final DateFormat format = new SimpleDateFormat(ISO_8601_UTC_FORMAT);
			mapper.registerModule(module);
			mapper.setDateFormat(format);
		}

		return bean;
	}

}
