package org.CacheEx;

import java.util.HashMap;

/**
 * the LRU cache implementation
 * @param <K> the key
 * @param <V> the value
 */
public class CacheImpl<K, V> implements Cache<K, V>
{
    //private final Cache<K, V> cache; // for decoder pattern
    private final int capacity;
    private int size;
    private final HashMap<K, Node<K, V>> map;
    private final CustomLinkedList<K, V> list;
    private long hitCount, missCount;
    private final CacheReplacementPolicy policy;

    public CacheImpl(int capacity, CacheReplacementPolicy policy)
    {
        this.policy = policy;
        this.capacity = capacity;
        size = 0;
        map = new HashMap<>();
        list = new CustomLinkedList<>();
        hitCount = 0;
        missCount = 0;
    }

    @Override
    public V get(K key)
    {
        if(map.containsKey(key))
        {
            hitCount++;
            Node<K, V> node = map.get(key); //save the found node so we can use it
            list.moveToTail(node);          //move the accessed node to the tail
            return node.getValue();         //if key in map return node
        }
        missCount++;
        return null; //else return null
    }

    @Override
    public void put(K key, V value)
    {
        //if key exists move it to tail(most resent)
        Node<K, V> node = map.get(key);
        if(node != null)
        {
            updateExistingNode(node, value);
            return;
        }
        //if current size has reached the capacity, delete head (LRU) or tail (MRU) depending on the policy from the list and the map
        if(size == capacity)
        {
            if (policy == CacheReplacementPolicy.LRU) evictLRU();
            else evictMRU();
        }
        //add new node at the tail(Most resent) of the list
        addNewNode(key, value);
    }

    //============================ Helper Methods ============================
    //encapsulates the behaviour of the least recently used item removal
    private void evictLRU()
    {
        Node<K, V> LRUnode = list.deleteHead();
        map.remove(LRUnode.getKey());
        size--;
    }
    //encapsulates the behaviour of the most recently used item removal
    private void evictMRU()
    {
        Node<K, V> MRUnode = list.deleteTail();
        map.remove(MRUnode.getKey());
        size--;
    }
    //moves node to tail and updates the value(if its different)
    private void updateExistingNode(Node<K, V> node, V value)
    {
        node.setValue(value);
        list.moveToTail(node);
    }
    //adds a new node to the linked list and map and increments the size
    private void addNewNode(K key, V value)
    {
        Node<K, V> newNode = new Node<>(key, value);
        map.put(key, newNode);
        list.insertAtTail(newNode);
        size++;
    }
    //========================================================================

    @Override
    public int getSize() {return size;}

    @Override
    public long getHitCount(){return hitCount;}

    @Override
    public long getMissCount(){return missCount;}
}