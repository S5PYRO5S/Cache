package org.CacheEx;
/*
 * Project Cache Implementation - final part of the project assignment for the course Data Structures
 * Made by : it2023003, it2023052, it2023100
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

import java.util.Random;

public class App
{
    public static void main(String[] args)
    {
        int totalOperations = 100_000;
        int cacheCapacity = 2000;
        Random random = new Random();

        Cache<Integer, Integer> cache = new CacheImpl<>(cacheCapacity, CacheReplacementPolicy.LRU);

        int[] eightyPercentKeys = new int[1000];
        int[] twentyPercentKeys = new int[1000];

        for (int i = 0; i < eightyPercentKeys.length; i++)
        {
            eightyPercentKeys[i] = i;
            cache.put(eightyPercentKeys[i], eightyPercentKeys[i]);
            twentyPercentKeys[i] = i + eightyPercentKeys.length - 1;
            cache.put(twentyPercentKeys[i], twentyPercentKeys[i]);
        }

        for (int i = 0; i < totalOperations; i++)
        {
            //get something that does exist
            if(random.nextBoolean())
            {
                if(random.nextDouble() < 0.8) cache.get(random.nextInt(1000));//80 of the time get keys with index 0-999
                else cache.get(random.nextInt(1001) + 1000);//20 of the time get keys with index 1000-1999
            }
            else cache.get(2000 + random.nextInt(2001));    //get something that doesn't exist
        }

        System.out.printf("Total operations: %d\n", totalOperations);
        System.out.printf("Cache Hits: %d\n", cache.getHitCount());
        System.out.printf("Cache Misses: %d\n", cache.getMissCount());
        System.out.printf("Hit Rate: %.2f\n", (cache.getHitCount() / (double) totalOperations) * 100);
        System.out.printf("Miss Rate: %.2f\n", (cache.getMissCount() / (double) totalOperations) * 100);


    }
}