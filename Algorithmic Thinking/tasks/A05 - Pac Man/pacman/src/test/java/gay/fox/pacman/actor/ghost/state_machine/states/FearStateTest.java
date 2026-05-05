package gay.fox.pacman.actor.ghost.state_machine.states;

import gay.fox.pacman.actor.ghost.Ghost;
import gay.fox.pacman.maze.tile.TilePosition;
import gay.fox.pacman.maze.tile.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class FearStateTest
{
    private Ghost ghost;
    private FearState fearState;

    @BeforeEach
    void setUp()
    {
        ghost = mock(Ghost.class, RETURNS_DEEP_STUBS);
        fearState = new FearState();
    }

    @Test
    void onEnter_setTileTypeToFrightened()
    {
        var tile = mock(gay.fox.pacman.maze.tile.Tile.class);
        when(ghost.getActorTile()).thenReturn(tile);

        fearState.onEnter(ghost);

        verify(tile).setType(TileType.GHOST_FRIGHTENED);
    }

    @Test
    void onExit_setTileTypeToGhost()
    {
        var tile = mock(gay.fox.pacman.maze.tile.Tile.class);
        when(ghost.getActorTile()).thenReturn(tile);

        fearState.onExit(ghost);

        verify(tile).setType(TileType.GHOST);
    }

    @Test
    void update_switchToEatenIfEaten()
    {
        when(ghost.isEaten()).thenReturn(true);
        when(ghost.isPlayerSuperPowered()).thenReturn(true);

        fearState.onEnter(ghost);
        fearState.update(ghost);

        verify(ghost).switchState(any(EatenState.class));
    }

    @Test
    void update_switchToIdleOncePlayerIsNotSuperpowered()
    {
        when(ghost.isEaten()).thenReturn(false);
        when(ghost.isPlayerSuperPowered()).thenReturn(false);

        fearState.onEnter(ghost);
        fearState.update(ghost);

        verify(ghost).switchState(any(IdleState.class));
    }
}
