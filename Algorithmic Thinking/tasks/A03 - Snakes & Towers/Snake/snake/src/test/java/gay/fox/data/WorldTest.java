package gay.fox.data;

import gay.fox.snake.snake.Position;
import gay.fox.snake.snake.Snake;
import gay.fox.snake.snake.Tile;
import gay.fox.snake.snake.World;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link World}.
 *
 * <p>Covers grid initialisation, wall generation, food spawning,
 * position wrapping, win/lose conditions, and the toString() renderer.
 */
@DisplayName("World")
class WorldTest {

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /** Returns a plain world (no walls) with a snake already attached. */
    private World plainWorld(int size) {
        World w = new World(size, false);
        Snake s = new Snake(w);
        w.addSnake(s);
        return w;
    }

    /** Returns a walled world with a snake already attached. */
    private World walledWorld(int size) {
        World w = new World(size, true);
        Snake s = new Snake(w);
        w.addSnake(s);
        return w;
    }

    // =========================================================================
    // Construction
    // =========================================================================

    @Nested
    @DisplayName("Construction")
    class Construction {

        @Test
        @DisplayName("Minimum legal grid size (5) is accepted")
        void minimumGridSizeIsAccepted() {
            assertDoesNotThrow(() -> new World(5, false));
        }

        @Test
        @DisplayName("All tiles are TRAVERSABLE in a plain world")
        void allTilesTraversableNoWalls() {
            World w = new World(8, false);
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    Tile tile = w.getTile(new Position(x, y));
                    assertNotEquals(Tile.WALL, tile,
                            "Unexpected WALL at (%d, %d)".formatted(x, y));
                }
            }
        }

        @Test
        @DisplayName("Border tiles are WALLs when createWall=true")
        void borderTilesAreWalls() {
            int size = 8;
            World w = new World(size, true);

            for (int i = 0; i < size; i++) {
                assertEquals(Tile.WALL, w.getTile(new Position(i, 0)),        "top row x=%d".formatted(i));
                assertEquals(Tile.WALL, w.getTile(new Position(i, size - 1)), "bottom row x=%d".formatted(i));
                assertEquals(Tile.WALL, w.getTile(new Position(0, i)),        "left col y=%d".formatted(i));
                assertEquals(Tile.WALL, w.getTile(new Position(size - 1, i)), "right col y=%d".formatted(i));
            }
        }

        @Test
        @DisplayName("Interior tiles remain TRAVERSABLE when createWall=true")
        void interiorTilesTraversableWithWalls() {
            int size = 8;
            World w = new World(size, true);

            for (int y = 1; y < size - 1; y++) {
                for (int x = 1; x < size - 1; x++) {
                    Tile tile = w.getTile(new Position(x, y));
                    assertNotEquals(Tile.WALL, tile,
                            "Interior tile at (%d, %d) should not be WALL".formatted(x, y));
                }
            }
        }

        @Test
        @DisplayName("Food is spawned somewhere on the grid after construction")
        void foodSpawnedOnConstruction() {
            World w = new World(10, false);
            Position food = w.getCurrentFoodPosition();

            assertNotNull(food);
            assertEquals(Tile.FOOD, w.getTile(food));
        }

        @Test
        @DisplayName("getCenter() returns exact midpoint for odd-sized grid")
        void getCenterOddGrid() {
            World w = new World(9, false);
            Position center = w.getCenter();
            assertEquals(4, center.getX());
            assertEquals(4, center.getY());
        }

        @Test
        @DisplayName("getCenter() returns floor(size/2) for even-sized grid")
        void getCenterEvenGrid() {
            World w = new World(10, false);
            Position center = w.getCenter();
            assertEquals(5, center.getX());
            assertEquals(5, center.getY());
        }
    }

    // =========================================================================
    // Food Spawning
    // =========================================================================

    @Nested
    @DisplayName("Food Spawning")
    class FoodSpawning {

        @Test
        @DisplayName("spawnFood(Position) places FOOD at the given tile")
        void spawnFoodAtPosition() {
            World w = new World(8, false);
            Position target = new Position(3, 3);
            w.spawnFood(target);

            assertEquals(Tile.FOOD, w.getTile(target));
            assertEquals(target, w.getCurrentFoodPosition());
        }

        @Test
        @DisplayName("Random spawnFood() never places food on a WALL")
        void spawnFoodNeverOnWall() {
            World w = new World(10, true);

            for (int i = 0; i < 200; i++) {
                w.spawnFood();
                Position food = w.getCurrentFoodPosition();
                assertNotEquals(Tile.WALL, w.getTile(food),
                        "Food landed on a wall at " + food);
            }
        }

        @Test
        @DisplayName("getCurrentFoodPosition() tracks the latest food spawn")
        void currentFoodPositionTracked() {
            World w = new World(8, false);
            Position p = new Position(2, 2);
            w.spawnFood(p);

            assertEquals(p, w.getCurrentFoodPosition());
        }
    }

    // =========================================================================
    // Position Wrapping
    // =========================================================================

    @Nested
    @DisplayName("Position Wrapping")
    class PositionWrapping {

        @Test
        @DisplayName("x < 0 wraps to gridSize - 1")
        void wrapNegativeX() {
            World w = new World(8, false);
            Position result = w.wrapAroundPosition(new Position(-1, 4));
            assertEquals(7, result.getX());
        }

        @Test
        @DisplayName("x >= gridSize wraps to 0")
        void wrapOverflowX() {
            World w = new World(8, false);
            Position result = w.wrapAroundPosition(new Position(8, 4));
            assertEquals(0, result.getX());
        }

        @Test
        @DisplayName("y < 0 wraps to gridSize - 1")
        void wrapNegativeY() {
            World w = new World(8, false);
            Position result = w.wrapAroundPosition(new Position(4, -1));
            assertEquals(7, result.getY());
        }

        @Test
        @DisplayName("y >= gridSize wraps to 0")
        void wrapOverflowY() {
            World w = new World(8, false);
            Position result = w.wrapAroundPosition(new Position(4, 8));
            assertEquals(0, result.getY());
        }

        @Test
        @DisplayName("In-bounds position is returned unchanged")
        void inBoundsPositionUnchanged() {
            World w = new World(8, false);
            Position result = w.wrapAroundPosition(new Position(3, 5));
            assertEquals(3, result.getX());
            assertEquals(5, result.getY());
        }
    }

    // =========================================================================
    // Game-Over Condition
    // =========================================================================

    @Nested
    @DisplayName("Game-Over Condition")
    class GameOverCondition {

        @Test
        @DisplayName("isGameOver() is false immediately after construction")
        void notGameOverAtStart() {
            assertFalse(plainWorld(10).isGameOver());
        }

        @Test
        @DisplayName("setIsGameOver(true) flips the flag")
        void setGameOverFlipsFlag() {
            World w = plainWorld(10);
            w.setIsGameOver(true);
            assertTrue(w.isGameOver());
        }

        @Test
        @DisplayName("setIsGameOver(false) clears the flag")
        void setGameOverClearsFlag() {
            World w = plainWorld(10);
            w.setIsGameOver(true);
            w.setIsGameOver(false);
            assertFalse(w.isGameOver());
        }

        @Test
        @DisplayName("Snake hitting a wall triggers game over on update()")
        void wallCollisionTriggersGameOver() {
            // Size 5: walls on row 0 / row 4 / col 0 / col 4.
            // Centre = (2,2). Snake starts moving RIGHT.
            // Force it upward so it walks into the top wall.
            World w = new World(5, true);
            Snake s = new Snake(w);
            w.addSnake(s);

            s.moveUp();

            // Two updates to reach the wall (centre y=2, wall at y=0)
            w.update(); // y=1
            w.update(); // y=0 → WALL

            assertTrue(w.isGameOver());
        }
    }

    // =========================================================================
    // Win Condition
    // =========================================================================

    @Nested
    @DisplayName("Win Condition")
    class WinCondition {

        @Test
        @DisplayName("isGameWon() is false immediately after construction")
        void notWonAtStart() {
            assertFalse(plainWorld(10).isGameWon());
        }
    }

    // =========================================================================
    // toString()
    // =========================================================================

    @Nested
    @DisplayName("toString()")
    class ToStringRendering {

        @Test
        @DisplayName("Output contains 'H' (snake head) after a snake is added and update() runs")
        void outputContainsSnakeHead() {
            World w = plainWorld(8);
            // Place food away from the snake so it is not consumed accidentally
            w.spawnFood(new Position(0, 0));
            w.update();
            assertTrue(w.toString().contains("H"), "Expected 'H' for snake head");
        }

        @Test
        @DisplayName("Output contains '#' when createWall=true")
        void outputContainsWall() {
            World w = walledWorld(8);
            assertTrue(w.toString().contains("#"), "Expected '#' for wall tiles");
        }

        @Test
        @DisplayName("Output contains no '#' when createWall=false")
        void outputContainsNoWall() {
            World w = plainWorld(8);
            assertFalse(w.toString().contains("#"), "Did not expect '#' without walls");
        }

        @Test
        @DisplayName("Output contains '*' for food")
        void outputContainsFood() {
            World w = new World(8, false);
            assertTrue(w.toString().contains("*"), "Expected '*' for food tile");
        }

        @Test
        @DisplayName("Output has exactly gridSize newline-separated rows")
        void outputHasCorrectRowCount() {
            int size = 7;
            World w = new World(size, false);
            long rows = w.toString().lines().count();
            assertEquals(size, rows);
        }
    }
}