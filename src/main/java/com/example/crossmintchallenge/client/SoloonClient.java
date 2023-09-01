package com.example.crossmintchallenge.client;

import com.example.crossmintchallenge.model.Color;

public interface SoloonClient {
    void add(Integer row, Integer col, Color color);
    void delete(Integer row, Integer col);
}
