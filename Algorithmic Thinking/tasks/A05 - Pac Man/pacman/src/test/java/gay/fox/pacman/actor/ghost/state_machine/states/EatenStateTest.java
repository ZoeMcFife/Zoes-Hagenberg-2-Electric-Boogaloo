package gay.fox.pacman.actor.ghost.state_machine.states;

import gay.fox.pacman.actor.ghost.Ghost;
import gay.fox.pacman.maze.tile.TilePosition;
import gay.fox.pacman.maze.tile.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EatenStateTest
{
    private Ghost ghost;
    private EatenState eatenState;

    @BeforeEach
    void setUp()
    {
        ghost = mock(Ghost.class, RETURNS_DEEP_STUBS);
        eatenState = new EatenState();
    }

    @Test
    void onEnter_setTileToEaten()
    {
        var tile = mock(gay.fox.pacman.maze.tile.Tile.class);

        when(ghost.getActorTile()).thenReturn(tile);

        eatenState.onEnter(ghost);

        verify(tile).setType(TileType.GHOST_EATEN);
    }

    @Test
    void onExit_setTileTypeToGhost()
    {
        var tile = mock(gay.fox.pacman.maze.tile.Tile.class);

        when(ghost.getActorTile()).thenReturn(tile);

        eatenState.onExit(ghost);

        verify(tile).setType(TileType.GHOST);
    }

    @Test
    void onExit_isRevived()
    {
        eatenState.onExit(ghost);

        verify(ghost).revive();

        assertFalse(ghost.isEaten());
    }

    @Test
    void update_switchToIdleWhenBaseReached()
    {
        when(ghost.getActorTile().getPos()).thenReturn(new TilePosition(14,14));

        eatenState.onEnter(ghost);

        eatenState.update(ghost);

        verify(ghost).switchState(any(IdleState.class));
    }
}
