package com.toyota.jdpService.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "JDP Customer Master API",
                version = "1.0",
                description = "APIs for JDP Customer Master Management",
                contact = @Contact(
                        name = "Mithun S",
                        email = "mithun.s2@coforge.com"

                ),
                license = @License(
                        name = "Toyota -TKM"
                )
        )
)
public class SwaggerConfig {
}