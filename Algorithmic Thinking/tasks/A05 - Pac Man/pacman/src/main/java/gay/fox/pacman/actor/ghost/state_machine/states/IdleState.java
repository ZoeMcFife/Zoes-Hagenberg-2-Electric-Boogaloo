package gay.fox.pacman.actor.ghost.state_machine.states;

import gay.fox.pacman.actor.ghost.Ghost;
import gay.fox.pacman.actor.ghost.state_machine.State;
import gay.fox.pacman.maze.tile.TilePosition;

public class IdleState implements State
{
    private TilePosition goal;

    public IdleState(TilePosition goal)
    {
        this.goal = goal;
    }

    @Override
    public void onEnter(Ghost ghost)
    {
        ghost.pathFind(goal);
    }

    @Override
    public void onExit(Ghost ghost)
    {

    }

    @Override
    public void update(Ghost ghost)
    {
        ghost.move();

        if (ghost.isPlayerNearby())
        {
            ghost.switchState(new ChaseState());
            return;
        }

        if (ghost.getActorTile().getPos().equals(goal))
        {
            ghost.switchState(new IdleState(ghost.randomIdlePoint()));
            return;
        }
    }

    @Override
    public String getName()
    {
        return "Idle";
    }
}
