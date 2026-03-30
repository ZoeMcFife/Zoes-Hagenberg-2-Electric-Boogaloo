package geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import math.Vector2;
import math.Vector3;

public class Triangle extends Polygon
{
    private final Vector3[] originalVertices;

    private Vector2 vertexADelta;
    private Vector2 vertexBDelta;
    private Vector2 vertexCDelta;


    public Triangle(Vector2 vertexA, Vector2 vertexB, Vector2 vertexC, Color fillColor)
    {
        originalVertices = new Vector3[]
                {
                    vertexA.toVector3(), vertexB.toVector3(), vertexC.toVector3()
                };

        // calculate the centroid of the triangle to set as the position
        super(new Vector2((vertexA.x + vertexB.x + vertexC.x) / 3, (vertexA.y + vertexB.y + vertexC.y) / 3));

        vertexADelta = vertexA.subtract(getPosition());
        vertexBDelta = vertexB.subtract(getPosition());
        vertexCDelta = vertexC.subtract(getPosition());

        setFillColor(fillColor);
    }

    @Override
    public double getArea()
    {
        double s = getPerimeter() / 2;

        return Math.sqrt(s * (s - getA()) * (s - getB()) * (s - getC()));
    }

    @Override
    public double getPerimeter()
    {
        return getA() + getB() + getC();
    }

    @Override
    public double[][] getCoordinates()
    {
        Vector3[] vertices = new Vector3[3];

        vertices[0] = getPosition().add(vertexADelta).toVector3();
        vertices[1] = getPosition().add(vertexBDelta).toVector3();
        vertices[2] = getPosition().add(vertexCDelta).toVector3();

        return toCoordinates(transformed(vertices));
    }

    public double getA()
    {
        return originalVertices[1].distanceTo(originalVertices[2]);
    }

    public double getB()
    {
        return originalVertices[0].distanceTo(originalVertices[2]);
    }

    public double getC()
    {
        return originalVertices[0].distanceTo(originalVertices[1]);
    }
    @Override
    public String toString()
    {
        return String.format("Triangle [position=%s, a=%.2f, b=%.2f, c=%.2f]", getPosition(), getA(), getB(), getC());
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
