package org.CacheEx;

import java.util.HashMap;

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
        if(map.containsKey(key)) return map.get(key).getValue(); //if key in map return node
        return null; //else return null
    }

    @Override
    public void put(K key, V value)
    {
        //if key exists move it to tail(most resent)
        if(map.containsKey(key))
        {
            list.moveToTail(map.get(key));
            return;
        }

        //if current size has reached the capacity, delete head (LRU) from the list and the map
        if(size == capacity)
        {
            Node<K, V> LRUnode = list.deleteHead();
            map.remove(LRUnode.getKey());
            size--;
        }
        //add new node at the tail(Most resent) of the list
        Node<K, V> newNode = new Node<>(key, value);
        map.put(key, newNode);
        list.insertAtTail(newNode);
        size++;
    }
}
