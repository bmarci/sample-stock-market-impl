package com.opencast.martonblum.backend.stockmarket;

import com.opencast.martonblum.backend.stock.Stock;
import com.opencast.martonblum.backend.trade.Trade;
import com.opencast.martonblum.backend.util.MyMath;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * The representation of our main logic.
 */
@Component
public class StockMarket {
    private Map<Stock, List<Trade>> deals;

    private static final long FIFTEEN_MINUTES = 15 * 60 * 1000;

    public StockMarket() {
        deals = new HashMap<>();
    }

    /**
     * This represents an introduction of a new stock to our stock market.
     * @param stock The stock to add.
     */
    public void addStock(@NonNull final Stock stock) {
        deals.computeIfAbsent(stock, k -> new ArrayList<>());
    }

    /**
     * This represents a new trade for a given stock. Please be aware that the stock of the trade should be available
     * for the stock market (be added with the add stock previously.
     * @param trade The trade to be added.
     * @throws IllegalArgumentException If the stock is not added to the market.
     */
    public void addTrade(@NonNull final Trade trade) throws IllegalArgumentException {
        final List<Trade> tradesOfStock = deals.get(trade.getStock());
        if (tradesOfStock == null) {
            throw new IllegalArgumentException("Stock should be on market!");
        }
        tradesOfStock.add(trade);
    }

    /**
     * A simple function to calculate geometric mean of trades for each stock.
     * @return Geometric mean of trade prices for each stock.
     */
    public Map<Stock, Double> calculateGeometricMean() {
        return calculateGeometricMean(deals);
    }

    /**
     * A simple function to calculate geometric mean of trades for each stock.
     * @param deals The deals we should calculate the geometric mean of.
     * @return Geometric mean of trade prices for each stock.
     */
    public Map<Stock, Double> calculateGeometricMean(@NonNull final Map<Stock, List<Trade>> deals) {
        final Map<Stock, Double> geometricMeanForStocks = new HashMap<>();
        List<Double> priceList;
        double geometricMean;
        for (final Stock key : deals.keySet()) {
            priceList = getPricesOfDeals(deals, key);
            geometricMean = MyMath.geometricMean(priceList);
            geometricMeanForStocks.put(key, geometricMean);
        }
        return geometricMeanForStocks;
    }

    /**
     * Calculates the volume weighted price for a given stock where the trades happened in the past fifteen mins.
     * @param stock The stock which is in the stock market. If it's not, the result is defined to be 0.
     * @return The volume weighted price for a given stock.
     * @throws IllegalArgumentException If the given stock is not in the stock market.
     */
    public Double calculateVolumeWeightedPriceForStock(@NonNull final Stock stock) throws IllegalArgumentException {
        final Timestamp threshold = new Timestamp((new Date()).getTime() - FIFTEEN_MINUTES);
        final List<Trade> currentStockTrades = getStocksAfter(stock, threshold);
        return calculateVolumeWeightedPrice(currentStockTrades);
    }

    /**
     * Finds a stock if it is in the stock market.
     * @param symbol To find the stock. It identifies it uniquely.
     * @return The given stock if it is in the market.
     * @throws NoSuchElementException If the stock is not in the stock market.
     */
    public Stock getStockBySymbol(final String symbol) throws NoSuchElementException {
        return deals.keySet()
                .stream()
                .filter(i -> symbol.equals(i.getSymbol()))
                .findFirst().get();
    }

    private Double calculateVolumeWeightedPrice(@NonNull final List<Trade> trades) {
        double vwPrice = 0;
        int sumOfQuantities = 0;
        for (final Trade trade : trades) {
            vwPrice += trade.getPrice() * trade.getQuantity();
            sumOfQuantities += trade.getQuantity();
        }
        return sumOfQuantities > 0 ? vwPrice / sumOfQuantities : 0d;
    }

    private List<Trade> getStocksAfter(@NonNull final Stock stock, @NonNull final Timestamp threshold)
        throws IllegalArgumentException {
        final List<Trade> tradesOfStock = deals.get(stock);
        if (tradesOfStock == null) {
            throw new IllegalArgumentException("Stock should be on market!");
        }
        return tradesOfStock
                .stream()
                .filter(i -> i.getTimestamp().after(threshold))
                .collect(Collectors.toList());
    }

    private List<Double> getPricesOfDeals(@NonNull final Map<Stock, List<Trade>> deals, @NonNull final Stock key) {
        return deals.get(key)
                .stream()
                .map(Trade::getPrice)
                .collect(Collectors.toList());
    }

}
