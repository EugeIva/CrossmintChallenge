package com.example.crossmintchallenge.client;

import com.example.crossmintchallenge.CrossmintPapams;
import com.example.crossmintchallenge.model.GoalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
@Component
@RequiredArgsConstructor
public class GoalClientImpl implements GoalClient {
    private final CrossmintPapams crossmintPapams;
    private final WebClient webClient;
    @Override
    public GoalResponse get() {

        GoalResponse response = webClient.get()
                .uri(String.format("map/%s/goal", crossmintPapams.getCandidateId()))
                .retrieve()
                .bodyToMono(GoalResponse.class)
                .block();
        log.info("Goal received");

        return response;
    }
}
