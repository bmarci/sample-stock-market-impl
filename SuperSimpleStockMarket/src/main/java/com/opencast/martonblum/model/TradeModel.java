package com.opencast.martonblum.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Represents a trade data class for our model.
 */
@Data
public class TradeModel {
    private Timestamp timestamp;
    private int quantity;
    private boolean buy;
    private double price;
    private String stock;
}

