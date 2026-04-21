package gay.fox;

public class MyLinkedList
{
    private Node head;

    static class Node
    {
        int value;
        Node next;
        Node (int v)
        {
            value = v;
        }
    }

    public void addFirst(int value)
    {
        Node n = new Node(value);
        n.next = head;
        head = n;
    }

    public void addLast(int value)
    {
        if (head == null)
        {
            head = new Node(value);
            return;
        }

        Node curr = head;

        while (curr.next != null)
        {
            curr = curr.next;
        }

        curr.next = new Node(value);
    }

    public String toString ()
    {
        StringBuilder sb = new StringBuilder () ;
        Node current = head ;
        while ( current != null ) {
            sb . append ( current . value ) ;
            if ( current . next != null ) sb . append ( " -> " ) ;
            current = current . next ;
        }
        return sb . toString () ;
    }
}
