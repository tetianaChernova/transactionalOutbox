package com.chernova.transactional.outbox;

import lombok.SneakyThrows;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
@ComponentScan
//@ComponentScan("com.chernova.transactional.outbox")
@ConfigurationPropertiesScan("com.chernova.transactional.outbox")
@ConditionalOnProperty(value = TransactionalOutboxConfiguration.TRANSACTIONAL_OUTBOX_ENABLED, havingValue = "true")
//@Slf4j
//@EnableAspectJAutoProxy
public class TransactionalOutboxConfiguration
		implements BeanFactoryPostProcessor {
	protected static final String TRANSACTIONAL_OUTBOX_ENABLED = "transactionaloutbox.enabled";

	@Override
	@SneakyThrows
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) {
		System.out.println("Hello there from the TransactionalOutboxConfiguration bean");
//		connectorConfigurator.configureConnector();
	}

}
