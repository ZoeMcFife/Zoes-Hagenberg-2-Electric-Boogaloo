package gay.fox.dungeon;

import java.util.List;

public class DungeonMain
{
    static void main()
    {
        List<DungeonRun> runs = DungeonRunner.runDungeons(20, 42L);

        for (DungeonRun dungeonRun : runs)
        {
            IO.println(dungeonRun.asShortString());
        }
    }
}
