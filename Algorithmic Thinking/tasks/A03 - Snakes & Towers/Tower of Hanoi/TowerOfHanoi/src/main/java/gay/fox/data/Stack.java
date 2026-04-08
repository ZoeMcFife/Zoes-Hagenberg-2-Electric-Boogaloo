package gay.fox.data;

import java.util.*;
import java.util.function.Consumer;

public class Stack<T> implements Iterable<T>
{
    private final List<T> data = new ArrayList<T>();

    protected void add(T item)
    {
        data.add(item);
    }

    public T push(T item)
    {
        data.add(item);

        return item;
    }

    public T pop()
    {
        if (data.isEmpty())
        {
            throw new EmptyStackException();
        }

        return data.removeLast();
    }

    public T peek()
    {
        if (data.isEmpty())
        {
            throw new EmptyStackException();
        }

        return data.getLast();
    }

    public boolean empty()
    {
        return data.isEmpty();
    }

    public int size()
    {
        return data.size();
    }

    @Override
    public String toString()
    {
        return data.toString();
    }

    @Override
    public Iterator<T> iterator()
    {
        return data.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action)
    {
        data.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator()
    {
        return data.spliterator();
    }
}
