package gay.fox.data;

import gay.fox.snake.snake.Position;
import gay.fox.snake.snake.Snake;
import gay.fox.snake.snake.Tile;
import gay.fox.snake.snake.World;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Snake}.
 *
 * <p>Covers initial state, movement, direction locking, wall collision,
 * food consumption, scoring, self-collision, and wrap-around behaviour.
 */
@DisplayName("Snake")
class SnakeTest {

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private static final int SIZE = 15; // large enough that no walls are a concern

    /** Creates a fresh plain world + snake pair ready to test. */
    private Snake freshSnake() {
        World w = new World(SIZE, false);
        Snake s = new Snake(w);
        w.addSnake(s);
        return s;
    }

    /** Creates a fresh walled world + snake pair. */
    private Snake freshWalledSnake() {
        World w = new World(SIZE, true);
        Snake s = new Snake(w);
        w.addSnake(s);
        return s;
    }

    // =========================================================================
    // Initial State
    // =========================================================================

    @Nested
    @DisplayName("Initial State")
    class InitialState {

        @Test
        @DisplayName("Snake starts with the correct initial size (3)")
        void initialSize() {
            assertEquals(3, freshSnake().getSize());
        }

        @Test
        @DisplayName("Snake head is at the world centre on spawn")
        void headAtCenter() {
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            Position center = w.getCenter();

            assertEquals(center.getX(), s.getFirst().getX());
            assertEquals(center.getY(), s.getFirst().getY());
        }

        @Test
        @DisplayName("Initial score is 0")
        void initialScoreIsZero() {
            assertEquals(0, freshSnake().getScore());
        }

        @Test
        @DisplayName("Snake is not self-colliding at spawn")
        void notSelfCollidingAtSpawn() {
            assertFalse(freshSnake().isSelfColliding());
        }

        @Test
        @DisplayName("Snake body segments are distinct on spawn")
        void bodySegmentsDistinctAtSpawn() {
            Snake s = freshSnake();
            // Check all three initial positions are unique
            Position head = s.get(0);
            Position mid  = s.get(1);
            Position tail = s.get(2);

            assertFalse(head.equals(mid),  "head and mid overlap");
            assertFalse(head.equals(tail), "head and tail overlap");
            assertFalse(mid.equals(tail),  "mid and tail overlap");
        }

        @Test
        @DisplayName("getWorld() returns the world the snake was constructed with")
        void getWorldReturnsBoundWorld() {
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            assertSame(w, s.getWorld());
        }
    }

    // =========================================================================
    // Movement
    // =========================================================================

    @Nested
    @DisplayName("Movement")
    class Movement {

        @Test
        @DisplayName("Head moves right by one tile per update (default direction)")
        void headMovesRightByDefault() {
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            w.addSnake(s);
            // Steer food away so the snake doesn't grow
            w.spawnFood(new Position(0, 0));

            int startX = s.getFirst().getX();
            w.update();
            assertEquals(startX + 1, s.getFirst().getX());
        }

        @Test
        @DisplayName("Head moves left after moveLeft()")
        void headMovesLeft() {
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            w.addSnake(s);
            w.spawnFood(new Position(0, 0));

            // Turn around: must go up/down first (can't reverse directly)
            s.moveUp();
            w.update();
            s.moveLeft();
            int beforeX = s.getFirst().getX();
            w.update();

            assertEquals(beforeX - 1, s.getFirst().getX());
        }

        @Test
        @DisplayName("Head moves up after moveUp()")
        void headMovesUp() {
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            w.addSnake(s);
            w.spawnFood(new Position(0, 0));

            s.moveUp();
            int beforeY = s.getFirst().getY();
            w.update();

            assertEquals(beforeY - 1, s.getFirst().getY());
        }

        @Test
        @DisplayName("Head moves down after moveDown()")
        void headMovesDown() {
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            w.addSnake(s);
            w.spawnFood(new Position(0, 0));

            s.moveDown();
            int beforeY = s.getFirst().getY();
            w.update();

            assertEquals(beforeY + 1, s.getFirst().getY());
        }

        @Test
        @DisplayName("Snake body follows the head after movement")
        void bodyFollowsHead() {
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            w.addSnake(s);
            w.spawnFood(new Position(0, 0));

            Position oldHead = new Position(s.getFirst().getX(), s.getFirst().getY());
            w.update();

            // Segment 1 should now be where the head was
            assertEquals(oldHead.getX(), s.get(1).getX());
            assertEquals(oldHead.getY(), s.get(1).getY());
        }
    }

    // =========================================================================
    // Direction Locking (can't reverse)
    // =========================================================================

    @Nested
    @DisplayName("Direction Locking")
    class DirectionLocking {

        @Test
        @DisplayName("Cannot reverse from RIGHT to LEFT directly")
        void cannotReverseRightToLeft() {
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            w.addSnake(s);
            w.spawnFood(new Position(0, 0));

            int startX = s.getFirst().getX();
            s.moveLeft(); // should be ignored
            w.update();

            // Head should still have moved right (+1), not left (-1)
            assertTrue(s.getFirst().getX() >= startX,
                    "Snake reversed illegally from RIGHT to LEFT");
        }

        @Test
        @DisplayName("Cannot reverse from UP to DOWN directly")
        void cannotReverseUpToDown() {
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            w.addSnake(s);
            w.spawnFood(new Position(0, 0));

            s.moveUp();
            w.update();

            int yAfterUp = s.getFirst().getY();
            s.moveDown(); // should be ignored
            w.update();

            assertTrue(s.getFirst().getY() <= yAfterUp,
                    "Snake reversed illegally from UP to DOWN");
        }

        @Test
        @DisplayName("Cannot reverse from DOWN to UP directly")
        void cannotReverseDownToUp() {
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            w.addSnake(s);
            w.spawnFood(new Position(0, 0));

            s.moveDown();
            w.update();

            int yAfterDown = s.getFirst().getY();
            s.moveUp(); // should be ignored
            w.update();

            assertTrue(s.getFirst().getY() >= yAfterDown,
                    "Snake reversed illegally from DOWN to UP");
        }

        @Test
        @DisplayName("Cannot reverse from LEFT to RIGHT directly")
        void cannotReverseLeftToRight() {
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            w.addSnake(s);
            w.spawnFood(new Position(0, 0));

            s.moveUp();
            w.update();
            s.moveLeft();
            w.update(); // now heading left

            int xBeforeReversal = s.getFirst().getX();
            s.moveRight(); // should be ignored
            w.update();

            assertTrue(s.getFirst().getX() <= xBeforeReversal,
                    "Snake reversed illegally from LEFT to RIGHT");
        }
    }

    // =========================================================================
    // Food & Scoring
    // =========================================================================

    @Nested
    @DisplayName("Food & Scoring")
    class FoodAndScoring {

        @Test
        @DisplayName("Eating food increases snake size by 1")
        void eatingFoodIncreasesSize() {
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            w.addSnake(s);

            // Place food directly ahead of the head (which moves RIGHT)
            Position head = s.getFirst();
            w.spawnFood(new Position(head.getX() + 1, head.getY()));

            int sizeBefore = s.getSize();
            w.update();

            assertEquals(sizeBefore + 1, s.getSize());
        }

        @Test
        @DisplayName("Eating food increments the score")
        void eatingFoodIncrementsScore() {
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            w.addSnake(s);

            Position head = s.getFirst();
            w.spawnFood(new Position(head.getX() + 1, head.getY()));

            w.update();
            assertEquals(1, s.getScore());
        }

        @Test
        @DisplayName("Eating multiple food items accumulates the score")
        void multipleEatsAccumulateScore() {
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            w.addSnake(s);

            for (int i = 0; i < 3; i++) {
                Position head = s.getFirst();
                w.spawnFood(new Position(head.getX() + 1, head.getY()));
                w.update();
            }

            assertEquals(3, s.getScore());
        }

        @Test
        @DisplayName("New food is placed somewhere after the old food is eaten")
        void newFoodSpawnedAfterEating() {
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            w.addSnake(s);

            Position head = s.getFirst();
            Position foodPos = new Position(head.getX() + 1, head.getY());
            w.spawnFood(foodPos);

            w.update(); // snake eats the food

            // A new food should have been placed somewhere
            Position newFood = w.getCurrentFoodPosition();
            assertNotNull(newFood);
            assertEquals(Tile.FOOD, w.getTile(newFood));
        }
    }

    // =========================================================================
    // Self-Collision
    // =========================================================================

    @Nested
    @DisplayName("Self-Collision")
    class SelfCollision {

        @Test
        @DisplayName("isSelfColliding() returns false when segments are all distinct")
        void noSelfCollisionWhenSegmentsDistinct() {
            assertFalse(freshSnake().isSelfColliding());
        }

        @Test
        @DisplayName("Game over is triggered when snake runs into itself")
        void gameOverOnSelfCollision() {
            // To force a self-collision we need the snake to grow and loop back.
            // Feed it several times and then make a tight U-turn.
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            w.addSnake(s);

            // Grow the snake to ≥ 5 segments by feeding it 4 times
            for (int i = 0; i < 4; i++) {
                Position head = s.getFirst();
                w.spawnFood(new Position(head.getX() + 1, head.getY()));
                w.update();
            }

            // Head is pointing RIGHT; make it chase its own tail: DOWN → LEFT → UP
            s.moveDown(); w.update();
            s.moveLeft(); w.update();
            s.moveLeft(); w.update();
            s.moveUp();   w.update();
            s.moveUp();   w.update();

            assertTrue(w.isGameOver(), "Expected game over after self-collision");
        }
    }

    // =========================================================================
    // Wall Collision
    // =========================================================================

    @Nested
    @DisplayName("Wall Collision")
    class WallCollision {

        @Test
        @DisplayName("Hitting a wall triggers game over in a walled world")
        void wallCollisionTriggersGameOver() {
            World w = new World(SIZE, true);
            Snake s = new Snake(w);
            w.addSnake(s);

            // Move UP repeatedly until the wall is reached
            s.moveUp();
            for (int i = 0; i < SIZE; i++) {
                w.update();
                if (w.isGameOver()) break;
            }

            assertTrue(w.isGameOver());
        }

        @Test
        @DisplayName("Snake wraps around correctly in a wall-free world")
        void snakeWrapsAroundInPlainWorld() {
            World w = new World(SIZE, false);
            Snake s = new Snake(w);
            w.addSnake(s);

            // Suppress food consumption by placing food off to the side
            w.spawnFood(new Position(0, 1));

            // Move UP enough times to overflow the top boundary
            s.moveUp();
            for (int i = 0; i < SIZE + 1; i++) {
                w.update();
            }

            // Game should NOT be over — we simply wrapped around
            assertFalse(w.isGameOver(), "Snake should have wrapped, not died");
        }
    }

    // =========================================================================
    // feed() Directly
    // =========================================================================

    @Nested
    @DisplayName("feed()")
    class FeedDirect {

        @Test
        @DisplayName("feed() appends a segment to the tail")
        void feedAppendsSegment() {
            Snake s = freshSnake();
            int before = s.getSize();
            s.feed();
            assertEquals(before + 1, s.getSize());
        }

        @Test
        @DisplayName("feed() increments the score")
        void feedIncrementsScore() {
            Snake s = freshSnake();
            s.feed();
            assertEquals(1, s.getScore());
        }
    }
}