package com.example.crossmintchallenge.model;

public enum Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT;

    public String value() {
        return name().toLowerCase();
    }
}
