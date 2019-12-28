package com.opencast.martonblum.service;

import com.opencast.martonblum.backend.stock.Stock;
import com.opencast.martonblum.backend.stockmarket.StockMarket;
import com.opencast.martonblum.backend.trade.Trade;
import com.opencast.martonblum.converter.StockConverter;
import com.opencast.martonblum.converter.TradeConverter;
import com.opencast.martonblum.model.StockModel;
import com.opencast.martonblum.model.TradeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StockMarketService {
    @Autowired
    StockMarket stockMarket;

    @Autowired
    StockConverter stockConverter;

    @Autowired
    TradeConverter tradeConverter;

    public void addStocks(final List<StockModel> stockModels) {
        stockModels.forEach(this::addStock);
    }

    public void addStock(final StockModel stockModel) {
        final Stock stock = stockConverter.convert(stockModel);
        stockMarket.addStock(stock);
    }

    public void addTrades(final List<TradeModel> tradeModels) {
        tradeModels.forEach(this::addTrade);
    }

    public void addTrade(final TradeModel tradeModel) {
        final Trade trade = tradeConverter.convert(tradeModel);
        stockMarket.addTrade(trade);
    }

    public double calculateDividendYield(final String symbol, final double price) {
        final Stock stock = stockMarket.getStockBySymbol(symbol);
        return stock.calculateDividendYield(price);
    }

    public double calculatePE(final String symbol, final Double price) {
        final Stock stock = stockMarket.getStockBySymbol(symbol);
        return stock.calculatePE(price);
    }

    public double calculateVolumeWeightedPriceForStock(final String symbol) {
        final Stock stock = stockMarket.getStockBySymbol(symbol);
        return stockMarket.calculateVolumeWeightedPriceForStock(stock);
    }

    public Map<String, Double> calculateGeometricMean() {
        final Map<Stock, Double> geometricMeans = stockMarket.calculateGeometricMean();
        return stockConverter.convertToGeometricMean(geometricMeans);
    }
}
