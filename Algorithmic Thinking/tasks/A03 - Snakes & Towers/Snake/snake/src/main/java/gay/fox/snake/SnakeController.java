package gay.fox.snake;

import gay.fox.snake.snake.Position;
import gay.fox.snake.snake.Snake;
import gay.fox.snake.snake.Tile;
import gay.fox.snake.snake.World;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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

    private Timeline gameLoop;

    public void onStartButtonPressed(ActionEvent actionEvent)
    {
        initialize();

        gameLoop = new Timeline(new KeyFrame(Duration.millis(500), e ->
        {
            update();
            drawGame();
        }));

        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private void drawGame()
    {
        drawBoard();

        IO.println(world);
        IO.println(snake);
    }

    private void update()
    {
        world.update();
    }

    private void drawBoard()
    {
        gc.setFill(traversableTileColor);
        gc.fillRect(0, 0, snakeCanvas.getWidth(), snakeCanvas.getHeight());

        for (int i = 0; i < gridSize; i++)
        {
            for (int  j = 0; j < gridSize; j++)
            {
                Tile t = world.getTile(new Position(i, j));

                switch (t)
                {
                    case FOOD:
                        drawTileAtPositionInCanvas(foodTileColor, new Position(i, j));
                        break;
                    case SNAKE_HEAD:
                        drawTileAtPositionInCanvas(snakeHeadColor, new Position(i, j));
                        break;
                    case SNAKE_TAIL:
                        drawTileAtPositionInCanvas(snakeTailColor, new Position(i, j));
                        break;
                }
            }
        }
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

        snakeCanvas.setFocusTraversable(true);
        snakeCanvas.setOnKeyPressed(event ->
        {
            switch (event.getCode())
            {
                case W -> snake.moveUp();
                case S -> snake.moveDown();
                case A -> snake.moveLeft();
                case D -> snake.moveRight();
            }
        });

        snakeCanvas.requestFocus();
    }
}
