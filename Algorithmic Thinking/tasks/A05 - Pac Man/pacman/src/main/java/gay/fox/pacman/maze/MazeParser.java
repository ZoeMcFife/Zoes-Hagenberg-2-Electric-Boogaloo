package gay.fox.pacman.maze;

public class MazeParser
{
    private static final String mazeAscii =
        "############################" +
        "#············##············#" +
        "#·####·#####·##·#####·####·#" +
        "#@####·#####·##·#####·####@#" +
        "#·####·#####·##·#####·####·#" +
        "#··························#" +
        "#·####·##·########·##·####·#" +
        "#·####·##·########·##·####·#" +
        "#······##····##····##······#" +
        "######·##### ## #####·######" +
        "######·##### ## #####·######" +
        "######·##          ##·######" +
        "######·## ###  ### ##·######" +
        "######·## #      # ##·######" +
        "      ·   #      #   ·      " +
        "######·## #      # ##·######" +
        "######·## ######## ##·######" +
        "######·##          ##·######" +
        "######·## ######## ##·######" +
        "######·## ######## ##·######" +
        "#············##············#" +
        "#·####·#####·##·#####·####·#" +
        "#·####·#####·##·#####·####·#" +
        "#@··##·······  ·······##··@#" +
        "###·##·##·########·##·##·###" +
        "###·##·##·########·##·##·###" +
        "#······##····##····##······#" +
        "#·##########·##·##########·#" +
        "#·##########·##·##########·#" +
        "#··························#" +
        "############################";

    public static Tile[][] createMaze()
    {
        Tile[][] maze = new Tile[Maze.MAZE_ROWS][Maze.MAZE_COLUMNS];

        int i = 0;

        for (int r = 0; r < Maze.MAZE_ROWS; r++)
        {
            for (int c = 0; c < Maze.MAZE_COLUMNS; c++)
            {
                maze[r][c] = parseTileString(mazeAscii.charAt(i), new TilePosition(r, c));
                i++;
            }
        }

        return maze;
    }

    public static Tile[][] createTraversalLayer()
    {
        Tile[][] traversalLayer = new Tile[Maze.MAZE_ROWS][Maze.MAZE_COLUMNS];

        int i = 0;

        for (int r = 0; r < Maze.MAZE_ROWS; r++)
        {
            for (int c = 0; c < Maze.MAZE_COLUMNS; c++)
            {
                if (mazeAscii.charAt(i) == '#' || mazeAscii.charAt(i) == ' ')
                {
                    traversalLayer[r][c] = parseTileString(mazeAscii.charAt(i), new TilePosition(r, c));
                }
                else
                {
                    traversalLayer[r][c] = new Tile(TileType.EMPTY, new TilePosition(r, c));
                }
                i++;
            }
        }

        return traversalLayer;
    }

    public static Tile[][] createPelletLayer()
    {
        Tile[][] pelletLayer = new Tile[Maze.MAZE_ROWS][Maze.MAZE_COLUMNS];

        int i = 0;

        for (int r = 0; r < Maze.MAZE_ROWS; r++)
        {
            for (int c = 0; c < Maze.MAZE_COLUMNS; c++)
            {
                if (mazeAscii.charAt(i) == '·' || mazeAscii.charAt(i) == '@')
                {
                    pelletLayer[r][c] = parseTileString(mazeAscii.charAt(i), new TilePosition(r, c));
                }
                i++;
            }
        }

        return pelletLayer;
    }


    private static Tile parseTileString(Character tileAscii, TilePosition position)
    {
        return switch (tileAscii)
        {
            case '#' -> new Tile(TileType.WALL,  position);
            case 'G' -> new Tile(TileType.GHOST,  position);
            case 'P' -> new Tile(TileType.PACMAN,  position);
            case 'F' -> new Tile(TileType.GHOST_FRIGHTENED,  position);
            case 'Ü' -> new Tile(TileType.PACMAN_POWER,  position);
            case '·' -> new Tile(TileType.PELLET,  position);
            case '@' -> new Tile(TileType.POWER_PELLET,  position);
            default -> new Tile(TileType.EMPTY,  position);
        };
    }

    public static String getMazeAscii()
    {
        StringBuilder sb = new StringBuilder();

        int i = 0;

        for (int r = 0; r < Maze.MAZE_ROWS; r++)
        {
            for (int c = 0; c < Maze.MAZE_COLUMNS; c++)
            {
                sb.append(mazeAscii.charAt(i));
                i++;
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
