package geometry;

import geometry.interfaces.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import math.Vector2;
import math.Vector3;

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

    public Rectangle(double minX, double minY, double width, double height, Color fillColor)
    {
        super(new Vector2(minX + width / 2, minY + height / 2));
        setWidth(width);
        setHeight(height);
        setFillColor(fillColor);
    }

    public Rectangle(double minX, double minY, double width, double height)
    {
        super(new Vector2(minX + width / 2, minY + height / 2));
        setWidth(width);
        setHeight(height);
    }

    public double getDiagonalLength()
    {
        return Math.sqrt(getWidth() * getWidth() + getHeight() * getHeight());
    }

    @Override
    public double getArea()
    {
        return getWidth() * getHeight();
    }

    @Override
    public double getPerimeter()
    {
        return 2 * (getWidth() + getHeight());
    }

    @Override
    public double[][] getCoordinates()
    {
        Vector3[] vertices = new Vector3[4];

        vertices[0] = new Vector3(getPosition().x - getWidth() / 2, getPosition().y - getHeight() / 2);
        vertices[1] = new Vector3(getPosition().x + getWidth() / 2, getPosition().y - getHeight() / 2);
        vertices[2] = new Vector3(getPosition().x + getWidth() / 2, getPosition().y + getHeight() / 2);
        vertices[3] = new Vector3(getPosition().x - getWidth() / 2, getPosition().y + getHeight() / 2);

        return toCoordinates(transformed(vertices));
    }

    public double getWidth()
    {
        return width;
    }

    private void setWidth(double width)
    {
        if (width < 0)
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
        if (height < 0)
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
    public void draw(GraphicsContext gc)
    {
        super.draw(gc);

        double[][] coords = getCoordinates();

        gc.fillPolygon(coords[0], coords[1], coords[0].length);
        gc.strokePolygon(coords[0], coords[1], coords[0].length);
    }
}
