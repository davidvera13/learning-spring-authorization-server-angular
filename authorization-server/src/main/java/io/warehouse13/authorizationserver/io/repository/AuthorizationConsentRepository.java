package io.warehouse13.authorizationserver.io.repository;

import io.warehouse13.authorizationserver.io.entity.AuthorizationConsentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorizationConsentRepository
		extends JpaRepository<AuthorizationConsentEntity, AuthorizationConsentEntity.AuthorizationConsentId> {
	Optional<AuthorizationConsentEntity> findByRegisteredClientIdAndPrincipalName(
			String registeredClientId, String principalName);
	void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
