package com.opencast.martonblum.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Represents a stock data class for our model.
 */
@Builder
@Data
public class StockModel {
    @NonNull
    private String symbol;
    @NonNull
    private String stockType;
    private double lastDividend;
    private double parValue;
    private double fixedDividend;
}
