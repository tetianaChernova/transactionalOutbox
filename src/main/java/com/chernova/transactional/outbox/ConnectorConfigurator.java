package com.chernova.transactional.outbox;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ConnectorConfigurator {

	private static final String CONNECTOR_CONFIG_ENDPOINT = "/connectors/source-debezium-order-events-v2/config";

	@Autowired
	private TransactionalOutboxConfigurationProperties properties;


	public void configureConnector() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String url = "http://localhost:8083" + CONNECTOR_CONFIG_ENDPOINT;

		System.out.println("TEST:" + properties.getTest());
		Map<String, Object> config = new HashMap<>();
		config.put("connector.class", "io.debezium.connector.mysql.MySqlConnector");
		config.put("database.hostname", "mysql");
		config.put("database.port", "3306");
		config.put("database.user", "debezium");
		config.put("database.password", "dbz");
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

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(config, headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			System.out.println("Connector configured successfully");
		} else {
			System.out.println("Failed to configure connector: " + response.getBody());
		}




//		// Start the connector
//		String connectorName = "source-debezium-order-events";
//		String url = connectUrl + "/connectors/" + connectorName + "/restart";
//		ResponseEntity<String> restartResponseEntity = restTemplate.postForEntity(url, null, String.class);
//		if (restartResponseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
//			logger.info("Connector was successfully started.");
//		} else {
//			logger.error("Failed to start the connector. Response status code: " + restartResponseEntity.getStatusCodeValue());
//		}
	}
}
