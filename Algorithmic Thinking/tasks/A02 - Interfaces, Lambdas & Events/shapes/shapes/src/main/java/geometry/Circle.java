package geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import math.Vector2;
import math.Vector3;

public class Circle extends Polygon
{
    private double radius;

    public Circle(double radius)
    {
        super();
        setRadius(radius);
    }

    public Circle(Vector2 position, double radius)
    {
        super(position);
        setRadius(radius);
    }

    public Circle(Vector2 position, double radius, Color fillColor)
    {
        super(position);
        setRadius(radius);
        setFillColor(fillColor);
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
    public double getArea()
    {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter()
    {
        return 2 * Math.PI * radius;
    }

    @Override
    public double[][] getCoordinates()
    {
        double vertexCount = 32;

        Vector3[] vertices = new Vector3[(int) vertexCount];

        for (int i = 0; i < vertexCount; i++)
        {
            double angle = 2 * Math.PI * i / vertexCount;
            double x = getPosition().x + radius * Math.cos(angle);
            double y = getPosition().y + radius * Math.sin(angle);

            vertices[i] = new Vector3(x, y);
        }

        return toCoordinates(transformed(vertices));
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
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Circle other = (Circle) obj;
        return Double.compare(other.radius, radius) == 0;
    }

    @Override
    public String toString()
    {
        return String.format("Circle(Position: %s, Radius: %.2f)", getPosition(), getRadius());
    }

    @Override
    public void draw(GraphicsContext gc)
    {
        super.draw(gc);

        double[][] coords = getCoordinates();

        gc.fillPolygon(coords[0], coords[1], coords.length);
        gc.strokePolygon(coords[0], coords[1], coords.length);
    }
}
