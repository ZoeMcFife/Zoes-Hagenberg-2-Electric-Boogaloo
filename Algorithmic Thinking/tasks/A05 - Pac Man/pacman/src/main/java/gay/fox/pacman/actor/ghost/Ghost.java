package gay.fox.pacman.actor.ghost;

import gay.fox.pacman.actor.Actor;
import gay.fox.pacman.maze.tile.Tile;
import gay.fox.pacman.maze.tile.TilePosition;
import gay.fox.pacman.maze.tile.TileType;

public class Ghost extends Actor
{
    public Ghost(String name, TilePosition position)
    {
        super(name, TileType.GHOST, position);
    }
}
