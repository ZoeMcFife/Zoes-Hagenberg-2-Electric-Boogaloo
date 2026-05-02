package gay.fox.pacman.ui.colors;

import gay.fox.pacman.maze.tile.Tile;
import gay.fox.pacman.maze.tile.TileType;
import javafx.scene.paint.Color;

public class TileColor
{
    public static Color getTileColor(TileType tileType)
    {
        return switch (tileType)
        {
            case PACMAN -> Color.rgb(255, 255, 0);
            case PACMAN_POWER -> Color.rgb(0, 255, 0);
            case GHOST -> Color.rgb(255, 30, 30);
            case GHOST_FRIGHTENED -> Color.rgb(30, 130, 255);
            case WALL -> Color.rgb(0, 0, 180);
            case PELLET, POWER_PELLET -> Color.rgb(255, 255, 255);
            case EMPTY -> Color.rgb(30, 30, 30);
            case PATH_FINDING_PREVIEW -> Color.rgb(255, 165, 0);
        };
    }
}
