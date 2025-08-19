package com.decolatech.easytravel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ET EasyTravel API")
                        .description("API para sistema de gerenciamento de viagens")
                        .version("1.0")
                        .contact(new Contact()
                                .name("DecolaTech Team")
                                .email("contato@decolatech.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Servidor Local"),
                        new Server().url("https://localhost:8080").description("Servidor Local HTTPS"),
                        new Server().url("/").description("Servidor Atual")
                ));
    }
}
