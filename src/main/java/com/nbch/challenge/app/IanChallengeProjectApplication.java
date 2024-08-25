package com.nbch.challenge.app;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "NBCH CHALLENGE REST API DOCUMENTACION",
				description = "Esta es la documentacion de la API REST para el challenge del NBCH. Creado por Ian Fernandez - 2024",
				version = "v1",
				contact = @Contact(
						name = "Ian Fernandez",
						email = "ianstgo@gmail.com",
						url = "https://www.linkedin.com/in/ian-fern%C3%A1ndez-a72598179/"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.nbch.com.ar/"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Proyecto NBCH DOCUMENTACION REST API",
				url = "https://www.nbch.com.ar/"
		)
)
public class IanChallengeProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(IanChallengeProjectApplication.class, args);
	}

}
