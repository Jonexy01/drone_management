package com.example.blusalt;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "DRONE MANAGEMENT", version = "1.0", description = "Blusalt Drone Management Documentation"))
public class BlusaltApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlusaltApplication.class, args);
	}

}
