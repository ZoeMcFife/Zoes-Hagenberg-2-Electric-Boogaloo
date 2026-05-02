package gay.fox.pacman.actor.ghost.state_machine.states;

import gay.fox.pacman.actor.ghost.Ghost;
import gay.fox.pacman.actor.ghost.state_machine.State;
import gay.fox.pacman.maze.tile.TilePosition;
import gay.fox.pacman.maze.tile.TileType;

public class FearState implements State
{
    private TilePosition goal =  new TilePosition(14, 14);

    @Override
    public void onEnter(Ghost ghost)
    {
        ghost.pathFind(goal);
        ghost.getActorTile().setType(TileType.GHOST_FRIGHTENED);
    }

    @Override
    public void onExit(Ghost ghost)
    {
        ghost.getActorTile().setType(TileType.GHOST);
    }

    @Override
    public void update(Ghost ghost)
    {
        if (!ghost.isPlayerSuperPowered())
        {
            ghost.switchState(new IdleState(ghost.randomIdlePoint()));
        }
    }

    @Override
    public String getName() {
        return "";
    }
}
