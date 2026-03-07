package geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends Polygon implements Shape
{
    private double width;
    private double height;

    public Rectangle(double width, double height)
    {
        super();
        setWidth(width);
        setHeight(height);
    }

    public Rectangle(Vector2 position, double width, double height)
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

    @Override
    public String toString()
    {
        return String.format("Rectangle [position=%s, width=%.2f, height=%.2f]", getPosition(), getWidth(), getHeight());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rectangle rectangle = (Rectangle) o;

        return Double.compare(rectangle.getWidth(), getWidth()) == 0 &&
               Double.compare(rectangle.getHeight(), getHeight()) == 0;
    }

    @Override
    public void draw(GraphicsContext gc, Color color)
    {
        gc.setFill(color);
        gc.fillRect(getPosition().x - getWidth() / 2, getPosition().y - getHeight() / 2, getWidth(), getHeight());
    }
}
