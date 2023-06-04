package com.chernova.transactional.outbox;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ConnectorConfigurator {

	private static final String CONNECTOR_CONFIG_ENDPOINT = "/connectors/source-debezium-order-events-v3/config";

	@Autowired
	private TransactionalOutboxConfigurationProperties properties;


	public void configureConnector() {
		Map<String, Object> config = initConnectorConfigMap();

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(config, headers);

		ResponseEntity<String> response = restTemplate.exchange
				("http://localhost:8083" + CONNECTOR_CONFIG_ENDPOINT,
						HttpMethod.PUT, request,
						String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			log.info("Connector configured successfully");
		} else {
			log.info("Failed to configure connector: exception {}", response.getBody());
		}
	}

	private Map<String, Object> initConnectorConfigMap() {
		Map<String, Object> config = new HashMap<>();
		config.put("connector.class", "io.debezium.connector.mysql.MySqlConnector");
		config.put("database.hostname", properties.getDatabase().getHostname());
		config.put("database.port", properties.getDatabase().getPort());
		config.put("database.user", properties.getDatabase().getUser());
		config.put("database.password", properties.getDatabase().getPassword());
		config.put("database.server.id", "42");
		config.put("database.server.name", "asgard");
		config.put("table.whitelist", "demo.outboxevent");
		config.put("database.history.kafka.bootstrap.servers", "broker:9092");
		config.put("database.history.kafka.topic", "dbhistory.demo");
		config.put("decimal.handling.mode", "double");
		config.put("include.schema.changes", "true");
		config.put("transforms", "unwrap,dropTopicPrefix");
		config.put("transforms.unwrap.type", "io.debezium.transforms.ExtractNewRecordState");
		config.put("transforms.dropTopicPrefix.type", "org.apache.kafka.connect.transforms.RegexRouter");
		config.put("transforms.dropTopicPrefix.regex", "asgard.demo.(.*)");
		config.put("transforms.dropTopicPrefix.replacement", "$1");
		config.put("key.converter", "io.confluent.connect.avro.AvroConverter");
		config.put("key.converter.schema.registry.url", "http://schema-registry:8081");
		config.put("value.converter", "io.confluent.connect.avro.AvroConverter");
		config.put("value.converter.schema.registry.url", "http://schema-registry:8081");
		return config;
	}
}
