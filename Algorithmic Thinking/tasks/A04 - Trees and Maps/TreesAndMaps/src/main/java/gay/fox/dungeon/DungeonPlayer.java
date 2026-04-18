package gay.fox.dungeon;

import gay.fox.stuff.Item;

import java.util.ArrayList;
import java.util.List;

public class DungeonPlayer
{
    private final String name;
    private int gold = 0;
    private int roomsVisited = 0;
    private int health;
    private final List<Item> items = new ArrayList<>();

    private boolean isDead = false;

    public DungeonPlayer(String name, int health)
    {
        this.name = name;
        this.health = health;
    }

    public void damage(int amount)
    {
        health -= amount;

        if (health <= 0)
        {
            isDead = true;
        }
    }

    public void heal(int amount)
    {
        health += amount;
    }

    public boolean isDead()
    {
        return isDead;
    }

    public void roomVisited()
    {
        roomsVisited++;
    }

    public int getRoomsVisited()
    {
        return roomsVisited;
    }

    public void addGold(int gold)
    {
        this.gold += gold;
    }

    public String getName()
    {
        return name;
    }

    public int getGold()
    {
        return gold;
    }

    public void addItem(Item item)
    {
        items.add(item);
    }

    public void addItems(List<Item> items)
    {
        this.items.addAll(items);
    }

    public List<Item> getItems()
    {
        return items;
    }
}
