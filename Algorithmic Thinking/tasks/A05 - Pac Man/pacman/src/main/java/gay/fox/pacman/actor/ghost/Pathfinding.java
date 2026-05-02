package gay.fox.pacman.actor.ghost;

import gay.fox.pacman.maze.layer.Layer;
import gay.fox.pacman.maze.tile.TilePosition;
import gay.fox.pacman.maze.tile.TileType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Pathfinding
{
    public static List<TilePosition> findPath(TilePosition start, TilePosition goal, Layer traversalLayer)
    {
        List<TilePosition> path = new LinkedList<>();

        simpleSearch(start, goal, path, new ArrayList<>(), traversalLayer);

        return path;
    }

    private static boolean isTraversableTile(TilePosition pos, Layer traversalLayer)
    {
        return traversalLayer.getTileType(pos) == TileType.EMPTY;
    }

    private static boolean simpleSearch(TilePosition current, TilePosition goal, List<TilePosition> path, List<TilePosition> visited, Layer traversalLayer)
    {
        if (current.equals(goal))
        {
            return true;
        }

        if (!isTraversableTile(current, traversalLayer) || visited.contains(current))
        {
            return false;
        }

        visited.add(current);

        for (TilePosition neighbour : getTraversableNeighbourTiles(current, traversalLayer))
        {
            if (simpleSearch(neighbour, goal, path, visited, traversalLayer))
            {
                path.add(neighbour);
                return true;
            }

            path.remove(neighbour);
        }

        return false;
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
}
