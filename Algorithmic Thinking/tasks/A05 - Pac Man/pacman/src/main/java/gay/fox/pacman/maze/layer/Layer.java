package gay.fox.pacman.maze.layer;

import gay.fox.pacman.actor.Direction;
import gay.fox.pacman.maze.Maze;
import gay.fox.pacman.maze.tile.Tile;
import gay.fox.pacman.maze.tile.TilePosition;

import java.util.ArrayList;
import java.util.List;

public class Layer
{
    protected final int layerId;
    public Tile[][] layer;
    protected Maze maze;

    public Layer(int layerId, Tile[][] layer, Maze maze)
    {
        this.layerId = layerId;
        this.layer = layer;
        this.maze = maze;
    }

    public Layer(int layerId, Maze maze)
    {
        this.layerId = layerId;
        this.maze = maze;
        this.layer = new Tile[Maze.MAZE_ROWS][Maze.MAZE_COLUMNS];
    }

    public void addTile(Tile tile)
    {
        layer[tile.getPos().getRow()][tile.getPos().getCol()] = tile;
    }

    public void removeTile(TilePosition position)
    {
        layer[position.getRow()][position.getCol()] = null;
    }

    public int getLayerId()
    {
        return layerId;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Layer id: ").append(getLayerId()).append("\n");

        for (int r = 0; r < Maze.MAZE_ROWS; r++)
        {
            for (int c = 0; c < Maze.MAZE_COLUMNS; c++)
            {
                if (layer[r][c] != null)
                {
                    sb.append(layer[r][c].getTileAsciiAppearance());
                }
                else
                {
                    sb.append(" ");
                }
            }

            sb.append('\n');
        }

        return sb.toString();
    }

    public List<Tile> flatten()
    {
        List<Tile> tiles = new ArrayList<>();

        for (int r = 0; r < Maze.MAZE_ROWS; r++)
        {
            for (int c = 0; c < Maze.MAZE_COLUMNS; c++)
            {
                if (layer[r][c] != null)
                    tiles.add(layer[r][c]);
            }
        }

        return tiles;
    }

    public Maze getMaze()
    {
        return maze;
    }
}
