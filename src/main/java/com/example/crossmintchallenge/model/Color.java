package com.example.crossmintchallenge.model;

import lombok.AllArgsConstructor;

public enum Color {
    BLUE,
    RED,
    PURPLE,
    WHITE;

    public String value() {
        return name().toLowerCase();
    }


}
