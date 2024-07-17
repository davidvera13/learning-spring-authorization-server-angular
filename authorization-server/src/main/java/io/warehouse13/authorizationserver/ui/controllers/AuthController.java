package io.warehouse13.authorizationserver.ui.controllers;


import io.warehouse13.authorizationserver.service.UserService;
import io.warehouse13.authorizationserver.shared.dto.MessageDto;
import io.warehouse13.authorizationserver.shared.dto.RoleDto;
import io.warehouse13.authorizationserver.shared.dto.UserDto;
import io.warehouse13.authorizationserver.shared.enums.RoleName;
import io.warehouse13.authorizationserver.ui.domains.request.UserCreateRequest;
import io.warehouse13.authorizationserver.ui.domains.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final ModelMapper modelMapper;
	private final UserService userService;

	@PostMapping(
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
	)
	public ResponseEntity<MessageResponse> createUser(@RequestBody UserCreateRequest userCreateRequest) {
		UserDto userDto = modelMapper.map(userCreateRequest, UserDto.class);
		userDto.setRoles(Set.of(RoleDto.builder().role(RoleName.ROLE_USER).build()));
		MessageDto messageDto = userService.createUser(userDto);
		MessageResponse response = modelMapper.map(messageDto, MessageResponse.class);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
