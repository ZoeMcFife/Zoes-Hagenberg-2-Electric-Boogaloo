package gay.fox.pacman;

import gay.fox.pacman.actor.Actor;
import gay.fox.pacman.maze.Maze;
import gay.fox.pacman.maze.tile.TileType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MazeTest
{
    @Test
    public void test()
    {
        Maze maze = new Maze();

        Actor actor = new Actor("test", TileType.PACMAN, Maze.playerStart);

        maze.addActor(actor);

        IO.println(maze);

        IO.println(maze.getLayersInDrawOrder());
    }
}
