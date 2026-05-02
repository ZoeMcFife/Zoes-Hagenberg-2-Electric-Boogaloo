package gay.fox.pacman.maze.tile;

public class TilePosition
{
    private int row;
    private int col;

    public TilePosition()
    {
        this.row = 0;
        this.col = 0;
    }

    public TilePosition(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    public TilePosition(TilePosition tilePosition)
    {
        this.row = tilePosition.getRow();
        this.col = tilePosition.getCol();
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public void setCol(int col)
    {
        this.col = col;
    }

    @Override
    public String toString()
    {
        return "TilePosition [row=" + row + ", col=" + col + "]";
    }
}
