package gay.fox.snake.snake;

public class TestingSnake
{
    public static void main( String[] args )
    {
        World world = new World(5, true);
        Snake snake = new Snake(world);
        world.addSnake(snake);

        world.update();
        IO.println(world);

        world.update();
        IO.println(world);

        world.update();
        IO.println(world);
    }
}
