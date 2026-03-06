package Geometry;

public class Line extends Polygon
{
    public Vector2 to;
    public Vector2 from;

    Line(Vector2 from, Vector2 to)
    {
        super();
        this.from = from;
        this.to = to;
        this.position = getMidPoint();
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
}
