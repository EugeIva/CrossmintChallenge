package com.example.crossmintchallenge.client;

import com.example.crossmintchallenge.model.Direction;

public interface ComethClient {
    void add(Integer row, Integer col, Direction direction);
    void delete(Integer row, Integer col);
}
