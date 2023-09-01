package com.example.crossmintchallenge.phase;

import com.example.crossmintchallenge.client.ComethClient;
import com.example.crossmintchallenge.client.GoalClient;
import com.example.crossmintchallenge.client.PolyanetClient;
import com.example.crossmintchallenge.client.SoloonClient;
import com.example.crossmintchallenge.model.Color;
import com.example.crossmintchallenge.model.Direction;
import com.example.crossmintchallenge.model.GoalResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class PhaseTwo implements Phase {

    private final PolyanetClient polyanetClient;
    private final SoloonClient soloonClient;
    private final ComethClient comethClient;
    private final GoalClient goalClient;
    private static final Integer SIZE = 30;

    @PostConstruct
    void init() {
        execute();
//        delete();
    }


    @Override
    public void execute() {
        GoalResponse goalResponse = goalClient.get();
        List<List<String>> goal = goalResponse.getGoal();
        for (int row  = 0; row < SIZE; row ++) {
            for (int col = 0; col < SIZE; col ++) {
                handleCell(goal.get(row).get(col), row, col);
            }
        }
        log.info("Finished adding");
    }

    private void handleCell(String cell, Integer row, Integer col) {
        switch (cell) {
            case "POLYANET" -> polyanetClient.add(row, col);
            case "RIGHT_COMETH", "UP_COMETH", "LEFT_COMETH", "DOWN_COMETH" -> handleCometh(cell, row, col);
            case "WHITE_SOLOON", "BLUE_SOLOON", "RED_SOLOON", "PURPLE_SOLOON" -> handleSoloon(cell, row, col);
        }
    }

    private void handleSoloon(String cell, Integer row, Integer col) {
        Color color = switch (cell) {
            case "WHITE_SOLOON" -> Color.WHITE;
            case "BLUE_SOLOON" -> Color.BLUE;
            case "RED_SOLOON" -> Color.RED;
            case "PURPLE_SOLOON" -> Color.PURPLE;
            default -> throw new IllegalStateException("Unexpected value: " + cell);
        };
        soloonClient.add(row, col, color);
    }

    private void handleCometh(String cell, Integer row, Integer col) {
        Direction direction = switch (cell) {
            case "RIGHT_COMETH" -> Direction.RIGHT;
            case "LEFT_COMETH" -> Direction.LEFT;
            case "UP_COMETH" -> Direction.UP;
            case "DOWN_COMETH" -> Direction.DOWN;
            default -> throw new IllegalStateException("Unexpected value: " + cell);
        };
        comethClient.add(row, col, direction);
    }

    private void delete () {
        for (int row = 1; row < 5; row ++) {
            for (int col = 0; col < SIZE; col ++) {
                polyanetClient.delete(row, col);
                comethClient.delete(row, col);
                soloonClient.delete(row, col);
            }
        }
    }
}
