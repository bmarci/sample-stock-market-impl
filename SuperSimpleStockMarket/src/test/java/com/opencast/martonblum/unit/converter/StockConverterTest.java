package com.opencast.martonblum.unit.converter;

import com.opencast.martonblum.backend.stock.CommonStock;
import com.opencast.martonblum.backend.stock.PreferredStock;
import com.opencast.martonblum.backend.stock.Stock;
import com.opencast.martonblum.converter.StockConverter;
import com.opencast.martonblum.model.StockModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StockConverterTest {


    StockConverter stockConverter;

    @BeforeEach
    void init() {
        stockConverter = new StockConverter();
    }

    @Test
    void convertCommonStock() {
        final StockModel commonStockModel = StockModel.builder()
                .lastDividend(0d)
                .parValue(100d)
                .symbol("TEA")
                .stockType("Common")
                .build();
        final CommonStock cs = CommonStock.builder()
                .lastDividend(0d)
                .parValue(100d)
                .symbol("TEA")
                .build();
        final Stock convertedStock = stockConverter.convert(commonStockModel);
        assertTrue(convertedStock instanceof CommonStock, "Should convert StockModel to CommonStock class");
        assertEquals(convertedStock, cs, "Should convert StockModel to CommonStock correctly");
    }

    @Test
    void convertPreferredStock() {
        final StockModel preferredStockModel = StockModel.builder()
                .fixedDividend(2d)
                .lastDividend(9d)
                .parValue(100)
                .symbol("GIN")
                .stockType("Preferred")
                .build();
        final PreferredStock ps = PreferredStock.builder()
                .fixedDividendPercent(2d)
                .lastDividend(9d)
                .parValue(100d)
                .symbol("GIN")
                .build();
        final Stock convertedStock = stockConverter.convert(preferredStockModel);
        assertTrue(convertedStock instanceof PreferredStock, "Should convert StockModel to PreferredStock class");
        assertEquals(convertedStock, ps, "Should convert StockModel to PreferredStock correctly");
    }

    @Test
    void convertToGeometricMean() {
        final PreferredStock ps = PreferredStock.builder()
                .fixedDividendPercent(2d)
                .lastDividend(9d)
                .parValue(100d)
                .symbol("GIN")
                .build();
        final CommonStock cs = CommonStock.builder()
                .lastDividend(0d)
                .parValue(100d)
                .symbol("TEA")
                .build();
        final Map<Stock, Double> geometricMeans = new HashMap<>();
        geometricMeans.put(ps, 110d);
        geometricMeans.put(cs, 241d);
        final Map<String, Double> convertedGeometricMeans = stockConverter.convertToGeometricMean(geometricMeans);
        assertEquals(2, convertedGeometricMeans.size(), "Should store the correct number of elements");
        assertTrue(convertedGeometricMeans.containsKey("GIN"));
        assertTrue(convertedGeometricMeans.containsKey("TEA"));
        assertEquals(110d, convertedGeometricMeans.get("GIN"));
        assertEquals(241d, convertedGeometricMeans.get("TEA"));

    }
}