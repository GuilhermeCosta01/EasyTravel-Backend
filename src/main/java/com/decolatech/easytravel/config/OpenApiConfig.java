package com.decolatech.easytravel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    @Profile("prod")
    public OpenAPI azureOpenAPI() {
        Server azureServer = new Server();
        azureServer.setUrl("https://back-et.azurewebsites.net");
        azureServer.setDescription("Azure Production Server");

        return new OpenAPI()
                .info(new Info()
                        .title("EasyTravel API")
                        .description("API para sistema de viagens EasyTravel")
                        .version("1.0.0"))
                .servers(List.of(azureServer));
    }

    @Bean
    @Profile("!prod")
    public OpenAPI localOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Local Development Server");

        return new OpenAPI()
                .info(new Info()
                        .title("EasyTravel API")
                        .description("API para sistema de viagens EasyTravel")
                        .version("1.0.0"))
                .servers(List.of(localServer));
    }
}
