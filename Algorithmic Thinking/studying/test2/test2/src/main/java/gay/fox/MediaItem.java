package gay.fox;

public abstract class MediaItem
{
    protected String title;
    protected int duration;

    MediaItem(String title, int duration)
    {
        this.title = title;
        this.duration = duration;
    }

    abstract boolean isLong();
}
