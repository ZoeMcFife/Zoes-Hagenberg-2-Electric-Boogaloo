package gay.fox.pacman.maze;

public class Layer
{
    private final int layerId;
    public Tile[][] layer;

    public Layer(int layerId, Tile[][] layer)
    {
        this.layerId = layerId;
        this.layer = layer;
    }

    public Layer(int layerId)
    {
        this.layerId = layerId;

        this.layer = new Tile[Maze.MAZE_ROWS][Maze.MAZE_COLUMNS];
    }

    public int getLayerId()
    {
        return layerId;
    }
}
