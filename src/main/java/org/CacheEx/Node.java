package org.CacheEx;

/**
 * The Node that is used be the {@link CustomLinkedList}
 * @param <K> the key
 * @param <V> the value
 */
public class Node<K, V>
{
    private K key;
    private V value;
    private Node<K, V> next, prev;
    private long frequency;

    public Node(K key, V value)
    {
        this.key = key;
        this.value = value;
        next = prev = null;
        frequency = 1;
    }

    public V getValue() {return value;}
    public void setValue(V value) {this.value = value;}

    public K getKey() {return key;}
    public void setKey(K key) {this.key = key;}

    public Node<K, V> getNext() {return next;}
    public void setNext(Node<K, V> next) {this.next = next;}

    public Node<K, V> getPrev() {return prev;}
    public void setPrev(Node<K, V> prev) {this.prev = prev;}

    public long getFrequency() {return frequency;}
    public void incrementFrequency() {frequency++;}

    @Override
    public String toString() {return "Key=" + key + ", Value=" + value;}
}