package gay.fox.pacman.maze;

import gay.fox.pacman.actor.Direction;

public class Layer
{
    private final int layerId;
    public Tile[][] layer;

    public Layer(int layerId, Tile[][] layer)
    {
        this.layerId = layerId;
        this.layer = layer;
    }

    public Layer(int layerId)
    {
        this.layerId = layerId;

        this.layer = new Tile[Maze.MAZE_ROWS][Maze.MAZE_COLUMNS];
    }

    public void addTile(Tile tile)
    {
        layer[tile.getPos().getRow()][tile.getPos().getCol()] = tile;
    }

    public void moveTile(TilePosition position, Direction direction)
    {


    }

    public void removeTile(TilePosition position)
    {
        layer[position.getRow()][position.getCol()] = null;
    }


    public int getLayerId()
    {
        return layerId;
    }
}
