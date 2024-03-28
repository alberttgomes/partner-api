package com.eco.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Albert Gomes Cabral
 */
@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/platform-manager")
                .allowedOrigins(_allowedOriginsPath())
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS",
                        "HEAD", "TRACE", "CONNECT");
    }

    private String[] _allowedOriginsPath() {
        return new String[] {
                "http://localhost:3000", "http://localhost:3001",
                "http://localhost:3002", "http://localhost:3003",
                "http://localhost:3004", "http://localhost:3005",
                "http://localhost:3030", "http://localhost:3031"
        };
    }
}
