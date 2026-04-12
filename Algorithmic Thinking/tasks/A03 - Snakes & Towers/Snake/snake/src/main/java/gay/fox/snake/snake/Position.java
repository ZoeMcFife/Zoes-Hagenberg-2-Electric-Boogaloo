package gay.fox.snake.snake;

import java.util.Objects;

public class Position
{
    private int x;
    private int y;

    public Position(int x, int y)
    {
        setX(x);
        setY(y);
    }

    public void copy(Position other)
    {
        setX(other.getX());
        setY(other.getY());
    }

    public void shift(Direction direction)
    {
        switch (direction)
        {
            case UP:
                setY(getY() - 1);
                break;
            case DOWN:
                setY(getY() + 1);
                break;
            case LEFT:
                setX(getX() - 1);
                break;
            case RIGHT:
                setX(getX() + 1);
                break;
        }
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    @Override
    public String toString()
    {
        return "Position [x=" + x + ", y=" + y + "]";
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return getX() == position.getX() && getY() == position.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
