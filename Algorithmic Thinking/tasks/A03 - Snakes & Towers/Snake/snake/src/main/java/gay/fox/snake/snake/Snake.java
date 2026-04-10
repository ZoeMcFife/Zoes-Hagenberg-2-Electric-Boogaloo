package gay.fox.snake.snake;

import gay.fox.data.LinkedList;

public class Snake extends LinkedList<Position>
{
    private World world;
    private Direction direction;

    public Snake(World world)
    {
        setWorld(world);

        Position center = world.getCenter();

        append(center);
        append(new Position(center.getX() - 1, center.getY()));
        append(new Position(center.getX() - 2, center.getY()));

        direction = Direction.RIGHT;

        wrapAround();
    }

    public void feed()
    {
        Position newTail = new Position(0,0);
        newTail.copy(getLast());
        newTail.shift(getOppositeDirection(direction));

        append(newTail);
    }

    public void move()
    {
        move(direction);
    }

    public void move(Direction direction)
    {
        for (int i = getSize() - 1; i > 0; i--)
        {
            get(i).copy(get(i-1));
        }

        getFirst().shift(direction);

        wrapAround();
    }

    private void wrapAround()
    {
        for (Position p : this)
        {
            p.copy(world.wrapAroundPosition(p));
        }
    }

    private void setWorld(World world)
    {
        this.world = world;
    }

    public World getWorld()
    {
        return world;
    }

    private Direction getOppositeDirection(Direction direction)
    {
        return switch (direction) {
            case UP -> Direction.DOWN;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
            default -> Direction.UP;
        };
    }
}
