package org.CacheEx;

import org.junit.Test;

import java.util.HashSet;
import java.util.Random;

import static org.junit.Assert.*;

public class CacheImplTest
{
    static int MIN_CACHE_CAPACITY = 3;
    static int MAX_CACHE_CAPACITY = 10_000;
    static int SEED_FOR_RANDOM = 17;

    @Test
    public void testPutAndGetMethods()
    {
        Cache<Integer, String> cache = new CacheImpl<>(MIN_CACHE_CAPACITY, CacheReplacementPolicy.LRU);

        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        assertEquals("one", cache.get(1));
        assertEquals("two", cache.get(2));
        assertEquals("three", cache.get(3));
    }

    @Test
    public void testLRURemoval()
    {
        Cache<Integer, String> cache = new CacheImpl<>(MIN_CACHE_CAPACITY, CacheReplacementPolicy.LRU);

        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        cache.put(4, "four");

        assertNull(cache.get(1)); // Key 1 should be evicted
        assertEquals("two", cache.get(2));
        assertEquals("three", cache.get(3));
        assertEquals("four", cache.get(4));
    }

    @Test
    public void testMRURemoval()
    {
        Cache<Integer, String> cache = new CacheImpl<>(MIN_CACHE_CAPACITY, CacheReplacementPolicy.MRU);

        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        cache.put(4, "four");

        assertNull(cache.get(3)); // Key 3 should be evicted
        assertEquals("two", cache.get(2));
        assertEquals("one", cache.get(1));
        assertEquals("four", cache.get(4));
    }

    @Test
    public void testLRUKeyUpdate()
    {
        Cache<Integer, String> cache = new CacheImpl<>(MIN_CACHE_CAPACITY, CacheReplacementPolicy.LRU);

        cache.put(1, "one");
        cache.put(1, "new one");

        assertEquals("new one", cache.get(1));
        assertEquals(1, cache.getSize()); //no size increase on update
    }

    @Test
    public void testMRUKeyUpdate()
    {
        Cache<Integer, String> cache = new CacheImpl<>(MIN_CACHE_CAPACITY, CacheReplacementPolicy.MRU);

        cache.put(1, "one");
        cache.put(1, "new one");

        assertEquals("new one", cache.get(1));
        assertEquals(1, cache.getSize()); //no size increase on update
    }

    @Test
    public void testLRUOrder()
    {
        Cache<Integer, String> cache = new CacheImpl<>(MIN_CACHE_CAPACITY, CacheReplacementPolicy.LRU);

        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        cache.get(1);           //moves 1 node to the tail(most recent) of the list
        cache.put(4, "four");   //the cache size is now 4 >= capacity (evict least recently used node (2))

        //check cache items
        assertEquals("one", cache.get(1));
        assertEquals("three", cache.get(3));
        assertEquals("four", cache.get(4));
        assertNull(cache.get(2));   //check if LRU was evicted
    }

    @Test
    public void testMRUOrder()
    {
        Cache<Integer, String> cache = new CacheImpl<>(MIN_CACHE_CAPACITY, CacheReplacementPolicy.MRU);

        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        cache.get(1);           //moves 1 node to the tail(most recent) of the list
        cache.put(4, "four");   //the cache size is now 4 >= capacity (evict most recently used node (1))

        //check cache items
        assertEquals("two", cache.get(2));
        assertEquals("three", cache.get(3));
        assertEquals("four", cache.get(4));
        assertNull(cache.get(1));   //check if MRU was evicted
    }

    @Test
    public void testLRUEdgeCase()
    {
        Cache<Integer, String> cache = new CacheImpl<>(MIN_CACHE_CAPACITY, CacheReplacementPolicy.LRU);
        assertNull(cache.get(1));   //test item that doesn't exist
    }

    @Test
    public void testMRUEdgeCase()
    {
        Cache<Integer, String> cache = new CacheImpl<>(MIN_CACHE_CAPACITY, CacheReplacementPolicy.MRU);
        assertNull(cache.get(1));   //test item that doesn't exist
    }

    @Test
    public void testLRUStressTest()
    {
        Cache<Integer, String> cache = new CacheImpl<>(MAX_CACHE_CAPACITY, CacheReplacementPolicy.LRU);
        HashSet<Integer> insertedKeys = new HashSet<>();    //to keep track of inserted keys

        Random random = new Random(SEED_FOR_RANDOM);    //init random with a static seed

        //insert double the amount of items in the cache
        for(int i = 0; i < MAX_CACHE_CAPACITY * 2; i++)
        {
            int key = random.nextInt(MAX_CACHE_CAPACITY * 2);//make a random key
            cache.put(key, "value" + key);                         //the value is the string "value" combined with the key
            insertedKeys.add(key);
        }

        //for every key that was inserted
        for(int key : insertedKeys)
        {
            String expected = cache.get(key); //get the value
            //if the value is null that means it was evicted
            //verifies that the retrieved value matches the value("value" + key)
            if(expected != null) assertEquals("value" + key, expected);
        }
    }

    @Test
    public void testMRUStressTest()
    {
        Cache<Integer, String> cache = new CacheImpl<>(MAX_CACHE_CAPACITY, CacheReplacementPolicy.MRU);
        HashSet<Integer> insertedKeys = new HashSet<>();

        Random random = new Random(SEED_FOR_RANDOM);

        for(int i = 0; i < MAX_CACHE_CAPACITY * 2; i++)
        {
            int key = random.nextInt(MAX_CACHE_CAPACITY * 2);
            cache.put(key, "value" + key);
            insertedKeys.add(key);
        }

        for(int key : insertedKeys)
        {
            String expected = cache.get(key);
            if(expected != null) assertEquals("value" + key, expected);
        }
    }
}