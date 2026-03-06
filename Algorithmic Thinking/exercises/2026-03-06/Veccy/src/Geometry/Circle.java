package Geometry;

public class Circle extends Polygon
{
    private double radius;

    Circle(double radius)
    {
        super();
        setRadius(radius);
    }

    Circle(Vector2 position, double radius)
    {
        super(position);
        setRadius(radius);
    }

    public Circle scale(double scale)
    {
        return new Circle(getRadius() * scale);
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

    public double getRadius()
    {
        return radius;
    }

    private void setRadius(double radius)
    {
        if (radius <= 0)
        {
            throw new IllegalArgumentException("Radius must be positive.");
        }

        this.radius = radius;
    }

    @Override
    public String toString()
    {
        return String.format("Circle(Position: %s, Radius: %.2f)", getPosition(), radius);
    }
}
