package meow;

import java.util.List;

class Main
{
    static void main()
    {

    }

    public static <T extends Valued> int totalValue(List<T> items)
    {
        return items.stream().mapToInt(Valued::value).sum();
    }
}
