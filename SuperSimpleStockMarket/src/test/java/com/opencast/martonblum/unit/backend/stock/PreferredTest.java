package com.opencast.martonblum.unit.backend.stock;

import com.opencast.martonblum.backend.stock.CommonStock;
import com.opencast.martonblum.backend.stock.PreferredStock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PreferredTest {

    public static final String GEN = "GEN";
    public static final String GIN = "GIN";
    public static final String TEA = "TEA";

    @Test
    void shouldBeCreated() {
        final PreferredStock ps = new PreferredStock(GIN, 8d, 100d, 2d);
        assertNotNull(ps, "PreferredStock should be created");
    }
    /**
     * Testing PE calculation.
     */

    @Test
    void calculatePE() {
        final PreferredStock ps = new PreferredStock(GIN, 8d, 100d, 2d);
        final double pe = ps.calculatePE(2d);
        assertEquals(1d, pe, "Dividend should be calculated properly for decimals");
    }

    @Test
    void calculatePEDouble() {
        final PreferredStock ps = new PreferredStock(GIN, 5d, 100d, 20d);
        final double pe = ps.calculatePE(250d);
        assertEquals(12.5d, pe, "PE should be calculated properly for decimals");
    }

    @Test
    void calculatePEZeroPrice() {
        final PreferredStock ps = new PreferredStock(GIN, 5d, 100d, 20d);
        assertThrows(IllegalArgumentException.class, () -> ps.calculatePE(0d), "Price can not be zero!");
    }

    @Test
    void calculatePEZeroDividend() {
        final PreferredStock ps = new PreferredStock(TEA, 5d, 100d, 0d);
        final double pe = ps.calculatePE(150d);
        assertEquals(0d, pe, "We define PE rate as 0 if we want to divide by zero");
    }

    /**
     * Testing yield dividend calculation.
     */

    @Test
    void calculateYieldDividend() {
        final PreferredStock ps = new PreferredStock(GIN, 8d, 100d, 2d);
        final double dividend = ps.calculateDividendYield(2d);
        assertEquals(1d, dividend, "Dividend should be calculated properly for decimals");
    }

    @Test
    void calculateYieldDividendDouble() {
        final PreferredStock ps = new PreferredStock(GIN, 8d, 100d, 1d);
        final double dividend = ps.calculateDividendYield(2d);
        assertEquals(.5d, dividend, "Dividend should be calculated properly for non decimals");
    }

    @Test
    void calculateYieldDividendWithZeroFixedDividendPercent() {
        final PreferredStock ps = new PreferredStock(GIN, 8d, 100d, 0d);
        final double dividend = ps.calculateDividendYield(4d);
        assertEquals(0d, dividend, "Dividend should be 0");
    }

    @Test
    void calculateYieldDividendWithZeroParValue() {
        final PreferredStock ps = new PreferredStock(GIN, 8d, 100d, 0d);
        final double dividend = ps.calculateDividendYield(4d);
        assertEquals(0d, dividend, "Dividend should be 0");
    }

    @Test
    void calculateYieldDividendZeroPrice() {
        final PreferredStock ps = new PreferredStock(GIN, 8d, 100d, 2d);
        assertThrows(IllegalArgumentException.class, () -> ps.calculateDividendYield(0d), "No division by 0");
    }

    /**
     * Equality tests.
     */
    @Test
    void equality() {
        final PreferredStock ps = new PreferredStock(GIN, 8d, 100d, 10d);
        assertEquals(ps, ps, "Should be equal");
    }

    @Test
    void equality2() {
        final PreferredStock ps = new PreferredStock(GIN, 8d, 100d, 10d);
        final PreferredStock ps2 = new PreferredStock(GIN, 8d, 100d, 10d);
        assertEquals(ps, ps2, "Should be equal");
    }

    @Test
    void equality3() {
        final PreferredStock ps = new PreferredStock(GIN, 8d, 100d, 10d);
        final PreferredStock ps2 = new PreferredStock(GIN, 8d, 100d, 10d);
        assertEquals(ps2, ps, "Should be equal");
    }

    @Test
    void nonEquality() {
        final PreferredStock ps = new PreferredStock(GIN, 8d, 100d, 10d);
        assertNotEquals(ps, null, "Should NOT be equal");
    }

    @Test
    void nonEquality2() {
        final PreferredStock ps = new PreferredStock(GIN, 8d, 100d, 10d);
        final PreferredStock ps2 = new PreferredStock(GEN, 8d, 100d, 10d);
        assertNotEquals(ps, ps2, "Should NOT be equal");
    }

    @Test
    void nonEquality3() {
        final PreferredStock ps = new PreferredStock(GIN, 8d, 100d, 10d);
        final PreferredStock ps2 = new PreferredStock(GIN, 0d, 100d, 10d);
        assertNotEquals(ps, ps2, "Should NOT be equal");
    }

    @Test
    void nonEquality4() {
        final PreferredStock ps = new PreferredStock(GIN, 8d, 100d, 10d);
        final PreferredStock ps2 = new PreferredStock(GIN, 8d, 101d, 10d);
        assertNotEquals(ps, ps2, "Should NOT be equal");
    }

    @Test
    void nonEquality5() {
        final PreferredStock ps = new PreferredStock(GIN, 8d, 100d, 10d);
        final PreferredStock ps2 = new PreferredStock(GIN, 8d, 100d, 2d);
        assertNotEquals(ps, ps2, "Should NOT be equal");
    }

    @Test
    void nonEquality6() {
        final CommonStock cs = new CommonStock(TEA, 0d, 10d);
        final PreferredStock ps = new PreferredStock(GIN, 0d, 100d, 1d);
        assertNotEquals(ps, cs, "Should NOT be equal");
    }

    @Test
    void nonEquality7() {
        final CommonStock cs = new CommonStock(TEA, 0d, 10d);
        final PreferredStock ps = new PreferredStock(GIN, 0d, 100d, 1d);
        assertNotEquals(cs, ps, "Should NOT be equal");
    }
}
