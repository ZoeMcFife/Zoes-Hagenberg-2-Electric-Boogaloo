package gay.fox.towerOfHanoi;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PegStack extends Stack<Integer>
{
    @Override
    public Integer push(Integer item)
    {
        if (isEmpty())
        {
            add(item);
            return item;
        }

        if (peek().compareTo(item) >= 0)
        {
            add(item);
            return item;
        }
        else
        {
            throw new RuntimeException("Disk cannot be placed on smaller disk!");
        }
    }

    public List<String> getAllPegs()
    {
        List<String> result = new ArrayList<>();

        for (Integer i : this)
        {
            result.add(i.toString());
        }

        return result.reversed();
    }

    public List<String> getPegListWithSize(int size)
    {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < size - size(); i++)
        {
            result.add("|");
        }

        result.addAll(getAllPegs());

        return result;
    }
}
