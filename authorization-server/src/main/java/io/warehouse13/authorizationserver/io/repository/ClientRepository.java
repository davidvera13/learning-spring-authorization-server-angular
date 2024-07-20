package io.warehouse13.authorizationserver.io.repository;

import io.warehouse13.authorizationserver.io.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
	Optional<ClientEntity> findByClientId(String clientId);
}