package gay.fox.pacman.maze;

public class Tile
{
    private TileType type;
    private TilePosition pos;

    public Tile(TileType type)
    {
        this.type = type;
    }

    public Tile(TileType type, TilePosition pos)
    {
        this.type = type;
        this.pos = pos;
    }

    public TileType getType()
    {
        return type;
    }

    public void setType(TileType type)
    {
        this.type = type;
    }

    public TilePosition getPos()
    {
        return pos;
    }

    public void setPos(TilePosition pos)
    {
        this.pos = pos;
    }

    public String getTileAsciiAppearance()
    {
        return switch (type)
        {
            case WALL -> "#";
            case GHOST -> "G";
            case GHOST_FRIGHTENED -> "F";
            case PACMAN -> "P";
            case PACMAN_POWER -> "Ü";
            case PELLET -> "·";
            case POWER_PELLET -> "@";
            case EMPTY -> " ";
        };
    }

    @Override
    public String toString()
    {
        return "Tile{" + "type=" + type + ", pos=" + pos + '}';
    }
}
