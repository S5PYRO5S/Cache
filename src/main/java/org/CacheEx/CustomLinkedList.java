package org.example;

public class CustomLinkedList<T extends Comparable<T>>
{
    private Node<T> head, tail;
    private int size;
    private final int maxCapacity;

    public CustomLinkedList(int maxCapacity)
    {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.maxCapacity = maxCapacity;
    }

    public boolean isEmpty() {return head == null && tail == null;}
    public int getSize(){return size;}
    public void clear(){head = null;tail = null;size = 0;}

    public void insertAtTail(T data)
    {
        Node<T> newNode = new Node<T>(data);
        if(size == maxCapacity) deleteHead();
        if(isEmpty()) head=tail=newNode;
        else
        {
            newNode.setPrev(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }
    public void insertAtHead(T data)
    {
        Node<T> newNode = new Node<>(data);
        if(isEmpty()) head=tail=newNode;
        else
        {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        }
        size++;
    }

    public Node<T> deleteHead()
    {
        if(isEmpty()) return null;
        if(head == tail) {clear(); return null;}
        Node<T> temp = head;
        head.getNext().setPrev(null);
        head.setNext(null);
        head = temp.getNext();
        return temp;
    }
    public Node<T> deleteTail()
    {
        if(isEmpty()) return null;
        if(head == tail) {clear(); return null;}
        Node<T> temp = tail;
        tail.getPrev().setNext(null);
        tail.setPrev(null);
        tail = temp.getPrev();
        return temp;
    }

}

