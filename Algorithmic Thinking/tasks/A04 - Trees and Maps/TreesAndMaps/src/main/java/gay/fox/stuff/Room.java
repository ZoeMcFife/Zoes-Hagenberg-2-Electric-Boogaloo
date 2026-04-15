package gay.fox.stuff;

public record Room(String name, String description) implements Comparable<Room> {

    @Override
    public int compareTo(Room other)
    {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString()
    {
        return name + " (" + description + ")";
    }
}
