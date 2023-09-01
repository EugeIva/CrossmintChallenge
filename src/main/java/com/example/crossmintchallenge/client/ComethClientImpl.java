package com.example.crossmintchallenge.client;

import com.example.crossmintchallenge.CrossmintPapams;
import com.example.crossmintchallenge.model.ComethRequest;
import com.example.crossmintchallenge.model.Direction;
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
public class ComethClientImpl implements ComethClient {

    private final CrossmintPapams crossmintPapams;
    private final String path = "comeths";

    private final WebClient webClient;

    @Override
    public void add(Integer row, Integer col, Direction direction) {
        String response = webClient.post()
                .uri(path)
                .body(prepareBody(col, row, direction), ComethRequest.class).retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
                .block();
        log.info("Cometh added: {}:{}:{}", row, col, direction.value());
    }

    @Override
    public void delete(Integer row, Integer col) {
        String response = webClient.method(HttpMethod.DELETE)
                .uri(path)
                .body(prepareBody(col, row, null), ComethRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
                .block();
        log.info("Soloon deleted: {}:{}", row, col);
    }

    private Mono<ComethRequest> prepareBody(Integer col, Integer row, Direction direction) {
        String directionString = direction == null? null : direction.value();
        return Mono.just(new ComethRequest(col, row, crossmintPapams.getCandidateId(), directionString));
    }
}
