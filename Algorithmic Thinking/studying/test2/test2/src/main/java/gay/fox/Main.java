package gay.fox;

public class Main
{
	static void main()
    {
        MyLinkedList myLinkedList = new MyLinkedList();

        myLinkedList.addLast(1);
        myLinkedList.addLast(7);
        myLinkedList.addLast(3);

        IO.println(myLinkedList);

        myLinkedList.addFirst(9);

        IO.println(myLinkedList);
    }
}
