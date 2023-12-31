package com.example.crossmintchallenge.client;

import com.example.crossmintchallenge.CrossmintPapams;
import com.example.crossmintchallenge.model.PolyanetRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;


@Log4j2
@Component
@RequiredArgsConstructor
public class PolyanetClientImpl implements PolyanetClient {
    private final CrossmintPapams crossmintPapams;
    private final String path = "polyanets";

    private final WebClient webClient;

    @Override
    public void add(Integer row, Integer col) {
        String response = webClient.post()
                .uri(path)
                .body(prepareBody(col, row), PolyanetRequest.class).retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
                .block();
        log.info("Polyanet added: {}:{}", row, col);
    }

    @Override
    public void delete(Integer row, Integer col) {
        String response = webClient.method(HttpMethod.DELETE)
                .uri(path)
                .body(prepareBody(col, row), PolyanetRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
                .block();
        log.info("Polyanet deleted: {}:{}", row, col);
    }

    private Mono<PolyanetRequest> prepareBody(Integer col, Integer row) {
        return Mono.just(new PolyanetRequest(col, row, crossmintPapams.getCandidateId()));
    }
}
