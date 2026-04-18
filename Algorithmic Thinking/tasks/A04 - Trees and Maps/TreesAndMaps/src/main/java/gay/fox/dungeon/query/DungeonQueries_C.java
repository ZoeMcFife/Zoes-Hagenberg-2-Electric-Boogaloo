package gay.fox.dungeon.query;

import gay.fox.dungeon.DungeonRun;
import gay.fox.dungeon.DungeonRunner;

import java.util.*;
import java.util.stream.Collectors;

public class DungeonQueries_C
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

        IO.println("\n=== AVERAGE GOLD PER RUN ===");
        IO.println(averageGold() + "g");

        IO.println("\n=== BEST RUN PER PLAYER ===");
        for (Map.Entry<String, DungeonRun> entry : bestRunPerPlayer().entrySet())
            IO.println(entry.getKey() + " -> " + entry.getValue().asShortString());

        IO.println("\n=== RUN COUNT PER PLAYER ===");
        for (Map.Entry<String, Integer> entry : runCountPerPlayer().entrySet())
            IO.println(entry.getKey() + " -> " + entry.getValue() + " runs");

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
        return runs.stream().filter(DungeonRun::survived).toList();
    }

    static List<DungeonRun> topRuns(int n)
    {
        return runs.stream().sorted(Comparator.comparingInt(DungeonRun::goldAcquired)).toList().reversed().subList(0, n);
    }

    static int totalGold()
    {
        return runs.stream().mapToInt(DungeonRun::goldAcquired).sum();
    }

    static Map<String, List<DungeonRun>> groupByPlayers()
    {
        return runs.stream().collect(Collectors.groupingBy(DungeonRun::name));
    }

    static int averageGold()
    {
        return runs.stream().mapToInt(DungeonRun::goldAcquired).sum() / runs.size();
    }

    static Map<String, DungeonRun> bestRunPerPlayer()
    {
        Map<String, DungeonRun> bestRunPerPlayer = new HashMap<>();

        groupByPlayers()
                .forEach( (key, value) -> bestRunPerPlayer.put(
                        key,
                        value.stream().sorted(Comparator.comparingInt(DungeonRun::goldAcquired)
                        ).toList().getLast()));

        return bestRunPerPlayer;
    }

    static TreeMap<String, Integer> runCountPerPlayer()
    {
        TreeMap<String, Integer> runCountPerPlayer = new TreeMap<>();

        groupByPlayers()
                .forEach((key, value) -> runCountPerPlayer.put(key, value.size()));

        return runCountPerPlayer;
    }
}
