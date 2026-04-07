package gay.fox;

public class Stack
{
    private final int[] data;
    private int top;

    public Stack(int capacity)
    {
        data = new int[capacity];
        top = -1;
    }

    public void push(int value)
    {
        if (top == data.length - 1)
        {
            throw new RuntimeException("Stack overflow - capacity " + data.length + " exceeded!");
        }

        data[++top] = value;
    }

    public int pop()
    {
        if (isEmpty())
        {
            throw new RuntimeException("Stack is empty! Cannot Pop!");
        }

        return data[top--];
    }

    public int peek()
    {
        if (isEmpty())
        {
            throw new RuntimeException("Stack is empty! Cannot Peek!");
        }

        return data[top];
    }

    public int size()
    {
        return top + 1;
    }

    @Override
    public String  toString()
    {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i <= top; i++)
        {
            sb.append(data[i]);
            sb.append(", ");
        }

        sb.append("]");
        return sb.toString();
    }

    public boolean isEmpty()
    {
        return top == -1;
    }
}
