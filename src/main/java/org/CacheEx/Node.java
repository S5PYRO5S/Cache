package org.example;

public class Node<T extends Comparable<T>>
{
    private T data;
    private Node<T> next, prev;

    public Node(T data, Node<T> next, Node<T> prev)
    {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public Node(T data, Node<T> prev)
    {
        this.data = data;
        this.prev = prev;
        this.next = null;
    }

    public Node(T data)
    {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    public T getData() {return data;}
    public void setData(T data) {this.data = data;}

    public Node<T> getNext() {return next;}
    public void setNext(Node<T> next) {this.next = next;}

    public Node<T> getPrev() {return prev;}
    public void setPrev(Node<T> prev) {this.prev = prev;}
}
