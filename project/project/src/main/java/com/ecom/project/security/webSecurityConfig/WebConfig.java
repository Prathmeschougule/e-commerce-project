package com.ecom.project.security.webSecurityConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${frontEnd.url}")
    String frontUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(frontUrl, "http://localhost:3000") // ✅ Correct React dev server port
                .allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS") // ✅ Fixed typo (OPTIONS)
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
