package com.commerce.sahumerios;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "My API", version = "v1"))
public class SahumeriosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SahumeriosApplication.class, args);
	}

}
