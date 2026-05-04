package gay.fox.pacman.actor.ghost.state_machine.states;

import gay.fox.pacman.actor.ghost.Ghost;
import gay.fox.pacman.actor.ghost.state_machine.State;
import gay.fox.pacman.maze.tile.TilePosition;

public class EatenState implements State
{
    private TilePosition goal =  new TilePosition(14, 14);


    @Override
    public void onEnter(Ghost ghost)
    {
        ghost.pathFind(goal);
    }

    @Override
    public void onExit(Ghost ghost)
    {
        if (!ghost.isPlayerSuperPowered())
        {
            ghost.switchState(new IdleState(ghost.randomIdlePoint()));
        }
    }

    @Override
    public void update(Ghost ghost)
    {

    }

    @Override
    public String getName()
    {
        return "Eaten";
    }
}
