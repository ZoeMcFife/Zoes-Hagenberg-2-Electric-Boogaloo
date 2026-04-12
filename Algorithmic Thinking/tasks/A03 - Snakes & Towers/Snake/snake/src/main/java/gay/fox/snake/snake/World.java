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

    private boolean isGameOver = false;
    private boolean isGameWon = false;

    private boolean createWall = false;

    public World(int gridSize, boolean createWall)
    {
        if (gridSize < MINIMUM_GRID_SIZE)
        {
            throw new IllegalArgumentException("Grid size must be greater than or equal to 10.");
        }

        this.gridSize = gridSize;
        this.createWall = createWall;
        grid = new Tile[gridSize][gridSize];

        fillTraversalTiles();

        spawnFood();
    }

    public void spawnFood()
    {
        Random rand = new Random();
        Position food = new Position(rand.nextInt(0, gridSize), rand.nextInt(0, gridSize));

        if (getTile(food).equals(Tile.WALL))
        {
            spawnFood();
            return;
        }

        setTile(food, Tile.FOOD);
        currentFoodPosition = food;
    }

    public void spawnFood(Position position)
    {
        setTile(position, Tile.FOOD);
        currentFoodPosition = position;
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

        if (!createWall)
            return;

        for (int i = 0; i < gridSize; i++)
        {
            grid[0][i] = Tile.WALL;             // top row
            grid[gridSize - 1][i] = Tile.WALL;  // bottom row
            grid[i][0] = Tile.WALL;             // left column
            grid[i][gridSize - 1] = Tile.WALL;  // right column
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

        if (snake.isSelfColliding())
        {
            isGameOver = true;
        }

        if (createWall)
        {
            if (snake.getSize() >= gridSize * gridSize - (gridSize * 4 - 4))
            {
                isGameWon = true;
            }
        }
        else
        {
            if (snake.getSize() >= gridSize * gridSize)
            {
                isGameWon = true;
            }
        }
    }

    public void setIsGameOver(boolean isGameOver)
    {
        this.isGameOver = isGameOver;
    }

    public boolean isGameOver()
    {
        return isGameOver;
    }

    public boolean isGameWon()
    {
        return isGameWon;
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
                        case WALL -> c = '#';
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
