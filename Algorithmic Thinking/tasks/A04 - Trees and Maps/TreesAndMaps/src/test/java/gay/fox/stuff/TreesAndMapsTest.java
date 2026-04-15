package gay.fox.stuff;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TreesAndMapsTest {

    // =========================================================================
    // BST Tests
    // =========================================================================

    @Nested
    @DisplayName("BinarySearchTree")
    class BSTTests {

        @Test
        @DisplayName("isEmpty() returns true on a new tree")
        void emptyTreeIsEmpty() {
            // Given
            BinarySearchTree<String> bst = new BinarySearchTree<>();
            // Then
            assertTrue(bst.isEmpty());
        }

        @Test
        @DisplayName("contains() returns true after insert")
        void insertAndContains() {
            // Given
            BinarySearchTree<String> bst = new BinarySearchTree<>();
            // When
            bst.insert("Hall");
            bst.insert("Armory");
            bst.insert("Vault");
            // Then
            assertTrue(bst.contains("Hall"));
            assertTrue(bst.contains("Armory"));
            assertTrue(bst.contains("Vault"));
        }

        @Test
        @DisplayName("contains() returns false for a value never inserted")
        void containsReturnsFalseForMissing() {
            // Given
            BinarySearchTree<String> bst = new BinarySearchTree<>();
            bst.insert("Hall");
            bst.insert("Crypt");
            // Then
            assertFalse(bst.contains("Banana"));
            assertFalse(bst.contains("Vault"));
        }

        @Test
        @DisplayName("inorder() returns all values in ascending sorted order")
        void inorderIsSorted() {
            // Given
            BinarySearchTree<String> bst = new BinarySearchTree<>();
            bst.insert("Hall");
            bst.insert("Armory");
            bst.insert("Vault");
            bst.insert("Crypt");
            bst.insert("Dungeon");
            bst.insert("Entrance");
            // When
            List<String> result = bst.inorder();
            // Then
            List<String> sorted = new ArrayList<>(result);
            Collections.sort(sorted);
            assertEquals(sorted, result, "inorder() must yield values in ascending order");
        }

        @Test
        @DisplayName("inorder() on Room BST matches alphabetical order by name")
        void inorderRoomIsSortedByName() {
            // Given
            BinarySearchTree<Room> bst = new BinarySearchTree<>();
            bst.insert(new Room("Hall",    "Grand hall"));
            bst.insert(new Room("Armory",  "Weapon store"));
            bst.insert(new Room("Vault",   "Treasure room"));
            bst.insert(new Room("Crypt",   "Eerie silence"));
            bst.insert(new Room("Dungeon", "Dark cells"));
            // When
            List<String> names = bst.inorder().stream()
                    .map(Room::getName)
                    .collect(Collectors.toList());
            // Then
            assertEquals(List.of("Armory", "Crypt", "Dungeon", "Hall", "Vault"), names);
        }

        @Test
        @DisplayName("preorder() visits root before its subtrees")
        void preorderVisitsRootFirst() {
            // Given  — insert "Hall" first so it becomes the root
            BinarySearchTree<String> bst = new BinarySearchTree<>();
            bst.insert("Hall");
            bst.insert("Armory");
            bst.insert("Vault");
            // When
            List<String> result = bst.preorder();
            // Then — root must be the first element
            assertEquals("Hall", result.get(0));
        }

        @Test
        @DisplayName("postorder() visits root after its subtrees")
        void postorderVisitsRootLast() {
            // Given  — insert "Hall" first so it becomes the root
            BinarySearchTree<String> bst = new BinarySearchTree<>();
            bst.insert("Hall");
            bst.insert("Armory");
            bst.insert("Vault");
            // When
            List<String> result = bst.postorder();
            // Then — root must be the last element
            assertEquals("Hall", result.get(result.size() - 1));
        }

        @Test
        @DisplayName("bfs() visits root first, then root's children before deeper nodes")
        void bfsLevelOrder() {
            // Given — "Hall" is root; "Armory" goes left, "Vault" goes right
            BinarySearchTree<String> bst = new BinarySearchTree<>();
            bst.insert("Hall");
            bst.insert("Armory");
            bst.insert("Vault");
            // When
            List<String> result = bst.bfs();
            // Then — Hall (root), then Armory + Vault (level 1)
            assertEquals("Hall",   result.get(0));
            assertEquals("Armory", result.get(1));
            assertEquals("Vault",  result.get(2));
        }

        @Test
        @DisplayName("bfs() on empty tree returns empty list")
        void bfsEmptyTree() {
            // Given
            BinarySearchTree<Integer> bst = new BinarySearchTree<>();
            // Then
            assertTrue(bst.bfs().isEmpty());
        }

        @Test
        @DisplayName("duplicate inserts are silently ignored")
        void duplicateInsertIgnored() {
            // Given
            BinarySearchTree<String> bst = new BinarySearchTree<>();
            bst.insert("Hall");
            bst.insert("Hall");
            bst.insert("Hall");
            // Then — only one entry
            assertEquals(1, bst.inorder().size());
        }
    }

    // =========================================================================
    // Collections Tests
    // =========================================================================

    @Nested
    @DisplayName("Collections")
    class CollectionsTests {

        private List<Item> sampleItems() {
            return new ArrayList<>(List.of(
                    new Item("Iron Sword",    "weapon", 8,  120),
                    new Item("Health Potion", "potion", 1,   50),
                    new Item("Magic Staff",   "weapon", 5,  200),
                    new Item("Vault Key",     "key",    1,  300),
                    new Item("Mana Potion",   "potion", 1,   80)
            ));
        }

        @Test
        @DisplayName("HashMap: get() returns item after put()")
        void hashMapGetByName() {
            // Given
            HashMap<String, Item> map = new HashMap<>();
            Item sword = new Item("Iron Sword", "weapon", 8, 120);
            // When
            map.put(sword.getName(), sword);
            // Then
            assertEquals(sword, map.get("Iron Sword"));
            assertNull(map.get("Banana"));
        }

        @Test
        @DisplayName("TreeMap: iterating keySet() gives alphabetical order")
        void treeMapIteratesAlphabetically() {
            // Given
            TreeMap<String, Item> map = new TreeMap<>();
            for (Item item : sampleItems()) {
                map.put(item.getName(), item);
            }
            // When
            List<String> keys = new ArrayList<>(map.keySet());
            // Then
            List<String> sorted = new ArrayList<>(keys);
            Collections.sort(sorted);
            assertEquals(sorted, keys, "TreeMap keys must be in alphabetical order");
        }

        @Test
        @DisplayName("TreeSet: adding duplicate room names keeps only one entry")
        void treeSetDeduplicates() {
            // Given
            TreeSet<String> visited = new TreeSet<>();
            // When
            visited.add("Hall");
            visited.add("Armory");
            visited.add("Hall");    // duplicate
            visited.add("Armory");  // duplicate
            visited.add("Vault");
            // Then
            assertEquals(3, visited.size());
            assertTrue(visited.contains("Hall"));
        }

        @Test
        @DisplayName("TreeSet: iteration yields room names in sorted order")
        void treeSetIsSorted() {
            // Given
            TreeSet<String> visited = new TreeSet<>();
            visited.add("Vault");
            visited.add("Armory");
            visited.add("Hall");
            // When
            List<String> result = new ArrayList<>(visited);
            // Then
            assertEquals(List.of("Armory", "Hall", "Vault"), result);
        }

        @Test
        @DisplayName("ArrayList + Comparator: sort by value descending")
        void listSortedByValueDesc() {
            // Given
            List<Item> items = sampleItems();
            // When
            items.sort(Comparator.comparingInt(Item::getValue).reversed());
            // Then — Vault Key (300) must be first, Health Potion (50) must be last
            assertEquals("Vault Key",     items.get(0).getName());
            assertEquals("Health Potion", items.get(items.size() - 1).getName());
        }

        @Test
        @DisplayName("Streams: filter keeps only weapons")
        void streamFilterWeapons() {
            // Given
            List<Item> items = sampleItems();
            // When
            List<Item> weapons = items.stream()
                    .filter(i -> i.getType().equals("weapon"))
                    .collect(Collectors.toList());
            // Then
            assertTrue(weapons.stream().allMatch(i -> i.getType().equals("weapon")));
            assertEquals(2, weapons.size());
        }

        @Test
        @DisplayName("Streams: total value of all potions")
        void streamPotionValueSum() {
            // Given
            List<Item> items = sampleItems();
            // When
            int total = items.stream()
                    .filter(i -> i.getType().equals("potion"))
                    .mapToInt(Item::getValue)
                    .sum();
            // Then: Health Potion 50 + Mana Potion 80 = 130
            assertEquals(130, total);
        }

        @Test
        @DisplayName("Streams: groupingBy produces one entry per type")
        void streamGroupingByType() {
            // Given
            List<Item> items = sampleItems();
            // When
            Map<String, List<Item>> byType = items.stream()
                    .collect(Collectors.groupingBy(Item::getType));
            // Then
            assertTrue(byType.containsKey("weapon"));
            assertTrue(byType.containsKey("potion"));
            assertTrue(byType.containsKey("key"));
            assertEquals(2, byType.get("weapon").size());
            assertEquals(2, byType.get("potion").size());
        }
    }
}
