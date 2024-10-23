package br.org.serratec.FinalAPI.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class SwaggerConfig {
	


	@Bean
	public OpenAPI config() {
		return new OpenAPI().info(new Info().title("Projeto final de API").description("API do projeto final Serratec 2024.2"));
	}
	
}