package gay.fox.pacman.ui.colors;

import gay.fox.pacman.maze.tile.Tile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TileDrawer
{
    public static void drawTile(GraphicsContext gc, Tile tile, int pixelGridSize)
    {
        switch (tile.getType())
        {
            case PACMAN, PACMAN_POWER, POWER_PELLET -> drawCircle(gc, tile.getPos().getCol(), tile.getPos().getRow(), TileColor.getTileColor(tile.getType()), pixelGridSize, 2);
            case GHOST, GHOST_FRIGHTENED -> drawGhost(gc, tile.getPos().getCol(), tile.getPos().getRow(), TileColor.getTileColor(tile.getType()), pixelGridSize);
            case WALL, EMPTY, PATH_FINDING_PREVIEW -> drawSquare(gc, tile.getPos().getCol(), tile.getPos().getRow(), TileColor.getTileColor(tile.getType()), pixelGridSize);
            case PELLET -> drawCircle(gc, tile.getPos().getCol(), tile.getPos().getRow(), TileColor.getTileColor(tile.getType()), pixelGridSize, 1.5);
        }
    }

    private static void drawSquare(GraphicsContext gc, int x, int y, Color color, int size)
    {
        gc.setFill(color);
        gc.fillRect(x * size, y * size, size, size);
    }

    private static void drawCircle(GraphicsContext gc, int x, int y, Color color, int size, double shrink)
    {
        double buffer = size / shrink;
        gc.setFill(color);
        gc.fillOval(x * size + (double) buffer / 2, y *size + (double) buffer / 2, size - buffer, size - buffer);
    }

    private static void drawGhost(GraphicsContext gc, int x, int y, Color color, int size)
    {
        drawCircle(gc, x, y, color, size, 1.5);
        Color inverted = color.invert();
        drawCircle(gc, x, y, inverted, size, 3);
    }
}
