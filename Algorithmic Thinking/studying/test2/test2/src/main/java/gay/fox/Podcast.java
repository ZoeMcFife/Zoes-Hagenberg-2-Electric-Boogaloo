package gay.fox;

public class Podcast extends MediaItem implements Playable
{
    private final String host;

    public Podcast(String title, int duration, String host)
    {
        super(title, duration);
        this.host = host;
    }

    public String getHost()
    {
        return host;
    }

    @Override
    boolean isLong()
    {
        return duration > 60;
    }

    @Override
    public String play()
    {
        return title + " hosted by " + getHost();
    }
}
