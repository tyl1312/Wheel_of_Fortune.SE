package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class PrizeHistoryResponse {
    private String prizeName;
    private Timestamp timestamp;
}
