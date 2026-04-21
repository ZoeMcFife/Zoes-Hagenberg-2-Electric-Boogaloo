package gay.fox;

public interface Playable
{
    String play();

    default String info()
    {
        return "Playing: " + play();
    }
}
