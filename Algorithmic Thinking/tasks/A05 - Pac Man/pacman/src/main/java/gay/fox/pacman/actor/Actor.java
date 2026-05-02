package gay.fox.pacman.actor;

import gay.fox.pacman.maze.layer.ActorLayer;
import gay.fox.pacman.maze.tile.Tile;
import gay.fox.pacman.maze.tile.TilePosition;
import gay.fox.pacman.maze.tile.TileType;
public class Actor
{
    private String name;
    private Tile actorTile;
    protected ActorLayer<?> layer;
    private Direction currentDirection = Direction.RIGHT;

    public Actor(String name, TileType tileType, TilePosition position)
    {
        setName(name);
        actorTile = new Tile(tileType, position);
    }

    public void setCurrentDirection(Direction direction)
    {
        if (!isValidDirection(direction))
            return;

        currentDirection = direction;
    }

    public boolean isValidDirection(Direction direction)
    {
        if (currentDirection == direction)
            return false;

        return layer.getMaze().isValidTraversableTile(layer.getMaze().getNextTile(getActorTile().getPos(), direction));
    }

    public void move()
    {
        if (layer == null)
        {
            throw new RuntimeException("Actor " + name +  " hasn't been assigned a layer! Did you forget?");
        }

        layer.moveActorAlongCurrentDirection();
    }

    public void setLayer(ActorLayer<?> layer)
    {
        this.layer = layer;
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

    public Direction getCurrentDirection()
    {
        return currentDirection;
    }
}
