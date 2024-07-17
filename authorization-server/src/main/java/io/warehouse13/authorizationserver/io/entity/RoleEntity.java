package io.warehouse13.authorizationserver.io.entity;

import io.warehouse13.authorizationserver.shared.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class RoleEntity implements GrantedAuthority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private RoleName role;

	@Override
	public String getAuthority() {
		return role.name();
	}
}
