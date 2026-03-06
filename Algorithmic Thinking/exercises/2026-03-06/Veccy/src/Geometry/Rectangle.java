package Geometry;

public class Rectangle extends Polygon implements Shape
{
    private double width;
    private double height;

    Rectangle(double width, double height)
    {
        super();
        setWidth(width);
        setHeight(height);
    }

    Rectangle(Vector2 position, double width, double height)
    {
        super(position);
        setWidth(width);
        setHeight(height);
    }

    public double getDiagonalLength()
    {
        return Math.sqrt(getWidth() * getWidth() + getHeight() * getHeight());
    }

    @Override
    public double area()
    {
        return getWidth() * getHeight();
    }

    @Override
    public double perimeter()
    {
        return 2 * (getWidth() + getHeight());
    }

    public double getWidth()
    {
        return width;
    }

    private void setWidth(double width)
    {
        if (width <= 0)
        {
            throw new IllegalArgumentException("Width must be positive.");
        }

        this.width = width;
    }

    public double getHeight()
    {
        return height;
    }

    private void setHeight(double height)
    {
        if (height <= 0)
        {
            throw new IllegalArgumentException("Height must be positive.");
        }

        this.height = height;
    }
}
