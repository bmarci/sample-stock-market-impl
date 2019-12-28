package com.opencast.martonblum.backend.util;

import lombok.NonNull;

import java.util.List;

/**
 * Util class for common math calculations.
 */
public final class MyMath {

    private MyMath() { }

    /**
     * A simple implementation of geometric mean calculation.
     * @param numbers A list of numbers to calculate the geometric mean from.
     * @return The geometric mean of the input or zero if it's empty.
     */
    public static double geometricMean(@NonNull final List<Double> numbers) {
        if (numbers.isEmpty()) {
            return 0d;
        }
        return Math.pow((numbers.stream().reduce(1d, (a, b) -> a * b)), 1.0 / numbers.size());
    }
}
