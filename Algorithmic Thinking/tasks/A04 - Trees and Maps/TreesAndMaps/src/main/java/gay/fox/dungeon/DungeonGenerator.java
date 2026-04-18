package gay.fox.dungeon;

import gay.fox.stuff.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonGenerator
{
    private final List<String> names = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();

    private final long seed;
    private final Random random;
    private final int roomCount;

    private final DungeonPlayer dungeonPlayer;
    private final List<DungeonRoom> dungeonRooms;

    public DungeonGenerator(int roomCount, long seed)
    {
        createNames();
        createItems();

        this.roomCount = roomCount;
        this.seed = seed;
        this.random = new Random(seed);

        dungeonPlayer = createDungeonPlayer();
        dungeonRooms = createDungeonRooms();
    }

    public DungeonPlayer getDungeonPlayer()
    {
        return dungeonPlayer;
    }

    public DungeonRoom getDungeonRoom(int index)
    {
        return dungeonRooms.get(index);
    }

    public List<DungeonRoom> getDungeonRooms()
    {
        return dungeonRooms;
    }

    private DungeonPlayer createDungeonPlayer()
    {
        return new DungeonPlayer(getRandomName(), random.nextInt(50, 150));
    }

    private List<DungeonRoom> createDungeonRooms()
    {
        List<DungeonRoom> dungeonRooms = new ArrayList<>();

        for (int i = 0; i < roomCount; i++)
        {
            dungeonRooms.add(createDungeonRoom());
        }

        return dungeonRooms;
    }

    private DungeonRoom createDungeonRoom()
    {
        return new DungeonRoom("Dungeon of " + getRandomName(), "", getRandomItems(), random.nextInt(25), random.nextInt(25), random.nextInt(100));
    }

    private String getRandomName()
    {
        return names.get(random.nextInt(names.size()));
    }

    private List<Item> getRandomItems()
    {
        List<Item> randomItems = new ArrayList<>();

        int itemCount = random.nextInt(3);

        for (int i = 0; i < itemCount; i++)
        {
            randomItems.add(items.get(random.nextInt(items.size())));
        }

        return randomItems;
    }

    public long getSeed()
    {
        return seed;
    }

    private void createNames()
    {
        // stupid formatting so i created a separate method to clean up...

        List<String> nameList = new ArrayList<>(List.of("Glarb Snootkins",
                "Zopplewurm the Mild",
                "Flib Norzax",
                "Bogglewump",
                "Drix Poontangle",
                "Snorflax the Unready",
                "Vorp Squibbleston",
                "Glorbnor",
                "Plumbthar of Grob",
                "Skeezlox",
                "Wumbledon Frix",
                "Quazzle Bort",
                "Droomp",
                "Zibbo Flarg",
                "Norb Thistlebuns",
                "Gleep Wortzenheimer",
                "Blatherskite McGurp",
                "Flonx",
                "Wobblethrix",
                "Snarfle Zun",
                "Ploopus the Damp",
                "Krix Fumbledore",
                "Nugblax",
                "Thumwick Bleargh",
                "Splrx",
                "Gorple Snagworth",
                "Zumble Frax",
                "Blorticus",
                "Winklenob",
                "Plogg of the Dim Vale",
                "Fizzwidget Zorn",
                "Squimbo Larph",
                "Clorp Neebleson",
                "Thungle McSnort",
                "Bweep Florznak",
                "Gussock the Moist",
                "Zax Fumbleby",
                "Nooble Vrex",
                "Binglesworth Plop",
                "Glorpnik",
                "Snaggle Pfunk",
                "Trix Wobblox",
                "Mulchwort",
                "Zibb Floonberger",
                "Gorpus Snaggleton",
                "Bleeg Norzfinkle",
                "Bumblethwaite the Third",
                "Splorg",
                "Dinkle Morbifax",
                "Quorp Zibblax",
                "Flibbet of Gnorb",
                "Vrix Smoldersnoot",
                "Wumbus Flargle",
                "Blorpnix",
                "Snodwick Fumbleplonk",
                "Gleeb Wonkafrix",
                "Throbble Dungsworth",
                "Zog Nurpleson",
                "Pondsworth Blerg",
                "Frex Smibblehorn",
                "Grumplethwaite",
                "Plop Zorbnick",
                "Snorbert the Adequate",
                "Drizzle Flonksworth",
                "Quibb Norzfax",
                "Glumble Snortwick",
                "Vrex Ooblestein",
                "Waddleplop",
                "Fizznox",
                "Borgle Dumpmore",
                "Skeeb Wrinklefrix",
                "Murplethong",
                "Zinnox Squimble",
                "Thrumblewick",
                "Bleek Glorpfart",
                "Snarglewump",
                "Klorp Fibblenose",
                "Gob Twaddlesworth",
                "Frizzle Norbax",
                "Dumblethrix",
                "Voorp Snibbleton",
                "Wumplesnort",
                "Glib Florzwick",
                "Borkle the Perplexed",
                "Zeeble Clonkfrix",
                "Gobblewump",
                "Sqwirb Norzington",
                "Thribble Dunderplink",
                "Nib Glorzifax",
                "Fumblehorn the Pale",
                "Drex Snottlewick",
                "Blumbleplonk",
                "Zorp Fickleby",
                "Squibbles McWurm",
                "Fleeb Gonkfrix",
                "Gobsnort Wumblemore",
                "Klix Drizzlenob",
                "Thwumple",
                "Blerf Zomfington",
                "Lord Zaragothrax",
                "Angus McFife",
                "Florence Meean'kar",
                "Zoe McFife",
                "Jack Storm",
                "William Storm",
                "Nathaniel Storm",
                "Mina Gaia",
                "Snorkelwump the Bewildered"));

        names.addAll(nameList);
    }

    private void createItems()
    {
        items.add(new Item("Wobblestaff of Mild Inconvenience", "weapon", 4, 12));
        items.add(new Item("Helmet of Perpetual Confusion", "armour", 6, 18));
        items.add(new Item("Potion of Slightly Damp Courage", "potion", 1, 9));
        items.add(new Item("Boots of Accidental Tripping", "armour", 3, 7));
        items.add(new Item("Glarp's Rusty Zap Wand", "weapon", 2, 22));
        items.add(new Item("Cheese of the Void", "food", 1, 55));
        items.add(new Item("Orb of Uncertain Purpose", "relic", 5, 30));
        items.add(new Item("Flonk Crystal Shard", "relic", 1, 14));
        items.add(new Item("Snorflax's Leftover Sandwich", "food", 1, 2));
        items.add(new Item("Dagger of Passive Aggression", "weapon", 2, 17));
        items.add(new Item("Breastplate of Mild Embarrassment", "armour", 10, 25));
        items.add(new Item("Scroll of Barely Relevant Knowledge", "relic", 1, 8));
        items.add(new Item("Glarb Juice (Warm)", "potion", 1, 4));
        items.add(new Item("Cloak of Suspicious Odours", "armour", 4, 11));
        items.add(new Item("Zibbo's Broken Laser Pike", "weapon", 7, 19));
        items.add(new Item("Toenail of the Ancient Blorbax", "relic", 0, 40));
        items.add(new Item("Potion of Temporary Confidence", "potion", 1, 13));
        items.add(new Item("Ploopus Brand Protein Slurry", "food", 2, 3));
        items.add(new Item("Helmet of Minor Protection", "armour", 5, 10));
        items.add(new Item("Wumble Grenade (Dud)", "weapon", 3, 6));
        items.add(new Item("Amulet of Forgetting Where You Put Things", "relic", 0, 21));
        items.add(new Item("Stale Zorgon Biscuit", "food", 1, 1));
        items.add(new Item("Gauntlets of Fumbling", "armour", 4, 8));
        items.add(new Item("Snottlewick's Staff of Wobbling", "weapon", 5, 16));
        items.add(new Item("Fizznox-Brand Healing Paste", "potion", 1, 18));
        items.add(new Item("Shield of Barely Adequate Defense", "armour", 9, 20));
        items.add(new Item("Mysterious Damp Cloth", "junk", 1, 2));
        items.add(new Item("Flarg's Cursed Toenail Clipper", "junk", 0, 5));
        items.add(new Item("Potion of Mild Nausea", "potion", 1, 3));
        items.add(new Item("Droomp Horn (Slightly Cracked)", "weapon", 6, 14));
        items.add(new Item("Relic of the Forgotten Snorkelwump", "relic", 3, 45));
        items.add(new Item("Bag of Mostly Useless Dust", "junk", 2, 1));
        items.add(new Item("Gorple's Enchanted Sock", "junk", 0, 12));
        items.add(new Item("Sword of Vague Menace", "weapon", 5, 28));
        items.add(new Item("Quaff of Blorbax Marmalade", "food", 1, 7));
        items.add(new Item("Boots of Moderate Swiftness", "armour", 2, 15));
        items.add(new Item("Zax's Pocket Void Cube", "relic", 1, 35));
        items.add(new Item("Halfhearted Crossbow", "weapon", 6, 20));
        items.add(new Item("Potion of Questionable Origin", "potion", 1, 10));
        items.add(new Item("Throbble's Dented Shin Guard", "armour", 4, 9));
        items.add(new Item("Mouldy Starfarer's Ration", "food", 2, 2));
        items.add(new Item("Norb's Malfunctioning Stun Ring", "weapon", 0, 25));
        items.add(new Item("Scroll of Mild Suggestion", "relic", 1, 11));
        items.add(new Item("Leaking Plasma Flask", "junk", 2, 4));
        items.add(new Item("Dumblethrix Fur Vest", "armour", 5, 14));
        items.add(new Item("Potion of Remembering That One Thing", "potion", 1, 16));
        items.add(new Item("Ancient Crouton of Power", "food", 0, 33));
        items.add(new Item("Gloop Blaster (No Ammo)", "weapon", 4, 8));
        items.add(new Item("Cape of Dramatic Tripping", "armour", 3, 7));
        items.add(new Item("Skeezlox Brand Adhesive Bomb", "weapon", 2, 19));
        items.add(new Item("Orb of Mildly Interesting Visions", "relic", 4, 27));
        items.add(new Item("Flibbet's Slightly Magic Rock", "relic", 2, 9));
        items.add(new Item("Soggy Spellbook", "relic", 3, 13));
        items.add(new Item("Knuckle Dusters of Mild Discomfort", "weapon", 2, 11));
        items.add(new Item("Wumbledon's Stale Portal Wafer", "food", 0, 6));
        items.add(new Item("Jar of Suspicious Goo", "junk", 1, 3));
        items.add(new Item("Potion of Brief Invisibility (Legs Only)", "potion", 1, 20));
        items.add(new Item("Glorbnor's Cracked Plasma Buckler", "armour", 7, 18));
        items.add(new Item("Plogg's Enchanted Belly Button Fluff", "junk", 0, 1));
        items.add(new Item("Wand of Insignificant Sparks", "weapon", 1, 14));
        items.add(new Item("Cloak of Mild Mystery", "armour", 3, 12));
        items.add(new Item("Zorn's Emergency Cheese Wheel", "food", 4, 22));
        items.add(new Item("Blorpnix Bone Charm", "relic", 1, 17));
        items.add(new Item("Snorbert's Half-Eaten Ration Bar", "food", 1, 2));
        items.add(new Item("Potion of Slightly Improved Balance", "potion", 1, 8));
        items.add(new Item("Shield of Unreliable Blocking", "armour", 8, 15));
        items.add(new Item("Gnorb Crystal Lint Roller", "junk", 1, 4));
        items.add(new Item("Vorpal Spoon", "weapon", 1, 30));
        items.add(new Item("Squimbo's Leaking Jetpack", "junk", 8, 35));
        items.add(new Item("Potion of Unconvincing Bravery", "potion", 1, 7));
        items.add(new Item("Scarf of Mild Warmth", "armour", 1, 6));
        items.add(new Item("Bweep's Tactical Rubber Mallet", "weapon", 4, 10));
        items.add(new Item("Relic of Snaggle Pfunk", "relic", 2, 38));
        items.add(new Item("Mostly-Empty Healing Vial", "potion", 0, 5));
        items.add(new Item("Norb Thistlebuns' Lucky Pebble", "junk", 0, 15));
        items.add(new Item("Frex's Ration of Mystery Meat", "food", 2, 3));
        items.add(new Item("Breastplate of Questionable Craftsmanship", "armour", 11, 16));
        items.add(new Item("Potion of Temporary Horn Growth", "potion", 1, 12));
        items.add(new Item("Zeeble's Humming Trinket", "relic", 1, 22));
        items.add(new Item("Bag of Holding (Slightly Sticky)", "junk", 2, 40));
        items.add(new Item("Gussock's Damp Battle Mop", "weapon", 5, 11));
        items.add(new Item("Cursed Novelty Arrow", "weapon", 1, 9));
        items.add(new Item("Bleeg's Fizzling Grenade", "weapon", 3, 17));
        items.add(new Item("Potion of Remembering Spells Poorly", "potion", 1, 6));
        items.add(new Item("Gorpus's Enchanted Underpants", "armour", 1, 25));
        items.add(new Item("Stale Moonbread Loaf", "food", 2, 4));
        items.add(new Item("Thwumple's Barely-Glowing Pendant", "relic", 1, 19));
        items.add(new Item("Slightly Bent Plasma Sword", "weapon", 4, 23));
        items.add(new Item("Fumblehorn's Cracked War Horn", "weapon", 6, 13));
        items.add(new Item("Drizzle's Pocket Void Pebble", "relic", 0, 28));
        items.add(new Item("Glorp Blort Tonic", "potion", 1, 9));
        items.add(new Item("Nib's Shield Plank", "armour", 5, 8));
        items.add(new Item("Snarfle's Stasis Bread Roll", "food", 1, 10));
        items.add(new Item("Potion of Accidental Shrinkage", "potion", 1, 14));
        items.add(new Item("Winklenob's Ceremonial Fork", "junk", 1, 7));
        items.add(new Item("Vorp Squibbleston's Staff Stub", "weapon", 3, 15));
        items.add(new Item("Cloak of Semi-Invisibility", "armour", 2, 20));
        items.add(new Item("Zibbo's Emergency Glarp Jerky", "food", 1, 5));
        items.add(new Item("Orb of Complete Uselessness", "junk", 3, 0));
    }

}
