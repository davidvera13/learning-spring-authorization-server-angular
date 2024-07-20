package io.warehouse13.authorizationserver.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import io.warehouse13.authorizationserver.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Basic security configuration :
 * <a href="https://docs.spring.io/spring-authorization-server/reference/getting-started.html">
 *     https://docs.spring.io/spring-authorization-server/reference/getting-started.html
 * </a>
 * After startup:
 * Open <a href="http://localhost:9000/.well-known/oauth-authorization-server">
 *     http://localhost:9000/.well-known/oauth-authorization-server</a> to display endpoints
 * After configuration:
 * Open <a href="https://oauthdebugger.com/debug">https://oauthdebugger.com/debug</a>
 * And click on start over
 */
@Configuration
@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final ClientService clientService;
	private final PasswordEncoder passwordEncoder;

	/**
	 * A Spring Security filter chain for the Protocol Endpoints.
	 * @param http httpSecurity configuration
	 * @return Security filter chain bean
	 * @throws Exception exception
	 */
	@Bean
	@Order(1)
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
			throws Exception {
		log.info("### SecurityConfig -> authorizationServerSecurityFilterChain called");
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
				// Enable OpenID Connect 1.0
				.oidc(Customizer.withDefaults());
		return http
				// Redirect to the login page when not authenticated from the authorization endpoint
				.exceptionHandling((exceptions) -> exceptions
						.authenticationEntryPoint(
								new LoginUrlAuthenticationEntryPoint("/login")))
				// Accept access tokens for User Info and/or Client Registration
				.oauth2ResourceServer((resourceServer) -> resourceServer
						.jwt(Customizer.withDefaults())).build();

	}

	/**
	 * A Spring Security filter chain for authentication.
	 * @param http httpSecurity configuration
	 * @return Security filter chain bean
	 * @throws Exception exception
	 */
	@Bean
	@Order(2)
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
			throws Exception {
		log.info("### SecurityConfig -> defaultSecurityFilterChain called");
		return http
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers(HttpMethod.POST, "api/v1/auth").permitAll()
						.requestMatchers("/api/v1/clients").permitAll()
						.anyRequest().authenticated())
				// Form login handles the redirect to the login page from the authorization server filter chain
				.formLogin(Customizer.withDefaults())
				.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer
						.ignoringRequestMatchers("api/v1/auth", "api/v1/clients")
						.disable())
				.build();
	}


	// /**
	// * An instance of UserDetailsService for retrieving users to authenticate.
	// * Note: default password decoder is deprecated, it will have to be replaced ASAP
	// * @return a bean of UserDetailsService
	// */
	// @Bean
	// @SuppressWarnings("deprecation")
	// public UserDetailsService userDetailsService() {
	// 	log.info("### SecurityConfig -> userDetailsService called");
	// 	log.info("### SecurityConfig -> using in memory user details: user / password with role USER");
	// 	UserDetails userDetails = User
	// 			.withDefaultPasswordEncoder()
	// 			.username("user")
	// 			.password("password")
	// 			.roles("USER")
	// 			.build();
	// 	return new InMemoryUserDetailsManager(userDetails);
	// }

	// Note: RegisteredClientRepository that allow to have inMemory client
	// is handled and stored in db, no need to have a bean
	/**
	 * An instance of RegisteredClientRepository for managing clients.
	 * @return a RegisteredClientRepository bean to retrieve the configured clients
	 */
	//@Bean
	//public RegisteredClientRepository registeredClientRepository() {
	//	log.info("### SecurityConfig -> registeredClientRepository called");
	//	RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
	//			.clientId("oidc-client")
	//			.clientSecret(passwordEncoder.encode("secret"))
	//			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
	//			// we retrieve an authorization code to be changed to a token
	//			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
	//			.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
	//			.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
	//			//.redirectUri("http://127.0.0.1:8080/login/oauth2/code/oidc-client")
	//			// at start, we will debug the application no url is actually provided
	//			.redirectUri("https://oauthdebugger.com/debug")
	//			//.postLogoutRedirectUri("http://127.0.0.1:8080/")
	//			.scope(OidcScopes.OPENID)
	//			.scope(OidcScopes.PROFILE)
	//			.clientSettings(clientSettings())
	//			.build();
	//	return new InMemoryRegisteredClientRepository(oidcClient);
	//}

	// Note: the client setting is handled and stored in db, no need to have a bean
	// /**
	//  * Define the client setting: we could have defined it in registeredClientRepository method directly
	//  * @return a client settings configuration
	//  */
	// @Bean
	// public ClientSettings clientSettings() {
	// 	return ClientSettings.builder()
	// 			.requireProofKey(true)
	// 			//.requireAuthorizationConsent(true)
	// 			.build();
	// }

	/**
	 * We can customize token by adding claims.
	 * Claims are usually data concerning user such as roles for instance, username, etc.
	 * by default, token returns
	 * We have 3 kind of tokens:
	 * <ul>
	 *     <li>id_tokens: An ID token is an artifact that proves that
	 *     	the user has been authenticated. It was introduced by OpenID
	 *     	Connect (OIDC), an open standard for authentication used by
	 *     	many identity providers such as Google, Facebook, and, of
	 *     	course, OAuth</li>
	 *     <li>access_token: the access token allows a client application
	 *     	to access a specific resource to perform specific actions on
	 *     	behalf of the user. That is what is known as a delegated
	 *     	authorization scenario: the user delegates a client application
	 *     	to access a resource on their behalf. That means, for example,
	 *     	that you can authorize your LinkedIn app to access Twitter’s
	 *     	API on your behalf to cross-post on both social platforms. </li>
	 *     <li>refresh_token: token that allow to get a new access token: </li>
	 * </ul>
	 * Here we add username and roles to the claims
	 * @return a token customizer
	 */
	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(){
		log.info("### SecurityConfig -> tokenCustomizer called");
		return context -> {
			Authentication principal = context.getPrincipal();
			if(context.getTokenType().getValue().equals("id_token")){
				context.getClaims().claim("token_type", "id token");
			}
			if(context.getTokenType().getValue().equals("access_token")){
				context.getClaims().claim("token_type", "access token");
				Set<String> roles = principal.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority)
						.collect(Collectors.toSet());
				context.getClaims().claim("roles", roles).claim("username", principal.getName());
			}
		};
	}


	/**
	 * An instance of com.nimbusds.jose.jwk.source.JWKSource for signing access tokens.
	 * @return a JWKSource<SecurityContext> bean
	 */
	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		log.info("### SecurityConfig -> jwkSource called");
		KeyPair keyPair = generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAKey rsaKey = new RSAKey
				.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
		log.info("public key: \t" + publicKey);
		log.info("private key: \t" + privateKey.toString());
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}

	/**
	 * An instance of java.security.KeyPair with keys generated on startup used to create
	 * the JWKSource above.
	 * @return a KeyPair object used by the JWKSource instance
	 */
	private static KeyPair generateRsaKey() {
		log.info("### SecurityConfig -> generateRsaKey called");
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		}
		catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}

	/**
	 * An instance of JwtDecoder for decoding signed access tokens.
	 * @param jwkSource jwkSource
	 * @return a jwtDecoder bean
	 */
	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		log.info("### SecurityConfig -> jwtDecoder called");
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	/**
	 * An instance of AuthorizationServerSettings to configure Spring Authorization Server.
	 * Who provides the authorizations ?
	 * @return an AuthorizationServerSettings bean
	 */
	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		log.info("### SecurityConfig -> authorizationServerSettings called");
		return AuthorizationServerSettings.builder()
				.issuer("http://localhost:9000")
				.build();
	}

}