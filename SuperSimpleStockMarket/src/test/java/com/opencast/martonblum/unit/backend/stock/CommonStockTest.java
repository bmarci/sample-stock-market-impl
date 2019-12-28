package com.opencast.martonblum.unit.backend.stock;

import com.opencast.martonblum.backend.stock.CommonStock;
import com.opencast.martonblum.backend.stock.PreferredStock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonStockTest {

    public static final String POP = "Pop";
    public static final String GIN = "GIN";
    public static final String TEA = "TEA";

    @Test
    void shouldBeCreated() {
        final CommonStock cs = new CommonStock(POP, 8d, 100d);
        assertNotNull(cs, "CommonStock should be created");
    }

    /**
     * Testing PE calculation.
     */

    @Test
    void calculatePE() {
        final CommonStock cs = new CommonStock(GIN, 8d, 100d);
        final double pe = cs.calculatePE(80d);
        assertEquals(10d, pe, "Dividend should be calculated properly for decimals");
    }

    @Test
    void calculatePEDouble() {
        final CommonStock cs = new CommonStock(GIN, 4d, 100d);
        final double pe = cs.calculatePE(150d);
        assertEquals(37.5d, pe, "PE should be calculated properly for decimals");
    }

    @Test
    void calculatePEZeroPrice() {
        final CommonStock cs = new CommonStock(GIN, 5d, 100d);
        assertThrows(IllegalArgumentException.class, () -> cs.calculatePE(0d), "Price can not be zero!");
    }

    @Test
    void calculatePEZeroDividend() {
        final CommonStock cs = new CommonStock(TEA, 0d, 100d);
        final double pe = cs.calculatePE(150d);
        assertEquals(0d, pe, "We define PE rate as 0 if we want to divide by zero");
    }

    /**
     * Testing yield dividend calculation.
     */

    @Test
    void calculateYieldDividend() {
        final CommonStock cs = new CommonStock(POP, 8d, 100d);
        final double dividend = cs.calculateDividendYield(2d);
        assertEquals(4d, dividend, "Dividend should be calculated properly for decimals");
    }

    @Test
    void calculateYieldDividendDouble() {
        final CommonStock cs = new CommonStock(POP, 10d, 100d);
        final double dividend = cs.calculateDividendYield(4d);
        assertEquals(2.5d, dividend, "Dividend should be calculated properly for non decimals");
    }

    @Test
    void calculateYieldDividendWithZeroLast() {
        final CommonStock cs = new CommonStock(POP, 0d, 100d);
        final double dividend = cs.calculateDividendYield(4d);
        assertEquals(0d, dividend, "Dividend should be 0");
    }

    @Test
    void calculateYieldDividendZeroPrice() {
        final CommonStock cs = new CommonStock(POP, 10d, 100d);
        assertThrows(IllegalArgumentException.class, () -> cs.calculateDividendYield(0d), "No division by 0");
    }

    /**
     * Equality tests.
     */
    @Test
    void equality() {
        final CommonStock cs = new CommonStock(POP, 0d, 100d);
        assertEquals(cs, cs, "Should be equal");
    }

    @Test
    void equality2() {
        final CommonStock cs = new CommonStock(POP, 0d, 100d);
        final CommonStock cs2 = new CommonStock(POP, 0d, 100d);
        assertEquals(cs, cs2, "Should be equal");
    }

    @Test
    void equality3() {
        final CommonStock cs = new CommonStock(POP, 0d, 100d);
        final CommonStock cs2 = new CommonStock(POP, 0d, 100d);
        assertEquals(cs2, cs, "Should be equal");
    }

    @Test
    void nonEquality() {
        final CommonStock cs = new CommonStock(POP, 0d, 100d);
        assertNotEquals(null, cs, "Should NOT be equal");
    }

    @Test
    void nonEquality2() {
        final CommonStock cs = new CommonStock(POP, 0d, 100d);
        final CommonStock cs2 = new CommonStock(TEA, 0d, 100d);
        assertNotEquals(cs, cs2, "Should NOT be equal");
    }

    @Test
    void nonEquality3() {
        final CommonStock cs = new CommonStock(POP, 0d, 100d);
        final CommonStock cs2 = new CommonStock(POP, 1d, 100d);
        assertNotEquals(cs, cs2, "Should NOT be equal");
    }

    @Test
    void nonEquality4() {
        final CommonStock cs = new CommonStock(POP, 0d, 101d);
        final CommonStock cs2 = new CommonStock(POP, 0d, 100d);
        assertNotEquals(cs, cs2, "Should NOT be equal");
    }

    @Test
    void nonEquality5() {
        final CommonStock cs = new CommonStock(POP, 0d, 10d);
        final PreferredStock ps = new PreferredStock(GIN, 0d, 100d, 1d);
        assertNotEquals(cs, ps, "Should NOT be equal");
    }

    @Test
    void nonEquality6() {
        final CommonStock cs = new CommonStock(POP, 0d, 10d);
        final PreferredStock ps = new PreferredStock(GIN, 0d, 100d, 1d);
        assertNotEquals(ps, cs, "Should NOT be equal");
    }
}
