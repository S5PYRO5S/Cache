package org.CacheEx;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * the LRU cache implementation
 *
 * @param <K> the key
 * @param <V> the value
 */
public class CacheImpl<K, V> implements Cache<K, V> {
    //private final Cache<K, V> cache; // for decoder pattern
    protected final int capacity;
    protected int size;
    protected final HashMap<K, Node<K, V>> map;
    private final CustomLinkedList<K, V> list;
    private final TreeMap<Long, CustomLinkedList<K, V>> frequencyMap;
    protected long hitCount, missCount;
    private final CacheReplacementPolicy policy;

    public CacheImpl(int capacity, CacheReplacementPolicy policy) {
        this.policy = policy;
        this.capacity = capacity;
        size = 0;
        hitCount = 0;
        missCount = 0;
        map = new HashMap<>();
        frequencyMap = new TreeMap<>();
        list = new CustomLinkedList<>();
    }

    @Override
    public V get(K key) {
        if (map.containsKey(key)) {
            hitCount++;
            Node<K, V> node = map.get(key); //save the found node so we can use it
            if (policy == CacheReplacementPolicy.LFU)
                incrementFrequency(node); //increment the frequency of the node if the policy is LFU
            else list.moveToTail(node);          //move the accessed node to the tail if the policy is LRU or MRU
            return node.getValue();         //if key in map return node
        }

        missCount++;
        return null; //else return null
    }

    @Override
    public void put(K key, V value) {
        //if key exists move it to tail(most resent)
        Node<K, V> node = map.get(key);
        if (node != null) {
            if (policy == CacheReplacementPolicy.LFU) {
                node.setValue(value);
                incrementFrequency(node);

            } else {
                updateExistingNode(node, value);
            }
            return;
        }
        //if current size has reached the capacity, delete head (LRU) or tail (MRU) depending on the policy from the list and the map
        if (size == capacity) {
            if (policy == CacheReplacementPolicy.LRU) evictLRU();
            else if (policy == CacheReplacementPolicy.MRU) evictMRU();
            else evictLFU();
        }

        addNewNode(key, value);
    }

    //============================ Helper Methods ============================
    //encapsulates the behaviour of the least recently used item removal
    private void evictLRU() {
        Node<K, V> LRUnode = list.deleteHead();
        map.remove(LRUnode.getKey());
        size--;
    }

    //encapsulates the behaviour of the most recently used item removal
    private void evictMRU() {
        Node<K, V> MRUnode = list.deleteTail();
        map.remove(MRUnode.getKey());
        size--;
    }

    private void evictLFU() {
        long lowestFrequency = frequencyMap.firstKey();
        Node<K, V> LFUnode = frequencyMap.get(lowestFrequency).deleteHead();
        if (frequencyMap.get(lowestFrequency).isEmpty()) frequencyMap.remove(lowestFrequency);
        map.remove(LFUnode.getKey());
        size--;
    }
    //moves node to tail and updates the value(if its different)

    private void updateExistingNode(Node<K, V> node, V value) {
        node.setValue(value);
        list.moveToTail(node);
    }

    //adds a new node to the linked list and map and increments the size
    private void addNewNode(K key, V value) {
        Node<K, V> newNode = new Node<>(key, value);
        map.put(key, newNode);
        if (policy == CacheReplacementPolicy.LFU)
            frequencyMap.computeIfAbsent(newNode.getFrequency(), k -> new CustomLinkedList<>()).insertAtTail(newNode); //add new node at the frequency list
        else
            list.insertAtTail(newNode); //add new node at the tail(Most resent) of the list
        size++;
    }

    private void incrementFrequency(Node<K, V> node) {
        long frequency = node.getFrequency();
        frequencyMap.get(frequency).detachFromList(node);
        if (frequencyMap.get(frequency).isEmpty()) frequencyMap.remove(frequency);
        node.incrementFrequency();
        frequencyMap.computeIfAbsent(node.getFrequency(), k -> new CustomLinkedList<>()).insertAtTail(node);
    }
    //========================================================================

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public long getHitCount() {
        return hitCount;
    }

    @Override
    public long getMissCount() {
        return missCount;
    }


//    public class LFUCache<K, V> extends CacheImpl<K, V> {
//
//        private final TreeMap<Long, CustomLinkedList<K, V>> frequencyMap;
//
//        public LFUCache(int capacity) {
//            super(capacity, CacheReplacementPolicy.LFU);
//            frequencyMap = new TreeMap<>();
//        }
//
//
//        @Override
//        public V get(K key) {
//            if (map.containsKey(key)) {
//                hitCount++;
//                Node<K, V> node = map.get(key);
//                incrementFrequency(node);
//                return node.getValue();
//            }
//            missCount++;
//            return null;
//        }
//
//        @Override
//        public void put(K key, V value) {
//
//            if (map.containsKey(key)) {
//                Node<K, V> node = map.get(key);
//                node.setValue(value);
//                incrementFrequency(node);
//                return;
//            }
//            if (size == capacity) {
//                evictLFU();
//            }
//            addNewNode(key, value);
//        }
//
//        private void evictLFU() {
//            long lowestFrequency = frequencyMap.firstKey();
//            Node<K, V> LFUnode = frequencyMap.get(lowestFrequency).deleteHead();
//            if (frequencyMap.get(lowestFrequency).isEmpty()) frequencyMap.remove(lowestFrequency);
//            frequencyMap.remove(LFUnode.getFrequency());
//            map.remove(LFUnode.getKey());
//            size--;
//        }
//
//        private void incrementFrequency(Node<K, V> node) {
//            long frequency = node.getFrequency();
//            frequencyMap.get(frequency).detachFromList(node);
//            if (frequencyMap.get(frequency).isEmpty()) frequencyMap.remove(frequency);
//            node.incrementFrequency();
//            frequencyMap.computeIfAbsent(node.getFrequency(), k -> new CustomLinkedList<>()).insertAtTail(node);
//        }
//
//        private void addNewNode(K key, V value) {
//            Node<K, V> newNode = new Node<>(key, value);
//            map.put(key, newNode);
//            frequencyMap.computeIfAbsent(newNode.getFrequency(), k -> new CustomLinkedList<>()).insertAtTail(newNode);
//            size++;
//        }
//    }
}