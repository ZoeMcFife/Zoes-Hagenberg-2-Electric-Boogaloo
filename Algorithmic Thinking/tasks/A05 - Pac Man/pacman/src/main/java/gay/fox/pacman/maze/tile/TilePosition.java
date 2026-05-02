package gay.fox.pacman.maze.tile;

import java.util.Objects;

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

    public double getManhattanDistance(TilePosition tilePosition)
    {
        return Math.abs(this.row - tilePosition.getRow()) + Math.abs(this.col - tilePosition.getCol());
    }

    @Override
    public String toString()
    {
        return "TilePosition [row=" + row + ", col=" + col + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TilePosition that = (TilePosition) o;
        return getRow() == that.getRow() && getCol() == that.getCol();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getCol());
    }
}
