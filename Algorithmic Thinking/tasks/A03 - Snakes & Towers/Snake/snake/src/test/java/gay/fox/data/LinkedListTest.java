package gay.fox.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LinkedListTest
{
    @Test
    public void testGetFirst()
    {
        LinkedList<Integer> list = new LinkedList<>();

        list.addItem(0, 0);
        list.addItem(1, 1);
        list.addItem(2, 2);

        assertEquals(0, list.getFirst());
    }

    @Test
    public void testGetLast()
    {
        LinkedList<Integer> list = new LinkedList<>();

        list.addItem(0, 0);
        list.addItem(1, 1);
        list.addItem(2, 2);

        assertEquals(2, list.getLast());
    }


    @Test
    public void testAdd()
    {
        LinkedList<Integer> list = new LinkedList<>();

        list.addItem(0, 0);
        list.addItem(1, 1);
        list.addItem(2, 2);

        assertEquals(0, list.get(0));
        assertEquals(1, list.get(1));
        assertEquals(2, list.get(2));
        assertEquals(3, list.getSize());
    }

    @Test
    public void testAppend()
    {
        LinkedList<Integer> list = new LinkedList<>();

        list.append(1);
        list.append(2);
        list.append(3);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
        assertEquals(3, list.getSize());

    }

    @Test
    public void testRemove()
    {
        LinkedList<Integer> list = new LinkedList<>();

        list.append(1);
        list.append(2);
        list.append(3);

        list.remove(1);

        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
        assertEquals(2, list.getSize());
    }

    @Test
    public void testRemoveFirst()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);

        list.removeFirst();

        assertEquals(2, list.get(0));
        assertEquals(3, list.get(1));
        assertEquals(2, list.getSize());
    }

    @Test
    public void testRemoveLast()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);

        list.removeLast();

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(2, list.getSize());
    }

    @Test
    public void testIterator()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);

        IO.println(list.toNodeString());

        int i = 0;

        for (Integer element : list)
        {
            assertEquals(list.get(i), element);
            IO.println(element);
            i++;
        }

        assertEquals(i, list.getSize());
    }
}

