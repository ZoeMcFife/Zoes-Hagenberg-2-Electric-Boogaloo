package geometry;

public class Point extends Polygon
{
    public Point()
    {
        super();
    }

    public Point(Vector2 position)
    {
        super(position);
    }

    @Override
    public double area()
    {
        return 0; // A point has no area
    }

    @Override
    public double perimeter()
    {
        return 0; // A point has no perimeter
    }

    @Override
    public String toString()
    {
        return String.format("Point at (%.2f, %.2f)", getPosition().x, getPosition().y);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Point other = (Point) obj;
        return getPosition().equals(other.getPosition());
    }

}
