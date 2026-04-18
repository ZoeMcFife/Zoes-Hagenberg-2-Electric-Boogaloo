package gay.fox.dungeon.query;

import gay.fox.dungeon.DungeonPlayer;
import gay.fox.dungeon.DungeonRun;
import gay.fox.dungeon.DungeonRunner;

import java.util.*;

public class DungeonQueries_B
{
    static List<DungeonRun> runs = DungeonRunner.runDungeons(20, 42L);

    static void main()
    {
        IO.println("=== SURVIVORS ===");
        for (DungeonRun run : filterSurvivors())
            IO.println(run.asShortString());

        IO.println("\n=== TOP 5 RUNS BY GOLD ===");
        for (DungeonRun run : topRuns(5))
            IO.println(run.asShortString());

        IO.println("\n=== TOTAL GOLD ACROSS ALL RUNS ===");
        IO.println(totalGold() + "g");

        IO.println("\n=== RUNS BY PLAYER ===");
        for (Map.Entry<String, List<DungeonRun>> entry : groupByPlayers().entrySet())
        {
            IO.println("\n~ " + entry.getKey() + " ~");
            for (DungeonRun run : entry.getValue())
                IO.println("  " + run.asShortString());
        }
    }

    static List<DungeonRun> filterSurvivors()
    {
        List<DungeonRun> survivors = new ArrayList<>();

        for (DungeonRun dungeonRun : runs)
        {
            if (dungeonRun.survived())
            {
                survivors.add(dungeonRun);
            }
        }

        return survivors;
    }

    static List<DungeonRun> topRuns(int n)
    {
        List<DungeonRun> topRuns = new ArrayList<>();
        List<DungeonRun> sorted = sortRunsByGoldDesc();

        for (int i = 0; i < n; i++)
        {
            topRuns.add(sorted.get(i));
        }

        return topRuns;
    }

    static int totalGold()
    {
        int gold = 0;

        for (DungeonRun dungeonRun : runs)
        {
            gold += dungeonRun.goldAcquired();
        }

        return gold;
    }

    static HashMap<String, List<DungeonRun>> groupByPlayers()
    {
        HashMap<String, List<DungeonRun>> playerRuns = new HashMap<>();

        for (DungeonRun dungeonRun : runs)
        {
            if (playerRuns.containsKey(dungeonRun.name()))
            {
                playerRuns.get(dungeonRun.name()).add(dungeonRun);
            }
            else
            {
                playerRuns.put(dungeonRun.name(), new ArrayList<>());
                playerRuns.get(dungeonRun.name()).add(dungeonRun);
            }
        }

        return playerRuns;
    }

    static List<DungeonRun> sortRunsByGoldDesc()
    {
        List<DungeonRun> sortedRuns = new ArrayList<>(runs);

        for (int i = 0; i < sortedRuns.size() - 1; i++)
        {
            for (int j = 0; j < sortedRuns.size() - i - 1; j++)
            {
                if (sortedRuns.get(j).goldAcquired() < sortedRuns.get(j + 1).goldAcquired())
                {
                    DungeonRun temp = sortedRuns.get(j);
                    sortedRuns.set(j, sortedRuns.get(j + 1));
                    sortedRuns.set(j + 1, temp);
                }
            }
        }

        return sortedRuns;
    }
}
