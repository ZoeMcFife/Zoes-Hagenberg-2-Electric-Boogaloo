package gay.fox.item_shop;

import java.util.ArrayList;
import java.util.List;

public class ShopDemo
{
    public static Shop shop = new Shop();

    public static List<Customer> customers = new ArrayList<>();

    public static void main()
    {
        setCustomers();
        stockShop();

        customers.forEach(shop::processSale);
        customers.forEach(shop::processSale);
    }

    public static void setCustomers()
    {
        /*Auto generated random customers*/

        customers.addAll(new ArrayList<>(List.of(
                // budget in aUEC
                new Customer("Jax Renly",       1500, true),   // Bounty hunter, trigger-happy
                new Customer("Vera Osei",        3000, false),  // Cargo hauler, wants protection
                new Customer("Drex Korvath",     7000, true),   // Pirate, big budget & loves offence
                new Customer("Sable Tannis",     500,  false),  // New citizen, budget-conscious medic
                new Customer("Admiral Rix Vane", 9999, false)  // UEE Navy, max budget & pure defence
        )));
    }

    public static void stockShop()
    {
        /*Auto generated random items*/

        List<ShopItem> items = new ArrayList<>(List.of(
                // Weapons
                new ShopItem("Klaus & Werner P8-SC",  "SMG",        2,  420,  800, 18,  3, 2),
                new ShopItem("Kastak Arms Devastator", "Shotgun",    5,  900, 1400, 28,  1, 1),
                new ShopItem("Gemini LH86 Pistol",    "Pistol",     1,  180,  350, 10,  2, 1),
                // Armor
                new ShopItem("Lightstrike V Helmet",  "Helmet",     3,  500,  900,  0, 20, 2),
                new ShopItem("Pyro RGD Chest Plate",  "Armor",      8, 1100, 1800,  0, 35, 1),
                new ShopItem("Novikov EVA Suit",       "Space Suit", 6,  800, 1300,  1, 28, 1),
                // Ship Components
                new ShopItem("M7A Laser Cannon",      "Ship Weapon",20, 4000, 6500, 50,  0, 1),
                new ShopItem("AllMax S2 Shield Gen",  "Shield",     15, 3200, 5000,  0, 60, 2),
                // Gear / Consumables
                new ShopItem("Stims MedPen",          "Medical",    0,   80,  120,  0,  5,2),
                new ShopItem("BRT4 Breacher Grenade", "Explosive",  1,  300,  500, 22,  0, 3)
        ));
        items.forEach(shop::restock);
    }
}
