package gay.fox.pacman.maze;

import gay.fox.pacman.actor.Actor;

import java.util.ArrayList;
import java.util.List;

public class Maze
{
    public static final int MAZE_ROWS = 31;
    public static final int MAZE_COLUMNS = 28;

    private Layer traversalLayer = new Layer(0, MazeParser.createTraversalLayer());
    private Layer pelletLayer = new Layer(1,  MazeParser.createPelletLayer());
    private List<Layer> actorLayers = new ArrayList<>();

    @Override
    public String toString()
    {
        Tile[][] mergedMaze = getMergedMaze();

        StringBuilder sb = new StringBuilder();

        for (int r = 0; r < MAZE_ROWS; r++)
        {
            for (int c = 0; c < MAZE_COLUMNS; c++)
            {
                sb.append(mergedMaze[r][c].getTileAsciiAppearance());
            }

            sb.append('\n');
        }

        return sb.toString();
    }

    private Tile[][] getMergedMaze()
    {
        List<Layer> layers = new ArrayList<>(List.of(traversalLayer, pelletLayer));
        layers.addAll(actorLayers);

        Tile[][] mergedLayer = new Tile[MAZE_ROWS][MAZE_COLUMNS];

        for (Layer layer : layers)
        {
            for (int r = 0; r < MAZE_ROWS; r++)
            {
                for (int c = 0; c < MAZE_COLUMNS; c++)
                {
                    if (layer.layer[r][c] != null)
                    {
                        mergedLayer[r][c] = layer.layer[r][c];
                    }
                }
            }
        }

        return mergedLayer;
    }

    /*private void setTile(Tile tile)
    {
        maze[tile.getPos().getRow()][tile.getPos().getCol()]  = tile;
    }*/

    private TileType getTileType(TilePosition pos)
    {
        return getMergedMaze()[pos.getRow()][pos.getRow()].getType();
    }

    private boolean isTileValidSpawnPosition(TilePosition pos)
    {
        return getTileType(pos) ==  TileType.EMPTY;
    }

    public void addActor(Actor actor)
    {
        if (!isTileValidSpawnPosition(actor.getActorTile().getPos()))
        {
            throw new IllegalStateException("Actor tile has invalid spawn position");
        }

        Layer actorLayer = new Layer(actorLayers.size() + 100);




    }

}
