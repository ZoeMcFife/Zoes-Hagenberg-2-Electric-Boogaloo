package gay.fox.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


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
    public void testRemoveLastOnSmallList()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);

        list.removeLast();

        assertTrue(list.isEmpty());
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

    // ===== CONTAINS / FIND / COUNT =====

    @Test
    public void testContainsTrue()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);

        assertTrue(list.contains(2));
    }

    @Test
    public void testContainsFalse()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(2);

        assertFalse(list.contains(99));
    }

    @Test
    public void testContainsOnEmptyList()
    {
        LinkedList<Integer> list = new LinkedList<>();
        assertFalse(list.contains(1));
    }

    @Test
    public void testFind()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);

        assertEquals(2, list.find(2));
    }

    @Test
    public void testFindNotPresent()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(2);

        assertNull(list.find(99));
    }

    @Test
    public void testCountOccurrences()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(2);
        list.append(2);
        list.append(3);

        assertEquals(2, list.countOccurrences(2));
    }

    @Test
    public void testCountOccurrencesNoneFound()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(2);

        assertEquals(0, list.countOccurrences(99));
    }

    @Test
    public void testCountOccurrencesOnEmptyList()
    {
        LinkedList<Integer> list = new LinkedList<>();
        assertEquals(0, list.countOccurrences(1));
    }

    // the other ones are auto generated tests (lazy developer)

// ===== SET =====

    @Test
    public void testSet()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);

        list.set(1, 99);

        assertEquals(99, list.get(1));
        assertEquals(3, list.getSize()); // size shouldn't change
    }

    @Test
    public void testSetFirst()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(2);

        list.set(0, 99);

        assertEquals(99, list.getFirst());
    }

    @Test
    public void testSetLast()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(2);

        list.set(1, 99);

        assertEquals(99, list.getLast());
    }

// ===== addItem (middle insertion) =====

    @Test
    public void testAddItemInMiddle()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(3);

        list.addItem(2, 1);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
        assertEquals(3, list.getSize());
    }

    @Test
    public void testAddItemAtStart()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(2);
        list.append(3);

        list.addItem(1, 0);

        assertEquals(1, list.getFirst());
        assertEquals(3, list.getSize());
    }

    @Test
    public void testAddItemAtEnd()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(2);

        list.addItem(3, 2);

        assertEquals(3, list.getLast());
        assertEquals(3, list.getSize());
    }

// ===== EXCEPTIONS =====

    @Test
    public void testGetOutOfBoundsThrows()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(5));
    }

    @Test
    public void testRemoveFromEmptyThrows()
    {
        LinkedList<Integer> list = new LinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
    }

    @Test
    public void testRemoveFirstFromEmptyThrows()
    {
        LinkedList<Integer> list = new LinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, list::removeFirst);
    }

    @Test
    public void testRemoveLastFromEmptyThrows()
    {
        LinkedList<Integer> list = new LinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, list::removeLast);
    }

    @Test
    public void testAddItemOutOfBoundsThrows()
    {
        LinkedList<Integer> list = new LinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> list.addItem(1, 5));
    }

// ===== EDGE CASES =====

    @Test
    public void testSingleElementList()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(42);

        assertEquals(42, list.getFirst());
        assertEquals(42, list.getLast());
        assertEquals(1, list.getSize());
    }

    @Test
    public void testIsEmptyAfterRemovingAllElements()
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(2);

        list.removeFirst();
        list.removeFirst();

        assertTrue(list.isEmpty());
    }

    @Test
    public void testLargeListTraversal()
    {
        LinkedList<Integer> list = new LinkedList<>();

        for (int i = 0; i < 1000; i++)
        {
            list.append(i);
        }

        for (int i = 0; i < 1000; i++)
        {
            assertEquals(i, list.get(i));
        }
    }
}

