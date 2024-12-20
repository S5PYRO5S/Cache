package org.CacheEx;

/**
 * Custom Doubly Linked List implementation for the {@link LRUCache} Data Structure
 * @param <K>  The key of a Node
 * @param <V>  The value of a Node
 */
public class CustomLinkedList<K, V>
{
    private Node<K, V> head, tail;

    public CustomLinkedList()
    {
        head = tail = null;
    }

    //getters for tail and head
    public Node<K, V> getHead() {return head;}
    public Node<K, V> getTail() {return tail;}

    //clear method (maybe useless)
    public void clear() {head = tail = null;}
    public boolean isEmpty(){return head == null;}

    public void insertAtTail(Node<K, V> newNode)
    {
        if(newNode == null) return; //null check
        if (isEmpty()) head = tail = newNode;
        else
        {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
    }

    public void insertAtHead(Node<K, V> newNode)
    {
        if(newNode == null) return;
        if (isEmpty()) head = tail = newNode;
        else
        {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        }
    }

    public Node<K, V> deleteHead()
    {
        if(isEmpty()) return null;
        Node<K, V> temp = head;
        //if list has only 1 node
        if(head.getNext() == null) clear();
        else
        {
            head = head.getNext();
            head.setPrev(null);
        }
        return temp;
    }


    public Node<K, V> deleteTail()
    {
        if (isEmpty()) return null;
        Node<K, V> temp = tail;
        if (tail.getPrev() == null) clear();
        else
        {
            tail = tail.getPrev();
            tail.setNext(null);
        }
        return temp;
    }

    //(helper method) takes a node and removes it from the list
    private void detachFromList(Node<K, V> node)
    {
        if (node == null) return;   //null check

        //if node is the head
        if (node == head)
        {
            head = node.getNext();
            if (head != null) head.setPrev(null);
        }
        // if node is the tail
        else if (node == tail)
        {
            tail = node.getPrev();
            if (tail != null) tail.setNext(null);
        }
        //if node is in the list
        else
        {
            //remove the node from the list
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
        }

        // make node's next and prev pointers null
        node.setPrev(null);
        node.setNext(null);
    }


    public void moveToTail(Node<K, V> node)
    {
        if (node == tail || node == null) return; //null check

        //detach the node from its list
        detachFromList(node);

        // place the detached node at the tail
        node.setNext(null);
        node.setPrev(tail);
        tail.setNext(node);
        tail = node;
    }

    public void moveToHead(Node<K, V> node)
    {
        //similar process for the head
        if (node == head || node == null) return; //null check
        detachFromList(node);
        node.setPrev(null);
        node.setNext(head);
        head.setPrev(node);
        head = node;
    }

    //(helper method) takes a node and removes it from the list
//    private void detachFromList(Node<K, V> node)
//    {
//        // If the node is null, do nothing
//        if (node == null) return;
//
//        // Remove the node from the list (disconnect the node from its neighbors)
//        if (node.getPrev() != null) node.getPrev().setNext(node.getNext());
//        if (node.getNext() != null) node.getNext().setPrev(node.getPrev());
//
//        // Update head and tail if needed
//        if (node == head) head = node.getNext();
//        if (node == tail) tail = node.getPrev();
//
//        // If the list has only one element left
//        if (head == null) tail = null;
//
//        // Break the node's links to fully detach it
//        node.setNext(null);
//        node.setPrev(null);
//    }
}