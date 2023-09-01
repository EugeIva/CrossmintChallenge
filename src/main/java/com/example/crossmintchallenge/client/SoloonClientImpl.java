package com.example.crossmintchallenge.client;

import com.example.crossmintchallenge.CrossmintPapams;
import com.example.crossmintchallenge.model.Color;
import com.example.crossmintchallenge.model.SoloonRequest;
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
public class SoloonClientImpl implements SoloonClient {
    private final CrossmintPapams crossmintPapams;
    private final String path = "soloons";

    private final WebClient webClient;

    @Override
    public void add(Integer row, Integer col, Color color) {
        String response = webClient.post()
                .uri(path)
                .body(prepareBody(col, row, color), SoloonRequest.class).retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
                .block();
        log.info("Soloon added: {}:{}:{}", row, col, color.value());
    }

    @Override
    public void delete(Integer row, Integer col) {
        String response = webClient.method(HttpMethod.DELETE)
                .uri(path)
                .body(prepareBody(col, row, null), SoloonRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
                .block();
        log.info("Soloon deleted: {}:{}", row, col);
    }

    private Mono<SoloonRequest> prepareBody(Integer col, Integer row, Color color) {
        String colorString = color == null? null : color.value();
        return Mono.just(new SoloonRequest(col, row, crossmintPapams.getCandidateId(), colorString));
    }
}
