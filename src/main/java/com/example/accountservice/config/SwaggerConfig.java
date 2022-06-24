package com.example.accountservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author m-sabbaghi
 * <a href="https://www.linkedin.com/in/sabbaghi/">...</a>
 * @version 6/14/2022
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Account Service")
                        .description("this is a micro service for exposing a financial API")
                        .version("v1")
                        .contact(new Contact()
                                .name("Mohsen Sabbaghi")
                                .url("https://www.linkedin.com/in/sabbaghi/")
                                .email("mohsen.sabbaghi@gmail.com"))
                );
    }
}
