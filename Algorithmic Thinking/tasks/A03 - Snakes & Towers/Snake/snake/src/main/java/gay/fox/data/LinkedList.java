package gay.fox.data;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class LinkedList<E> implements Iterable<E>
{
    private Node<E> head;
    private Node<E> tail;

    private int size = 0;

    public LinkedList()
    {

    }

    public void addFirst(E item)
    {
        Node<E> newHead = new Node<>(item);

        if (head != null)
        {
            newHead.next = head;
            head.prev = newHead;
        }

        if (tail == null)
        {
            tail = head;
        }

        head = newHead;

        size++;
    }

    public void addLast(E item)
    {
        Node<E> newTail = new Node<>(item);

        if (tail != null)
        {
            newTail.prev = tail;
            tail.next = newTail;
        }
        tail = newTail;

        size++;
    }

    public void addItem(E  item, int index)
    {
        if (index < 0 || index > size)
        {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0)
        {
            addFirst(item);
        }
        else if (index == size)
        {
            addLast(item);
        }
        else
        {
            Node<E> oldNode = getNode(index - 1);

            Node<E> newNode = new Node<>(item);
            newNode.prev = oldNode.prev;
            newNode.next = oldNode;
            oldNode.prev = newNode;

            size++;
        }
    }

    public void remove(int index)
    {
        if  (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0)
        {
            removeFirst();
        }
        else if (index == size - 1)
        {
            removeLast();
        }
        else
        {
            Node<E> node = getNode(index);

            node.remove();
            size--;
        }
    }

    public void removeFirst()
    {
        Node<E> oldHead = head;

        head = oldHead.next;

        oldHead.remove();

        size--;
    }

    public void removeLast()
    {
        Node<E> oldTail = tail;

        tail = oldTail.prev;

        oldTail.remove();

        size--;
    }

    private Node<E> getNode(int index)
    {
        if(index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException();
        }

        Node<E> current = head;

        if (index < size / 2)
        {
            for (int i = index; i < size / 2; i++)
            {
                current = current.next;
            }
        }
        else
        {
            for (int i = index; i < size - 1; i++)
            {
                current = current.prev;
            }
        }

        return current;
    }

    public E get(int index)
    {
        return getNode(index).data;
    }

    @Override
    public Iterator<E> iterator()
    {
        return head;
    }

    @Override
    public void forEach(Consumer<? super E> action)
    {
        Iterable.super.forEach(action);
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder("[");

        forEach(e -> result.append(e.toString()).append(", "));

        return result.append("]").toString();
    }
}
