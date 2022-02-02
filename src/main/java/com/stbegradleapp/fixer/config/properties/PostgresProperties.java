package com.stbegradleapp.fixer.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class PostgresProperties {
    private String url;
    private String username;
    private String password;
}
