package io.warehouse13.authorizationserver.io.repository;

import io.warehouse13.authorizationserver.io.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);
}
