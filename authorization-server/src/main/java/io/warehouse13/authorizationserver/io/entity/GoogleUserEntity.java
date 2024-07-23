package io.warehouse13.authorizationserver.io.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Entity
@Table(name = "google_users")
@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class GoogleUserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String name;
	private String givenName;
	private String familyName;
	private String pictureUrl;

	public static GoogleUserEntity fromOauth2User(OAuth2User user){
		GoogleUserEntity googleUser = GoogleUserEntity.builder()
				.email(user.getName())
				.name(user.getAttributes().get("name").toString())
				.givenName(user.getAttributes().get("given_name").toString())
				.familyName(user.getAttributes().get("family_name").toString())
				.pictureUrl(user.getAttributes().get("picture").toString())
				.build();
		return googleUser;
	}

	@Override
	public String toString() {
		return "GoogleUser{" +
				"id=" + id +
				", email='" + email + '\'' +
				", name='" + name + '\'' +
				", givenName='" + givenName + '\'' +
				", familyName='" + familyName + '\'' +
				", pictureUrl='" + pictureUrl + '\'' +
				'}';
	}
}
