package gay.fox.pacman.actor.ghost.state_machine.states;

import gay.fox.pacman.actor.ghost.Ghost;
import gay.fox.pacman.maze.tile.TilePosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ChaseStateTest
{
    private Ghost ghost;
    private ChaseState chaseState;

    @BeforeEach
    void setUp()
    {
        ghost = mock(Ghost.class, RETURNS_DEEP_STUBS);
        chaseState = new ChaseState();
    }

    @Test
    void onEnter_stepsShouldBeAtMaxChaseSteps()
    {
        chaseState.onEnter(ghost);

        assertEquals(ghost.playerChaseMaxSteps, chaseState.getSteps());
    }

    @Test
    void onUpdate_switchToFearWhenPlayerSuperpowered()
    {
        when(ghost.isPlayerSuperPowered()).thenReturn(true);

        chaseState.update(ghost);

        verify(ghost).switchState(any(FearState.class));
    }

    @Test
    void onUpdate_pathFindToPlayer()
    {
        TilePosition playerPos = mock(TilePosition.class);

        when(ghost.getName()).thenReturn("Blinky");
        when(ghost.getPlayerPosition()).thenReturn(playerPos);

        chaseState.onEnter(ghost);
        chaseState.update(ghost);

        verify(ghost).pathFind(playerPos);
    }

    @Test
    void onUpdate_reduceStepsWhenPlayerOutsideDisengagementRange()
    {
        TilePosition playerPos = mock(TilePosition.class);

        when(ghost.getName()).thenReturn("Blinky");
        when(ghost.getPlayerPosition()).thenReturn(playerPos);
        when(ghost.getPlayerDistance()).thenReturn(ghost.playerDisengagementRange * 2);
        when(ghost.isPlayerSuperPowered()).thenReturn(false);

        chaseState.onEnter(ghost);
        chaseState.update(ghost);

        assertEquals(ghost.playerChaseMaxSteps - 1, chaseState.getSteps());
    }

    @Test
    void onUpdate_resetStepsWhenPlayerInsideDisengagementRange()
    {
        TilePosition playerPos = mock(TilePosition.class);

        when(ghost.getName()).thenReturn("Blinky");
        when(ghost.getPlayerPosition()).thenReturn(playerPos);
        when(ghost.getPlayerDistance()).thenReturn(ghost.playerDisengagementRange / 2);
        when(ghost.isPlayerSuperPowered()).thenReturn(false);

        chaseState.onEnter(ghost);
        chaseState.update(ghost);

        assertEquals(ghost.playerChaseMaxSteps, chaseState.getSteps());
    }

    @Test
    void onUpdate_SwitchToIdleStateWhenStepsLessOrEqualZero()
    {
        TilePosition playerPos = mock(TilePosition.class);

        when(ghost.getName()).thenReturn("Blinky");
        when(ghost.getPlayerPosition()).thenReturn(playerPos);
        when(ghost.getPlayerDistance()).thenReturn(ghost.playerDisengagementRange * 1.4);
        when(ghost.isPlayerSuperPowered()).thenReturn(false);

        chaseState.onEnter(ghost);
        for (int i = 0; i < ghost.playerChaseMaxSteps; i++)
        {
            chaseState.update(ghost);
        }

        verify(ghost).switchState(any(IdleState.class));
    }
}
