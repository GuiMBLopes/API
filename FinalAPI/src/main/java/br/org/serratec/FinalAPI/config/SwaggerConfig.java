package br.org.serratec.FinalAPI.config;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Minha API", version = "1.0", description = "Documentação da API de Exemplo"))
public class SwaggerConfig {
}