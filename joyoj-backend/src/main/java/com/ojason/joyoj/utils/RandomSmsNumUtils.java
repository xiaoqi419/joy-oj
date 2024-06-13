package com.ojason.joyoj.utils;

import java.util.Random;

public class RandomSmsNumUtils {
    private static final Random RANDOM = new Random();

    public static String getSixBitRandom() {
        int num = RANDOM.nextInt(900000) + 100000; // This will generate random numbers in the range 100000-999999
        return String.valueOf(num);
    }
}