package com.example.crossmintchallenge.client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PolyanetRequest {
    private Integer column;
    private Integer row;
    private String candidateId;
}
