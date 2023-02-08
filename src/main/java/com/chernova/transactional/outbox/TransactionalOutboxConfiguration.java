package com.chernova.transactional.outbox;

import com.chernova.transactional.outbox.aspect.OutboxAspect;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ConditionalOnProperty(value = TransactionalOutboxConfiguration.TRANSACTIONAL_OUTBOX_ENABLED, havingValue = "true")
@NoArgsConstructor
@Slf4j
@ComponentScan
@EnableAspectJAutoProxy
public class TransactionalOutboxConfiguration
		implements BeanFactoryPostProcessor {
	protected static final String TRANSACTIONAL_OUTBOX_ENABLED = "transactional-outbox.enabled";

	@Override
	@SneakyThrows
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) {
		System.out.println("Hello there from the TransactionalOutboxConfiguration bean");
		System.out.println(beanFactory.getBean(OutboxAspect.class));
	}

}
