package gay.fox.pacman.actor.ghost;

import gay.fox.pacman.actor.Actor;
import gay.fox.pacman.actor.ghost.state_machine.State;
import gay.fox.pacman.actor.ghost.state_machine.states.IdleState;
import gay.fox.pacman.maze.Maze;
import gay.fox.pacman.maze.tile.TilePosition;
import gay.fox.pacman.maze.tile.TileType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Ghost extends Actor
{
    private List<TilePosition> randomIdlePoints;
    public final double playerDetectionRange = 3;
    public final double playerDisengagementRange = 7;
    public final int playerChaseMaxSteps = 10;

    private List<TilePosition> currentPath;

    private State currentState;

    public boolean previewPathfinding = false;

    public Ghost(String name, TilePosition position)
    {
        super(name, TileType.GHOST, position);
    }

    public void activateGhost()
    {
        generateRandomIdlePoints();
        switchState(new IdleState(randomIdlePoint()));
    }

    public void switchState(State newState)
    {
        if (currentState != null)
            currentState.onExit(this);

        currentState = newState;
        newState.onEnter(this);
    }

    public TilePosition randomIdlePoint()
    {
        return randomIdlePoints.get(new Random().nextInt(randomIdlePoints.size()));
    }

    public void update()
    {
        currentState.update(this);

        IO.println("Ghost update: " + currentState.getName() + " path: " + currentPath.toString());
    }

    private void generateRandomIdlePoints()
    {
        randomIdlePoints = new LinkedList<>();

        for (int i = 0; i < 5; i++)
        {
            TilePosition pos = new TilePosition(-1, -1);

            while (!layer.getMaze().isValidTraversableTile(pos, layer.getMaze().getTraversalLayer()))
            {
                pos = new TilePosition((int) (Math.random() * Maze.MAZE_ROWS), (int) (Math.random() * Maze.MAZE_COLUMNS));
            }

            randomIdlePoints.add(pos);
        }
    }

    public TilePosition getPlayerPosition()
    {
        return layer.getMaze().getPlayerPostion();
    }

    public double getPlayerDistance()
    {
        TilePosition playerPos = getPlayerPosition();
        TilePosition selfPos = getActorTile().getPos();

        return Math.sqrt(Math.pow(playerPos.getRow() - selfPos.getRow(), 2) + Math.pow(playerPos.getCol() - selfPos.getCol(), 2));
    }

    public State getCurrentState()
    {
        return currentState;
    }

    public void pathFind(TilePosition goal)
    {
        currentPath = new LinkedList<>();

        currentPath = Pathfinding.findPath(getActorTile().getPos(), goal, layer.getMaze().getTraversalLayer());
    }

    public void move()
    {
        if (currentPath.isEmpty())
            return;

        layer.moveActor(currentPath.getLast());
        currentPath.removeLast();
    }

    public List<TilePosition> getCurrentPath()
    {
        return currentPath;
    }

    public boolean isPlayerNearby()
    {
        return getPlayerDistance() < playerDetectionRange;
    }
}
