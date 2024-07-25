package io.warehouse13.authorizationserver.io.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "authorization_consents")
@IdClass(AuthorizationConsentEntity.AuthorizationConsentId.class)
@AllArgsConstructor @NoArgsConstructor  @Getter @Setter
public class AuthorizationConsentEntity {
	// composite key
	@Id
	private String registeredClientId;
	@Id
	private String principalName;
	@Column(length = 1000)
	private String authorities;
	@Getter @Setter
	public static class AuthorizationConsentId implements Serializable {
		private String registeredClientId;
		private String principalName;

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			AuthorizationConsentId that = (AuthorizationConsentId) o;
			return registeredClientId.equals(that.registeredClientId) &&
					principalName.equals(that.principalName);
		}
		@Override
		public int hashCode() {
			return Objects.hash(registeredClientId, principalName);
		}
	}
}
