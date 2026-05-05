package gay.fox.pacman.actor.ghost.state_machine.states;

import gay.fox.pacman.actor.ghost.Ghost;
import gay.fox.pacman.maze.tile.TilePosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class IdleStateTest
{
    private Ghost ghost;
    private TilePosition goal;
    private IdleState idleState;

    @BeforeEach
    void setUp()
    {
        ghost = mock(Ghost.class, RETURNS_DEEP_STUBS );
        goal = mock(TilePosition.class);
        idleState = new IdleState(goal);
    }

    // onEnter

    @Test
    void onEnter_shouldPathFindToGoal()
    {
        when(ghost.getName()).thenReturn("Blinky");

        idleState.onEnter(ghost);

        verify(ghost).pathFind(goal);
    }

    // update — FearState transition

    @Test
    void update_shouldSwitchToFearState_whenPlayerIsSuperPowered()
    {
        when(ghost.isPlayerSuperPowered()).thenReturn(true);

        idleState.update(ghost);

        verify(ghost).switchState(any(FearState.class));
    }

    @Test
    void update_shouldNotSwitchToChaseState_whenPlayerIsSuperPowered()
    {
        when(ghost.isPlayerSuperPowered()).thenReturn(true);

        idleState.update(ghost);

        verify(ghost, never()).switchState(any(ChaseState.class));
    }

    // update — ChaseState transition

    @Test
    void update_shouldSwitchToChaseState_whenPlayerIsNearby()
    {
        when(ghost.isPlayerSuperPowered()).thenReturn(false);
        when(ghost.isPlayerNearby()).thenReturn(true);

        idleState.update(ghost);

        verify(ghost).switchState(any(ChaseState.class));
    }

    @Test
    void update_shouldNotSwitchToChaseState_whenPlayerIsNearbyButSuperPowered()
    {
        when(ghost.isPlayerSuperPowered()).thenReturn(true);
        when(ghost.isPlayerNearby()).thenReturn(true);

        idleState.update(ghost);

        verify(ghost, never()).switchState(any(ChaseState.class));
    }

    // update — IdleState re-transition on goal reached

    @Test
    void update_shouldSwitchToNewIdleState_whenGoalReached()
    {
        TilePosition ghostPos = mock(TilePosition.class);
        TilePosition actorTile = mock(gay.fox.pacman.maze.tile.Tile.class).getPos(); // adjust if Tile is a real class
        TilePosition newGoal = mock(TilePosition.class);

        when(ghost.isPlayerSuperPowered()).thenReturn(false);
        when(ghost.isPlayerNearby()).thenReturn(false);
        when(ghost.getActorTile().getPos()).thenReturn(goal);
        when(ghost.randomIdlePoint()).thenReturn(newGoal);

        idleState.update(ghost);

        verify(ghost).switchState(any(IdleState.class));
    }

    @Test
    void update_shouldNotSwitch_whenGoalNotReachedAndNoPlayerThreat()
    {
        TilePosition differentPos = mock(TilePosition.class);

        when(ghost.isPlayerSuperPowered()).thenReturn(false);
        when(ghost.isPlayerNearby()).thenReturn(false);
        when(ghost.getActorTile().getPos()).thenReturn(differentPos);

        idleState.update(ghost);

        verify(ghost, never()).switchState(any());
    }

    // getName

    @Test
    void getName_shouldReturnIdle()
    {
        assertEquals("Idle", idleState.getName());
    }
}