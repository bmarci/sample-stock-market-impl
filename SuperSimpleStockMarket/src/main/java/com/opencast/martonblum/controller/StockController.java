package com.opencast.martonblum.controller;

import com.opencast.martonblum.model.StockModel;
import com.opencast.martonblum.service.StockMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Represents an endpoint for stock related operations.
 */
@RestController
public class StockController {
    @Autowired
    StockMarketService stockMarketService;

    @PostMapping("/stock")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addStock(@RequestBody final StockModel stockModel) {
        stockMarketService.addStock(stockModel);
    }

    @PostMapping("/stocks")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addStock(@RequestBody final List<StockModel> stockModels) {
        stockMarketService.addStocks(stockModels);
    }

    @GetMapping("/stock/{symbol}/dividend-yield/{price}")
    public double calculateDividend(@PathVariable final String symbol, @PathVariable final Double price) {
        return stockMarketService.calculateDividendYield(symbol, price);
    }

    @GetMapping("/stock/{symbol}/pe-ratio/{price}")
    public double calculatePe(@PathVariable final String symbol, @PathVariable final Double price) {
        return stockMarketService.calculatePE(symbol, price);
    }

    @GetMapping("/stock/{symbol}/volume-weighted")
    public double calculateVolumeWeighted(@PathVariable final String symbol) {
        return stockMarketService.calculateVolumeWeightedPriceForStock(symbol);
    }

    @GetMapping("/stock/geometric-mean")
    public Map<String, Double> calculateGeometricMean() {
        return stockMarketService.calculateGeometricMean();
    }
}
