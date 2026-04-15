package gay.fox.stuff;

/**
 * @param type "weapon", "potion", or "key"
 */
public record Item(String name, String type, int weight, int value)
{
    @Override
    public String toString()
    {
        return name + " [" + type + ", w=" + weight + ", v=" + value + "]";
    }
}
