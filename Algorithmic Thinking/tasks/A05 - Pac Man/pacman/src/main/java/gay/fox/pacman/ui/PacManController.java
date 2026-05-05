package gay.fox.pacman.ui;

import gay.fox.pacman.actor.Actor;
import gay.fox.pacman.actor.Direction;
import gay.fox.pacman.actor.ghost.Ghost;
import gay.fox.pacman.actor.player.Player;
import gay.fox.pacman.maze.Maze;
import gay.fox.pacman.maze.layer.Layer;
import gay.fox.pacman.maze.tile.Tile;
import gay.fox.pacman.maze.tile.TileType;
import gay.fox.pacman.ui.colors.TileDrawer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class PacManController
{
    @FXML
    public Button startButton;

    @FXML
    private Canvas pacmanCanvas;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label livesLabel;

    private GraphicsContext gc;

    private int pixelGridSize;
    private Maze maze;

    private Timeline gameLoop;
    private final int tickRate = 5;

    private int tickCounter = 0;

    private Player playerActor;
    private boolean hasPressedKeyThisTick = false;

    private void init()
    {
        pixelGridSize = (int) (pacmanCanvas.getHeight() / Maze.MAZE_ROWS);
        maze = new Maze();
        maze.addGhosts();

        playerActor = maze.createPlayer();

        gc = pacmanCanvas.getGraphicsContext2D();

        if (gameLoop != null)
            gameLoop.stop();

        gameLoop = new Timeline(new KeyFrame(Duration.millis((double) 1000 / tickRate), e -> update()));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();

        pacmanCanvas.requestFocus();
        pacmanCanvas.setFocusTraversable(true);
        pacmanCanvas.setOnKeyPressed(event ->
        {
            if (hasPressedKeyThisTick)
            {
                return;
            }

            switch (event.getCode())
            {
                case W:
                    playerActor.setCurrentDirection(Direction.UP);
                    hasPressedKeyThisTick = true;
                    break;
                case A:
                    playerActor.setCurrentDirection(Direction.LEFT);
                    hasPressedKeyThisTick = true;
                    break;
                case S:
                    playerActor.setCurrentDirection(Direction.DOWN);
                    hasPressedKeyThisTick = true;
                    break;
                case D:
                    playerActor.setCurrentDirection(Direction.RIGHT);
                    hasPressedKeyThisTick = true;
                    break;
            }
        });
    }

    @FXML
    private void start()
    {
        init();
    }

    private void update()
    {
        tickCounter++;

        if (tickCounter >= tickRate)
            tickCounter = 0;

        hasPressedKeyThisTick = false;
        playerActor.collectPellet();
        playerActor.move();
        playerActor.updatePowerSteps();

        scoreLabel.setText(String.valueOf(playerActor.getPoints()));
        livesLabel.setText(playerActor.getLives() + " Lives left");

        if (tickCounter % 3 == 0)
            maze.updateGhosts();

        if (maze.ghostOverlapsWithPlayer())
        {
            try
            {
                gameLoop.stop();
                Thread.sleep(1000);
                gameLoop.play();
            }
            catch (InterruptedException e)
            {
                // do nothing
            }

            maze.reset();
            playerActor.death();

            if (playerActor.getLives() <= 0)
            {
                gameLoop.stop();
                livesLabel.setText(playerActor.getLives() + " Lives left");
                startButton.setText("You died! Restart Game!");
                return;
            }
        }

        if (playerActor.getPoints() >= 280)
        {
            gameLoop.stop();
            startButton.setText("You won! Restart Game!");
            return;
        }

        drawFrame();
    }

    private void drawFrame()
    {
        gc.clearRect(0, 0, pacmanCanvas.getWidth(), pacmanCanvas.getHeight());

        for (Layer layer : maze.getLayersInDrawOrder())
        {
            for (Tile t : layer.flatten())
            {
                TileDrawer.drawTile(gc, t, pixelGridSize);
            }
        }

    }
}
