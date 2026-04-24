package gay.fox;

import java.util.*;

public class InvertStack
{
    void main()
    {
        Stack<Integer> stack = new Stack<>();

        Queue<Integer> queue = new LinkedList<>();

        queue.offer(6);
        queue.offer(7);
        queue.offer(8);

        IO.println(queue);

        Stack<Integer> stackReverse = new Stack<>();

        stackReverse.push(queue.poll());
        stackReverse.push(queue.poll());
        stackReverse.push(queue.poll());


        queue.offer(stackReverse.pop());
        queue.offer(stackReverse.pop());
        queue.offer(stackReverse.pop());

        IO.println(queue);


        stack.push(1);
        stack.push(2);
        stack.push(3);

        IO.println(stack);

        Stack<Integer> stack2 = new Stack<>();

        for (int i = 0; i < 3 ; i++)
        {
            stack2.push(stack.pop());
        }

        /*for (Integer i : list)
        {
            stack.push(i);
        }*/

        IO.println(stack2);
    }


}
