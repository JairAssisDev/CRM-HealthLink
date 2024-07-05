package br.edu.ifpe.CRMHealthLink.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("REST API - crmhealthlink")
                                .description("API para  gestão hospitalar")
                                .contact(new Contact().name("JairAssisDev,Lucasptrick,matheus,lucasManuel").email("jvla2@discente.ifpe.edu.br"))
                );
    }
}