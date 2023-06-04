package com.chernova.transactional.outbox;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "transactionaloutbox")
@PropertySource(value = "classpath:application.yaml", factory = YamlPropertySourceFactory.class)
public class TransactionalOutboxConfigurationProperties {
	private String connectorClass;
	private Database database;
	private String databaseHistoryKafkaBootstrapServers;
	private String databaseHistoryKafkaTopic;
	private String keyConverterSchemaRegistryUrl;
	private String valueConverterSchemaRegistryUrl;

	@Data
	public static class Database {
		private String hostname;
		private String port;
		private String user;
		private String password;
	}
}
