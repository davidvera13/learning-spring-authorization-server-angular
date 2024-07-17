package io.warehouse13.authorizationserver.shared.dto;

import io.warehouse13.authorizationserver.shared.enums.RoleName;
import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class RoleDto {
	private Long id;
	private RoleName role;
}
