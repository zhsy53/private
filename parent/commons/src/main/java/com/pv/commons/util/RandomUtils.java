package com.pv.commons.util;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.concurrent.ThreadLocalRandom;

public abstract class RandomUtils {
    private static final int[] POWERS_OF_10 = {1, 10, 100, 1000, 10_000, 100_000, 1_000_000, 10_000_000, 100_000_000, 1_000_000_000};

    public static long range(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }

    public static int range(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    @NotBlank
    public static String randomNumberString(@Positive int length) {
        assert length <= 10;
        return String.format("%0" + length + "d", (long) range(0, POWERS_OF_10[length] - 1));
    }
}
