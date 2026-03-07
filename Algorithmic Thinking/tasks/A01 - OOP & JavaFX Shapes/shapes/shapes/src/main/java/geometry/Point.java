package geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

    @Override
    public void draw(GraphicsContext gc, Color color)
    {
        double radius = 2.5;

        gc.setFill(color);
        gc.fillOval(getPosition().x - radius / 2, getPosition().y - radius / 2, radius, radius);
    }
}
