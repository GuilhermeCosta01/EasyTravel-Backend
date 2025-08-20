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
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server azureServer = new Server();
        azureServer.setUrl("https://back-et.azurewebsites.net");
        azureServer.setDescription("Azure Production Server");

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Local Development Server");

        return new OpenAPI()
                .info(new Info()
                        .title("EasyTravel API")
                        .description("API para sistema de viagens EasyTravel")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("DecolaTech Team")
                                .email("contato@decolatech.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(azureServer, localServer));
    }
}
