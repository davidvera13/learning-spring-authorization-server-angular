package io.warehouse13.authorizationserver.service;

import io.warehouse13.authorizationserver.shared.dto.MessageDto;
import io.warehouse13.authorizationserver.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	MessageDto createUser(UserDto userDto);
}
