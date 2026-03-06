package Geometry;

public class Circle extends Polygon
{
    public double radius;

    Circle(double radius)
    {
        super();
        this.radius = radius;
    }

    Circle(Vector2 position, double radius)
    {
        super(position);
        this.radius = radius;
    }

    public void scale(double scale)
    {
        this.radius *= scale;
    }

    public boolean isBiggerThan(Circle other)
    {
        return this.radius > other.radius;
    }

    @Override
    public double area()
    {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter()
    {
        return 2 * Math.PI * radius;
    }

    @Override
    public String toString()
    {
        return String.format("Circle(Position: %s, Radius: %.2f)", position, radius);
    }
}
