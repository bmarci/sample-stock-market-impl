package com.opencast.martonblum.converter;

import com.opencast.martonblum.backend.stock.CommonStock;
import com.opencast.martonblum.backend.stock.PreferredStock;
import com.opencast.martonblum.backend.stock.Stock;
import com.opencast.martonblum.model.StockModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * This is used to map the outer representation of the stock data into business representation.
 */
@Component
public class StockConverter {

    /**
     * Produces a Stock which the backend can use. Please note that it decides about the subtype to instantiate.
     * It is pretty similar as a factory.
     * @param stockModel The outer representation.
     * @return The inner representation which can be used by backend.
     */
    public Stock convert(final StockModel stockModel) {
        Stock convertedStock = null;
        switch (stockModel.getStockType().toLowerCase()) {
            case "common":
                convertedStock = convertToCommon(stockModel);
                break;
            case "preferred":
                convertedStock = convertToPreferred(stockModel);
                break;
        }
        return convertedStock;
    }

    public Map<String, Double> convertToGeometricMean(final Map<Stock, Double> geometricMeans) {
        final Map<String, Double> collect = new HashMap<>();
        for (final Stock stock : geometricMeans.keySet()) {
            collect.put(stock.getSymbol(), geometricMeans.get(stock));
        }
        return collect;
    }

    private CommonStock convertToCommon(final StockModel stockModel) {
        return CommonStock.builder()
                .lastDividend(stockModel.getLastDividend())
                .parValue(stockModel.getParValue())
                .symbol(stockModel.getSymbol())
                .build();
    }

    private PreferredStock convertToPreferred(final StockModel stockModel) {
        return PreferredStock.builder()
                .fixedDividendPercent(stockModel.getFixedDividend())
                .lastDividend(stockModel.getLastDividend())
                .parValue(stockModel.getParValue())
                .symbol(stockModel.getSymbol())
                .build();
    }
}
