package io.warehouse13.authorizationserver.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserDto {
	private Long id;
	private String username;
	private String password;
	private Set<RoleDto> roles;
	private boolean expired;
	private boolean locked;
	private boolean credentialsExpired;
	private boolean disabled;
}
