package com.kipl.common;

import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class HibernateConfig {

	// Specify the package where the custom naming strategy class is located

	@Bean
	public PhysicalNamingStrategy physicalNamingStrategy() {
		return new com.kipl.common.CustomPhysicalNamingStrategy();
	}
}
