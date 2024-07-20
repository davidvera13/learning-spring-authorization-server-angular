package io.warehouse13.authorizationserver.ui.controllers;


import io.warehouse13.authorizationserver.io.entity.ClientEntity;
import io.warehouse13.authorizationserver.service.ClientService;
import io.warehouse13.authorizationserver.service.UserService;
import io.warehouse13.authorizationserver.shared.dto.ClientDto;
import io.warehouse13.authorizationserver.shared.dto.MessageDto;
import io.warehouse13.authorizationserver.ui.domains.request.ClientCreateRequest;
import io.warehouse13.authorizationserver.ui.domains.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
	private final ModelMapper modelMapper;
	private final ClientService clientService;

	@PostMapping(
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
	)
	public ResponseEntity<MessageResponse> createClient(
			@RequestBody ClientCreateRequest clientCreateRequest) {
		ClientDto clientDto = clientFromRequest(clientCreateRequest);
		MessageDto messageDto = clientService.create(clientDto);
				MessageResponse response = modelMapper
				.map(messageDto, MessageResponse.class);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}


	private ClientDto clientFromRequest(ClientCreateRequest client) {
		log.info("## ClientController -> clientFromRequest() called");
		return ClientDto.builder()
				.clientName(client.getClientName())
				.clientId(client.getClientId())
				.clientSecret(client.getClientSecret())
				.authenticationMethods(client.getAuthenticationMethods())
				.authorizationGrantTypes(client.getAuthorizationGrantTypes())
				.redirectUris(client.getRedirectUris())
				.scopes(client.getScopes())
				.requireProofKey(client.isRequireProofKey())
				.build();
	}
}
