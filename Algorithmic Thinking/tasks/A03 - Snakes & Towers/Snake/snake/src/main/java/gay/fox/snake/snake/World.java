package gay.fox.snake.snake;

import javafx.geometry.Pos;

import java.util.Random;

public class World
{
    private Tile[][] grid;
    private final int gridSize;

    private Snake snake;

    private final int MINIMUM_GRID_SIZE = 5;
    private Position currentFoodPosition;

    public World(int gridSize)
    {
        if (gridSize < MINIMUM_GRID_SIZE)
        {
            throw new IllegalArgumentException("Grid size must be greater than or equal to 10.");
        }

        this.gridSize = gridSize;
        grid = new Tile[gridSize][gridSize];

        fillTraversalTiles();

        spawnFood();
    }

    public void spawnFood()
    {
        Random rand = new Random();
        Position food = new Position(rand.nextInt(0, gridSize), rand.nextInt(0, gridSize));
        setTile(food, Tile.FOOD);
        currentFoodPosition = food;
    }

    public Position getCurrentFoodPosition()
    {
        return currentFoodPosition;
    }

    public Position wrapAroundPosition(Position position)
    {
        if (position.getX() < 0)
        {
            position.setX(gridSize - 1);
        }
        else if (position.getX() >= gridSize)
        {
            position.setX(0);
        }

        if (position.getY() < 0)
        {
            position.setY(gridSize - 1);
        }
        else if (position.getY() >= gridSize)
        {
            position.setY(0);
        }

        return new Position(position.getX(), position.getY());
    }

    private void fillTraversalTiles()
    {
        for (int i = 0; i < gridSize; i++)
        {
            for (int j = 0; j < gridSize; j++)
            {
                grid[i][j] = Tile.TRAVERSABLE;
            }
        }
    }

    private void drawSnake()
    {
        for (Position snakePos : snake)
        {
            if (snakePos.equals(snake.getFirst()))
            {
                setTile(snakePos, Tile.SNAKE_HEAD);
            }
            else
            {
                setTile(snakePos, Tile.SNAKE_TAIL);
            }
        }
    }

    private void drawFood()
    {
        setTile(currentFoodPosition, Tile.FOOD);
    }

    private void setTile(Position pos, Tile tile)
    {
        grid[pos.getY()][pos.getX()] = tile;
    }

    public void update()
    {
        fillTraversalTiles();
        drawFood();
        snake.move();
        drawSnake();
    }

    public Tile getTile(Position pos)
    {
        return grid[pos.getY()][pos.getX()];
    }

    public void addSnake(Snake snake)
    {
        this.snake = snake;
    }

    public Position getCenter()
    {
        return new Position(gridSize / 2, gridSize / 2);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < gridSize; y++)
        {
            for (int x = 0; x < gridSize; x++)
            {
                Tile tile = grid[y][x];

                char c;
                if (tile == null)
                {
                    c = '.'; // empty/uninitialized
                }
                else
                {
                    switch (tile)
                    {
                        case SNAKE_HEAD -> c = 'H';
                        case SNAKE_TAIL -> c = 'o';
                        case FOOD -> c = '*';
                        case TRAVERSABLE -> c = '·';
                        default -> c = '?';
                    }
                }

                sb.append(c).append(' ');
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
