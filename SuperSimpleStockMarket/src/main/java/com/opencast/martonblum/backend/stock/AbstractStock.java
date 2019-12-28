package com.opencast.martonblum.backend.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

/**
 * Default behaviour for stocks.
 */
@AllArgsConstructor
@Getter
public abstract class AbstractStock implements Stock {

    private String symbol;
    private double lastDividend;
    private double parValue;

    public abstract double calculateDividendYield(@NonNull final double price);

    /**
     * Calculate P/E ratio if the price is not null.
     * @param price The price for the stock.
     * @return Price / Dividend or zero if the dividend is zero.
     * @throws IllegalArgumentException Price should not be zero.
     */
    public double calculatePE(@NonNull final double price) throws IllegalArgumentException {
        final double dividend = calculateDividend();
        if (price == 0d) {
            throw new IllegalArgumentException("Price can not be zero!");
        }
        return dividend == 0d ? 0d : price / dividend;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSymbol());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AbstractStock other = (AbstractStock) obj;
        return Objects.equals(this.symbol, other.symbol)
                && Objects.equals(this.lastDividend, other.lastDividend)
                && Objects.equals(this.parValue, other.parValue);
    }
}
