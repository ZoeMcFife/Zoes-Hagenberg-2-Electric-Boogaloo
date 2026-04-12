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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SnakeController
{
    @FXML
    public Label snakeTitle;

    @FXML
    public Canvas snakeCanvas;
    @FXML
    public Button startButton;
    @FXML
    public Label currentDifficultyLabel;

    @FXML
    public CheckBox worldSpawnWithWallsToggle;
    @FXML
    public Slider worldSizeSlider;
    @FXML
    public Label scoreLabel;

    private World world;
    private Snake snake;

    private int gridSize;
    private int pixelGridSize;
    private GraphicsContext gc;

    private final Color traversableTileColor = Color.BLACK;
    private final Color foodTileColor = Color.RED;
    private final Color snakeHeadColor = Color.YELLOW;
    private final Color snakeTailColor = Color.GREEN;
    private final Color wallColor = Color.BLUEVIOLET;

    private final int EASY_TICK_TIME = 500;
    private final int MEDIUM_TICK_TIME = 300;
    private final int HARD_TICK_TIME = 150;
    private final int SUICIDE_TICK_TIME = 30;

    private int current_tick = EASY_TICK_TIME;

    private Timeline gameLoop;

    private boolean hasPressedKeyThisTick = false;

    @FXML
    public void onStartButtonPressed(ActionEvent actionEvent)
    {
        initialize();
    }

    private void update()
    {
        hasPressedKeyThisTick = false;
        world.update();

        scoreLabel.setText("Score: " + snake.getScore());

        drawBoard();

        if (world.isGameOver())
        {
            gameLoop.stop();
            snakeTitle.setText("GAME OVER");
            startButton.setText("Play Again");
        }
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
                    case WALL:
                        drawTileAtPositionInCanvas(wallColor, new Position(i, j));
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
        scoreLabel.setText("Score: " + 0);
        startButton.setText("RESTART");
        snakeTitle.setText("Snake");

        gridSize = (int) worldSizeSlider.getValue();

        // Snake Game
        world = new World(gridSize, worldSpawnWithWallsToggle.isSelected());
        snake = new Snake(world);
        world.addSnake(snake);

        // Game Loop
        if (gameLoop != null)
            gameLoop.stop();

        gameLoop = new Timeline(new KeyFrame(Duration.millis(current_tick), e -> update()));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();

        // Canvas
        gc = snakeCanvas.getGraphicsContext2D();

        pixelGridSize = (int) (snakeCanvas.getHeight() / gridSize);

        snakeCanvas.setFocusTraversable(true);
        snakeCanvas.setOnKeyPressed(event ->
        {
            if (hasPressedKeyThisTick)
            {
                return;
            }

            switch (event.getCode())
            {
                case W:
                    snake.moveUp();
                    hasPressedKeyThisTick = true;
                    break;
                case A:
                    snake.moveLeft();
                    hasPressedKeyThisTick = true;
                    break;
                case S:
                    snake.moveDown();
                    hasPressedKeyThisTick = true;
                    break;
                case D:
                    snake.moveRight();
                    hasPressedKeyThisTick = true;
                    break;
            }
        });

        snakeCanvas.requestFocus();
    }

    @FXML
    public void onEasyButtonPressed(ActionEvent actionEvent)
    {
        currentDifficultyLabel.setText("EASY");
        current_tick = EASY_TICK_TIME;
    }

    @FXML
    public void onMediumButtonPressed(ActionEvent actionEvent)
    {
        currentDifficultyLabel.setText("MEDIUM");
        current_tick = MEDIUM_TICK_TIME;
    }

    @FXML
    public void onHardButtonPressed(ActionEvent actionEvent)
    {
        currentDifficultyLabel.setText("HARD");
        current_tick = HARD_TICK_TIME;
    }

    @FXML
    public void onSuicideButtonPressed(ActionEvent actionEvent)
    {
        currentDifficultyLabel.setText("SUICIDE");
        current_tick = SUICIDE_TICK_TIME;
    }
}
