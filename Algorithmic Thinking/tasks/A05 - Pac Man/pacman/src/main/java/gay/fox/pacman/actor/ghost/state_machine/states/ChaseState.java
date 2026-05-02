package gay.fox.pacman.actor.ghost.state_machine.states;

import gay.fox.pacman.actor.ghost.Ghost;
import gay.fox.pacman.actor.ghost.state_machine.State;

public class ChaseState implements State
{
    private int steps;

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
        ghost.pathFind(ghost.getPlayerPosition());

        ghost.move();

        if (ghost.getPlayerDistance() > ghost.playerDisengagementRange)
        {
            steps--;

            if (steps <= 0)
            {
                ghost.switchState(new IdleState(ghost.randomIdlePoint()));
            }
        }
        else
        {
            steps = ghost.playerChaseMaxSteps;
        }
    }

    @Override
    public String getName()
    {
        return "Chase";
    }
}
