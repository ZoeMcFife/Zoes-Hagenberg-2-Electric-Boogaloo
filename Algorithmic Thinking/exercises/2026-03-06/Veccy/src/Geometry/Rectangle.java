package Geometry;

public class Rectangle extends Polygon implements Shape
{
    public double width;
    public double height;

    Rectangle(double width, double height)
    {
        super();
        this.width = width;
        this.height = height;
    }

    Rectangle(Vector2 position, double width, double height)
    {
        super(position);
        this.width = width;
        this.height = height;
    }

    public double getDiagonalLength()
    {
        return Math.sqrt(width * width + height * height);
    }

    @Override
    public double area()
    {
        return width * height;
    }

    @Override
    public double perimeter()
    {
        return 2 * (width + height);
    }
}
