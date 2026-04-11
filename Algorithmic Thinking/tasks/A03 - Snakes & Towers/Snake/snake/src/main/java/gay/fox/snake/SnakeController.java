package gay.fox.snake;

import gay.fox.snake.snake.Position;
import gay.fox.snake.snake.Snake;
import gay.fox.snake.snake.World;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class SnakeController
{
    @FXML
    public Label snakeTitle;

    @FXML
    public Canvas snakeCanvas;
    public Button startButton;

    private World world;
    private Snake snake;

    private final int gridSize = 30;
    private int pixelGridSize;
    private GraphicsContext gc;

    private final Color traversableTileColor = Color.DARKGRAY;
    private final Color foodTileColor = Color.RED;
    private final Color snakeHeadColor = Color.YELLOW;
    private final Color snakeTailColor = Color.GREEN;

    public void onStartButtonPressed(ActionEvent actionEvent)
    {
        initialize();
        drawGame();

        drawTileAtPositionInCanvas(foodTileColor, new Position(5, 5));
    }

    private void drawGame()
    {
        drawBoard();
        drawSnake();
    }

    private void update()
    {
        world.update();
        snake.move();
    }

    private void drawBoard()
    {
        gc.setFill(traversableTileColor);
        gc.fillRect(0, 0, snakeCanvas.getWidth(), snakeCanvas.getHeight());
    }

    private void drawSnake()
    {
        for (Position p : snake)
        {
            if (p.equals(snake.getFirst()))
            {
                drawTileAtPositionInCanvas(snakeHeadColor, p);
            }

            drawTileAtPositionInCanvas(snakeTailColor, p);
        }
    }

    private void drawFood()
    {

    }

    private void drawTileAtPositionInCanvas(Color color, Position position)
    {
        gc.setFill(color);
        gc.fillRect(position.getX() * pixelGridSize, position.getY() * pixelGridSize, pixelGridSize, pixelGridSize);
    }

    private void initialize()
    {
        world = new World(gridSize);
        snake = new Snake(world);
        world.addSnake(snake);

        pixelGridSize = (int) (snakeCanvas.getHeight() / gridSize);

        gc = snakeCanvas.getGraphicsContext2D();
    }
}
