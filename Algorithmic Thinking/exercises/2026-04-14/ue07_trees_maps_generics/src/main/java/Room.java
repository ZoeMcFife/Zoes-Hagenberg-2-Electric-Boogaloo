public class Room implements Comparable<Room> {
    private final String name;
    private final String description;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(Room other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }
}
