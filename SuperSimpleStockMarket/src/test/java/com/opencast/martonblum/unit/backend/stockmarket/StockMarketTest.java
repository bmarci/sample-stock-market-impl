package com.opencast.martonblum.unit.backend.stockmarket;

import com.opencast.martonblum.backend.stockmarket.StockMarket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.opencast.martonblum.backend.stock.CommonStock;
import com.opencast.martonblum.backend.stock.PreferredStock;
import com.opencast.martonblum.backend.stock.Stock;
import com.opencast.martonblum.backend.trade.Trade;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StockMarketTest {

    private static final long TWENTY_MINUTES = 20 * 60 * 1000;
    private final Timestamp now = new Timestamp((new Date()).getTime());
    private final Timestamp veryPast = new Timestamp((new Date()).getTime() - TWENTY_MINUTES);

    StockMarket stockMarket;

    @BeforeEach
    void init() {
        stockMarket =  new StockMarket();
    }

    @Test
    void preventInvalidTrades() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        final Stock ps = new PreferredStock("GIN", 2, 100d, 8d); // Intentionally not added
        final Trade tr = new Trade(now, 1, Trade.Direction.BUY, 64d, ps);

        stockMarket.addStock(cs);
         assertThrows(IllegalArgumentException.class, () -> stockMarket.addTrade(tr),
                 "Should not be able to add trade which stock is not registered in the stock market");
    }

    /**
     * Geometric mean test.
     */
    @Test
    void calculateGeometricMeanWithoutStock() {
        final Map<Stock, Double> geometricMeans = stockMarket.calculateGeometricMean();
        assertTrue(geometricMeans.isEmpty(), "should give empty map back, if there is no stock in the market");
    }

    @Test
    void calculateGeometricMean2() {
        final StockMarket stockMarket = Mockito.mock(StockMarket.class);
        stockMarket.calculateGeometricMean(Collections.emptyMap());
        Mockito.verify(stockMarket, Mockito.times(1)).calculateGeometricMean(Mockito.anyMap());
    }

    @Test
    void calculateGeometricMeanOneStockNoTrade() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        stockMarket.addStock(cs);
        final Map<Stock, Double> geometricMeans = stockMarket.calculateGeometricMean();
        assertEquals(geometricMeans.size(), 1);
        assertEquals(geometricMeans.get(cs), 0d);
    }

    @Test
    void calculateGeometricMeanOneStockOneTrade() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        final Trade tr = new Trade(now, 1, Trade.Direction.BUY, 199, cs);
        stockMarket.addStock(cs);
        stockMarket.addTrade(tr);
        final Map<Stock, Double> geometricMeans = stockMarket.calculateGeometricMean();
        assertEquals(geometricMeans.size(), 1);
        assertEquals(geometricMeans.get(cs), 199d);
    }

    @Test
    void calculateGeometricMeanOneStockTwoTrade() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        final Trade tr = new Trade(now, 1, Trade.Direction.BUY, 199, cs);
        final Trade tr2 = new Trade(now, 1, Trade.Direction.SELL, 199, cs);
        stockMarket.addStock(cs);
        stockMarket.addTrade(tr);
        stockMarket.addTrade(tr2);
        final Map<Stock, Double> geometricMeans = stockMarket.calculateGeometricMean();
        assertEquals(geometricMeans.size(), 1);
        assertEquals(geometricMeans.get(cs), 199d);
    }

    @Test
    void calculateGeometricMeanTwoStockTwoTrade() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        final Stock cs2 = new CommonStock("ALE", 23d, 100d);
        final Trade tr = new Trade(now, 1, Trade.Direction.BUY, 199, cs);
        final Trade tr2 = new Trade(now, 1, Trade.Direction.SELL, 199, cs2);
        stockMarket.addStock(cs);
        stockMarket.addStock(cs2);
        stockMarket.addTrade(tr);
        stockMarket.addTrade(tr2);
        final Map<Stock, Double> geometricMeans = stockMarket.calculateGeometricMean();
        assertEquals(geometricMeans.size(), 2);
        assertEquals(geometricMeans.get(cs), 199d);
        assertEquals(geometricMeans.get(cs2), 199d);
    }

    @Test
    void calculateGeometricMeanTwoStockTwoTrade3() {
        final Stock ps = new PreferredStock("GIN", 2, 100d, 8d);
        final Stock ps2 = new PreferredStock("GEN", 4, 100d, 12d);
        final Trade tr = new Trade(now, 1, Trade.Direction.BUY, 199, ps);
        final Trade tr2 = new Trade(now, 1, Trade.Direction.SELL, 199, ps2);
        stockMarket.addStock(ps);
        stockMarket.addStock(ps2);
        stockMarket.addTrade(tr);
        stockMarket.addTrade(tr2);
        final Map<Stock, Double> geometricMeans = stockMarket.calculateGeometricMean();
        assertEquals(geometricMeans.size(), 2);
        assertEquals(geometricMeans.get(ps), 199d);
        assertEquals(geometricMeans.get(ps2), 199d);
    }

    @Test
    void calculateGeometricMeanTwoStockTwoTrade2() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        final Stock ps = new PreferredStock("GIN", 2, 100d, 8d);
        final Trade tr = new Trade(now, 1, Trade.Direction.BUY, 199, cs);
        final Trade tr2 = new Trade(now, 1, Trade.Direction.SELL, 199, ps);
        stockMarket.addStock(cs);
        stockMarket.addStock(ps);
        stockMarket.addTrade(tr);
        stockMarket.addTrade(tr2);
        final Map<Stock, Double> geometricMeans = stockMarket.calculateGeometricMean();
        assertEquals(geometricMeans.size(), 2);
        assertEquals(geometricMeans.get(cs), 199d);
        assertEquals(geometricMeans.get(ps), 199d);
    }

    @Test
    void calculateGeometricMeanFourStockTwoTrade() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        final Stock ps = new PreferredStock("GIN", 2, 100d, 8d);
        final Trade tr = new Trade(now, 1, Trade.Direction.BUY, 64d, cs);
        final Trade tr2 = new Trade(now, 1, Trade.Direction.SELL, 81, cs);
        final Trade tr3 = new Trade(now, 1, Trade.Direction.BUY, 100, ps);
        final Trade tr4 = new Trade(now, 1, Trade.Direction.SELL, 121, ps);
        stockMarket.addStock(cs);
        stockMarket.addStock(ps);
        stockMarket.addTrade(tr);
        stockMarket.addTrade(tr2);
        stockMarket.addTrade(tr3);
        stockMarket.addTrade(tr4);
        final Map<Stock, Double> geometricMeans = stockMarket.calculateGeometricMean();
        assertEquals(geometricMeans.size(), 2);
        assertEquals(geometricMeans.get(cs), 72d);
        assertEquals(geometricMeans.get(ps), 110d);
    }

    @Test
    void calculateGeometricMeanFourStockTwoTrade2() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        final Stock ps = new PreferredStock("GIN", 2, 100d, 8d);
        final Trade tr = new Trade(now, 1, Trade.Direction.BUY, 64d, cs);
        final Trade tr2 = new Trade(veryPast, 1, Trade.Direction.SELL, 81, cs);
        final Trade tr3 = new Trade(now, 1, Trade.Direction.BUY, 100, ps);
        final Trade tr4 = new Trade(veryPast, 1, Trade.Direction.SELL, 121, ps);
        stockMarket.addStock(cs);
        stockMarket.addStock(ps);
        stockMarket.addTrade(tr);
        stockMarket.addTrade(tr2);
        stockMarket.addTrade(tr3);
        stockMarket.addTrade(tr4);
        final Map<Stock, Double> geometricMeans = stockMarket.calculateGeometricMean();
        assertEquals(geometricMeans.size(), 2);
        assertEquals(geometricMeans.get(cs), 72d, "Should not depend on the timestamp");
        assertEquals(geometricMeans.get(ps), 110d, "Should not depend on the timestamp");
    }

    /**
     * VolumeWeightedPriceForStock tests.
     */

    @Test
    void calculateVolumeWeightedPriceForStockWithNoTrade() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        stockMarket.addStock(cs);
        final Double vwPrice = stockMarket.calculateVolumeWeightedPriceForStock(cs);
        assertEquals(vwPrice, 0d, "Should be zero, if there is no trade for stock");
    }

    @Test
    void calculateVolumeWeightedPriceForInvalidStockWithTrade() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        final Stock ps = new PreferredStock("GIN", 2, 100d, 8d);
        final Trade tr = new Trade(now, 1, Trade.Direction.BUY, 64d, ps);
        stockMarket.addStock(cs);
        assertThrows(IllegalArgumentException.class, () -> stockMarket.addTrade(tr));
        assertThrows(IllegalArgumentException.class, () -> stockMarket.calculateVolumeWeightedPriceForStock(ps),
                "Should throw an error if the stock of the trade is not added to the stock market.");
    }

    @Test
    void calculateVolumeWeightedPriceForStockWithOneTrade() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        final Trade tr = new Trade(now, 1, Trade.Direction.BUY, 64d, cs);
        stockMarket.addStock(cs);
        stockMarket.addTrade(tr);
        final Double vwPrice = stockMarket.calculateVolumeWeightedPriceForStock(cs);
        assertEquals(vwPrice, 64d, "Should be calculate the average properly (quantity = 1)");
    }

    @Test
    void calculateVolumeWeightedPriceForStockWithOneTrade2() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        final Trade tr = new Trade(now, 2, Trade.Direction.BUY, 64d, cs);
        stockMarket.addStock(cs);
        stockMarket.addTrade(tr);
        final Double vwPrice = stockMarket.calculateVolumeWeightedPriceForStock(cs);
        assertEquals(vwPrice, 64d, "Should be calculate the average properly (quantity = 2)");
    }

    @Test
    void calculateVolumeWeightedPriceForStockWithTwoTrade() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        final Stock ps = new PreferredStock("GIN", 2, 100d, 8d);
        final Trade tr = new Trade(now, 1, Trade.Direction.BUY, 64d, cs);
        final Trade tr2 = new Trade(now, 1, Trade.Direction.BUY, 128d, cs);

        stockMarket.addStock(cs);
        stockMarket.addStock(ps);
        stockMarket.addTrade(tr);
        stockMarket.addTrade(tr2);

        final Double vwPrice = stockMarket.calculateVolumeWeightedPriceForStock(cs);
        assertEquals(vwPrice, 96d, "Should be calculate the average properly (2 trades, each quantity = 1)");
    }

    @Test
    void calculateVolumeWeightedPriceForStockWithTwoTrade2() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        final Trade tr = new Trade(now, 1, Trade.Direction.BUY, 65d, cs);
        final Trade tr2 = new Trade(now, 2, Trade.Direction.BUY, 128d, cs);

        stockMarket.addStock(cs);
        stockMarket.addTrade(tr);
        stockMarket.addTrade(tr2);

        final Double vwPrice = stockMarket.calculateVolumeWeightedPriceForStock(cs);
        assertEquals(vwPrice, 107d, "Should be calculate the average properly (2 trades, quantity = {1, 2})");
    }

    @Test
    void calculateVolumeWeightedPriceForStockWithOldTrade() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        final Trade tr = new Trade(veryPast, 2, Trade.Direction.BUY, 64d, cs);
        stockMarket.addStock(cs);
        stockMarket.addTrade(tr);
        final Double vwPrice = stockMarket.calculateVolumeWeightedPriceForStock(cs);
        assertEquals(vwPrice, 0d, "Should be 0 if there is no fresh trades there");
    }

    @Test
    void calculateVolumeWeightedPriceForStockWithOldTrade2() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        final Trade tr = new Trade(veryPast, 2, Trade.Direction.BUY, 64d, cs);
        final Trade tr2 = new Trade(now, 1, Trade.Direction.BUY, 80d, cs);
        stockMarket.addStock(cs);
        stockMarket.addTrade(tr);
        stockMarket.addTrade(tr2);
        final Double vwPrice = stockMarket.calculateVolumeWeightedPriceForStock(cs);
        assertEquals(vwPrice, 80d, "Should only calculate the fresh trade (1 fresh)");
    }

    @Test
    void calculateVolumeWeightedPriceForStockWithOldTrade3() {
        final Stock cs = new CommonStock("POP", 8d, 100d);
        final Trade tr = new Trade(veryPast, 2, Trade.Direction.BUY, 64d, cs);
        final Trade tr2 = new Trade(now, 2, Trade.Direction.BUY, 80d, cs);
        final Trade tr3 = new Trade(now, 1, Trade.Direction.BUY, 50d, cs);
        stockMarket.addStock(cs);
        stockMarket.addTrade(tr);
        stockMarket.addTrade(tr2);
        stockMarket.addTrade(tr3);
        final Double vwPrice = stockMarket.calculateVolumeWeightedPriceForStock(cs);
        assertEquals(vwPrice, 70d, "Should only calculate the fresh trades (2 fresh)");
    }
}
