package gay.fox.data;

import java.util.Iterator;
import java.util.function.Consumer;

public class Node<T>
{
    public T data;
    public Node<T> next = null;
    public Node<T> prev = null;

    public Node(T data)
    {
        this.data = data;
    }

    public Node(T data, Node<T> next, Node<T> prev)
    {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public Node(T data, Node<T> next)
    {
        this.data = data;
        this.next = next;
    }

    // the garbage collector should technically get rid of this node from memory
    // i find it a little cursed, i want to remove it manually
    public void remove()
    {
        if (prev != null)
            prev.next = next;
        if (next != null)
            next.prev = prev;
    }

    @Override
    public String toString()
    {
        String prevString = prev == null ? "null" : prev.data.toString();
        String nextString = next == null ? "null" : next.data.toString();

        return "Node[" + data.toString() + ", prev: " +  prevString + ", next: " + nextString + "]";
    }
}
