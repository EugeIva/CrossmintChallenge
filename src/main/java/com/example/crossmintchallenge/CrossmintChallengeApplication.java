package com.example.crossmintchallenge;

import com.example.crossmintchallenge.phase.Phase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class CrossmintChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrossmintChallengeApplication.class, args);

    }

}
