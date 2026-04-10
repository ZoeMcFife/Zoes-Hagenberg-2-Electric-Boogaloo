package gay.fox.snake.snake;

public class TestingSnake
{
    public static void main( String[] args )
    {
        World world = new World(5);
        Snake snake = new Snake(world);
        world.addSnake(snake);
        world.update();

        IO.println(world);

        snake.move();
        world.update();
        IO.println(world);

        snake.move(Direction.UP);
        world.update();
        IO.println(world);

        snake.move(Direction.UP);
        world.update();
        IO.println(world);

        snake.move(Direction.LEFT);
        world.update();
        IO.println(world);

        snake.move(Direction.UP);
        IO.println(snake);

        world.update();
        IO.println(world);


        snake.move(Direction.UP);
        world.update();
        IO.println(world);


        snake.move(Direction.UP);
        world.update();
        IO.println(world);

        snake.feed();
        snake.move(Direction.UP);
        world.update();
        IO.println(world);

        IO.println(snake);
    }
}
