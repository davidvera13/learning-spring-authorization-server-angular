package io.warehouse13.authorizationserver.io.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserEntity implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleEntity> roles;

	private boolean expired;
	private boolean locked;
	private boolean credentialsExpired;
	private boolean disabled;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	@Override
	public boolean isEnabled() {
		return !disabled;
	}
}
