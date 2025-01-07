package org.CacheEx;

import java.util.Random;

public class App {
    public static void main(String[] args) {

        Random random = new Random();
        Cache<Integer, Integer> cache = new CacheImpl<>(100_000);
        for (int i = 0; i < 100_000; i++) {

            if (random.nextInt(10) < 7) {
                cache.put(random.nextInt(100_000), random.nextInt(100_000));
            } else {

            }
        }
    }
}