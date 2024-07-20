package io.warehouse13.authorizationserver.io.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.Date;
import java.util.Set;

/**
 * The following class defin es Client entity, which is used to
 * persist information mapped from the RegisteredClient domain
 * object.
 * https://docs.spring.io/spring-authorization-server/reference/guides/how-to-jpa.html
 */
@Entity
@Table(name = "clients")
@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ClientEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * client name
	 */
	private String clientName;
	/**
	 * client id
	 */
	private String clientId;
	/**
	 * client secret
	 */
	private String clientSecret;

	// we have a map of authentication methods in a string, we can use a set
	//@Column(length = 1000) private String clientAuthenticationMethods;
	/**
	 * Client authentication methods
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "authentication_methods",
			joinColumns = @JoinColumn(name = "client_id"))
	private Set<ClientAuthenticationMethod> authenticationMethods;

	//@Column(length = 1000) private String authorizationGrantTypes;
	/**
	 * Authotization grant types
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "authorization_grant_types",
			joinColumns = @JoinColumn(name = "client_id"))
	private Set<AuthorizationGrantType> authorizationGrantTypes;

	//@Column(length = 1000) private String redirectUris;
	/**
	 * list of redirect urls
	 */
	@ElementCollection(fetch = FetchType.EAGER)
 	@CollectionTable(
			name = "redirect_urls",
			joinColumns = @JoinColumn(name = "client_id"))
	private Set<String> redirectUris;

	// @Column(length = 1000) private String authorizationGrantTypes;
	/**
	 * list of scopes
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "scopes",
			joinColumns = @JoinColumn(name = "client_id"))
	private Set<String> scopes;

	/**
	 * is prof key required
	 */
	private boolean requiredProofKey;

	// private Instant clientSecretExpiresAt;
	// private Instant clientIdIssuedAt;
	// @Column(length = 1000)
	// private String postLogoutRedirectUris;
	// @Column(length = 1000) private String scopes;
	// @Column(length = 2000)
	// private String clientSettings;
	// @Column(length = 2000)
	// private String tokenSettings;

	// helper method
	public static RegisteredClient toRegisteredClient(ClientEntity client){
		RegisteredClient.Builder builder = RegisteredClient.withId(client.getClientId())
				.clientName(client.getClientName())
				.clientId(client.getClientId())
				.clientSecret(client.getClientSecret())
		 		.clientIdIssuedAt(new Date().toInstant())
				.clientAuthenticationMethods(am ->
						am.addAll(client.getAuthenticationMethods()))
				.authorizationGrantTypes(authorizationGrantTypes ->
						authorizationGrantTypes.addAll(client.getAuthorizationGrantTypes()))
				.redirectUris(redirectUrls ->
						redirectUrls.addAll(client.getRedirectUris()))
				.scopes(scope ->
						scope.addAll(client.getScopes()))
				.clientSettings(ClientSettings.builder()
						.requireProofKey(client.isRequiredProofKey())
						.build());
		return builder.build();
	}
}