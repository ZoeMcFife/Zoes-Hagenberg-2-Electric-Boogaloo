package at.fhooe.ald.pokemon.client.dto;

public class TrainerDto {

    private int id;
    private String name;
    private int badgeCount;

    public TrainerDto() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getBadgeCount() { return badgeCount; }
    public void setBadgeCount(int badgeCount) { this.badgeCount = badgeCount; }

    @Override
    public String toString() {
        return String.format("Trainer[%d] %-15s  badges: %d", id, name, badgeCount);
    }
}
