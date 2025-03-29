package com.kipl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties
@PropertySource("classpath:appconfig.properties")
@ComponentScan(basePackages = "com.kipl")
@EnableScheduling
public class KiplApplication {

	public static void main(String[] args) {
		SpringApplication.run(KiplApplication.class, args);
	}

}
