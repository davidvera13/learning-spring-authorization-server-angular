package io.warehouse13.authorizationserver.io.repository;

import io.warehouse13.authorizationserver.io.entity.RoleEntity;
import io.warehouse13.authorizationserver.shared.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	Optional<RoleEntity> findByRole(RoleName role);
}
