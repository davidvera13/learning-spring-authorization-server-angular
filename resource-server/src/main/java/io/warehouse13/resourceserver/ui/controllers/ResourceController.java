package io.warehouse13.resourceserver.ui.controllers;

import io.warehouse13.resourceserver.ui.domains.response.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/api/resources")
public class ResourceController {

	@GetMapping("/users")
	@PreAuthorize("hasAnyAuthority('ROLE_USER', 'OIDC_USER', 'SCOPE_openid')")
	public ResponseEntity<MessageResponse> getUserMessage(Authentication authentication) {
		log.info(authentication.getAuthorities().toString());
		log.info(authentication.getPrincipal().toString());
		return ResponseEntity.ok(new MessageResponse("Hello " + authentication.getName()));
	}

	@GetMapping("/admins")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<MessageResponse> getAdminMessage(Authentication authentication) {
		log.info(authentication.getAuthorities().toString());
		log.info(authentication.getPrincipal().toString());
		return ResponseEntity.ok(new MessageResponse("Hello Mr. " + authentication.getName()));
	}
}
