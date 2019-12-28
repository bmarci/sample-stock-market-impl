package com.opencast.martonblum.backend.stock;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * Representation of Common stocks. We calculate dividend differently.
 */
@Getter
public class CommonStock extends AbstractStock {

    @Builder
    public CommonStock(final String symbol, final double lastDividend, final double parValue) {
        super(symbol, lastDividend, parValue);
    }

    /**
     * For common stocks the dividend is defined as the last dividend.
     * @return The dividend.
     */
    @Override
    public double calculateDividend() {
        return getLastDividend();
    }

    /**
     * For common stocks the dividend yield is calculated by last dividend / price.
     * @param price The price of the stock.
     * @return Last dividend / price.
     * @throws IllegalArgumentException Price should not be zero.
     */
    @Override
    public double calculateDividendYield(@NonNull final double price) throws IllegalArgumentException {
        if (price == 0d) {
            throw new IllegalArgumentException("Price can not be zero!");
        }
        return getLastDividend() / price;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode();
    }


}
