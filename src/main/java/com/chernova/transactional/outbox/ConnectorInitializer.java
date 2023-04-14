package com.chernova.transactional.outbox;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class ConnectorInitializer {

	@Autowired
	private ConnectorConfigurator connectorConfigurator;

	public ConnectorInitializer(final ConnectorConfigurator connectorConfigurator) {
		this.connectorConfigurator = connectorConfigurator;
		this.connectorConfigurator.configureConnector();
	}
}
