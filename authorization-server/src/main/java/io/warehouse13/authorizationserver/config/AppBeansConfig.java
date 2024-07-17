package io.warehouse13.authorizationserver.config;

import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.module.jsr310.Jsr310Module;
import org.modelmapper.module.jsr310.Jsr310ModuleConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Configuration
public class AppBeansConfig {
	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		Jsr310ModuleConfig config = Jsr310ModuleConfig.builder()
				.dateTimeFormatter(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
				.dateFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
				.zoneId(ZoneOffset.UTC)
				.build();

		modelMapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT)
				.setPreferNestedProperties(false)
				.setFieldMatchingEnabled(true)
				.setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR)
				.setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
		modelMapper.getConfiguration().setPropertyCondition(context ->
				!(context.getSource() instanceof PersistentCollection));
		// using DateTimeFormatter directly
		modelMapper.registerModule(new Jsr310Module(config));

		return modelMapper;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
