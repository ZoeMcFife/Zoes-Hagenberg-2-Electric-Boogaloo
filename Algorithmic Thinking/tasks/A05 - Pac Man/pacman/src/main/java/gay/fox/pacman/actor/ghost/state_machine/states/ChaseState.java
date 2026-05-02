package gay.fox.pacman.actor.ghost.state_machine.states;

import gay.fox.pacman.actor.ghost.Ghost;
import gay.fox.pacman.actor.ghost.state_machine.State;
import gay.fox.pacman.maze.tile.TilePosition;

public class ChaseState implements State
{
    private int steps;
    private TilePosition playerPos;

    @Override
    public void onEnter(Ghost ghost)
    {
        steps = ghost.playerChaseMaxSteps;
    }

    @Override
    public void onExit(Ghost ghost)
    {

    }

    @Override
    public void update(Ghost ghost)
    {
        if (ghost.isPlayerSuperPowered())
        {
            ghost.switchState(new FearState());
            return;
        }

        ghost.pathFind(ghost.getPlayerPosition());

        if (ghost.getPlayerDistance() > ghost.playerDisengagementRange)
        {
            steps--;

            if (steps <= 0)
            {
                ghost.switchState(new IdleState(ghost.randomIdlePoint()));
                return;
            }
        }
        else
        {
            steps = ghost.playerChaseMaxSteps;
        }

        if (ghost.getPlayerDistance() > ghost.playerDisengagementRange * 1.5)
        {
            ghost.switchState(new IdleState(ghost.randomIdlePoint()));
            return;
        }
    }

    @Override
    public String getName()
    {
        return "Chase";
    }
}
