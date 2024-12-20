package org.CacheEx;

import java.util.HashMap;

/**
 * the LRU cache implementation
 * @param <K> the key
 * @param <V> the value
 */
public class LRUCache<K, V> implements Cache<K, V>
{
    private final int capacity;
    private int size;
    private final HashMap<K, Node<K, V>> map;
    private final CustomLinkedList<K, V> list;

    public LRUCache(int capacity)
    {
        this.capacity = capacity;
        size = 0;
        map = new HashMap<>();
        list = new CustomLinkedList<>();
    }

    @Override
    public V get(K key)
    {
        if(map.containsKey(key))
        {
            Node<K, V> node = map.get(key); //save the found node so we can use it
            list.moveToTail(node);          //move the accessed node to the tail
            return node.getValue();         //if key in map return node
        }
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
        //if current size has reached the capacity, delete head (LRU) from the list and the map
        if(size == capacity) evictLRU();
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
}