package io.warehouse13.authorizationserver.bootstrap;

import io.warehouse13.authorizationserver.io.entity.RoleEntity;
import io.warehouse13.authorizationserver.io.entity.UserEntity;
import io.warehouse13.authorizationserver.io.repository.RoleRepository;
import io.warehouse13.authorizationserver.io.repository.UserRepository;
import io.warehouse13.authorizationserver.shared.enums.RoleName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRoleInit {
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	@Transactional
	@EventListener
	public void onApplicationEvent(ApplicationReadyEvent event) {
		log.warn("# onApplicationEvent(event) called - @EventListener");
		log.info("event : " + event.toString());
		RoleEntity userRole = createRole(RoleName.ROLE_USER);
		RoleEntity adminRole = createRole(RoleName.ROLE_ADMIN);

		if(userRepository.findByUsername("admin").isEmpty()) {
			UserEntity admin = UserEntity.builder()
					.username("admin")
					.password(bCryptPasswordEncoder.encode("admin"))
					.roles(Set.of(userRole, adminRole))
					.build();
			userRepository.save(admin);
		}
		if(userRepository.findByUsername("user").isEmpty()) {
			UserEntity user = UserEntity.builder()
					.username("user")
					.password(bCryptPasswordEncoder.encode("user"))
					.roles(Set.of(userRole))
					.build();
			userRepository.save(user);
		}
	}

	@Transactional
	public RoleEntity createRole(
			RoleName roleName) {
		Optional<RoleEntity> stored = roleRepository.findByRole(roleName);
		RoleEntity roleEntity = null;
		if(stored.isEmpty()) {
			roleEntity = RoleEntity.builder().role(roleName).build();
			roleRepository.save(roleEntity);
		} else {
			roleEntity = stored.get();
		}
		return roleEntity;
	}

	public void createUsers() {

	}
}
