package gay.fox.pacman.maze.layer;

import gay.fox.pacman.actor.Actor;
import gay.fox.pacman.actor.player.Player;
import gay.fox.pacman.maze.Maze;
import gay.fox.pacman.maze.tile.TilePosition;
import gay.fox.pacman.maze.tile.TileType;

public class ActorLayer<T extends Actor> extends Layer
{
    protected final T actor;

    public ActorLayer(int layerId, T actor, Maze maze)
    {
        super(layerId, maze);
        this.actor = actor;

        addTile(actor.getActorTile());
    }

    public void moveActorAlongCurrentDirection()
    {
        TilePosition nextPosition = maze.getNextTile(actor.getActorTile().getPos(), actor.getCurrentDirection());

        if (!maze.isValidTraversableTile(nextPosition))
        {
            return;
        }

        TilePosition currentPosition = actor.getActorTile().getPos();

        actor.getActorTile().setPos(nextPosition);

        removeTile(currentPosition);
        addTile(actor.getActorTile());
    }

    public TileType collect()
    {
        return maze.collect(actor.getActorTile().getPos());
    }

}
