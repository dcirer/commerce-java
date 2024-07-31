package com.commerce.sahumerios.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "SAHUMERIOS API",
                version ="1.0",
                description = "CRUD OF COMMERCE"
        )
)
public class OpenApi {
}
