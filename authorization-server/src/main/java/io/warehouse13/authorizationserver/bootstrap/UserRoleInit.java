package io.warehouse13.authorizationserver.bootstrap;

import io.warehouse13.authorizationserver.io.entity.RoleEntity;
import io.warehouse13.authorizationserver.io.repository.RoleRepository;
import io.warehouse13.authorizationserver.shared.enums.RoleName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRoleInit {
	private final RoleRepository roleRepository;

	@EventListener
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent event) {
		log.warn("# onApplicationEvent(event) called - @EventListener");
		log.info("event : " + event.toString());

		createRole(RoleName.ROLE_USER);
		createRole(RoleName.ROLE_ADMIN);
	}

	@Transactional
	public void createRole(
			RoleName roleName) {

		Optional<RoleEntity> stored = roleRepository.findByRole(roleName);
		RoleEntity roleEntity = null;
		if(stored.isEmpty()) {
			roleEntity = RoleEntity.builder().role(roleName).build();
			roleRepository.save(roleEntity);
		}
	}
}
