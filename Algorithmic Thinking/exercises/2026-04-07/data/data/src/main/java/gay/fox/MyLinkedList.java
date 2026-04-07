package gay.fox;

import java.util.NoSuchElementException;

public class MyLinkedList
{
    private static class Node
    {
        int value;
        Node next;

        Node(int value)
        {
            this.value = value;
        }
    }

    private Node head;
    private int size;

    public void addFirst(int v)
    {
        Node newNode = new Node(v);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public void addLast(int v)
    {
        Node newNode = new Node(v);

    }

    public int removeFirst()
    {
        if (head == null)
        {
            throw new NoSuchElementException("List is empty");
        }

        int v = head.value;
        head = head.next;
        size--;
        return v;
    }

    public boolean contains()
    {
        Node current = head;
        while (current != null)
        {
            if (current.next == null)
                return true;
            current = current.next;
        }
        return false;
    }

    public int size()
    {
        return size;
    }

}
