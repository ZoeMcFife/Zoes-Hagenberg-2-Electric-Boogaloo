package gay.fox.data;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class LinkedList<E> implements Iterable<E>
{
    private Node<E> head;
    private Node<E> tail;

    private int size;

    public LinkedList()
    {
        head = null;
        tail = null;
        size = 0;
    }

    public void addFirst(E item)
    {
        Node<E> newHead = new Node<>(item);

        if (head != null)
        {
            newHead.next = head;
            head.prev = newHead;
        }

        head = newHead;

        size++;
    }

    public void append(E item)
    {
        addLast(item);
    }

    public void addLast(E item)
    {
        Node<E> newTail = new Node<>(item);

        if (size == 0)
        {
            addFirst(item);
            return;
        }

        if (tail != null)
        {
            newTail.prev = tail;
            tail.next = newTail;
        }

        if (tail == null)
        {
            newTail.prev = head;
            head.next = newTail;
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
        if (isEmpty())
        {
            throw new IndexOutOfBoundsException("List is empty");
        }

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
        if (isEmpty())
        {
            throw new IndexOutOfBoundsException("List is empty");
        }

        Node<E> oldHead = head;

        head = oldHead.next;

        oldHead.remove();

        size--;
    }

    public void removeLast()
    {
        if (isEmpty())
        {
            throw new IndexOutOfBoundsException("List is empty");
        }

        Node<E> oldTail = tail;

        tail = oldTail.prev;

        oldTail.remove();

        size--;
    }

    private Node<E> getNode(int index)
    {
        if (isEmpty())
        {
            throw new IndexOutOfBoundsException("List is empty");
        }

        if(index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0)
        {
            return head;
        }

        if  (index == size - 1)
        {
            if (tail == null)
            {
                return head;
            }

            return tail;
        }

        Node<E> current;

        if (index <= size / 2)
        {
            current = head;

            for (int i = 0; i < index; i++)
            {
                current = current.next;
            }
        }
        else
        {
            current = tail;

            for (int i = size; i > index; i--)
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

    public E getFirst()
    {
        return get(0);
    }

    public E getLast()
    {
        return get(size - 1);
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder("[");

        Node<E>  current = head;

        for (int i = 0;  i != size; i++)
        {
            result.append(current.data);

            if (current.next != null)
            {
                current = current.next;
            }

            if (i != size - 1)
            {
                result.append(", ");
            }
        }

        return result.append("]").toString();
    }

    public String toNodeString()
    {
        StringBuilder result = new StringBuilder("[");

        Node<E>  current = head;

        for (int i = 0;  i != size; i++)
        {
            result.append(current);

            if (current.next != null)
            {
                current = current.next;
            }

            if (i != size - 1)
            {
                result.append(", ");
            }
        }

        return result.append("]").toString();
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int getSize()
    {
        return size;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new LinkedListIterator<>(head);
    }
}
