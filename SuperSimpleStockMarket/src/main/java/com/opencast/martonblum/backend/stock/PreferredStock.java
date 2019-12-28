package com.opencast.martonblum.backend.stock;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import java.util.Objects;

/**
 * Representation of Preferred stocks. We calculate dividend differently.
 */
@Getter
public class PreferredStock extends AbstractStock {

    private double fixedDividend;

    @Builder
    public PreferredStock(final String symbol, final double lastDividend, final double parValue,
          final double fixedDividendPercent) {
        super(symbol, lastDividend, parValue);
        this.fixedDividend = fixedDividendPercent / 100;
    }

    /**
     * For preferred stocks the dividend is defined as the multiplication of fixed dividend and par value.
     * @return Fixed dividend * par value
     */
    @Override
    public double calculateDividend() {
        return getFixedDividend() * getParValue();
    }

    /**
     * For preferred stocks the dividend yield is calculated by fixed dividend * par value / price.
     * @param price The price of the stock.
     * @return Last dividend / price.
     * @throws IllegalArgumentException Price should not be zero.
     */
    @Override
    public double calculateDividendYield(@NonNull final double price) throws IllegalArgumentException {
        if (price == 0d) {
            throw new IllegalArgumentException("Price can not be zero!");
        }
        return getFixedDividend() * getParValue() / price;
    }

    @Override
    public int hashCode() {
        return 32 * super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final PreferredStock other = (PreferredStock) obj;
        return Objects.equals(this.fixedDividend, other.fixedDividend);
    }
}
