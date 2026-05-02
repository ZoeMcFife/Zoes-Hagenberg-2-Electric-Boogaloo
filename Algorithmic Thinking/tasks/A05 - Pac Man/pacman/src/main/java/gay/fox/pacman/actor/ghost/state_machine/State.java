package gay.fox.pacman.actor.ghost.state_machine;

import gay.fox.pacman.actor.ghost.Ghost;
import gay.fox.pacman.maze.tile.TilePosition;

public interface State
{
    void onEnter(Ghost ghost);
    void onExit(Ghost ghost);
    void update(Ghost ghost);

    String getName();
}
