package io.warehouse13.authorizationserver.service;

import io.warehouse13.authorizationserver.shared.dto.ClientDto;
import io.warehouse13.authorizationserver.shared.dto.MessageDto;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

public interface ClientService extends RegisteredClientRepository {
	MessageDto create(ClientDto dto);
}



