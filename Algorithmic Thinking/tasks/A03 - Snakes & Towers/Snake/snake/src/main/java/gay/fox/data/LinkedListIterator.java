package gay.fox.data;

import java.util.Iterator;
import java.util.ListIterator;

public class LinkedListIterator<T> implements Iterator<T>
{
    Node<T> current;

    public LinkedListIterator(Node<T> head)
    {
        current = head;
    }

    @Override
    public boolean hasNext()
    {
        return current != null;
    }

    @Override
    public T next()
    {
        T data = current.data;
        current = current.next;

        return data;
    }
}
