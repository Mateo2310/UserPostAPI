package com.challenge.userpostapi.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.admin")
public class AdminProperties {
    private String username;
    private String password;
}
