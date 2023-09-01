package com.example.crossmintchallenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SoloonRequest {
    private Integer column;
    private Integer row;
    private String candidateId;
    private String color;
}
