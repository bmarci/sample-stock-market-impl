package com.opencast.martonblum.converter;

import com.opencast.martonblum.backend.stockmarket.StockMarket;
import com.opencast.martonblum.backend.trade.Trade;
import com.opencast.martonblum.model.TradeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TradeConverter {

    @Autowired
    StockMarket stockMarket;

    public Trade convert(final TradeModel tradeModel) {
        return Trade.builder()
            .direction(tradeModel.isBuy())
            .price(tradeModel.getPrice())
            .quantity(tradeModel.getQuantity())
            .stock(stockMarket.getStockBySymbol(tradeModel.getStock()))
            .timestamp(tradeModel.getTimestamp())
            .build();
    }
}
