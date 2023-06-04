package com.chernova.transactional.outbox;

import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import static java.util.Objects.requireNonNull;

@Slf4j
public class YamlPropertySourceFactory
		implements PropertySourceFactory {

	@Override
	public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) {
		YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
		factory.setResources(encodedResource.getResource());

		Properties properties = factory.getObject();
		log.info("properties: {}", properties);

		return new PropertiesPropertySource(
				requireNonNull(encodedResource.getResource().getFilename()),
				requireNonNull(properties));
	}
}
