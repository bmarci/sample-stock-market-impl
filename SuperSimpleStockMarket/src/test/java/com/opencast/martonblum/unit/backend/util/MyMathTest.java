package com.opencast.martonblum.unit.backend.util;

import com.opencast.martonblum.backend.util.MyMath;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyMathTest {

    @Test
    void geometricMeanZeroElement() {
        final List<Double> numbers = new ArrayList<>();
        assertEquals(0d, MyMath.geometricMean(numbers));
    }

    @Test
    void geometricMeanOneElement() {
        final List<Double> numbers = Collections.singletonList(0d);
        assertEquals(0d, MyMath.geometricMean(numbers), "Should be able to handle 0");
    }

    @Test
    void geometricMeanOneElement2() {
        final List<Double> numbers = Collections.singletonList(1d);
        assertEquals(1d, MyMath.geometricMean(numbers), "Should return the value of the element");
    }

    @Test
    void geometricMeanTwoElements() {
        final List<Double> numbers = Arrays.asList(4d, 9d);
        assertEquals(6d, MyMath.geometricMean(numbers),
                "Should calculate the geometric mean correctly");
    }

    @Test
    void geometricMeanTwoElementsWithZero() {
        final List<Double> numbers = Arrays.asList(4d, 0d);
        assertEquals(0d, MyMath.geometricMean(numbers),
                "Should calculate the geometric mean correctly with zero");
    }
}
