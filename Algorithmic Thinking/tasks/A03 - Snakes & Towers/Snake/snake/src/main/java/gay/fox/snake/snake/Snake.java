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
        append(new Position(center.getX() - 3, center.getY()));
        append(new Position(center.getX() - 4, center.getY()));
        append(new Position(center.getX() - 5, center.getY()));
        append(new Position(center.getX() - 6, center.getY()));

        IO.println("snake made: " + this);

        direction = Direction.RIGHT;

        wrapAround();
    }

    public void feed()
    {
        Position newTail = new Position(0,0);
        newTail.copy(getLast());
        newTail.shift(getOppositeDirection(direction));

        append(newTail);
        IO.println("Snake feed: " + newTail);
    }

    public void move()
    {
        move(direction);
    }

    public void move(Direction direction)
    {
        for (int i = getSize() - 1; i > 0; i--)
        {
            set(i, new Position(get(i - 1).getX(), get(i - 1).getY()));
        }

        getFirst().shift(direction);

        wrapAround();

        if (getFirst().equals(world.getCurrentFoodPosition()))
        {
            world.spawnFood();
            feed();
        }
    }

    public void moveLeft()
    {
        if (direction == Direction.RIGHT)
        {
            return;
        }

        direction = Direction.LEFT;
    }

    public void moveRight()
    {
        if (direction == Direction.LEFT)
        {
            return;
        }

        direction = Direction.RIGHT;
    }

    public void moveUp()
    {
        if (direction == Direction.DOWN)
        {
            return;
        }

        direction = Direction.UP;
    }

    public void moveDown()
    {
        if (direction == Direction.UP)
        {
            return;
        }

        direction = Direction.DOWN;
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
