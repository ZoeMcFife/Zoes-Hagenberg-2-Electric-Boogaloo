package gay.fox.dungeon;

import gay.fox.stuff.Item;
import gay.fox.stuff.Room;

import java.util.ArrayList;
import java.util.List;

public class DungeonRoom extends Room
{
    private final List<Item> items = new ArrayList<>();
    private final int roomDamage;
    private final int roomHealth;
    private final int gold;

    public DungeonRoom(String name, String description, List<Item> items, int roomDamage, int roomHealth, int gold)
    {
        super(name, description);
        this.items.addAll(items);
        this.roomDamage = roomDamage;
        this.roomHealth = roomHealth;
        this.gold = gold;
    }

    public int getRoomDamage()
    {
        return roomDamage;
    }

    public int getRoomHealth()
    {
        return roomHealth;
    }

    public int getGold()
    {
        return gold;
    }

    public List<Item> getItems()
    {
        return items;
    }
}


