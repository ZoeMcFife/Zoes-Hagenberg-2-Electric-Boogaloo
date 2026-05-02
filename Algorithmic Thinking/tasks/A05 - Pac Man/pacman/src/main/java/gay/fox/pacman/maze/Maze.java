package gay.fox.pacman.maze;

import gay.fox.pacman.actor.Direction;
import gay.fox.pacman.actor.ghost.Ghost;
import gay.fox.pacman.actor.player.Player;
import gay.fox.pacman.maze.layer.ActorLayer;
import gay.fox.pacman.maze.layer.Layer;
import gay.fox.pacman.maze.tile.Tile;
import gay.fox.pacman.maze.tile.TilePosition;
import gay.fox.pacman.maze.tile.TileType;

import java.util.ArrayList;
import java.util.List;

public class Maze
{
    public static final int MAZE_ROWS = 31;
    public static final int MAZE_COLUMNS = 28;

    public static final TilePosition playerStart = new TilePosition(17, 14);

    /**
     * Used to determine when the player loops around the maze!
     */
    private static final int MAZE_TELEPORT_ROW = 14;

    private Layer traversalLayer = new Layer(0, MazeParser.createTraversalLayer(), this);
    private Layer pelletLayer = new Layer(1,  MazeParser.createPelletLayer(), this);
    private Layer pathFindingPreviewLayer = new Layer(2, this);
    private List<ActorLayer<Ghost>> ghostLayers = new ArrayList<>();
    private ActorLayer<Player> playerLayer;
    private List<Ghost> ghosts = new ArrayList<>();

    public Maze()
    {
        Ghost blinky = new Ghost("Blinky", new TilePosition(14, 11));
        blinky.previewPathfinding = true;
        Ghost pinky = new Ghost("Pinky", new TilePosition(14, 13));
        Ghost inky = new Ghost("Inky", new TilePosition(14, 15));
        Ghost clyde = new Ghost("Clyde", new TilePosition(15, 13));

        addGhost(blinky);
        /*addGhost(pinky);
        addGhost(inky);
        addGhost(clyde);*/

        ghosts.add(blinky);
        /*ghosts.add(pinky);
        ghosts.add(inky);
        ghosts.add(clyde);*/
    }

    public void updateGhosts()
    {
        for (Ghost ghost : ghosts)
        {
            ghost.update();

            if (ghost.previewPathfinding)
            {
                pathFindingPreviewLayer.layer = new Tile[MAZE_ROWS][MAZE_COLUMNS];

                for (TilePosition pos : ghost.getCurrentPath())
                {
                    pathFindingPreviewLayer.addTile(new Tile(TileType.PATH_FINDING_PREVIEW, pos));
                }
            }
        }

    }

    public List<Layer> getLayersInDrawOrder()
    {
        List<Layer> layers = new ArrayList<>();

        layers.add(traversalLayer);
        layers.add(pathFindingPreviewLayer);
        layers.add(pelletLayer);
        layers.addAll(ghostLayers);
        layers.add(playerLayer);

        return layers;
    }

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
        List<Layer> layers = new ArrayList<>();

        layers.add(traversalLayer);
        layers.add(pelletLayer);
        layers.addAll(ghostLayers);

        if (playerLayer != null)
            layers.add(playerLayer);

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

    public TileType getTileType(TilePosition pos)
    {
        return getMergedMaze()[pos.getRow()][pos.getCol()].getType();
    }

    public TileType getTileType(Layer layer, TilePosition pos)
    {
        if (layer.layer[pos.getRow()][pos.getCol()] == null)
            return TileType.EMPTY;

        return layer.layer[pos.getRow()][pos.getCol()].getType();
    }

    private boolean isTileValidSpawnPosition(TilePosition pos)
    {
        return getTileType(pos) ==  TileType.EMPTY;
    }

    public boolean isValidTraversableTile(TilePosition pos)
    {
        if (getTileType(pos) == TileType.WALL)
            return false;

        if (pos.getCol() >= MAZE_COLUMNS || pos.getRow() >= MAZE_ROWS)
            return false;

        if (pos.getCol() < 0 || pos.getRow() < 0)
            return false;

        return true;
    }

    public boolean isValidTraversableTile(TilePosition pos, Layer layer)
    {
        if (pos.getCol() >= MAZE_COLUMNS || pos.getRow() >= MAZE_ROWS)
            return false;

        if (pos.getCol() < 0 || pos.getRow() < 0)
            return false;

        if (getTileType(layer, pos) == TileType.WALL || getTileType(layer, pos) == TileType.PACMAN)
            return false;

        return true;
    }


    public void addPlayer(Player player)
    {
        if (!isTileValidSpawnPosition(player.getActorTile().getPos()))
        {
            throw new IllegalStateException("Player tile has invalid spawn position");
        }

        playerLayer = new ActorLayer<>(3, player, this);
        player.setLayer(playerLayer);
    }

    public void addGhost(Ghost ghost)
    {
        if (!isTileValidSpawnPosition(ghost.getActorTile().getPos()))
        {
            throw new IllegalStateException("Ghost tile has invalid spawn position");
        }

        ActorLayer<Ghost> ghostLayer = new ActorLayer<>(ghostLayers.size() + 100, ghost, this);
        ghost.setLayer(ghostLayer);
        ghostLayers.add(ghostLayer);

        ghost.activateGhost();
    }


    public TilePosition getNextTile(TilePosition pos, Direction direction)
    {
        TilePosition nextTilePosition = new TilePosition(pos);

        switch (direction)
        {
            case UP:
                nextTilePosition.setRow(pos.getRow() - 1);
                break;
            case DOWN:
                nextTilePosition.setRow(pos.getRow() + 1);
                break;
            case LEFT:
                if (pos.getRow() == MAZE_TELEPORT_ROW && pos.getCol() == 0)
                {
                    nextTilePosition.setRow(MAZE_TELEPORT_ROW);
                    nextTilePosition.setCol(MAZE_COLUMNS - 1);
                }
                else
                {
                    nextTilePosition.setCol(pos.getCol() - 1);
                }
                break;
            case RIGHT:
                if (pos.getRow() == MAZE_TELEPORT_ROW && pos.getCol() == MAZE_COLUMNS - 1)
                {
                    nextTilePosition.setRow(MAZE_TELEPORT_ROW);
                    nextTilePosition.setCol(0);
                }
                else
                {
                    nextTilePosition.setCol(pos.getCol() + 1);
                }

                break;
        }

        return nextTilePosition;
    }

    public TileType collect(TilePosition pos)
    {
        TileType type = getTileType(pelletLayer, pos);

        if (type == TileType.PELLET || type == TileType.POWER_PELLET)
        {
            pelletLayer.removeTile(pos);
            return type;
        }

        return TileType.EMPTY;
    }

    public TilePosition getPlayerPostion()
    {
        return playerLayer.getActor().getActorTile().getPos();
    }

    public Layer getTraversalLayer()
    {
        return traversalLayer;
    }

    public boolean isPlayerSuperPowered()
    {
        return ((Player) playerLayer.getActor()).isSuperPowered();
    }
}
