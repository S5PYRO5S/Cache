package org.CacheEx;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * The Cache implementation
 * <p>
 *
 * The class has all the variables that are the same in all Cache replacements policies
 * And with the use of the Strategy Design Pattern it encapsulates the variables that
 * differ in the {@link CacheType} object that also has the necessary methods to do the correct operations
 * Also with the use of the Factory Pattern it determines the {@link CacheType}
 * based on the {@link CacheReplacementPolicy}
 *
 * @param <K> the key
 * @param <V> the value
 */
public class CacheImpl<K, V> implements Cache<K, V>
{
    private final CacheType<K, V> cacheType;
    private final int capacity;
    private int size;
    private final HashMap<K, Node<K, V>> map;
    private long hitCount, missCount;

    public CacheImpl(int capacity, CacheReplacementPolicy policy)
    {
        this.capacity = capacity;
        size = 0;
        hitCount = missCount = 0;
        map = new HashMap<>();
        cacheType = createCacheType(policy);
    }

    @Override
    public V get(K key)
    {
        Node<K, V> node = map.get(key);
        if (node != null)   //if the node exists
        {
            hitCount++;             //increment hit count
            cacheType.access(node); //do the necessary operation to the storing data structure
            return node.getValue(); //return node value
        }

        missCount++; //increment miss count
        return null; //return null
    }

    @Override
    public void put(K key, V value)
    {
        Node<K, V> node = map.get(key);
        if (node != null)   //if key exist
        {
            node.setValue(value);   //update the node value
            cacheType.access(node); //do the necessary operation to the storing data structure
            return;
        }
        //if current size has reached the capacity
        if (size == capacity)
        {
            node = cacheType.evict();   //evict node from storage
            map.remove(node.getKey());  //remove node from map
            size--;                     //decrement size
        }

        //add new node
        Node<K, V> newNode = new Node<>(key, value);
        map.put(key, newNode);      //put node to map
        cacheType.insert(newNode);  //put node to cacheType object
        size++;                     //increment size
    }

    @Override
    public int getSize() {return size;}

    @Override
    public long getHitCount() {return hitCount;}

    @Override
    public long getMissCount() {return missCount;}

    //factory pattern to create CacheType based on the CacheReplacementPolicy enum
    private CacheType<K, V> createCacheType(CacheReplacementPolicy policy)
    {
        switch (policy)
        {
            case LRU:
                return new LRU();
            case MRU:
                return new MRU();
            case LFU:
                return new LFU();
            default:
                throw new IllegalArgumentException("Unsupported cache replacement policy: " + policy);
        }
    }
    //================================= Inner Classes =======================================

    /**
     * The Interface that determines the behavior of the Cache Type objects
     * @param <K>
     * @param <V>
     */
    private interface CacheType<K, V>
    {
        void access(Node<K, V> node);
        void insert(Node<K, V> node);
        Node<K, V> evict();
    }

    private class LRU implements CacheType<K, V>
    {
        private final CustomLinkedList<K, V> list = new CustomLinkedList<>();

        @Override
        public void access(Node<K, V> node) {list.moveToTail(node);}

        @Override
        public void insert(Node<K, V> node) {list.insertAtTail(node);}

        @Override
        public Node<K, V> evict() {return list.deleteHead();}
    }

    private class MRU implements CacheType<K, V>
    {
        private final CustomLinkedList<K, V> list = new CustomLinkedList<>();

        @Override
        public void access(Node<K, V> node) {list.moveToTail(node);}

        @Override
        public void insert(Node<K, V> node) {list.insertAtTail(node);}

        @Override
        public Node<K, V> evict() {return list.deleteTail();}
    }

    private class LFU implements CacheType<K, V>
    {
        private final TreeMap<Long, CustomLinkedList<K, V>> frequencyMap = new TreeMap<>();

        @Override
        public void access(Node<K, V> node)
        {
            long frequency = node.getFrequency();
            frequencyMap.get(frequency).detachFromList(node);
            if (frequencyMap.get(frequency).isEmpty()) frequencyMap.remove(frequency);

            node.incrementFrequency();
            frequencyMap.computeIfAbsent(node.getFrequency(), k -> new CustomLinkedList<>()).insertAtTail(node);
        }

        @Override
        public void insert(Node<K, V> node)
        {
            frequencyMap.computeIfAbsent(node.getFrequency(), k -> new CustomLinkedList<>()).insertAtTail(node);
        }

        @Override
        public Node<K, V> evict()
        {
            long lowestFrequency = frequencyMap.firstKey();
            Node<K, V> node = frequencyMap.get(lowestFrequency).deleteHead();
            if (frequencyMap.get(lowestFrequency).isEmpty()) frequencyMap.remove(lowestFrequency);
            return node;
        }
    }
}