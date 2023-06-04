package com.chernova.transactional.outbox;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("com.chernova.transactional.outbox")
@ConfigurationPropertiesScan("com.chernova.transactional.outbox")
@EnableJpaRepositories
@EntityScan("com.chernova.transactional.outbox.data")
@ConditionalOnProperty(value = TransactionalOutboxConfiguration.TRANSACTIONAL_OUTBOX_ENABLED, havingValue = "true")
@Slf4j
public class TransactionalOutboxConfiguration
		implements BeanFactoryPostProcessor {
	protected static final String TRANSACTIONAL_OUTBOX_ENABLED = "transactionaloutbox.enabled";

	@Override
	@SneakyThrows
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) {
		log.info("TransactionalOutboxConfiguration bean was initialized");
	}

}
