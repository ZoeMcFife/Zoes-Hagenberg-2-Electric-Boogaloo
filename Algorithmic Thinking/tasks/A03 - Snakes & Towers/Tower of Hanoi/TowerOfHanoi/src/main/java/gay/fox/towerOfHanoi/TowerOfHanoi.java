package gay.fox.towerOfHanoi;

import java.util.List;

public class TowerOfHanoi
{
    private final PegStack[] pegStacks = new PegStack[3];

    private final PegStack a = new PegStack();
    private final PegStack b  = new PegStack();
    private final PegStack c  = new PegStack();

    private final int size;

    public TowerOfHanoi(int size)
    {
        this.size = size;

        for (int i = this.size; i > 0; i--)
        {
            a.push(i);
        }

        pegStacks[0] = a;
        pegStacks[1] = b;
        pegStacks[2] = c;
    }

    public boolean isSolved()
    {
        return c.size() == size;
    }

    public void move(int from, int to)
    {
        if (from <= 0 || from > 3)
        {
            throw new IndexOutOfBoundsException("from must be between 1 and 3");
        }

        if (to <= 0 || to > 3)
        {
            throw new IndexOutOfBoundsException("to must be between 1 and 3");
        }

        if (from == to)
        {
            throw new IllegalArgumentException("from and to must be equal");
        }

        from = from - 1;
        to = to - 1;

        if (pegStacks[from].empty())
        {
            throw new IllegalArgumentException("from is empty");
        }

        Integer disk = pegStacks[from].pop();

        pegStacks[to].push(disk);
    }

    public void printStacks()
    {
        int stackSize = Math.max(a.size(), Math.max(b.size(), c.size())) + 1;

        List<String> aList = a.getPegListWithSize(stackSize);
        List<String> bList = b.getPegListWithSize(stackSize);
        List<String> cList = c.getPegListWithSize(stackSize);

        for (int i = 0; i < stackSize; i++)
        {
            IO.print(" ");
            IO.print(" ");
            IO.print(aList.get(i));
            IO.print(" ");
            IO.print(" ");
            IO.print(bList.get(i));
            IO.print(" ");
            IO.print(" ");
            IO.print(cList.get(i));
            IO.println();
        }

        IO.print("-");
        IO.print("-");
        IO.print("1");
        IO.print("-");
        IO.print("-");
        IO.print("2");
        IO.print("-");
        IO.print("-");
        IO.print("3");
        IO.print("-");
        IO.print("-");
        IO.println();

    }
}
