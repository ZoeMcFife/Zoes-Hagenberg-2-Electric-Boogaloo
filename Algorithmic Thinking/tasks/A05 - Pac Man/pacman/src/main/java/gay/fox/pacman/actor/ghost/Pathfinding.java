package gay.fox.pacman.actor.ghost;

import gay.fox.pacman.maze.layer.Layer;
import gay.fox.pacman.maze.tile.TilePosition;
import gay.fox.pacman.maze.tile.TileType;

import java.util.*;

public class Pathfinding
{
    public static List<TilePosition> findPath(TilePosition start, TilePosition goal, Layer traversalLayer)
    {
        if (start.getManhattanDistance(goal) <= 1)
        {
            return List.of(goal);
        }

        return bfsSearch(start, goal, traversalLayer);
    }

    private static boolean isTraversableTile(TilePosition pos, Layer traversalLayer)
    {
        return traversalLayer.getTileType(pos) == TileType.EMPTY;
    }

    public static List<TilePosition> getTraversableNeighbourTiles(TilePosition tile, Layer traversalLayer)
    {
        List<TilePosition> neighbours = new ArrayList<>();

        int[] rowDeltas = {1, -1, 0, 0};
        int[] colDeltas = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++)
        {
            TilePosition neighbourTile = new TilePosition(
                    tile.getRow() + rowDeltas[i],
                    tile.getCol() + colDeltas[i]
            );

            if (traversalLayer.getMaze().isValidTraversableTile(neighbourTile, traversalLayer))
            {
                neighbours.add(neighbourTile);
            }
        }

        return neighbours;
    }

    public static List<TilePosition> bfsSearch(TilePosition start, TilePosition goal, Layer traversalLayer)
    {
        Queue<TilePosition> queue = new LinkedList<>();
        Map<TilePosition, TilePosition> cameFrom = new HashMap<>();

        queue.add(start);
        cameFrom.put(start, null);

        while (!queue.isEmpty())
        {
            TilePosition current = queue.poll();

            if (current.equals(goal))
            {
                return reconstructPath(cameFrom, start, goal);
            }

            for (TilePosition neighbour : getTraversableNeighbourTiles(current, traversalLayer))
            {
                if (!cameFrom.containsKey(neighbour))
                {
                    cameFrom.put(neighbour, current);
                    queue.add(neighbour);
                }
            }
        }

        return Collections.emptyList(); // no path found
    }

    private static List<TilePosition> reconstructPath(Map<TilePosition, TilePosition> cameFrom, TilePosition start, TilePosition goal)
    {
        List<TilePosition> path = new ArrayList<>();
        TilePosition current = goal;

        while (current != null)
        {
            path.add(0, current);
            current = cameFrom.get(current);
        }

        return path;
    }
}
