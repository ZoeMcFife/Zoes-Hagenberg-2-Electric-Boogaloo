package geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import math.Vector2;
import math.Vector3;

public class Ellipse extends Polygon
{
    private double semiMajorAxis;
    private double semiMinorAxis;

    public Ellipse(double semiMajorAxis, double semiMinorAxis)
    {
        super();
        setSemiMajorAxis(semiMajorAxis);
        setSemiMinorAxis(semiMinorAxis);
    }

    public Ellipse(Vector2 position, double semiMajorAxis, double semiMinorAxis)
    {
        super(position);
        setSemiMajorAxis(semiMajorAxis);
        setSemiMinorAxis(semiMinorAxis);
    }

    public Ellipse(Vector2 position, double semiMajorAxis, double semiMinorAxis, Color fillColor)
    {
        super(position);
        setSemiMajorAxis(semiMajorAxis);
        setSemiMinorAxis(semiMinorAxis);
        setFillColor(fillColor);
    }

    @Override
    public double getArea()
    {
        return Math.PI * semiMajorAxis * semiMinorAxis;
    }

    @Override
    public double getPerimeter()
    {
        return Math.PI * (3 * (semiMajorAxis + semiMinorAxis) - Math.sqrt((3 * semiMajorAxis + semiMinorAxis) * (semiMajorAxis + 3 * semiMinorAxis)));
    }

    @Override
    public double[][] getCoordinates()
    {
        double vertexCount = 32;

        Vector3[] vertices = new Vector3[(int) vertexCount];

        for (int i = 0; i < vertexCount; i++)
        {
            double angle = Math.PI / vertexCount * i - Math.PI / 2;
            double radius = i % 2 == 0 ? semiMajorAxis : semiMinorAxis;
            double x = getPosition().x + radius * Math.cos(angle);
            double y = getPosition().y + radius * Math.sin(angle);

            vertices[i] = new Vector3(x, y);
        }

        return toCoordinates(transformed(vertices));
    }

    public double getSemiMajorAxis()
    {
        return semiMajorAxis;
    }

    public double getSemiMinorAxis()
    {
        return semiMinorAxis;
    }

    private void setSemiMajorAxis(double semiMajorAxis)
    {
        if (semiMajorAxis <= 0)
        {
            throw new IllegalArgumentException("Semi-major axis must be positive.");
        }

        this.semiMajorAxis = semiMajorAxis;
    }

    private void setSemiMinorAxis(double semiMinorAxis)
    {
        if (semiMinorAxis <= 0)
        {
            throw new IllegalArgumentException("Semi-minor axis must be positive.");
        }

        this.semiMinorAxis = semiMinorAxis;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Ellipse other = (Ellipse) obj;

        return Double.compare(getSemiMajorAxis(), other.getSemiMajorAxis()) == 0 &&
               Double.compare(getSemiMinorAxis(), other.getSemiMinorAxis()) == 0;
    }

    @Override
    public String toString()
    {
        return String.format("Ellipse(semiMajorAxis=%.2f, semiMinorAxis=%.2f, position=%s)", getSemiMajorAxis(), getSemiMinorAxis(), getPosition());
    }

    @Override
    public void draw(GraphicsContext gc)
    {
        super.draw(gc);

        double[][] coords = getCoordinates();

        gc.fillPolygon(coords[0], coords[1], coords.length);
        gc.strokePolygon(coords[0], coords[1], coords.length);    }
}
