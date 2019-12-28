package com.opencast.martonblum.backend.trade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import com.opencast.martonblum.backend.stock.Stock;

import java.sql.Timestamp;

/**
 * Store simple trade info here.
 */
@AllArgsConstructor
@Builder
@Getter
public class Trade {

    /**
     * Constants to indicate buy/sell more meaningfully.
     */
    public final class Direction {
        public static final boolean BUY = true;
        public static final boolean SELL = false;

        private Direction() { }

    }

    private Timestamp timestamp;
    private int quantity;
    private boolean direction;
    private double price;
    private Stock stock;
}
