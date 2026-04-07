package gay.fox;

import java.util.NoSuchElementException;

public class Queue
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

    private int size;
    private Node head;
    private Node tail;

    public void offer(int v)
    {
        Node node = new Node(v);

        if (tail == null)
        {
            head = tail = node;
        }
        else
        {
            tail.next = node;
            tail = tail.next;
        }
        size++;
    }

    public int poll()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException("Queue is empty");
        }

        int v = head.value;
        head = head.next;
        size--;
        return v;
    }

    public int peek()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException("Queue is empty");
        }

        return head.value;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("[");
        Node current = head;
        while (current != null)
        {
            sb.append(current.value + ", ");
            current = current.next;
        }

        return sb.append("]").toString();
    }

    public boolean isEmpty()
    {
        return size == 0;
    }
}
