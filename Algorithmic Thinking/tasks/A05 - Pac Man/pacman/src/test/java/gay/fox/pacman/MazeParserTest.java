package gay.fox.pacman;

import gay.fox.pacman.maze.Maze;
import gay.fox.pacman.maze.MazeParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MazeParserTest
{
    @Test
    public void testParseMaze()
    {
        Maze maze = new Maze();

        IO.println(maze);

        assertEquals(MazeParser.getMazeAscii(), maze.toString());
    }
}
