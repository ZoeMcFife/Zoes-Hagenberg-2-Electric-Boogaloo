package Geometry;

public class Line extends Polygon
{
    private Vector2 to;
    private Vector2 from;

    Line(Vector2 from, Vector2 to)
    {
        super();
        this.from = from;
        this.to = to;
        setPosition(getMidPoint());
    }

    public Vector2 getMidPoint()
    {
        return new Vector2((from.x + to.x) / 2, (from.y + to.y) / 2);
    }

    public double getLength()
    {
        return Math.sqrt(Math.pow(to.x - from.x, 2) + Math.pow(to.y - from.y, 2));
    }

    @Override
    public double area()
    {
        return 0;
    }

    @Override
    public double perimeter()
    {
        return getLength();
    }

    public Vector2 getTo()
    {
        return to;
    }

    private void setTo(Vector2 to)
    {
        this.to = to;
        this.setPosition(getMidPoint());
    }

    public Vector2 getFrom()
    {
        return from;
    }

    private void setFrom(Vector2 from)
    {
        this.from = from;
        this.setPosition(getMidPoint());
    }
}
