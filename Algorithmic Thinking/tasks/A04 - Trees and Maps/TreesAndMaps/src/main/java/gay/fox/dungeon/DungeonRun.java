package gay.fox.dungeon;

import gay.fox.stuff.Item;

import java.util.List;
import java.util.stream.Collectors;

public record DungeonRun(String name, int goldAcquired, int roomsVisited, boolean survived, List<Item> itemsCollected)
{
    public String asShortString()
    {
        return String.format("[%s] %s | Rooms: %d | Gold: %d | Items: %d | %s",
                survived ? "ALIVE" : "DEAD",
                name,
                roomsVisited,
                goldAcquired,
                itemsCollected.size(),
                survived ? "Survived" : "Perished");
    }

    @Override
    public String toString()
    {
        String status = survived ? "Survived" : "Perished";

        StringBuilder sb = new StringBuilder();
        sb.append("""
            ╔════════════════════════════════════════╗
            ║         DUNGEON RUN                    ║
            ╠════════════════════════════════════════╣
            ║ Hero:    %-30s║
            ║ Status:  %-30s║
            ║ Rooms:   %-30d║
            ║ Gold:    %-30d║
            ╠════════════════════════════════════════╣
            ║ Items collected: %-21d ║
            ╚════════════════════════════════════════╝
            """.formatted(name, status, roomsVisited, goldAcquired, itemsCollected.size()));

        for (int i = 0; i < itemsCollected.size(); i++)
        {
            sb.append(String.format("  %2d. %s%n", i + 1, itemsCollected.get(i)));
        }

        return sb.toString();
    }
}
