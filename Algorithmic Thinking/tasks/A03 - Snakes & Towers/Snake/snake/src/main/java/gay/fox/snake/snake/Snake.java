package gay.fox.snake.snake;

import gay.fox.data.LinkedList;

public class Snake extends LinkedList<Position>
{
    private World world;
    private Direction direction;
    private final int initialSnakeSize = 3;
    private int score = 0;

    public Snake(World world)
    {
        setWorld(world);

        Position center = world.getCenter();

        for (int i = 0; i < initialSnakeSize; i++)
        {
            append(new Position(center.getX() - i, center.getY()));
        }

        direction = Direction.RIGHT;

        wrapAround();
    }

    public boolean isSelfColliding()
    {
        return countOccurrences(getFirst()) > 1;
    }

    public void feed()
    {
        Position newTail = new Position(0,0);
        newTail.copy(getLast());
        newTail.shift(getOppositeDirection(direction));

        score++;

        append(newTail);
    }

    public int getScore()
    {
        return score;
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
        if (world.getTile(getFirst()).equals(Tile.WALL))
        {
            world.setIsGameOver(true);
        }
    }


    // These could've been done more efficiently but oh well lol
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
