package gay.fox.dungeon;

import java.util.List;

public class Dungeon
{
    private DungeonPlayer dungeonPlayer;
    private List<DungeonRoom> dungeonRooms;

    public Dungeon(long seed, int roomCount)
    {
        createNewDungeon(seed, roomCount);
    }

    public void createNewDungeon(long seed, int roomCount)
    {
        DungeonGenerator dungeonGenerator = new DungeonGenerator(roomCount, seed);

        dungeonPlayer = dungeonGenerator.getDungeonPlayer();
        dungeonRooms = dungeonGenerator.getDungeonRooms();
    }

    public DungeonRun runDungeon()
    {
        for (DungeonRoom dungeonRoom : dungeonRooms)
        {
            runDungeonRoom(dungeonRoom);

            if (dungeonPlayer.isDead())
            {
                break;
            }
        }

        return new DungeonRun(  dungeonPlayer.getName(),
                                dungeonPlayer.getGold(),
                                dungeonPlayer.getRoomsVisited(),
                                !dungeonPlayer.isDead(),
                                dungeonPlayer.getItems());
    }

    private void runDungeonRoom(DungeonRoom dungeonRoom)
    {
        dungeonPlayer.roomVisited();
        dungeonPlayer.addGold(dungeonRoom.getGold());
        dungeonPlayer.addItems(dungeonRoom.getItems());
        dungeonPlayer.damage(dungeonRoom.getRoomDamage());
        dungeonPlayer.heal(dungeonRoom.getRoomHealth());
    }
}
