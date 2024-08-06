package com.blog.rs.config;

import io.swagger.v3.jaxrs2.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("RS-Blog/api")
@OpenAPIDefinition(info = @Info(title = "RS-Blog API", version = "1.0"))
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("com.blog.rs.resources");
        register(OpenApiResource.class);
        register(SwaggerSerializers.class);
    }
    
    
}
