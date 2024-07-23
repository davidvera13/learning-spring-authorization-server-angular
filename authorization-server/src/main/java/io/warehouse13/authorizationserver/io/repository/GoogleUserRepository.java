package io.warehouse13.authorizationserver.io.repository;

import io.warehouse13.authorizationserver.io.entity.GoogleUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoogleUserRepository extends JpaRepository<GoogleUserEntity, Long> {
	Optional<GoogleUserEntity> findByEmail(String email);
}