package com.opencast.martonblum.backend.stock;

import lombok.NonNull;

/**
 * We have to be able to do this two calculations on any stock.
 */
public interface Stock {
    double calculateDividend();
    double calculateDividendYield(@NonNull final double price) throws IllegalArgumentException;
    double calculatePE(@NonNull final double price) throws IllegalArgumentException;
    String getSymbol();
}
