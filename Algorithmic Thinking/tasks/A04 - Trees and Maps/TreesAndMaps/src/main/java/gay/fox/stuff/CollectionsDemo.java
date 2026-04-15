package gay.fox.stuff;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionsDemo {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // Sample data: dungeon rooms
        // ----------------------------------------------------------------
        BinarySearchTree<Room> directory = new BinarySearchTree<>();
        directory.insert(new Room("Hall",     "The grand entrance hall"));
        directory.insert(new Room("Armory",   "Weapons line the walls"));
        directory.insert(new Room("Vault",    "A locked treasure room"));
        directory.insert(new Room("Crypt",    "Eerie silence fills this room"));
        directory.insert(new Room("Dungeon",  "Prisoners once kept here"));
        directory.insert(new Room("Entrance", "The dungeon entrance"));

        System.out.println("=== Room Directory (BST inorder = alphabetical) ===");
        directory.inorder().forEach(System.out::println);

        System.out.println("\n=== BFS from root (level-by-level) ===");
        directory.bfs().forEach(r -> System.out.print(r.name() + "  "));
        System.out.println();

        // ----------------------------------------------------------------
        // Sample data: dungeon items
        // ----------------------------------------------------------------
        List<Item> allItems = List.of(
            new Item("Iron Sword",      "weapon", 8,  120),
            new Item("Health Potion",   "potion", 1,   50),
            new Item("Magic Staff",     "weapon", 5,  200),
            new Item("Rusty Dagger",    "weapon", 3,   40),
            new Item("Vault Key",       "key",    1,  300),
            new Item("Mana Potion",     "potion", 1,   80),
            new Item("Dragon Shield",   "weapon", 15, 250),
            new Item("Elixir",          "potion", 2,  150)
        );

        // ----------------------------------------------------------------
        // Part 3a: HashMap — O(1) item lookup by name
        // ----------------------------------------------------------------
        System.out.println("\n=== HashMap: O(1) item lookup by name ===");
        HashMap<String, Item> inventory = new HashMap<>();
        for (Item item : allItems) {
            inventory.put(item.name(), item);
        }
        System.out.println("Looking up 'Vault Key': " + inventory.get("Vault Key"));
        System.out.println("Looking up 'Banana':    " + inventory.get("Banana")); // null

        // ----------------------------------------------------------------
        // Part 3b: TreeMap — same data, sorted by key (alphabetical)
        // ----------------------------------------------------------------
        System.out.println("\n=== TreeMap: items in alphabetical order ===");
        TreeMap<String, Item> sortedInventory = new TreeMap<>(inventory);
        sortedInventory.forEach((name, item) -> System.out.println("  " + name + " -> " + item));

        // ----------------------------------------------------------------
        // Part 3c: TreeSet — visited rooms (sorted, no duplicates)
        // ----------------------------------------------------------------
        System.out.println("\n=== TreeSet: visited rooms (sorted, no duplicates) ===");
        TreeSet<String> visited = new TreeSet<>();
        visited.add("Hall");
        visited.add("Armory");
        visited.add("Hall");    // duplicate — silently ignored
        visited.add("Crypt");
        visited.add("Armory");  // duplicate — silently ignored
        System.out.println("Visited: " + visited); // [Armory, Crypt, Hall]

        // ----------------------------------------------------------------
        // Part 3d: ArrayList + Comparator — sort loot by value desc, weight asc
        // ----------------------------------------------------------------
        System.out.println("\n=== ArrayList + Comparator: loot sorted by value desc, weight asc ===");
        List<Item> loot = new ArrayList<>(allItems);
        loot.sort(Comparator.comparingInt(Item::value).reversed()
                            .thenComparingInt(Item::weight));
        loot.forEach(item -> System.out.println("  " + item));

        // ----------------------------------------------------------------
        // Part 4a: Streams — filter weapons, sorted by value desc, collect names
        // ----------------------------------------------------------------
        System.out.println("\n=== Streams: weapon names sorted by value (desc) ===");
        List<String> weaponNames = allItems.stream()
            .filter(i -> i.type().equals("weapon"))
            .sorted(Comparator.comparingInt(Item::value).reversed())
            .map(Item::name)
            .collect(Collectors.toList());
        System.out.println(weaponNames);

        // ----------------------------------------------------------------
        // Part 4b: Streams — group all items by type
        // ----------------------------------------------------------------
        System.out.println("\n=== Streams: items grouped by type ===");
        Map<String, List<Item>> byType = allItems.stream()
            .collect(Collectors.groupingBy(Item::type));
        byType.forEach((type, items) -> {
            System.out.println("  " + type + ":");
            items.forEach(item -> System.out.println("    " + item));
        });

        // ----------------------------------------------------------------
        // Part 4c: Streams — total value of all potions
        // ----------------------------------------------------------------
        int potionValue = allItems.stream()
            .filter(i -> i.type().equals("potion"))
            .mapToInt(Item::value)
            .sum();
        System.out.println("\n=== Streams: total potion value = " + potionValue + " gold ===");

        // ----------------------------------------------------------------
        // Part 4d: Streams — most valuable item per type (flatMap optional)
        // ----------------------------------------------------------------
        System.out.println("\n=== Streams: most valuable item per type ===");
        byType.forEach((type, items) -> {
            items.stream()
                .max(Comparator.comparingInt(Item::value))
                .ifPresent(best -> System.out.println("  " + type + " best: " + best));
        });
    }
}
