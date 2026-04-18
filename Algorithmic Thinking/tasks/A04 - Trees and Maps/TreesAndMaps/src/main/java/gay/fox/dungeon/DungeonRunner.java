package gay.fox.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonRunner
{
    public static List<DungeonRun> runDungeons(int count, long seed)
    {
        List<DungeonRun> runs = new ArrayList<DungeonRun>();
        Random rand = new Random(seed);

        for (int i = 0; i < count; i++)
        {
            Dungeon dungeon = new Dungeon(rand.nextLong(Long.MAX_VALUE), rand.nextInt(count * 10));
            runs.add(dungeon.runDungeon());
        }

        return runs;
    }
}
