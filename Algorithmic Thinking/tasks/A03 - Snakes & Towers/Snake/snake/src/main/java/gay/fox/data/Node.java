package gay.fox.data;

import java.util.Iterator;

public class Node<T> implements Iterator<T>
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

    @Override
    public boolean hasNext()
    {
        return next != null;
    }

    @Override
    public T next()
    {
        return next.data;
    }

    // the garbage collector should technically get rid of this node from memory
    // i find it a little cursed, i want to remove it manually
    @Override
    public void remove()
    {
        prev.next = next;
        next.prev = prev;
    }
}
