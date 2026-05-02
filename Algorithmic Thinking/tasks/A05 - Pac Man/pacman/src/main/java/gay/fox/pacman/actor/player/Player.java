package gay.fox.pacman.actor.player;

import gay.fox.pacman.actor.Actor;
import gay.fox.pacman.maze.tile.Tile;
import gay.fox.pacman.maze.tile.TilePosition;
import gay.fox.pacman.maze.tile.TileType;

public class Player extends Actor
{
    private boolean isSuperPowered = false;
    private int points = 0;
    private int powerStepsLeft = 0;

    public Player(String name, TilePosition position)
    {
        super(name, TileType.PACMAN, position);
    }

    public void collectPellet()
    {
        TileType tile = layer.collect();

        switch (tile)
        {
            case PELLET -> points++;
            case POWER_PELLET ->
            {
                points += 10;
                setSuperPowered(true);
            }
        }
    }

    public void updatePowerSteps()
    {
        powerStepsLeft--;

        if (powerStepsLeft <= 0)
        {
            powerStepsLeft = 0;
            setSuperPowered(false);
        }
    }

    public boolean isSuperPowered()
    {
        return isSuperPowered;
    }

    public void setSuperPowered(boolean superPowered)
    {
        if (superPowered)
        {
            powerStepsLeft = 45;
            isSuperPowered = true;
            getActorTile().setType(TileType.PACMAN_POWER);
        }
        else
        {
            isSuperPowered = false;
            getActorTile().setType(TileType.PACMAN);
        }
    }

    public int getPoints()
    {
        return points;
    }
}
