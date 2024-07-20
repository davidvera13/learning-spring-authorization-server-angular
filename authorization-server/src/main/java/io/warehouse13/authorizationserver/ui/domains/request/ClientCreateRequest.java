package io.warehouse13.authorizationserver.ui.domains.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Set;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ClientCreateRequest {
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
	/**
	 * Client authentication methods
	 */
	private Set<ClientAuthenticationMethod> authenticationMethods;
	/**
	 * Authotization grant types
	 */
	private Set<AuthorizationGrantType> authorizationGrantTypes;
	/**
	 * list of redirect urls
	 */
	private Set<String> redirectUris;
	/**
	 * list of scopes
	 */
	private Set<String> scopes;
	/**
	 * is prof key required
	 */
	private boolean requireProofKey;
}
