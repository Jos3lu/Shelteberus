package com.hiberus.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocumentationConfig {
    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Volunteers API")
                        .description("API for managing volunteers of shelter")
                        .version("0.0.1")
                        .contact(new Contact()
                                .name("Jose Luis")
                                .email("jlsierra@hiberus.com")));
    }
}
