package gay.fox.pacman.actor;

import gay.fox.pacman.maze.Maze;
import gay.fox.pacman.maze.Tile;
import gay.fox.pacman.maze.TilePosition;
import gay.fox.pacman.maze.TileType;
public class Actor
{
    private String name;
    private Tile actorTile;
    private int layerId;

    private Direction currentDirection = Direction.UP;

    public Actor(String name, TileType tileType, TilePosition position)
    {
        setName(name);
        actorTile = new Tile(tileType, position);
    }


    public void move()
    {

    }

    public String getName()
    {
        return name;
    }

    public Tile getActorTile()
    {
        return actorTile;
    }

    private void setName(String name)
    {
        this.name = name;
    }

    private void setLayerId(int layerId)
    {
        this.layerId = layerId;
    }
}
