package com.opencast.martonblum.controller;

import com.opencast.martonblum.model.TradeModel;
import com.opencast.martonblum.service.StockMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Represents an endpoint for trade related operations.
 */
@RestController
public class TradeController {
    @Autowired
    StockMarketService stockMarketService;

    @PostMapping("/trade")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addTrade(@RequestBody final TradeModel tradeModel) {
        stockMarketService.addTrade(tradeModel);
    }

    @PostMapping("/trades")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addTrades(@RequestBody final List<TradeModel> tradeModels) {
        stockMarketService.addTrades(tradeModels);
    }
}
