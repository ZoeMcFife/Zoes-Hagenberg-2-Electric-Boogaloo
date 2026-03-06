package Geometry;

public class Point extends Polygon
{
    Point()
    {
        super();
    }

    Point(Vector2 position)
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


}
