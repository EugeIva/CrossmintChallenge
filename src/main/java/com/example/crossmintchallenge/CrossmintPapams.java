package com.example.crossmintchallenge;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "crossmint")
public class CrossmintPapams {
    private String candidateId;
    private String basePath;
}
