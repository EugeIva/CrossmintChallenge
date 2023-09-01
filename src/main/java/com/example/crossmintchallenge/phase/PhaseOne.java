package com.example.crossmintchallenge.phase;

import com.example.crossmintchallenge.client.PolyanetClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhaseOne implements Phase {
    private final PolyanetClient polyanetClient;

    @PostConstruct
    void init() {
        execute();
    }
    @Override
    public void execute() {
        for (int i = 2; i < 9; i++) {
            polyanetClient.add(i, i);
            if (i != 5) {
                polyanetClient.add(i, 10 - i);
            }
        }
    }
}
