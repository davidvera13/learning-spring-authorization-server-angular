package io.warehouse13.authorizationserver.service.impl;

import io.warehouse13.authorizationserver.io.entity.ClientEntity;
import io.warehouse13.authorizationserver.io.repository.ClientRepository;
import io.warehouse13.authorizationserver.service.ClientService;
import io.warehouse13.authorizationserver.shared.dto.ClientDto;
import io.warehouse13.authorizationserver.shared.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
	private final ClientRepository repository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public void save(RegisteredClient registeredClient) {
		// not yet implemented
	}

	@Override
	public RegisteredClient findById(String id) {
		log.info("## ClientServiceImpl -> findById() called");
		ClientEntity client = repository.findByClientId(id)
				.orElseThrow(()-> new RuntimeException("client not found"));
		return ClientEntity.toRegisteredClient(client);
	}

	@Override
	public RegisteredClient findByClientId(String clientId) {
		log.info("## ClientServiceImpl -> findByClientId() called");
		ClientEntity client = repository.findByClientId(clientId)
				.orElseThrow(()-> new RuntimeException("client not found"));
		return ClientEntity.toRegisteredClient(client);
	}

	@Override
	public MessageDto create(ClientDto dto){
		log.info("## ClientServiceImpl -> create() called");
		ClientEntity client = clientFromDto(dto);
		repository.save(client);
		return new MessageDto("client " + client.getClientId() + " saved");
	}

	private ClientEntity clientFromDto(ClientDto client) {
		log.info("## ClientServiceImpl -> clientFromDto() called");
		return ClientEntity.builder()
				.clientName(client.getClientName())
				.clientId(client.getClientId())
				.clientSecret(passwordEncoder.encode(client.getClientSecret()))
				.authenticationMethods(client.getAuthenticationMethods())
				.authorizationGrantTypes(client.getAuthorizationGrantTypes())
				.redirectUris(client.getRedirectUris())
				.scopes(client.getScopes())
				.requiredProofKey(client.isRequireProofKey())
				.build();
	}
}